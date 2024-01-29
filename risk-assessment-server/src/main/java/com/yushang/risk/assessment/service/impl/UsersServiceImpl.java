package com.yushang.risk.assessment.service.impl;

import com.yushang.risk.assessment.dao.RoleDao;
import com.yushang.risk.assessment.dao.UserRoleDao;
import com.yushang.risk.assessment.dao.UsersDao;
import com.yushang.risk.assessment.domain.dto.RequestDataInfo;
import com.yushang.risk.domain.entity.User;
import com.yushang.risk.assessment.domain.vo.request.LoginReq;
import com.yushang.risk.assessment.domain.vo.request.RegisterReq;
import com.yushang.risk.assessment.domain.vo.request.UpdatePassReq;
import com.yushang.risk.assessment.domain.vo.request.UserReq;
import com.yushang.risk.assessment.domain.vo.response.LoginUserResp;
import com.yushang.risk.assessment.domain.vo.response.UserResp;
import com.yushang.risk.assessment.service.LoginService;
import com.yushang.risk.assessment.service.UsersService;
import com.yushang.risk.assessment.service.adapter.UserAdapter;
import com.yushang.risk.common.annotation.OptLog;
import com.yushang.risk.common.constant.RedisKey;
import com.yushang.risk.common.exception.CommonErrorEnum;
import com.yushang.risk.common.exception.SystemException;
import com.yushang.risk.common.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * @Author：zlp @Package：com.yushang.risk.assessment.service.impl @Project：risk_assessment
 *
 * @name：UsersServiceImpl @Date：2023/12/21 13:54 @Filename：UsersServiceImpl
 */
@Service
@Slf4j
public class UsersServiceImpl implements UsersService {

  @Resource private UsersDao usersDao;
  @Resource private UserRoleDao userRoleDao;
  @Resource private RoleDao roleDao;
  @Resource private JwtUtils jwtUtils;
  @Resource private LoginService loginService;
  /** 用户随机码放入Redis的过期时间 */
  public static final int USER_CODE_EXPIRE_TIME = 2;

  /**
   * 获取随机验证码(放到Redis中存储)
   *
   * @param ip
   * @return
   */
  @Override
  public Object[] getCode(String ip) {
    // 把旧的验证码删除,生成新的返回
    RedisUtils.del(RedisKey.getKey(RedisKey.USER_REDIS_CODE_PREFIX, ip));
    // 这个根据自己的需要设置对应的参数来实现个性化
    // 返回的数组第一个参数是生成的验证码，第二个参数是生成的图片
    Object[] objs =
        VerifyCodeUtil.newBuilder()
            .setWidth(120) // 设置图片的宽度
            .setHeight(35) // 设置图片的高度
            .setSize(5) // 设置字符的个数
            .setLines(3) // 设置干扰线的条数
            .setFontSize(30) // 设置字体的大小
            .setTilt(true) // 设置是否需要倾斜
            .setBackgroundColor(Color.WHITE) // 设置验证码的背景颜色
            .build() // 构建VerifyUtil项目
            .createImage(); // 生成图片
    String code = (String) objs[0];
    log.info("code:{}", code);
    RedisUtils.set(
        RedisKey.getKey(RedisKey.USER_REDIS_CODE_PREFIX, ip),
        code,
        USER_CODE_EXPIRE_TIME,
        TimeUnit.MINUTES);
    return objs;
  }

  /**
   * 注册
   *
   * @param registerReq
   * @param request
   */
  @Override
  public void register(RegisterReq registerReq, HttpServletRequest request) {
    // 校验验证码
    this.verifyCode(registerReq.getCode(), IpUtils.getClientIpAddress(request));
    // 用户名不能重复
    User user = usersDao.getByField(User::getUsername, registerReq.getUserName());
    AssertUtils.isEmpty(user, "用户名已经存在了");
    // 邀请码
    this.verifyInvitationCode(registerReq.getInvitationCode());
    // TODO 保存到申请表中,由后台管理员处理申请
    // 存储数据库
    // TODO 只有后台管理员批准之后,才能添加到数据库,下面要注释
    // 第一阶段解密
    String password = AesUtil.decryptPassword(registerReq.getPassword());
    try {
      // 第二阶段加密
      password = PasswordUtils.encryptPassword(password);
    } catch (Exception e) {
      throw new SystemException(CommonErrorEnum.SYSTEM_ERROR.getErrorCode(), "密码加密失败,抛出异常");
    }
    // 生成邀请码
    String myInvitationCode = generateRandomString();
    User saveUser = UserAdapter.buildSaveUser(registerReq, password, myInvitationCode);
    usersDao.save(saveUser);
  }

  /**
   * 登录
   *
   * @param loginReq
   * @param request
   * @return
   */
  @Override
  @OptLog(target = OptLog.Target.LOGIN)
  public LoginUserResp login(LoginReq loginReq, HttpServletRequest request) {
    // 校验验证码
    this.verifyCode(loginReq.getCode(), IpUtils.getClientIpAddress(request));
    // 校验账户基本信息
    User user = usersDao.getNormalByUsername(loginReq.getUserName());
    AssertUtils.assertNotNull(user, "用户已被封禁，请联系管理员");
    String password;
    try {
      password = PasswordUtils.decryptPassword(user.getPassword());
    } catch (Exception e) {
      throw new SystemException(CommonErrorEnum.SYSTEM_ERROR.getErrorCode(), "密码解密异常");
    }
    AssertUtils.equal(AesUtil.decryptPassword(loginReq.getPassword()), password, "密码错误");
    // 修改登录时间
    usersDao.updateLoginTime(user.getId());
    // 获取token
    String token = loginService.login(user.getId());
    RequestDataInfo dataInfo = new RequestDataInfo();
    dataInfo.setUid(user.getId());
    RequestHolder.set(dataInfo);
    return UserAdapter.buildLoginUserResp(user, token);
  }

  /** 用户退出登录 */
  @Override
  @OptLog(target = OptLog.Target.EXIT)
  public void exit() {
    // 清除token
    String key = RedisKey.getKey(RedisKey.USER_REDIS_TOKEN_PREFIX, RequestHolder.get().getUid());
    RedisUtils.del(key);
    // 记录退出时间
    User user = new User();
    user.setId(RequestHolder.get().getUid());
    user.setExitTime(LocalDateTime.now());
    usersDao.updateById(user);
  }

  /**
   * 获取用户信息
   *
   * @param uid
   * @return
   */
  @Override
  public UserResp getUserInfo(Integer uid) {
    User user = usersDao.getById(uid);
    UserResp userResp = new UserResp();
    BeanUtils.copyProperties(user, userResp);
    return userResp;
  }

  /**
   * 修改密码
   *
   * @param passReq
   * @param request
   */
  @Override
  public void updatePassword(UpdatePassReq passReq, HttpServletRequest request) {
    // 与前端协调密码的加密处理
    // 校验验证码
    this.verifyCode(passReq.getCode(), IpUtils.getClientIpAddress(request));
    // 修改密码
    User user = usersDao.getById(RequestHolder.get().getUid());
    String password;
    try {
      password = PasswordUtils.decryptPassword(user.getPassword());
    } catch (Exception e) {
      throw new SystemException(CommonErrorEnum.SYSTEM_ERROR.getErrorCode(), "密码解密异常");
    }
    AssertUtils.equal(AesUtil.decryptPassword(passReq.getPassword()), password, "密码错误");
    String newPass;
    try {
      newPass = PasswordUtils.encryptPassword(AesUtil.decryptPassword(passReq.getNewPassword()));
    } catch (Exception e) {
      throw new SystemException(CommonErrorEnum.SYSTEM_ERROR.getErrorCode(), "新密码加密异常");
    }
    AssertUtils.isTrue(usersDao.updatePass(user.getId(), newPass), "修改密码失败");
    // 清除token
    RedisUtils.del(RedisKey.getKey(RedisKey.USER_REDIS_TOKEN_PREFIX, user.getId()));
  }

  /**
   * 修改用户个人信息
   *
   * @param userReq
   */
  @Override
  public void changeInfo(UserReq userReq) {
    User user = new User();
    user.setId(RequestHolder.get().getUid());
    BeanUtils.copyProperties(userReq, user);
    usersDao.updateById(user);
  }

  /**
   * 随机生成邀请码
   *
   * @return
   */
  private String generateRandomString() {
    final String CHAR_LOWER = "abcdefghijklmnopqrstuvwxyz";
    final String CHAR_UPPER = CHAR_LOWER.toUpperCase();
    final String DIGITS = "0123456789";

    final String ALPHA_NUMERIC = CHAR_LOWER + CHAR_UPPER + DIGITS;
    SecureRandom random = new SecureRandom();
    StringBuilder sb = new StringBuilder(6);

    for (int i = 0; i < 6; i++) {
      int randomIndex = random.nextInt(ALPHA_NUMERIC.length());
      sb.append(ALPHA_NUMERIC.charAt(randomIndex));
    }

    return sb.toString();
  }

  /**
   * 校验邀请码
   *
   * @param invitationCode
   */
  private void verifyInvitationCode(String invitationCode) {
    User user = usersDao.getByField(User::getInvitationCode, invitationCode);
    AssertUtils.isNotEmpty(user, "无效的邀请码");

    /*  List<UserRole> userRoleList = userRoleDao.getByUserId(user.getId());
    Role admin = roleDao.getByField(Role::getName, UserRoleEnum.ADMIN.getDesc());

    List<UserRole> collect =
        userRoleList.stream()
            .filter(userRole -> userRole.getRoleId().equals(admin.getId()))
            .collect(Collectors.toList());
    AssertUtils.isNotEmpty(collect, "无效的邀请码"); */
  }

  /**
   * 校验验证码
   *
   * @param code
   * @param ip
   * @return
   */
  private void verifyCode(String code, String ip) {
    String s = RedisUtils.getStr(RedisKey.getKey(RedisKey.USER_REDIS_CODE_PREFIX, ip));
    AssertUtils.isNotEmpty(s, "验证码已过期，请重新输入。");
    AssertUtils.equal(code.toLowerCase(Locale.ROOT), s.toLowerCase(Locale.ROOT), "验证码错误，请重新输入。");
  }
}

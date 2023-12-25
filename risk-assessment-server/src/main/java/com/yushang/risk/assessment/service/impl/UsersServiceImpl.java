package com.yushang.risk.assessment.service.impl;

import com.yushang.risk.assessment.dao.RoleDao;
import com.yushang.risk.assessment.dao.UserRoleDao;
import com.yushang.risk.assessment.dao.UsersDao;
import com.yushang.risk.assessment.domain.entity.Role;
import com.yushang.risk.assessment.domain.entity.User;
import com.yushang.risk.assessment.domain.entity.UserRole;
import com.yushang.risk.assessment.domain.vo.request.LoginReq;
import com.yushang.risk.assessment.domain.vo.request.RegisterReq;
import com.yushang.risk.assessment.domain.vo.response.LoginUserResp;
import com.yushang.risk.assessment.service.LoginService;
import com.yushang.risk.assessment.service.UsersService;
import com.yushang.risk.assessment.service.adapter.UserAdapter;
import com.yushang.risk.common.constant.RedisKey;
import com.yushang.risk.common.enums.UserRoleEnum;
import com.yushang.risk.common.exception.BusinessException;
import com.yushang.risk.common.exception.CommonErrorEnum;
import com.yushang.risk.common.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

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
   * @param sessionId
   * @return
   */
  @Override
  public Object[] getCode(String sessionId) {
    // 把旧的验证码删除,生成新的返回
    RedisUtils.del(RedisKey.getKey(RedisKey.USER_REDIS_CODE_PREFIX, sessionId));
    // 这个根据自己的需要设置对应的参数来实现个性化
    // 返回的数组第一个参数是生成的验证码，第二个参数是生成的图片
    Object[] objs =
        VerifyCodeUtil.newBuilder()
            .setWidth(120) // 设置图片的宽度
            .setHeight(35) // 设置图片的高度
            .setSize(6) // 设置字符的个数
            .setLines(10) // 设置干扰线的条数
            .setFontSize(25) // 设置字体的大小
            .setTilt(true) // 设置是否需要倾斜
            .setBackgroundColor(Color.WHITE) // 设置验证码的背景颜色
            .build() // 构建VerifyUtil项目
            .createImage(); // 生成图片
    String code = (String) objs[0];
    log.info("code:{}", code);
    RedisUtils.set(
        RedisKey.getKey(RedisKey.USER_REDIS_CODE_PREFIX, sessionId),
        code,
        USER_CODE_EXPIRE_TIME,
        TimeUnit.MINUTES);
    return objs;
  }

  /**
   * 注册
   *
   * @param registerReq
   * @param session
   */
  @Override
  public void register(RegisterReq registerReq, HttpSession session) {
    // 校验验证码
    this.verifyCode(registerReq.getCode(), session.getId());
    // 用户名不能重复
    User user = usersDao.getByField(User::getUsername, registerReq.getUserName());
    AssertUtils.isEmpty(user, "用户名已经存在了");
    // 邀请码
    this.verifyInvitationCode(registerReq.getInvitationCode());
    // 存储数据库
    String newPassword;
    try {
      newPassword = PasswordUtils.encryptPassword(registerReq.getPassword());
    } catch (Exception e) {
      throw new BusinessException(CommonErrorEnum.SYSTEM_ERROR.getErrorCode(), "密码加密失败,抛出异常");
    }
    User saveUser = UserAdapter.buildSaveUser(registerReq, newPassword);
    usersDao.save(saveUser);
  }

  /**
   * 登录
   *
   * @param loginReq
   * @param session
   * @return
   */
  @Override
  public LoginUserResp login(LoginReq loginReq, HttpSession session) {
    // 校验验证码
    this.verifyCode(loginReq.getCode(), session.getId());
    // 校验账户基本信息
    User user = usersDao.getByField(User::getUsername, loginReq.getUserName());
    String password;
    try {
      password = PasswordUtils.decryptPassword(user.getPassword());
    } catch (Exception e) {
      throw new BusinessException(CommonErrorEnum.SYSTEM_ERROR.getErrorCode(), "密码解密异常");
    }
    AssertUtils.equal(loginReq.getPassword(), password, "密码错误");
    // 修改登录时间
    usersDao.updateLoginTime(user.getId());
    // 获取token
    String token = loginService.login(user.getId());
    return UserAdapter.buildLoginUserResp(user, token);
  }

  /** 用户退出登录 */
  @Override
  public void exit() {
    // 清除token
    String key = RedisKey.getKey(RedisKey.USER_REDIS_TOKEN_PREFIX, RequestHolder.get().getUid());
    RedisUtils.del(key);
  }

  /**
   * 校验邀请码
   *
   * @param invitationCode
   */
  private void verifyInvitationCode(String invitationCode) {
    User user = usersDao.getByField(User::getInvitationCode, invitationCode);
    AssertUtils.isNotEmpty(user, "无效的邀请码");

    List<UserRole> userRoleList = userRoleDao.getByUserId(user.getId());
    Role admin = roleDao.getByField(Role::getName, UserRoleEnum.ADMIN.getDesc());

    List<UserRole> collect =
        userRoleList.stream()
            .filter(userRole -> userRole.getRoleId().equals(admin.getId()))
            .collect(Collectors.toList());
    AssertUtils.isNotEmpty(collect, "无效的邀请码");
  }

  /**
   * 校验验证码
   *
   * @param code
   * @param sessionId
   * @return
   */
  private void verifyCode(String code, String sessionId) {
    String s = RedisUtils.getStr(RedisKey.getKey(RedisKey.USER_REDIS_CODE_PREFIX, sessionId));
    AssertUtils.isNotEmpty(s, "验证码已过期，请重新输入。");
    AssertUtils.equal(code, s, "验证码错误，请重新输入。");
  }
}

package com.yushang.risk.admin.service.impl;

import cn.hutool.core.collection.ListUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yushang.risk.admin.dao.AccountDao;
import com.yushang.risk.admin.dao.RegisterApplyDao;
import com.yushang.risk.admin.dao.UsersDao;
import com.yushang.risk.admin.domain.vo.request.*;
import com.yushang.risk.admin.domain.vo.response.PageBaseResp;
import com.yushang.risk.admin.domain.vo.response.UserAddResp;
import com.yushang.risk.admin.domain.vo.response.UserPageResp;
import com.yushang.risk.admin.service.UserService;
import com.yushang.risk.admin.service.adapter.UserAdapter;
import com.yushang.risk.common.constant.RedisKey;
import com.yushang.risk.common.exception.BusinessException;
import com.yushang.risk.common.exception.CommonErrorEnum;
import com.yushang.risk.common.exception.SystemException;
import com.yushang.risk.common.util.*;
import com.yushang.risk.domain.entity.Account;
import com.yushang.risk.domain.entity.RegisterApply;
import com.yushang.risk.domain.entity.User;
import com.yushang.risk.domain.enums.UserStatusEnum;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.security.SecureRandom;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @Author：zlp @Package：com.yushang.risk.admin.service.impl @Project：risk_assessment
 *
 * @name：UserServiceImpl @Date：2024/1/11 10:20 @Filename：UserServiceImpl
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {
  @Resource private UsersDao usersDao;
  @Resource private RegisterApplyDao registerApplyDao;
  @Resource private AccountDao accountDao;

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
            .setSize(4) // 设置字符的个数
            .setLines(2) // 设置干扰线的条数
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
   * 获取用户列表(条件+分页)
   *
   * @param userPageReq
   * @return
   */
  @Override
  public PageBaseResp<UserPageResp> getUserList(PageBaseReq<UserPageReq> userPageReq) {
    Page<User> page = userPageReq.plusPage();
    Page<User> userPage;
    try {
      userPage = usersDao.getUserListByPage(page, userPageReq.getData());
    } catch (Exception e) {
      log.error("获取用户列表失败", e);
      // 异常处理逻辑，具体实现根据实际情况调整
      return PageBaseResp.init(new Page<>(), ListUtil.empty());
    }
    List<UserPageResp> userResps =
        userPage.getRecords().stream()
            .map(
                user -> {
                  UserPageResp resp = new UserPageResp();
                  BeanUtils.copyProperties(user, resp);
                  return resp;
                })
            .collect(Collectors.toList());

    return PageBaseResp.init(userPage, userResps);
  }

  /**
   * 新增用户
   *
   * @param userReq 用户请求对象，包含新建用户的信息
   * @return UserAddResp 用户添加响应对象，包含用户名和设置的密码
   */
  @SneakyThrows
  @Override
  public UserAddResp addUser(UserReq userReq) {
    // 检查用户名在用户、注册申请、账号中是否已存在
    User userName = usersDao.getByField(User::getUsername, userReq.getUsername());
    AssertUtils.isEmpty(userName, "用户名已经存在了");

    RegisterApply registerApply =
        registerApplyDao.getByField(RegisterApply::getUsername, userReq.getUsername());
    AssertUtils.isEmpty(registerApply, "用户名已经存在了");

    Account account = accountDao.getByField(Account::getUsername, userReq.getUsername());
    AssertUtils.isEmpty(account, "用户名已经存在了");

    User user = UserAdapter.buildAddUser(userReq);
    // 为用户生成并设置随机密码
    String pass = generateRandomString();
    String newPass = PasswordUtils.encryptPassword(pass);
    user.setPassword(newPass);
    usersDao.save(user);

    // 返回包含用户名和设置的密码的响应对象
    return UserAddResp.builder().userName(userReq.getUsername()).password(pass).build();
  }

  /**
   * 修改用户
   *
   * @param userReq
   */
  @Override
  public void updateUser(UserReq userReq) {
    User user = new User();
    BeanUtils.copyProperties(userReq, user);
    usersDao.updateById(user);
  }

  /**
   * 修改用户状态
   *
   * @param userStaReq 用户状态请求对象，包含用户ID和待更新的状态
   */
  @Override
  public void updateUserStatus(UserStaReq userStaReq) {
    // 获取传入的状态值
    Integer status = userStaReq.getStatus();
    // 检查状态是否为正常和禁用状态的非法组合
    if (status.equals(UserStatusEnum.NORMAL.getCode())
        && status.equals(UserStatusEnum.BAN.getCode())) {
      // 如果是非法组合，抛出业务异常
      throw new BusinessException("修改状态异常");
    }
    // 更新用户状态
    usersDao.updateById(
        User.builder()
            // 用户ID
            .id(userStaReq.getId())
            // 更新后的状态
            .status(String.valueOf(userStaReq.getStatus()))
            .build());
  }

  /**
   * 修改密码
   *
   * @param passReq 包含密码修改请求信息的对象，包括旧密码的验证码、旧密码、新密码。
   * @param request HttpServletRequest对象，用于获取客户端IP地址进行验证码校验。
   */
  @Override
  public void chPass(UpdatePassReq passReq, HttpServletRequest request) {
    // 校验用户输入的验证码是否正确
    this.verifyCode(passReq.getCode(), IpUtils.getClientIpAddress(request));

    // 获取当前登录用户信息
    Account user = accountDao.getById(RequestHolder.get().getUid());
    String password;
    try {
      // 解密用户当前密码进行验证
      password = PasswordUtils.decryptPassword(user.getPassword());
    } catch (Exception e) {
      // 密码解密异常处理
      throw new SystemException(CommonErrorEnum.SYSTEM_ERROR.getErrorCode(), "密码解密异常");
    }

    // 验证旧密码是否正确
    AssertUtils.equal(AesUtil.decryptPassword(passReq.getPassword()), password, "密码错误");

    String newPass;
    try {
      // 对新密码进行加密处理
      newPass = PasswordUtils.encryptPassword(AesUtil.decryptPassword(passReq.getNewPassword()));
    } catch (Exception e) {
      // 新密码加密异常处理
      throw new SystemException(CommonErrorEnum.SYSTEM_ERROR.getErrorCode(), "新密码加密异常");
    }

    // 更新数据库中的用户密码
    AssertUtils.isTrue(accountDao.updatePass(user.getId(), newPass), "修改密码失败");

    // 修改密码成功后，清除用户token，强制用户重新登录
    RedisUtils.del(RedisKey.getKey(RedisKey.USER_REDIS_TOKEN_PREFIX, user.getId()));
  }

  private static final String CHARACTERS =
      "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
  /**
   * 随机生成初始化密码
   *
   * @return
   */
  public static String generateRandomString() {
    // 使用正则验证randomString
    Pattern pattern = Pattern.compile("^[A-Z][a-zA-Z0-9]{7,15}$");
    StringBuilder randomString;
    do {
      int length = 8 + new SecureRandom().nextInt(8);
      randomString = new StringBuilder(length);
      SecureRandom random = new SecureRandom();
      for (int i = 0; i < length; i++) {
        int randomIndex = random.nextInt(CHARACTERS.length());
        char randomChar = CHARACTERS.charAt(randomIndex);
        randomString.append(randomChar);
      }
    } while (!pattern.matcher(randomString).matches());
    return randomString.toString();
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

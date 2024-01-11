package com.yushang.risk.admin.service.impl;

import com.yushang.risk.admin.dao.UserRoleDao;
import com.yushang.risk.admin.dao.UsersDao;
import com.yushang.risk.admin.domain.dto.RequestDataInfo;
import com.yushang.risk.admin.domain.entity.User;
import com.yushang.risk.admin.domain.entity.UserRole;
import com.yushang.risk.admin.domain.enums.UserRoleEnum;
import com.yushang.risk.admin.domain.vo.request.LoginReq;
import com.yushang.risk.admin.domain.vo.response.LoginUserResp;
import com.yushang.risk.admin.service.LoginService;
import com.yushang.risk.admin.service.UserService;
import com.yushang.risk.admin.service.adapter.UserAdapter;
import com.yushang.risk.common.constant.RedisKey;
import com.yushang.risk.common.exception.CommonErrorEnum;
import com.yushang.risk.common.exception.SystemException;
import com.yushang.risk.common.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * @Author：zlp @Package：com.yushang.risk.admin.service.impl @Project：risk_assessment
 *
 * @name：UserServiceImpl @Date：2024/1/11 10:20 @Filename：UserServiceImpl
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {
  @Resource private UsersDao usersDao;
  @Resource private UserRoleDao userRoleDao;
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
   * 登录
   *
   * @param loginReq
   * @param request
   * @return
   */
  @Override
  // TODO  @OptLog(target = OptLog.Target.LOGIN)
  public LoginUserResp login(LoginReq loginReq, HttpServletRequest request) {
    // 校验验证码
    this.verifyCode(loginReq.getCode(), IpUtils.getClientIpAddress(request));
    // 校验账户基本信息
    User user = usersDao.getNormalByUsername(loginReq.getUserName());
    AssertUtils.assertNotNull(user, "用户已被封禁，请联系管理员");
    // 查询是否具有管理员权限
    UserRole userRole = userRoleDao.getByUserIdAndRole(user.getId(), UserRoleEnum.ADMIN.getCode());
    AssertUtils.assertNotNull(userRole, "用户无管理员权限");
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

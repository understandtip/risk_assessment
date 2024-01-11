package com.yushang.risk.admin.service;

import com.yushang.risk.admin.domain.vo.request.LoginReq;
import com.yushang.risk.admin.domain.vo.response.LoginUserResp;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户 服务类
 *
 * @author zlp
 * @since 2024-01-11
 */
public interface UserService {
  /**
   * 获取验证码
   *
   * @param ip
   * @return
   */
  Object[] getCode(String ip);

  /**
   * 登录
   *
   * @param loginReq
   * @param request
   * @return
   */
  LoginUserResp login(LoginReq loginReq, HttpServletRequest request);
}

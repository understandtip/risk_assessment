package com.yushang.risk.assessment.service;

import com.yushang.risk.assessment.domain.vo.request.LoginReq;
import com.yushang.risk.assessment.domain.vo.request.RegisterReq;
import com.yushang.risk.assessment.domain.vo.response.LoginUserResp;

import javax.servlet.http.HttpSession;

/**
 * 用户 服务类
 *
 * @author zlp
 * @since 2023-12-21
 */
public interface UsersService {
  /**
   * 获取随机验证码(放到Redis中存储)
   *
   * @param sessionId
   * @return
   */
  Object[] getCode(String sessionId);

  /**
   * 注册
   *
   * @param registerReq
   * @param session
   */
  void register(RegisterReq registerReq, HttpSession session);

  /**
   * 登录
   *
   * @param loginReq
   * @param session
   * @return
   */
  LoginUserResp login(LoginReq loginReq, HttpSession session);

  /** 用户退出登录 */
  void exit();
}

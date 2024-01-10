package com.yushang.risk.assessment.service;

import com.yushang.risk.assessment.domain.vo.request.LoginReq;
import com.yushang.risk.assessment.domain.vo.request.RegisterReq;
import com.yushang.risk.assessment.domain.vo.request.UpdatePassReq;
import com.yushang.risk.assessment.domain.vo.request.UserReq;
import com.yushang.risk.assessment.domain.vo.response.LoginUserResp;
import com.yushang.risk.assessment.domain.vo.response.UserResp;

import javax.servlet.http.HttpServletRequest;

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
   * @param ip
   * @return
   */
  Object[] getCode(String ip);

  /**
   * 注册
   *
   * @param registerReq
   * @param request
   */
  void register(RegisterReq registerReq, HttpServletRequest request);

  /**
   * 登录
   *
   * @param loginReq
   * @param request
   * @return
   */
  LoginUserResp login(LoginReq loginReq, HttpServletRequest request);

  /** 用户退出登录 */
  void exit();

  /**
   * 获取用户信息
   *
   * @param uid
   * @return
   */
  UserResp getUserInfo(Integer uid);

  /**
   * 修改密码
   *
   * @param passReq
   * @param request
   */
  void updatePassword(UpdatePassReq passReq, HttpServletRequest request);

  /**
   * 修改用户个人信息
   *
   * @param userReq
   */
  void changeInfo(UserReq userReq);
}

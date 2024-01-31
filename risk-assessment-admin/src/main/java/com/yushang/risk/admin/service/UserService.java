package com.yushang.risk.admin.service;

import com.yushang.risk.admin.domain.vo.request.*;
import com.yushang.risk.admin.domain.vo.response.LoginUserResp;
import com.yushang.risk.admin.domain.vo.response.PageBaseResp;
import com.yushang.risk.admin.domain.vo.response.UserAddResp;
import com.yushang.risk.admin.domain.vo.response.UserResp;

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
   * 获取用户列表(条件+分页)
   *
   * @param userPageReq
   * @return
   */
  PageBaseResp<UserResp> getUserList(PageBaseReq<UserPageReq> userPageReq);

  /**
   * 新增用户
   *
   * @param userReq
   * @return
   */
  UserAddResp addUser(UserReq userReq);

  /**
   * 修改用户信息
   *
   * @param userReq
   */
  void updateUser(UserReq userReq);

  /**
   * 修改用户状态
   *
   * @param userStaReq
   */
  void updateUserStatus(UserStaReq userStaReq);
}

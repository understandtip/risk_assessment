package com.yushang.risk.admin.service.adapter;

import com.yushang.risk.admin.domain.vo.request.UserReq;
import com.yushang.risk.admin.domain.vo.response.LoginUserResp;
import com.yushang.risk.domain.entity.User;

import java.time.LocalDateTime;

/**
 * @Author：zlp @Package：com.yushang.risk.admin.service.adapter @Project：risk_assessment
 *
 * @name：UserAdapter @Date：2024/1/11 14:38 @Filename：UserAdapter
 */
public class UserAdapter {
  /**
   * 构建登录成功后返回对象
   *
   * @param user
   * @return
   */
  public static LoginUserResp buildLoginUserResp(User user, String token) {
    return LoginUserResp.builder()
        .id(user.getId())
        .username(user.getUsername())
        .realName(user.getRealName())
        .phone(user.getPhone())
        .email(user.getEmail())
        .loginTime(LocalDateTime.now())
        .invitationCode(user.getInvitationCode())
        .useCode(user.getUseCode())
        .token(token)
        .build();
  }

  /**
   * 构建添加用户对象
   *
   * @param userReq
   * @return
   */
  public static User buildAddUser(UserReq userReq) {

    return User.builder()
        .username(userReq.getUsername())
        .realName(userReq.getRealName())
        .phone(userReq.getPhone())
        .email(userReq.getEmail())
        .build();
  }
}

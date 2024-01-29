package com.yushang.risk.assessment.service.adapter;

import com.yushang.risk.domain.entity.User;
import com.yushang.risk.assessment.domain.vo.request.RegisterReq;
import com.yushang.risk.assessment.domain.vo.response.LoginUserResp;

import java.time.LocalDateTime;

/**
 * @Author：zlp @Package：com.yushang.risk.assessment.service.adapter @Project：risk_assessment
 *
 * @name：UserAdapter @Date：2023/12/21 15:12 @Filename：UserAdapter
 */
public class UserAdapter {

  /**
   * 构建注册时User保存对象
   *
   * @param registerReq
   * @param newPassword
   * @param myInvitationCode
   * @return
   */
  public static User buildSaveUser(
      RegisterReq registerReq, String newPassword, String myInvitationCode) {
    return User.builder()
        .username(registerReq.getUserName())
        .password(newPassword)
        .useCode(registerReq.getInvitationCode())
        .invitationCode(myInvitationCode)
        .build();
  }

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
}

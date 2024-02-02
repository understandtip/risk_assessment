package com.yushang.risk.admin.service.adapter;

import com.yushang.risk.domain.entity.Account;
import com.yushang.risk.admin.domain.vo.request.AccountReq;
import com.yushang.risk.admin.domain.vo.response.LoginUserResp;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author：zlp @Package：com.yushang.risk.admin.service.adapter @Project：risk_assessment
 *
 * @name：AccountAdapter @Date：2024/1/30 14:49 @Filename：AccountAdapter
 */
public class AccountAdapter {

  /**
   * 构建登录成功后返回对象
   *
   * @param user
   * @param permissions
   * @return
   */
  public static LoginUserResp buildLoginUserResp(
      Account user, String token, List<String> permissions) {
    return LoginUserResp.builder()
        .id(user.getId())
        .username(user.getUsername())
        .realName(user.getRealName())
        .phone(user.getPhone())
        .email(user.getEmail())
        .loginTime(LocalDateTime.now())
        .permissionList(permissions)
        .token(token)
        .build();
  }

  /**
   * 构建添加账户对象
   *
   * @param accountReq
   * @return
   */
  public static Account buildAddAccount(AccountReq accountReq) {
    return Account.builder()
        .username(accountReq.getUsername())
        .realName(accountReq.getRealName())
        .phone(accountReq.getPhone())
        .email(accountReq.getEmail())
        .build();
  }
}

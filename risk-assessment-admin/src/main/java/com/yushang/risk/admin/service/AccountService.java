package com.yushang.risk.admin.service;

import com.yushang.risk.admin.domain.entity.Account;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yushang.risk.admin.domain.vo.request.*;
import com.yushang.risk.admin.domain.vo.response.AccountPageResp;
import com.yushang.risk.admin.domain.vo.response.LoginUserResp;
import com.yushang.risk.admin.domain.vo.response.PageBaseResp;

import javax.servlet.http.HttpServletRequest;

/**
 * 账户 服务类
 *
 * @author zlp
 * @since 2024-01-30
 */
public interface AccountService {
  /**
   * 登录
   *
   * @param loginReq
   * @param request
   * @return
   */
  LoginUserResp login(LoginReq loginReq, HttpServletRequest request);

  /**
   * 查询所有账户
   *
   * @param reqPageBaseReq
   * @return
   */
  PageBaseResp<AccountPageResp> getUserList(PageBaseReq<AccountPageReq> reqPageBaseReq);

  /**
   * 查询账户信息
   *
   * @param accId
   * @return
   */
  AccountPageResp getAccInfo(Integer accId);

  /**
   * 添加账户信息
   *
   * @param accountReq
   * @return
   */
  String addAccount(AccountReq accountReq);

  /**
   * 给账户赋予角色
   *
   * @param accountRoleReq
   */
  void grantRoleToAcc(AccountRoleReq accountRoleReq);

  /**
   * 修改账户信息
   *
   * @param accountReq
   */
  void upAccount(AccountReq accountReq);
}

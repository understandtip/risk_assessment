package com.yushang.risk.admin.controller;

import com.yushang.risk.admin.domain.vo.request.*;
import com.yushang.risk.admin.domain.vo.response.AccountPageResp;
import com.yushang.risk.admin.domain.vo.response.LoginUserResp;
import com.yushang.risk.admin.domain.vo.response.PageBaseResp;
import com.yushang.risk.admin.domain.vo.response.UserResp;
import com.yushang.risk.admin.service.AccountService;
import com.yushang.risk.common.domain.vo.ApiResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.util.Removal;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @Author：zlp @Package：com.yushang.risk.admin.controller @Project：risk_assessment
 *
 * @name：AccountController @Date：2024/1/30 14:12 @Filename：AccountController
 */
@Api(tags = "账户管理接口")
@RestController
@RequestMapping("/capi/acc")
public class AccountController {
  @Resource private AccountService accountService;
  /**
   * 用户登录
   *
   * @param loginReq
   * @param request
   * @return
   */
  @PostMapping("/login")
  @ApiOperation("登录")
  public ApiResult<LoginUserResp> login(
      @RequestBody @Validated LoginReq loginReq, HttpServletRequest request) {
    LoginUserResp resp = accountService.login(loginReq, request);
    return ApiResult.success(resp);
  }

  @PostMapping("/getAccounts")
  @ApiOperation("查询所有账户")
  public ApiResult<PageBaseResp<AccountPageResp>> getAccounts(
      @RequestBody PageBaseReq<AccountPageReq> reqPageBaseReq) {
    PageBaseResp<AccountPageResp> resp = accountService.getUserList(reqPageBaseReq);
    return ApiResult.success(resp);
  }

  @GetMapping("/getAccInfo")
  @ApiOperation("查询账户信息")
  public ApiResult<AccountPageResp> getAccInfo(Integer accId) {
    AccountPageResp resp = accountService.getAccInfo(accId);
    return ApiResult.success(resp);
  }

  @PostMapping("/addAccount")
  @ApiOperation("添加账户信息")
  public ApiResult<String> addAccount(@RequestBody @Validated AccountReq accountReq) {
    String pass = accountService.addAccount(accountReq);
    return ApiResult.success(pass);
  }

  @PutMapping("/upAccount")
  @ApiOperation("修改账户信息")
  public ApiResult<Void> upAccount(@RequestBody @Validated AccountReq accountReq) {
    accountService.upAccount(accountReq);
    return ApiResult.success();
  }

  @PostMapping("/grantAuthorityToAcc")
  @ApiOperation("给账户赋予角色")
  public ApiResult<Void> grantRoleToAcc(@RequestBody @Validated AccountRoleReq accountRoleReq) {
    accountService.grantRoleToAcc(accountRoleReq);
    return ApiResult.success();
  }
}

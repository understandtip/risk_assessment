package com.yushang.risk.admin.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yushang.risk.admin.dao.AccountDao;
import com.yushang.risk.admin.dao.UserRoleDao;
import com.yushang.risk.admin.domain.dto.RequestDataInfo;
import com.yushang.risk.admin.domain.entity.Account;
import com.yushang.risk.admin.domain.entity.UserRole;
import com.yushang.risk.admin.domain.enums.UserRoleEnum;
import com.yushang.risk.admin.domain.vo.request.*;
import com.yushang.risk.admin.domain.vo.response.AccountPageResp;
import com.yushang.risk.admin.domain.vo.response.LoginUserResp;
import com.yushang.risk.admin.domain.vo.response.PageBaseResp;
import com.yushang.risk.admin.service.AccountService;
import com.yushang.risk.admin.service.LoginService;
import com.yushang.risk.admin.service.adapter.AccountAdapter;
import com.yushang.risk.common.constant.RedisKey;
import com.yushang.risk.common.exception.BusinessException;
import com.yushang.risk.common.exception.CommonErrorEnum;
import com.yushang.risk.common.exception.SystemException;
import com.yushang.risk.common.util.*;
import lombok.SneakyThrows;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * @Author：zlp @Package：com.yushang.risk.admin.service.impl @Project：risk_assessment
 *
 * @name：AccountServiceImpl @Date：2024/1/30 14:44 @Filename：AccountServiceImpl
 */
@Service
public class AccountServiceImpl implements AccountService {

  @Resource private AccountDao accountDao;
  @Resource private UserRoleDao userRoleDao;
  @Resource private LoginService loginService;

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
    Account account = accountDao.getNormalByUsername(loginReq.getUserName());
    AssertUtils.assertNotNull(account, "用户已被封禁，请联系管理员");
    // 查询是否具有管理员权限
    UserRole userRole =
        userRoleDao.getByUserIdAndRole(account.getId(), UserRoleEnum.ADMIN.getCode());
    AssertUtils.assertNotNull(userRole, "用户无管理员权限");
    String password;
    try {
      password = PasswordUtils.decryptPassword(account.getPassword());
    } catch (Exception e) {
      throw new SystemException(CommonErrorEnum.SYSTEM_ERROR.getErrorCode(), "密码解密异常");
    }
    AssertUtils.equal(AesUtil.decryptPassword(loginReq.getPassword()), password, "密码错误");
    // 修改登录时间
    accountDao.updateLoginTime(account.getId());
    // 获取token
    String token = loginService.login(account.getId());
    RequestDataInfo dataInfo = new RequestDataInfo();
    dataInfo.setUid(account.getId());
    RequestHolder.set(dataInfo);
    return AccountAdapter.buildLoginUserResp(account, token);
  }

  /**
   * 查询所有账户
   *
   * @param reqPageBaseReq
   * @return
   */
  @Override
  public PageBaseResp<AccountPageResp> getUserList(PageBaseReq<AccountPageReq> reqPageBaseReq) {
    Page<Account> page = reqPageBaseReq.plusPage();
    AccountPageReq accountPageReq = reqPageBaseReq.getData();
    List<Integer> accIds = null;
    if (accountPageReq != null
        && accountPageReq.getRoleId() != null
        && accountPageReq.getRoleId() != 0) {
      accIds = userRoleDao.getAccIdsByRoleId(accountPageReq.getRoleId());
    }

    Page<Account> accountPage = accountDao.getAccListByPage(page, accountPageReq, accIds);
    List<AccountPageResp> collect =
        accountPage.getRecords().stream()
            .map(
                ap -> {
                  AccountPageResp resp = new AccountPageResp();
                  BeanUtils.copyProperties(ap, resp);
                  return resp;
                })
            .collect(Collectors.toList());
    return PageBaseResp.init(accountPage, collect);
  }

  /**
   * 查询账户信息
   *
   * @param accId
   * @return
   */
  @Override
  public AccountPageResp getAccInfo(Integer accId) {

    AccountPageResp resp = new AccountPageResp();
    BeanUtils.copyProperties(accountDao.getById(accId), resp);
    return resp;
  }

  /**
   * 添加账户信息
   *
   * @param accountReq
   * @return
   */
  @SneakyThrows
  @Override
  public String addAccount(AccountReq accountReq) {
    Account account = AccountAdapter.buildAddAccount(accountReq);
    String password = UserServiceImpl.generateRandomString();
    account.setPassword(PasswordUtils.encryptPassword(password));
    return password;
  }

  /**
   * 修改账户信息
   *
   * @param accountReq
   */
  @Override
  public void upAccount(AccountReq accountReq) {
    if (accountReq.getId() == null || accountReq.getId() == 0) throw new BusinessException("无账户id");
    Account account = new Account();
    BeanUtils.copyProperties(accountReq, account);
    accountDao.updateById(account);
  }

  /**
   * 给账户赋予角色
   *
   * @param accountRoleReq
   */
  @Override
  public void grantRoleToAcc(AccountRoleReq accountRoleReq) {
    UserRole userRole = new UserRole();
    userRole.setUserId(accountRoleReq.getAccId());
    userRole.setRoleId(accountRoleReq.getRoleId());
    userRoleDao.save(userRole);
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

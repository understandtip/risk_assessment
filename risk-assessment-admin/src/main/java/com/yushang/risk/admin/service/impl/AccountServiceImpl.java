package com.yushang.risk.admin.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sun.xml.internal.bind.v2.TODO;
import com.yushang.risk.admin.dao.AccountDao;
import com.yushang.risk.admin.dao.RoleDao;
import com.yushang.risk.admin.dao.RolePermissionDao;
import com.yushang.risk.admin.dao.UserRoleDao;
import com.yushang.risk.admin.domain.dto.RequestDataInfo;
import com.yushang.risk.admin.domain.vo.response.*;
import com.yushang.risk.admin.service.adapter.UserAdapter;
import com.yushang.risk.common.annotation.OptLog;
import com.yushang.risk.domain.entity.Account;
import com.yushang.risk.admin.domain.entity.UserRole;
import com.yushang.risk.admin.domain.enums.UserRoleEnum;
import com.yushang.risk.admin.domain.vo.request.*;
import com.yushang.risk.admin.service.AccountService;
import com.yushang.risk.admin.service.LoginService;
import com.yushang.risk.admin.service.adapter.AccountAdapter;
import com.yushang.risk.common.constant.RedisKey;
import com.yushang.risk.common.exception.BusinessException;
import com.yushang.risk.common.exception.CommonErrorEnum;
import com.yushang.risk.common.exception.SystemException;
import com.yushang.risk.common.util.*;
import com.yushang.risk.domain.entity.Role;
import com.yushang.risk.domain.enums.UserStatusEnum;
import lombok.SneakyThrows;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.security.SecureRandom;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;
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
  @Resource private RoleDao roleDao;
  @Resource private RolePermissionDao rolePermissionDao;

  /**
   * 登录
   *
   * @param loginReq
   * @param request
   * @return
   */
  @Override
  @OptLog(target = OptLog.Target.LOGIN)
  public LoginUserResp login(LoginReq loginReq, HttpServletRequest request) {
    // 校验验证码
    this.verifyCode(loginReq.getCode(), IpUtils.getClientIpAddress(request));
    // 校验账户基本信息
    Account account = accountDao.getAccountByUsername(loginReq.getUserName());
    AssertUtils.assertNotNull(account, "用户名或密码错误");
    AssertUtils.notEqual(account.getState(), UserStatusEnum.BAN.getCode(), "用户已被封禁，请联系管理员");
    // 查询是否具有管理员权限
    UserRole userRole =
        userRoleDao.getByUserIdAndRole(account.getId(), UserRoleEnum.USER.getCode());
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
    // 设置角色信息
    Role role = userRoleDao.getRoleByUserId(account.getId());
    List<String> permissions = rolePermissionDao.getByRoleId(role.getId());
    return AccountAdapter.buildLoginUserResp(account, token, permissions);
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
    Map<Integer, Integer> userIdAndRoleIdMap =
        userRoleDao.list().stream()
            .collect(Collectors.toMap(UserRole::getUserId, UserRole::getRoleId));
    Map<Integer, Role> roleMap =
        roleDao.list().stream().collect(Collectors.toMap(Role::getId, Function.identity()));

    Page<Account> accountPage = accountDao.getAccListByPage(page, accountPageReq, accIds);
    List<AccountPageResp> collect =
        accountPage.getRecords().stream()
            .map(
                ap -> {
                  AccountPageResp resp = new AccountPageResp();
                  BeanUtils.copyProperties(ap, resp);
                  Role role = roleMap.get(userIdAndRoleIdMap.get(ap.getId()));
                  if (role != null) {
                    RoleResp roleResp = new RoleResp();
                    BeanUtils.copyProperties(role, roleResp);
                    resp.setRoleResp(roleResp);
                  }
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
    Role role = userRoleDao.getRoleByUserId(accId);
    RoleResp roleResp = new RoleResp();
    BeanUtils.copyProperties(role, roleResp);
    resp.setRoleResp(roleResp);
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
  public AccountAddResp addAccount(AccountReq accountReq) {
    if (accountReq.getRoleId() == null || accountReq.getRoleId() == 0)
      throw new BusinessException("请选择角色");
    if (accountDao.getAccountByUsername(accountReq.getUsername()) != null) {
      throw new BusinessException("用户名已存在");
    }
    Account account = AccountAdapter.buildAddAccount(accountReq);
    String password = UserServiceImpl.generateRandomString();
    account.setPassword(PasswordUtils.encryptPassword(password));
    account.setInvitationCode(generateRandomString());
    accountDao.save(account);
    UserRole userRole = UserAdapter.buildUserRole(account.getId(), accountReq.getRoleId());
    userRoleDao.save(userRole);
    return AccountAddResp.builder().username(account.getUsername()).password(password).build();
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
  @Transactional(rollbackFor = Exception.class)
  public void grantRoleToAcc(AccountRoleReq accountRoleReq) {
    userRoleDao.removeByUserId(accountRoleReq.getAccId());
    UserRole userRole = new UserRole();
    userRole.setUserId(accountRoleReq.getAccId());
    userRole.setRoleId(accountRoleReq.getRoleId());
    userRoleDao.save(userRole);
  }

  /**
   * 修改账户状态
   *
   * @param accId
   * @param state
   */
  @Override
  public void upAccountState(Integer accId, Integer state) {
    Account account = new Account();
    account.setId(accId);
    account.setState(String.valueOf(state));
    accountDao.updateById(account);
  }

  /**
   * 查询账户角色
   *
   * @param accId
   * @return
   */
  @Override
  public RoleResp getAccRole(Integer accId) {
    Role role = userRoleDao.getRoleByUserId(accId);
    RoleResp resp = new RoleResp();
    BeanUtils.copyProperties(role, resp);
    return resp;
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
  /**
   * 随机生成邀请码
   *
   * @return
   */
  private String generateRandomString() {
    final String CHAR_LOWER = "abcdefghijklmnopqrstuvwxyz";
    final String CHAR_UPPER = CHAR_LOWER.toUpperCase();
    final String DIGITS = "0123456789";

    final String ALPHA_NUMERIC = CHAR_LOWER + CHAR_UPPER + DIGITS;
    SecureRandom random = new SecureRandom();
    StringBuilder sb = new StringBuilder(6);

    for (int i = 0; i < 6; i++) {
      int randomIndex = random.nextInt(ALPHA_NUMERIC.length());
      sb.append(ALPHA_NUMERIC.charAt(randomIndex));
    }
    return sb.toString();
  }
}

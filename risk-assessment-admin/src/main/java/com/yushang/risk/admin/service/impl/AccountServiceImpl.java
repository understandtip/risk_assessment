package com.yushang.risk.admin.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yushang.risk.admin.dao.*;
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
import com.yushang.risk.domain.entity.RegisterApply;
import com.yushang.risk.domain.entity.Role;
import com.yushang.risk.domain.entity.User;
import com.yushang.risk.domain.enums.UserStatusEnum;
import lombok.SneakyThrows;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ValidationException;
import java.net.InetAddress;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Author：zlp @Package：com.yushang.risk.admin.service.impl @Project：risk_assessment
 *
 * @name：AccountServiceImpl @Date：2024/1/30 14:44 @Filename：AccountServiceImpl
 */
@Service
public class AccountServiceImpl implements AccountService {

  @Resource private UsersDao usersDao;
  @Resource private RegisterApplyDao registerApplyDao;
  @Resource private AccountDao accountDao;
  @Resource private UserRoleDao userRoleDao;
  @Resource private LoginService loginService;
  @Resource private RoleDao roleDao;
  @Resource private RolePermissionDao rolePermissionDao;
  @Resource private OnlineUserDao onlineUserDao;

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
    RequestDataInfo info = new RequestDataInfo();
    info.setUserName(loginReq.getUserName());
    RequestHolder.set(info);
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
    dataInfo.setUserName(loginReq.getUserName());
    dataInfo.setIp(IpUtils.getClientIpAddress(request));
    RequestHolder.set(dataInfo);
    // 设置角色信息
    Role role = userRoleDao.getRoleByUserId(account.getId());
    List<String> permissions = rolePermissionDao.getByRoleId(role.getId());
    return AccountAdapter.buildLoginUserResp(account, token, permissions, role.getId());
  }

  /**
   * 查询所有账户信息
   *
   * @param reqPageBaseReq 包含分页和查询条件的请求对象
   * @return 返回账户信息的分页响应对象，其中包含账户列表及其角色信息
   */
  @Override
  public PageBaseResp<AccountPageResp> getUserList(PageBaseReq<AccountPageReq> reqPageBaseReq) {
    // 校验请求参数是否为空
    if (reqPageBaseReq == null) {
      throw new IllegalArgumentException("请求参数不能为空");
    }

    // 初始化分页信息
    Page<Account> page = reqPageBaseReq.plusPage();
    AccountPageReq accountPageReq = reqPageBaseReq.getData();

    // 根据角色ID查询相关的账户ID列表
    List<Integer> accIds = null;
    if (accountPageReq != null
        && accountPageReq.getRoleId() != null
        && accountPageReq.getRoleId() != 0) {
      accIds = userRoleDao.getAccIdsByRoleId(accountPageReq.getRoleId());
    }

    // 组合用户ID和角色ID的映射关系
    Map<Integer, Integer> userIdAndRoleIdMap =
        Optional.ofNullable(userRoleDao.list())
            .map(
                list ->
                    list.stream()
                        .collect(Collectors.toMap(UserRole::getUserId, UserRole::getRoleId)))
            .orElse(Collections.emptyMap());

    // 组合角色ID和角色对象的映射关系
    Map<Integer, Role> roleMap =
        Optional.ofNullable(roleDao.list())
            .map(list -> list.stream().collect(Collectors.toMap(Role::getId, Function.identity())))
            .orElse(Collections.emptyMap());

    // 根据条件查询账户列表并分页
    Page<Account> accountPage = accountDao.getAccListByPage(page, accountPageReq, accIds);

    // 将账户信息转换为响应对象列表
    List<AccountPageResp> collect =
        accountPage.getRecords().stream()
            .map(ap -> convertAccountToAccountPageResp(ap, userIdAndRoleIdMap, roleMap))
            .collect(Collectors.toList());

    // 构建并返回分页响应对象
    return PageBaseResp.init(accountPage, collect);
  }

  /**
   * 将账户对象转换为账户页面响应对象
   *
   * @param ap 账户对象
   * @param userIdAndRoleIdMap 用户ID和角色ID的映射
   * @param roleMap 角色ID和角色对象的映射
   * @return 返回转换后的账户页面响应对象
   */
  private AccountPageResp convertAccountToAccountPageResp(
      Account ap, Map<Integer, Integer> userIdAndRoleIdMap, Map<Integer, Role> roleMap) {
    AccountPageResp resp = new AccountPageResp();
    BeanUtils.copyProperties(ap, resp);

    // 根据账户ID查询角色ID
    Integer roleId = userIdAndRoleIdMap != null ? userIdAndRoleIdMap.get(ap.getId()) : null;
    if (roleId != null) {
      // 根据角色ID查询角色对象
      Role role = roleMap.get(roleId);
      if (role != null) {
        // 将角色对象转换为角色响应对象
        RoleResp roleResp = new RoleResp();
        BeanUtils.copyProperties(role, roleResp);
        resp.setRoleResp(roleResp);
      }
    }
    return resp;
  }

  /**
   * 查询指定账户的信息，并封装成页面响应对象返回。
   *
   * @param accId 账户的ID，用于查询特定账户的信息。
   * @return 返回一个包含账户信息和角色信息的页面响应对象（AccountPageResp）。
   */
  @Override
  public AccountPageResp getAccInfo(Integer accId) {
    // 创建账户页面响应对象
    AccountPageResp resp = new AccountPageResp();
    // 从数据库获取账户信息，并将其属性复制到响应对象中
    BeanUtils.copyProperties(accountDao.getById(accId), resp);
    // 获取账户对应的角色信息
    Role role = userRoleDao.getRoleByUserId(accId);
    // 创建角色响应对象，并将角色信息属性复制到其中
    RoleResp roleResp = new RoleResp();
    BeanUtils.copyProperties(role, roleResp);
    // 将角色响应对象设置到账户页面响应对象中
    resp.setRoleResp(roleResp);
    return resp;
  }

  /**
   * 添加账户信息
   *
   * @param accountReq
   * @return
   */
  @Transactional
  @Override
  public AccountAddResp addAccount(AccountReq accountReq) throws Exception {
    if (accountReq.getRoleId() == null || accountReq.getRoleId() == 0)
      throw new BusinessException("请选择角色");
    // 用户名不能重复
    User userName = usersDao.getByField(User::getUsername, accountReq.getUsername());
    AssertUtils.isEmpty(userName, "用户名已经存在了");
    // 用户名不能重复
    RegisterApply registerApply =
        registerApplyDao.getByField(RegisterApply::getUsername, accountReq.getUsername());
    AssertUtils.isEmpty(registerApply, "用户名已经存在了");
    // 用户名不能重复
    Account accountName = accountDao.getByField(Account::getUsername, accountReq.getUsername());
    AssertUtils.isEmpty(accountName, "用户名已经存在了");

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
   * 个人中心
   *
   * @return
   */
  @Override
  public AccountInfoResp accInfo() {
    Integer accId = RequestHolder.get().getUid();
    AccountInfoResp resp = new AccountInfoResp();
    BeanUtils.copyProperties(accountDao.getById(accId), resp);
    return resp;
  }

  /**
   * 修改个人信息
   *
   * @param accountInfoReq
   */
  @Override
  public void chInfo(AccountInfoReq accountInfoReq) {
    Integer accId = RequestHolder.get().getUid();
    Account account = new Account();
    BeanUtils.copyProperties(accountInfoReq, account);
    account.setId(accId);
    accountDao.updateById(account);
  }

  /**
   * 退出
   *
   * @return
   */
  @OptLog(target = OptLog.Target.EXIT)
  @Override
  public void exit() {
    // 清除token
    RedisUtils.del(RedisKey.getKey(RedisKey.USER_REDIS_TOKEN_PREFIX, RequestHolder.get().getUid()));
    // 设置退出时间
    Account account = new Account();
    account.setId(RequestHolder.get().getUid());
    account.setExitTime(LocalDateTime.now());
    accountDao.updateById(account);
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

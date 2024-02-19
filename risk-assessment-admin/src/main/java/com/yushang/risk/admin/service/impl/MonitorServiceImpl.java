package com.yushang.risk.admin.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yushang.risk.admin.dao.AccountDao;
import com.yushang.risk.admin.dao.OnlineUserDao;
import com.yushang.risk.admin.dao.RegisterApplyDao;
import com.yushang.risk.admin.dao.UsersDao;
import com.yushang.risk.admin.domain.enums.SysLoginLogEnum;
import com.yushang.risk.admin.domain.vo.request.OnlineUserPageReq;
import com.yushang.risk.admin.domain.vo.request.PageBaseReq;
import com.yushang.risk.admin.domain.vo.response.PageBaseResp;
import com.yushang.risk.admin.service.MonitorService;
import com.yushang.risk.common.exception.BusinessException;
import com.yushang.risk.common.util.AssertUtils;
import com.yushang.risk.common.util.RedisUtils;
import com.yushang.risk.constant.RedisCommonKey;
import com.yushang.risk.domain.entity.Account;
import com.yushang.risk.domain.entity.OnlineUser;
import com.yushang.risk.domain.entity.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @Author：zlp @Package：com.yushang.risk.admin.service.impl @Project：risk_assessment
 *
 * @name：MonitorServiceImpl @Date：2024/2/18 14:14 @Filename：MonitorServiceImpl
 */
@Service
public class MonitorServiceImpl implements MonitorService {
  @Resource private UsersDao usersDao;
  @Resource private AccountDao accountDao;
  @Resource private OnlineUserDao onlineUserDao;

  /**
   * 强退
   *
   * @param userName
   * @param platformType
   */
  @Override
  public void forceExit(String userName, String platformType) {
    switch (Objects.requireNonNull(SysLoginLogEnum.getEnum(platformType))) {
      case ADMIN:
        exitAdminUser(userName);
        break;
      case FRONT:
        exitFrontUser(userName);
        break;
      default:
        throw new BusinessException("平台类型错误");
    }
  }

  /**
   * 查询在线用户
   *
   * @param pageBaseReq
   * @return
   */
  @Override
  public PageBaseResp<OnlineUser> getOnlineList(PageBaseReq<OnlineUserPageReq> pageBaseReq) {
    Page<OnlineUser> page = pageBaseReq.plusPage();
    Page<OnlineUser> userPage = onlineUserDao.getOnlineList(page, pageBaseReq.getData());
    return PageBaseResp.init(page, userPage.getRecords());
  }

  /**
   * 强退前台用户
   *
   * @param userName
   */
  private void exitFrontUser(String userName) {
    User user = usersDao.getByField(User::getUsername, userName);
    AssertUtils.isNotEmpty(user, "用户不存在");
    String key =
        RedisCommonKey.getKey(
            RedisCommonKey.FRONT_PREFIX + RedisCommonKey.USER_REDIS_TOKEN_PREFIX, user.getId());
    RedisUtils.del(key);
  }

  /**
   * 强退后台用户
   *
   * @param userName
   */
  private void exitAdminUser(String userName) {
    Account account = accountDao.getByField(Account::getUsername, userName);
    AssertUtils.isNotEmpty(account, "用户不存在");
    String key =
        RedisCommonKey.getKey(
            RedisCommonKey.ADMIN_PREFIX + RedisCommonKey.USER_REDIS_TOKEN_PREFIX, account.getId());
    RedisUtils.del(key);
  }
}

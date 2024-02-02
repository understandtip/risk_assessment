package com.yushang.risk.admin.dao;

import cn.hutool.core.date.LocalDateTimeUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yushang.risk.domain.entity.Account;
import com.yushang.risk.admin.domain.vo.request.AccountPageReq;
import com.yushang.risk.admin.mapper.AccountMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 账户 服务实现类
 *
 * @author zlp
 * @since 2024-01-30
 */
@Service
public class AccountDao extends ServiceImpl<AccountMapper, Account> {
  /**
   * 根据用户名查询用户
   *
   * @param userName
   * @return
   */
  public Account getAccountByUsername(String userName) {
    return this.lambdaQuery().eq(Account::getUsername, userName).one();
  }

  /**
   * 修改登录时间
   *
   * @param id
   */
  public void updateLoginTime(Integer id) {
    this.lambdaUpdate()
        .eq(Account::getId, id)
        .set(Account::getLoginTime, LocalDateTimeUtil.now())
        .update();
  }

  /**
   * 分页获取账户信息
   *
   * @param page
   * @param userPageReq
   * @param accIds
   * @return
   */
  public Page<Account> getAccListByPage(
      Page<Account> page, AccountPageReq userPageReq, List<Integer> accIds) {
    LambdaQueryWrapper<Account> wrapper = new LambdaQueryWrapper<>();
    if (userPageReq != null) {
      wrapper.in(accIds != null && !accIds.isEmpty(), Account::getId, accIds);
      wrapper.like(
          StringUtils.isNotBlank(userPageReq.getUserName()),
          Account::getUsername,
          userPageReq.getUserName());
      wrapper.like(
          StringUtils.isNotBlank(userPageReq.getPhone()),
          Account::getPhone,
          userPageReq.getPhone());
      wrapper.eq(userPageReq.getState() != null, Account::getState, userPageReq.getState());
      wrapper.ge(
          userPageReq.getStartTime() != null, Account::getCreatedTime, userPageReq.getStartTime());
      wrapper.le(
          userPageReq.getEndTime() != null, Account::getCreatedTime, userPageReq.getEndTime());
    }
    return this.page(page, wrapper);
  }

  /**
   * 根据真实姓名查询账户
   *
   * @param applyName
   * @return
   */
  public List<Account> getAccListByName(String applyName) {
    return this.lambdaQuery().like(Account::getRealName, applyName).list();
  }
}

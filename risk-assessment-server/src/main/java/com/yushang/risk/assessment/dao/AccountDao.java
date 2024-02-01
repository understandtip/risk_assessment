package com.yushang.risk.assessment.dao;

import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.yushang.risk.domain.entity.Account;
import com.yushang.risk.assessment.mapper.AccountMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yushang.risk.domain.entity.User;
import org.springframework.stereotype.Service;

/**
 * 账户 服务实现类
 *
 * @author zlp
 * @since 2024-01-31
 */
@Service
public class AccountDao extends ServiceImpl<AccountMapper, Account> {
  /**
   * 根据指定字段查询用户
   *
   * @param value
   * @return
   */
  public Account getByField(SFunction<Account, ?> function, String value) {
    return this.lambdaQuery().eq(function, value).one();
  }
}

package com.yushang.risk.assessment.dao;

import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.yushang.risk.assessment.domain.entity.User;
import com.yushang.risk.assessment.mapper.UsersMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.poi.ss.formula.functions.Function;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 用户 服务实现类
 *
 * @author zlp
 * @since 2023-12-21
 */
@Service
public class UsersDao extends ServiceImpl<UsersMapper, User> {

  /**
   * 根据指定字段查询用户
   *
   * @param value
   * @return
   */
  public User getByField(SFunction<User, ?> function, String value) {
    return this.lambdaQuery().eq(function, value).one();
  }

  /**
   * 修改用户登录时间
   *
   * @param id
   */
  public void updateLoginTime(Integer id) {
    this.lambdaUpdate().eq(User::getId, id).set(User::getLoginTime, LocalDateTime.now()).update();
  }
}

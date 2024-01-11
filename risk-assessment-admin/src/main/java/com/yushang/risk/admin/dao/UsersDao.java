package com.yushang.risk.admin.dao;

import cn.hutool.core.date.LocalDateTimeUtil;
import com.yushang.risk.admin.domain.entity.User;
import com.yushang.risk.admin.mapper.UserMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 用户 服务实现类
 *
 * @author zlp
 * @since 2024-01-11
 */
@Service
public class UsersDao extends ServiceImpl<UserMapper, User> {
  /**
   * 根据用户名查询用户
   *
   * @param userName
   * @return
   */
  public User getNormalByUsername(String userName) {
    return this.lambdaQuery().eq(User::getUsername, userName).one();
  }

  /**
   * 修改登录时间
   *
   * @param id
   */
  public void updateLoginTime(Integer id) {
    this.lambdaUpdate().eq(User::getId, id).set(User::getLoginTime, LocalDateTimeUtil.now());
  }
}

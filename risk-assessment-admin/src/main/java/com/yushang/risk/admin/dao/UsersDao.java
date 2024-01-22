package com.yushang.risk.admin.dao;

import cn.hutool.core.date.LocalDateTimeUtil;
import com.yushang.risk.admin.domain.entity.User;
import com.yushang.risk.admin.mapper.UserMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yushang.risk.common.constant.NormalConstant;
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

  /**
   * 获取月增用户数
   *
   * @return
   */
  public Long getAddedUser() {
    Integer count =
        this.lambdaQuery()
            .ge(
                User::getCreatedTime,
                LocalDateTimeUtil.of(System.currentTimeMillis() - NormalConstant.MONTH_TIME_MSEC))
            .count();
    return Long.valueOf(count);
  }

  /**
   * 获取总增用户数
   *
   * @return
   */
  public Long getAddedUserAll() {
    Integer count = this.lambdaQuery().count();
    return Long.valueOf(count);
  }
}

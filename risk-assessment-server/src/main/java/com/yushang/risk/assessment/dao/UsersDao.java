package com.yushang.risk.assessment.dao;

import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.yushang.risk.assessment.domain.entity.User;
import com.yushang.risk.assessment.domain.enums.UserStatusEnum;
import com.yushang.risk.assessment.mapper.UsersMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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

  /**
   * 根据用户名查询用户
   *
   * @param userName
   * @return
   */
  public User getNormalByUsername(String userName) {
    return this.lambdaQuery()
        .eq(User::getUsername, userName)
        .eq(User::getStatus, UserStatusEnum.NORMAL.getCode())
        .one();
  }

  public List<User> getNormalByRealNameLike(String realName) {
    return this.lambdaQuery()
        .like(User::getRealName, realName)
        .eq(User::getStatus, UserStatusEnum.NORMAL.getCode())
        .list();
  }

  /**
   * 修改用户密码
   *
   * @param id
   * @param newPass
   */
  public boolean updatePass(Integer id, String newPass) {
    return this.lambdaUpdate().eq(User::getId, id).set(User::getPassword, newPass).update();
  }
}

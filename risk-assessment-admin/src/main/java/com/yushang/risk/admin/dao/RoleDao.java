package com.yushang.risk.admin.dao;

import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.yushang.risk.domain.entity.Account;
import com.yushang.risk.domain.enums.RoleStateEnum;
import com.yushang.risk.domain.entity.Role;
import com.yushang.risk.admin.mapper.RoleMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 角色 服务实现类
 *
 * @author zlp
 * @since 2024-01-11
 */
@Service
public class RoleDao extends ServiceImpl<RoleMapper, Role> {
  /**
   * 根据指定字段查询用户
   *
   * @param value
   * @return
   */
  public Role getByField(SFunction<Role, ?> function, String value) {
    return this.lambdaQuery().eq(function, value).one();
  }

  public Role getById(Integer roleId) {
    return this.lambdaQuery()
        .eq(Role::getId, roleId)
        .eq(Role::getState, RoleStateEnum.NORMAL.getState())
        .one();
  }

  /**
   * 获取角色列表
   *
   * @return
   */
  public List<Role> getRoleList() {
    return this.lambdaQuery().list();
  }

  /**
   * upRoleState
   *
   * @param roleId
   * @param state
   */
  public void upRoleState(Integer roleId, boolean state) {
    this.lambdaUpdate()
        .eq(Role::getId, roleId)
        .set(Role::getState, state ? RoleStateEnum.NORMAL.getState() : RoleStateEnum.BAN.getState())
        .update();
  }
}

package com.yushang.risk.admin.dao;

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

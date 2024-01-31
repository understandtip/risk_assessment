package com.yushang.risk.admin.service;

import com.yushang.risk.admin.domain.vo.request.RoleAllotReq;
import com.yushang.risk.admin.domain.vo.request.RoleReq;
import com.yushang.risk.admin.domain.vo.response.RolePerResp;
import com.yushang.risk.admin.domain.vo.response.RoleResp;

import java.util.List;

/**
 * 角色 服务类
 *
 * @author zlp
 * @since 2024-01-11
 */
public interface RoleService {
  /**
   * 查询角色信息
   *
   * @return
   */
  List<RoleResp> getRoleList();

  /**
   * 修改角色状态
   *
   * @param roleId
   * @param state
   */
  void upRoleState(Integer roleId, boolean state);

  /**
   * 修改角色信息
   *
   * @param roleReq
   */
  void upRoleInfo(RoleReq roleReq);

  /**
   * 添加角色信息
   *
   * @param roleReq
   */
  void addRoleInfo(RoleReq roleReq);

  /**
   * 角色分配权限
   *
   * @param roleAllotReq
   */
  void allotPermissionByRole(RoleAllotReq roleAllotReq);

  /**
   * 查询角色权限
   *
   * @param roleId
   * @return
   */
  RolePerResp getPerByRole(Integer roleId);
}

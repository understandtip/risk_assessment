package com.yushang.risk.admin.service.impl;

import com.yushang.risk.admin.dao.RoleDao;
import com.yushang.risk.admin.dao.RolePermissionDao;
import com.yushang.risk.admin.domain.enums.UserRoleEnum;
import com.yushang.risk.admin.domain.vo.request.RoleAllotReq;
import com.yushang.risk.admin.domain.vo.request.RoleReq;
import com.yushang.risk.admin.domain.vo.response.RolePerResp;
import com.yushang.risk.admin.domain.vo.response.RoleResp;
import com.yushang.risk.admin.service.RoleService;
import com.yushang.risk.common.exception.BusinessException;
import com.yushang.risk.common.util.AssertUtils;
import com.yushang.risk.domain.entity.Role;
import com.yushang.risk.domain.entity.RolePermission;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author：zlp @Package：com.yushang.risk.admin.service.impl @Project：risk_assessment
 *
 * @name：RoleServiceImpl @Date：2024/1/29 15:58 @Filename：RoleServiceImpl
 */
@Service
public class RoleServiceImpl implements RoleService {
  @Resource private RoleDao roleDao;
  @Resource private RolePermissionDao rolePermissionDao;
  /**
   * 查询角色信息
   *
   * @return
   */
  @Override
  public List<RoleResp> getRoleList() {
    return roleDao.getRoleList().stream()
        .map(
            role -> {
              RoleResp resp = new RoleResp();
              BeanUtils.copyProperties(role, resp);
              return resp;
            })
        .collect(Collectors.toList());
  }

  /**
   * 修改角色状态
   *
   * @param roleId
   * @param state
   */
  @Override
  public void upRoleState(Integer roleId, boolean state) {
    if (UserRoleEnum.ADMIN.getCode().equals(roleId) || UserRoleEnum.USER.getCode().equals(roleId))
      throw new BusinessException("管理员或普通用户无法禁用");
    roleDao.upRoleState(roleId, state);
  }

  /**
   * 修改角色信息
   *
   * @param roleReq
   */
  @Override
  public void upRoleInfo(RoleReq roleReq) {
    Role byField = roleDao.getByField(Role::getName, roleReq.getName());
    AssertUtils.isEmpty(byField, "角色名已存在");
    Role role = new Role();
    BeanUtils.copyProperties(roleReq, role);
    roleDao.updateById(role);
  }

  /**
   * 添加角色信息
   *
   * @param roleReq
   */
  @Override
  public void addRoleInfo(RoleReq roleReq) {
    Role role = new Role();
    role.setName(roleReq.getName());
    Role roleDaoByField = roleDao.getByField(Role::getName, roleReq.getName());
    AssertUtils.isEmpty(roleDaoByField, "角色名已存在");
    roleDao.save(role);
  }

  /**
   * 角色分配权限
   *
   * @param roleAllotReq
   */
  @Override
  public void allotPermissionByRole(RoleAllotReq roleAllotReq) {
    if (UserRoleEnum.ADMIN.getCode().equals(roleAllotReq.getRoleId())
        || UserRoleEnum.USER.getCode().equals(roleAllotReq.getRoleId()))
      throw new BusinessException("超管或普通用户无法修改权限");
    rolePermissionDao.allotPermissionByRole(roleAllotReq);
  }

  /**
   * 查询角色权限
   *
   * @param roleId
   * @return
   */
  @Override
  public RolePerResp getPerByRole(Integer roleId) {
    if (roleId.equals(UserRoleEnum.USER.getCode())) throw new BusinessException("普通用户无法赋予权限");
    RolePerResp resp = new RolePerResp();
    List<Integer> perList = new ArrayList<>();
    List<RolePermission> list = rolePermissionDao.listByRoleId(roleId);
    list.forEach(
        rolePermission -> {
          perList.add(rolePermission.getPerId());
        });
    resp.setPermissionIds(perList);
    return resp;
  }
}

package com.yushang.risk.admin.dao;

import com.yushang.risk.admin.domain.vo.request.RoleAllotReq;
import com.yushang.risk.domain.entity.Permission;
import com.yushang.risk.domain.entity.RolePermission;
import com.yushang.risk.admin.mapper.RolePermissionMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 角色权限 服务实现类
 *
 * @author zlp
 * @since 2024-01-26
 */
@Service
public class RolePermissionDao extends ServiceImpl<RolePermissionMapper, RolePermission> {

  @Resource private PermissionDao permissionDao;
  /**
   * 根据角色id获取权限信息
   *
   * @param id
   * @return
   */
  public List<String> getByRoleId(Integer id) {
    List<Permission> permissions = permissionDao.list();

    List<String> perList = new ArrayList<>();

    this.lambdaQuery()
        .eq(RolePermission::getRoleId, id)
        .list()
        .forEach(
            rolePermission -> {
              permissions.forEach(
                  p -> {
                    if (rolePermission.getPerId().equals(p.getId())) perList.add(p.getPermission());
                  });
            });
    return perList;
  }

  /**
   * 角色分配权限
   *
   * @param roleAllotReq
   */
  @Transactional(rollbackFor = Exception.class)
  public void allotPermissionByRole(RoleAllotReq roleAllotReq) {
    this.lambdaUpdate().eq(RolePermission::getRoleId, roleAllotReq.getRoleId()).remove();

    List<Integer> permissionIds = roleAllotReq.getPermissionIds();
    List<RolePermission> list = new ArrayList<>();
    permissionIds.forEach(
        p -> {
          RolePermission rolePermission = new RolePermission();
          rolePermission.setRoleId(roleAllotReq.getRoleId());
          rolePermission.setPerId(p);
          list.add(rolePermission);
        });
    this.saveBatch(list);
  }

  /**
   * 根据角色id查询权限集合
   *
   * @param roleId
   * @return
   */
  public List<RolePermission> listByRoleId(Integer roleId) {
    return this.lambdaQuery().eq(RolePermission::getRoleId, roleId).list();
  }
}

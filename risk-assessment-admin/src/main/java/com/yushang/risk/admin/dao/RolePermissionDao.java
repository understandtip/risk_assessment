package com.yushang.risk.admin.dao;

import com.yushang.risk.domain.entity.Permission;
import com.yushang.risk.domain.entity.RolePermission;
import com.yushang.risk.admin.mapper.RolePermissionMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

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
}

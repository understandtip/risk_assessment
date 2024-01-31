package com.yushang.risk.admin.dao;

import com.yushang.risk.domain.entity.Permission;
import com.yushang.risk.admin.mapper.PermissionMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 权限 服务实现类
 *
 * @author zlp
 * @since 2024-01-26
 */
@Service
public class PermissionDao extends ServiceImpl<PermissionMapper, Permission> {
  /**
   * 根据pid查询权限信息
   *
   * @param
   * @return
   */
  public Permission getByPid(Integer pid) {
    return this.lambdaQuery().eq(Permission::getPid, pid).one();
  }
}

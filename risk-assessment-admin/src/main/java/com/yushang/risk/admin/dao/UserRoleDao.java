package com.yushang.risk.admin.dao;

import com.yushang.risk.admin.domain.entity.UserRole;
import com.yushang.risk.admin.mapper.UserRoleMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 用户角色 服务实现类
 *
 * @author zlp
 * @since 2024-01-11
 */
@Service
public class UserRoleDao extends ServiceImpl<UserRoleMapper, UserRole> {
  /**
   * 根据用户id和角色获取信息
   *
   * @param id
   * @param code
   * @return
   */
  public UserRole getByUserIdAndRole(Integer id, Integer code) {
    return this.lambdaQuery().eq(UserRole::getUserId, id).eq(UserRole::getRoleId, code).one();
  }
}

package com.yushang.risk.admin.dao;

import com.yushang.risk.admin.domain.entity.Role;
import com.yushang.risk.admin.domain.entity.UserRole;
import com.yushang.risk.admin.mapper.UserRoleMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yushang.risk.domain.entity.Permission;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.annotation.Resources;
import java.util.List;

/**
 * 用户角色 服务实现类
 *
 * @author zlp
 * @since 2024-01-11
 */
@Service
public class UserRoleDao extends ServiceImpl<UserRoleMapper, UserRole> {
  @Resource private RoleDao roleDao;
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

  /**
   * 根据用户id获取角色信息
   *
   * @param userId
   * @return
   */
  public Role getRoleByUserId(Integer userId) {
    UserRole one = this.lambdaQuery().eq(UserRole::getUserId, userId).one();
    return roleDao.getById(one.getRoleId());
  }
}

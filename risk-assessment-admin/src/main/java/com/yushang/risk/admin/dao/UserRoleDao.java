package com.yushang.risk.admin.dao;

import com.yushang.risk.domain.entity.Role;
import com.yushang.risk.admin.domain.entity.UserRole;
import com.yushang.risk.admin.mapper.UserRoleMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

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
   * @param uid
   * @param code
   * @return
   */
  public UserRole getByUserIdAndRole(Integer uid, Integer code) {
    return this.lambdaQuery().eq(UserRole::getUserId, uid).eq(UserRole::getRoleId, code).one();
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

  /**
   * 根据角色id获取账户id集合
   *
   * @param roleId
   * @return
   */
  public List<Integer> getAccIdsByRoleId(Integer roleId) {
    return this.lambdaQuery().eq(UserRole::getRoleId, roleId).list().stream()
        .map(UserRole::getUserId)
        .collect(Collectors.toList());
  }
}

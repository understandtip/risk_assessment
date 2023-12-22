package com.yushang.risk.assessment.dao;

import com.yushang.risk.assessment.domain.entity.UserRole;
import com.yushang.risk.assessment.mapper.UserRoleMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户角色 服务实现类
 *
 * @author zlp
 * @since 2023-12-21
 */
@Service
public class UserRoleDao extends ServiceImpl<UserRoleMapper, UserRole> {
  /**
   * 根据用户id查询用户角色信息
   *
   * @param userId
   * @return
   */
  public List<UserRole> getByUserId(Integer userId) {
    return this.lambdaQuery().eq(UserRole::getUserId, userId).list();
  }
}

package com.yushang.risk.assessment.dao;

import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.yushang.risk.assessment.domain.entity.Role;
import com.yushang.risk.assessment.domain.entity.User;
import com.yushang.risk.assessment.mapper.RoleMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 角色 服务实现类
 *
 * @author zlp
 * @since 2023-12-21
 */
@Service
public class RoleDao extends ServiceImpl<RoleMapper, Role> {
  /**
   * 根据指定字段查询角色
   *
   * @param value
   * @return
   */
  public Role getByField(SFunction<Role, ?> function, String value) {
    return this.lambdaQuery().eq(function, value).one();
  }
}

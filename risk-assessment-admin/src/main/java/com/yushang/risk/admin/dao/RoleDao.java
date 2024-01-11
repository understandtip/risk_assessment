package com.yushang.risk.admin.dao;

import com.yushang.risk.admin.domain.entity.Role;
import com.yushang.risk.admin.mapper.RoleMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 角色 服务实现类
 *
 * @author zlp
 * @since 2024-01-11
 */
@Service
public class RoleDao extends ServiceImpl<RoleMapper, Role> {}

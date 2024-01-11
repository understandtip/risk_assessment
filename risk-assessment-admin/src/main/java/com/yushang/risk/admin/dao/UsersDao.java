package com.yushang.risk.admin.dao;

import com.yushang.risk.admin.domain.entity.Users;
import com.yushang.risk.admin.mapper.UsersMapper;
import com.yushang.risk.admin.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 用户 服务实现类
 *
 * @author zlp
 * @since 2024-01-11
 */
@Service
public class UsersDao extends ServiceImpl<UsersMapper, Users> {}

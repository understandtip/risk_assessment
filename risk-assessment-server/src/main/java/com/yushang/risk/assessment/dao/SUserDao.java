package com.yushang.risk.assessment.dao;

import com.yushang.risk.domain.entity.SUser;
import com.yushang.risk.assessment.mapper.SUserMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 安全服务用户 服务实现类
 *
 * @author zlp
 * @since 2024-01-16
 */
@Service
public class SUserDao extends ServiceImpl<SUserMapper, SUser> {}

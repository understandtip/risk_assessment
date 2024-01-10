package com.yushang.risk.assessment.dao;

import com.yushang.risk.assessment.domain.entity.UserLog;
import com.yushang.risk.assessment.mapper.UserLogMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 用户日志 服务实现类
 *
 * @author zlp
 * @since 2024-01-08
 */
@Service
public class UserLogDao extends ServiceImpl<UserLogMapper, UserLog> {}

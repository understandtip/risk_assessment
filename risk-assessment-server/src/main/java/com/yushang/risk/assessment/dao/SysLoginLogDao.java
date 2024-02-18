package com.yushang.risk.assessment.dao;

import com.yushang.risk.domain.entity.SysLoginLog;
import com.yushang.risk.assessment.mapper.SysLoginLogMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 系统登录日志 服务实现类
 *
 * @author zlp
 * @since 2024-02-04
 */
@Service
public class SysLoginLogDao extends ServiceImpl<SysLoginLogMapper, SysLoginLog> {}

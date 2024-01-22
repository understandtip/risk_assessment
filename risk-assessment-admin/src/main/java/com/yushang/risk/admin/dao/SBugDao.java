package com.yushang.risk.admin.dao;

import com.yushang.risk.admin.mapper.SBugMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yushang.risk.domain.entity.SBug;
import org.springframework.stereotype.Service;

/**
 * 漏洞 服务实现类
 *
 * @author zlp
 * @since 2024-01-16
 */
@Service
public class SBugDao extends ServiceImpl<SBugMapper, SBug> {}

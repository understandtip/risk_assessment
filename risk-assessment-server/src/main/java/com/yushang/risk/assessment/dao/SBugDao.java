package com.yushang.risk.assessment.dao;

import com.yushang.risk.domain.entity.SBug;
import com.yushang.risk.assessment.mapper.SBugMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 漏洞 服务实现类
 *
 * @author zlp
 * @since 2024-01-15
 */
@Service
public class SBugDao extends ServiceImpl<SBugMapper, SBug> {}

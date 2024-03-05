package com.yushang.risk.assessment.dao;

import com.yushang.risk.assessment.domain.entity.BsRisk;
import com.yushang.risk.assessment.mapper.BsRiskMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 业务风险 服务实现类
 *
 * @author zlp
 * @since 2024-03-05
 */
@Service
public class BsRiskDao extends ServiceImpl<BsRiskMapper, BsRisk> {}

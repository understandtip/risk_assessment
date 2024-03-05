package com.yushang.risk.assessment.dao;

import com.yushang.risk.assessment.domain.entity.BsRiskComplexityRel;
import com.yushang.risk.assessment.mapper.BsRiskComplexityRelMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 风险复杂度关系 服务实现类
 *
 * @author zlp
 * @since 2024-03-05
 */
@Service
public class BsRiskComplexityRelDao
    extends ServiceImpl<BsRiskComplexityRelMapper, BsRiskComplexityRel> {}

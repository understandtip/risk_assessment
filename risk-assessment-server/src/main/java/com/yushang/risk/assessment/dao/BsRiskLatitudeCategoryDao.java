package com.yushang.risk.assessment.dao;

import com.yushang.risk.assessment.domain.entity.BsRiskLatitudeCategory;
import com.yushang.risk.assessment.mapper.BsRiskLatitudeCategoryMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 风险纬度分类关系 服务实现类
 *
 * @author zlp
 * @since 2024-03-05
 */
@Service
public class BsRiskLatitudeCategoryDao
    extends ServiceImpl<BsRiskLatitudeCategoryMapper, BsRiskLatitudeCategory> {}

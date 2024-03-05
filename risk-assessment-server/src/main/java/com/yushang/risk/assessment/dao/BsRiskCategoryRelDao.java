package com.yushang.risk.assessment.dao;

import com.yushang.risk.assessment.domain.entity.BsRiskCategoryRel;
import com.yushang.risk.assessment.mapper.BsRiskCategoryRelMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 风险与分类关系表 服务实现类
 *
 * @author zlp
 * @since 2024-03-05
 */
@Service
public class BsRiskCategoryRelDao extends ServiceImpl<BsRiskCategoryRelMapper, BsRiskCategoryRel> {}

package com.yushang.risk.assessment.dao;

import com.yushang.risk.assessment.domain.entity.BsRiskCategory;
import com.yushang.risk.assessment.mapper.BsRiskCategoryMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 风险分类 服务实现类
 *
 * @author zlp
 * @since 2024-03-05
 */
@Service
public class BsRiskCategoryDao extends ServiceImpl<BsRiskCategoryMapper, BsRiskCategory> {}

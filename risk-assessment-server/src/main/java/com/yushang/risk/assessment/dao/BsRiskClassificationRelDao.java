package com.yushang.risk.assessment.dao;

import com.yushang.risk.assessment.domain.entity.BsRiskClassificationRel;
import com.yushang.risk.assessment.mapper.BsRiskClassificationRelMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 风险种类关系表 服务实现类
 *
 * @author zlp
 * @since 2024-03-12
 */
@Service
public class BsRiskClassificationRelDao
    extends ServiceImpl<BsRiskClassificationRelMapper, BsRiskClassificationRel> {
  /**
   * 根据分类id获取风险id
   *
   * @param categoryId 分类id
   * @return 风险id
   */
  public List<Integer> getByCategoryId(Integer categoryId) {
    return this.lambdaQuery().eq(BsRiskClassificationRel::getCategoryId, categoryId).list().stream()
        .map(BsRiskClassificationRel::getRiskId)
        .collect(Collectors.toList());
  }
}

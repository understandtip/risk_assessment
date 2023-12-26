package com.yushang.risk.assessment.dao;

import com.yushang.risk.assessment.domain.entity.RiskGrade;
import com.yushang.risk.assessment.mapper.RiskGradeMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 风险评分表 服务实现类
 *
 * @author zlp
 * @since 2023-12-20
 */
@Service
public class RiskGradeDao extends ServiceImpl<RiskGradeMapper, RiskGrade> {
  /**
   * 根据风险id查询分数情况
   *
   * @param riskId
   * @return
   */
  public List<RiskGrade> getGradeByRiskId(Integer riskId) {
    return this.lambdaQuery().eq(RiskGrade::getRiskId, riskId).list();
  }
}

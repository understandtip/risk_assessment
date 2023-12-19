package com.yushang.risk.assessment.dao;

import com.yushang.risk.assessment.domain.entity.Risk;
import com.yushang.risk.assessment.mapper.RiskMapper;
import com.yushang.risk.assessment.service.RiskService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 风险 服务实现类
 *
 * @author zlp
 * @since 2023-12-18
 */
@Service
public class RiskDao extends ServiceImpl<RiskMapper, Risk> {
  /**
   * 查询指定分类下的风险集合
   *
   * @param categoryId
   * @return
   */
  public List<Risk> getRiskFromCategory(Integer categoryId) {
    return this.lambdaQuery().eq(Risk::getCategoryId, categoryId).list();
  }
}

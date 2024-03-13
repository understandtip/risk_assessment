package com.yushang.risk.assessment.service;

import com.yushang.risk.assessment.domain.entity.BsCategory;
import com.yushang.risk.assessment.domain.vo.response.BsRiskResp;

import java.util.List;

/**
 * 种类 服务类
 *
 * @author zlp
 * @since 2024-03-05
 */
public interface BsCategoryService {
  /**
   * 分类数据
   *
   * @return
   */
  List<BsCategory> getCategory();

  /**
   * 获取业务风险数据
   *
   * @param categoryId
   * @return
   */
  BsRiskResp getBSRisk(Integer categoryId);
}

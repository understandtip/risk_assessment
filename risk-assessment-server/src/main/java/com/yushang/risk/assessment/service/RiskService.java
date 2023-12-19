package com.yushang.risk.assessment.service;

import com.yushang.risk.assessment.domain.entity.Risk;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yushang.risk.assessment.domain.vo.response.RiskResp;

import java.util.List;

/**
 * 风险 服务类
 *
 * @author zlp
 * @since 2023-12-18
 */
public interface RiskService {
  /**
   * 查询指定分类下的风险集合
   *
   * @param categoryId
   * @return
   */
  List<RiskResp> getRiskList(Integer categoryId);

  /**
   * 获取所有风险集合
   *
   * @return
   */
  List<RiskResp> getRiskList();
}

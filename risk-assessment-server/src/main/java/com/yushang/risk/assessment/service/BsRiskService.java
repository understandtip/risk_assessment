package com.yushang.risk.assessment.service;

import com.yushang.risk.assessment.domain.vo.response.*;

import java.util.List;
import java.util.Map;

/**
 * 业务风险 服务类
 *
 * @author zlp
 * @since 2024-03-05
 */
public interface BsRiskService {
  /**
   * 获取风险信息
   *
   * @param riskId 风险id
   * @return 风险信息
   */
  BsRiskInfoResp getRiskInfo(Integer riskId);

  /**
   * 获取规避信息
   *
   * @param avoidId 规避id
   * @return 规避信息
   */
  BsAvoidInfoResp getAvoidInfo(Integer avoidId);
  /**
   * 获取攻击工具信息
   *
   * @param toolId 攻击工具id
   * @return 攻击工具信息
   */
  BsAttackToolInfoResp getToolInfo(Integer toolId);

  /**
   * 所有风险
   *
   * @return
   */
  List<BsAllRiskInfoResp> getAllRisk();

  /**
   * 所有规避手段
   *
   * @return
   */
  List<BsAllAvoidInfoResp> getAllAvoid();

  /**
   * 所有攻击工具
   *
   * @return
   */
  List<BsAllAttackToolInfoResp> getAllAttackTool();
}

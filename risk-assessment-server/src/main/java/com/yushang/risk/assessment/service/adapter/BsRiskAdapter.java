package com.yushang.risk.assessment.service.adapter;

import com.yushang.risk.assessment.domain.entity.BsRisk;
import com.yushang.risk.assessment.domain.entity.Risk;
import com.yushang.risk.assessment.domain.vo.response.BsRiskResp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author：zlp @Package：com.yushang.risk.assessment.service.adapter @Project：risk_assessment
 *
 * @name：BsRiskAdapter @Date：2024/3/5 14:59 @Filename：BsRiskAdapter
 */
public class BsRiskAdapter {
  /**
   * 构建内部类Risk
   *
   * @param risk
   * @param r
   */
  public static void buildBsRiskResp(BsRiskResp.Risk risk, BsRisk r) {
    risk.setId(r.getId());
    risk.setDefinition(r.getDefinition());
    risk.setTitle(r.getTitle());
  }

  /**
   * 构建内部类Risk的子风险集合
   *
   * @param riskList
   * @param r
   */
  public static void buildRsRiskChild(Map<Integer, List<BsRisk>> riskList, BsRiskResp.Risk r) {
    Integer pid = r.getId();
    List<BsRisk> childRisks = riskList.get(pid);
    if (childRisks == null || childRisks.isEmpty()) return;
    List<BsRiskResp.Risk> list = new ArrayList<>();
    childRisks.forEach(
        c -> {
          BsRiskResp.Risk risk = new BsRiskResp.Risk();
          risk.setId(c.getId());
          risk.setDefinition(c.getDefinition());
          risk.setTitle(c.getTitle());
          risk.setChildRiskList(null);
          list.add(risk);
        });
    r.setChildRiskList(list);
  }

  /**
   * k=pid value = self
   *
   * @param bsRisks
   * @return
   */
  public static Map<Integer, List<BsRisk>> dealRiskPidToMap(List<BsRisk> bsRisks) {
    return bsRisks.stream().collect(Collectors.groupingBy(BsRisk::getPid));
  }
}

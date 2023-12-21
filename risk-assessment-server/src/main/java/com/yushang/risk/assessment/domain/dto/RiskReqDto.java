package com.yushang.risk.assessment.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author：zlp @Package：com.yushang.risk.assessment.domain.dto @Project：risk_assessment
 *
 * @name：RiskReqDto @Date：2023/12/20 14:59 @Filename：RiskReqDto
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RiskReqDto {
  private Integer cycleId;
  private String riskId;
  private String title;
  private String desc;
  private String optionName;
  private Integer score;
  private String advise;
}

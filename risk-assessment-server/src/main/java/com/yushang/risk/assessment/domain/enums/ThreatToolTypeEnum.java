package com.yushang.risk.assessment.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

/**
 * @Author：zlp @Package：com.yushang.risk.assessment.domain.enums @Project：risk_assessment
 *
 * @name：ThreatToolTypeEnum @Date：2024/3/20 15:01 @Filename：ThreatToolTypeEnum
 */
@AllArgsConstructor
@Getter
public enum ThreatToolTypeEnum {
  //
  BUILD(1, "建造"),
  USE(0, "使用");
  private final Integer code;
  private final String desc;
}

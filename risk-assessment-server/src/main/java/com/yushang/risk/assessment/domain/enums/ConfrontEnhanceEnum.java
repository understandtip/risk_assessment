package com.yushang.risk.assessment.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.AssertFalse;

/**
 * @Author：zlp @Package：com.yushang.risk.assessment.domain.enums @Project：risk_assessment
 *
 * @name：ConfrontEnhenceEnum @Date：2024/2/27 10:19 @Filename：ConfrontEnhenceEnum
 */
@Getter
@AllArgsConstructor
public enum ConfrontEnhanceEnum {
  //
  ENHANCE("1", "增强"),
  NO_ENHANCE("0", "未增强");
  private String code;
  private String desc;
}

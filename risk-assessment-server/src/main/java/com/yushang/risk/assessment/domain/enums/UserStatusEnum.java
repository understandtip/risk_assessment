package com.yushang.risk.assessment.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author：zlp @Package：com.yushang.risk.assessment.domain.enums @Project：risk_assessment
 *
 * @name：UserStatusEnum @Date：2023/12/29 9:37 @Filename：UserStatusEnum
 */
@Getter
@AllArgsConstructor
public enum UserStatusEnum {
  //
  NORMAL(1, "正常"),
  BAN(0, "封禁");
  private final Integer code;
  private final String desc;
}

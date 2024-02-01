package com.yushang.risk.admin.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author：zlp @Package：com.yushang.risk.admin.domain.enums @Project：risk_assessment
 *
 * @name：AccountStateEnum @Date：2024/1/31 15:45 @Filename：AccountStateEnum
 */
@Getter
@AllArgsConstructor
public enum AccountStateEnum {
  //
  NORMAL(1, "正常"),
  BAN(0, "封禁");
  private final Integer state;
  private final String desc;
}

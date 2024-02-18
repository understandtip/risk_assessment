package com.yushang.risk.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.awt.print.PrinterAbortException;

/**
 * @Author：zlp @Package：com.yushang.risk.domain.enums @Project：risk_assessment
 *
 * @name：LoginLogTypeEnum @Date：2024/2/4 14:19 @Filename：LoginLogTypeEnum
 */
@Getter
@AllArgsConstructor
public enum LoginLogTypeEnum {
  //
  FRONT(0, "前台"),
  ADMIN(1, "后台");
  private final Integer type;
  private final String desc;
}

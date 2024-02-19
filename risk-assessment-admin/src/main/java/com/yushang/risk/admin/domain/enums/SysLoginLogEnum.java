package com.yushang.risk.admin.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.core.ReactiveTypeDescriptor;

/**
 * @Author：zlp @Package：com.yushang.risk.admin.domain.enums @Project：risk_assessment
 *
 * @name：SysLoginLogEnum @Date：2024/2/18 10:13 @Filename：SysLoginLogEnum
 */
@AllArgsConstructor
@Getter
public enum SysLoginLogEnum {
  //
  FRONT("0", "前台"),
  ADMIN("1", "后台");

  private final String code;
  private final String desc;

  public static SysLoginLogEnum getEnum(String type) {
    for (SysLoginLogEnum value : SysLoginLogEnum.values()) {
      if (value.code.equals(type)) return value;
    }
    return null;
  }
}

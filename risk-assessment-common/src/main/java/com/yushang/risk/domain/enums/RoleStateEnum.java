package com.yushang.risk.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author：zlp @Package：com.yushang.risk.admin.domain.enums @Project：risk_assessment
 *
 * @name：RoleStateEnum @Date：2024/1/29 16:20 @Filename：RoleStateEnum
 */
@Getter
@AllArgsConstructor
public enum RoleStateEnum {
  //
  NORMAL(1, "角色正常"),
  BAN(0, "角色封禁");
  private final Integer state;
  private final String desc;
}

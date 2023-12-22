package com.yushang.risk.common.enums;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @Author：zlp @Package：com.yushang.risk.common.enums @Project：risk_assessment
 *
 * @name：UserRoleEnum @Date：2023/12/21 14:40 @Filename：UserRoleEnum
 */
@AllArgsConstructor
@Getter
public enum UserRoleEnum {
  //
  ADMIN(1, "管理员"),
  USER(2, "普通用户");

  private Integer code;
  private String desc;
}

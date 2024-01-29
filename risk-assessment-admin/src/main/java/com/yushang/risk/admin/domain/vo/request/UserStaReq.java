package com.yushang.risk.admin.domain.vo.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author：zlp @Package：com.yushang.risk.admin.domain.vo.request @Project：risk_assessment
 *
 * @name：UserStaReq @Date：2024/1/23 16:05 @Filename：UserStaReq
 */
@Data
public class UserStaReq {
  @NotNull private Integer id;
  /**
   * @see com.yushang.risk.domain.enums.UserStatusEnum
   */
  @NotNull private Integer status;
}

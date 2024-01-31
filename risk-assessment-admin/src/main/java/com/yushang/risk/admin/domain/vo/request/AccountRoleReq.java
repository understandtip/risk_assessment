package com.yushang.risk.admin.domain.vo.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Author：zlp @Package：com.yushang.risk.admin.domain.vo.request @Project：risk_assessment
 *
 * @name：AccountRoleReq @Date：2024/1/30 16:29 @Filename：AccountRoleReq
 */
@Data
public class AccountRoleReq {
  @NotNull private Integer accId;
  @NotNull private Integer roleId;
}

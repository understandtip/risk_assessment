package com.yushang.risk.admin.domain.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Author：zlp @Package：com.yushang.risk.admin.domain.vo.request @Project：risk_assessment
 *
 * @name：RoleAllotReq @Date：2024/1/30 9:44 @Filename：RoleAllotReq
 */
@Data
public class RoleAllotReq {
  @NotNull private Integer roleId;

  @ApiModelProperty("权限集合")
  List<Integer> permissionIds;
}

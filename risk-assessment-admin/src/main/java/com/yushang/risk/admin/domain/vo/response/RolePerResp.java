package com.yushang.risk.admin.domain.vo.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author：zlp @Package：com.yushang.risk.admin.domain.vo.response @Project：risk_assessment
 *
 * @name：RolePerResp @Date：2024/1/30 9:55 @Filename：RolePerResp
 */
@Data
public class RolePerResp {
  @ApiModelProperty("权限id集合")
  private List<Integer> permissionIds;
}

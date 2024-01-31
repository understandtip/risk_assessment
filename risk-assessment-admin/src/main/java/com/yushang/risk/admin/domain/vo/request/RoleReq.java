package com.yushang.risk.admin.domain.vo.request;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author：zlp @Package：com.yushang.risk.admin.domain.vo.request @Project：risk_assessment
 *
 * @name：RoleReq @Date：2024/1/30 9:36 @Filename：RoleReq
 */
@Data
public class RoleReq {
  /** 主键id */
  @ApiModelProperty(notes = "主键id")
  private Integer id;
  /** 角色名称 */
  @ApiModelProperty(notes = "角色名称")
  @NotNull
  private String name;
}

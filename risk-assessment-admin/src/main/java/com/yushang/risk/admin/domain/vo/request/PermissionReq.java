package com.yushang.risk.admin.domain.vo.request;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author：zlp @Package：com.yushang.risk.admin.domain.vo.request @Project：risk_assessment
 *
 * @name：PermissionReq @Date：2024/1/29 15:43 @Filename：PermissionReq
 */
@Data
public class PermissionReq {
  /** 标题 */
  @ApiModelProperty(notes = "标题")
  private String title;
  /** 父id */
  @ApiModelProperty(notes = "父id")
  private Integer pid;
  /** 权限 */
  @ApiModelProperty(notes = "权限")
  private String permission;
}

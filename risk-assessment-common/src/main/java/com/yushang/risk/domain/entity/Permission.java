package com.yushang.risk.domain.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 权限
 *
 * @author zlp
 * @since 2024-01-26
 */
@Data
@ApiModel(value = "权限", description = "")
@TableName("permission")
public class Permission implements Serializable, Cloneable {
  /** 主键id */
  @ApiModelProperty(notes = "主键id")
  @TableId
  private Integer id;
  /** 标题 */
  @ApiModelProperty(notes = "标题")
  private String title;
  /** 父id */
  @ApiModelProperty(notes = "父id")
  private Integer pid;
  /** 权限 */
  @ApiModelProperty(notes = "权限")
  private String permission;
  /** 逻辑删除;1:删除 0:未删除 */
  @ApiModelProperty(notes = "逻辑删除 1:删除   0:未删除")
  @TableLogic
  private String isDeleted;
  /** 创建时间 */
  @ApiModelProperty(notes = "创建时间")
  private LocalDateTime createdTime;
  /** 更新时间 */
  @ApiModelProperty(notes = "更新时间")
  private LocalDateTime updatedTime;
}

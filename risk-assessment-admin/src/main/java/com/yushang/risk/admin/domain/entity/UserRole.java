package com.yushang.risk.admin.domain.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.xmlbeans.impl.xb.xmlschema.IdAttribute;

/**
 * 用户角色
 *
 * @author zlp
 * @since 2024-01-11
 */
@Data
@ApiModel(value = "用户角色", description = "")
@TableName("user_role")
public class UserRole implements Serializable, Cloneable {
  /** 主键id */
  @ApiModelProperty(notes = "主键id")
  @TableId(type = IdType.AUTO)
  private Integer id;
  /** 用户id */
  @ApiModelProperty(notes = "用户id")
  private Integer userId;
  /** 角色id */
  @ApiModelProperty(notes = "角色id")
  private Integer roleId;
  /** 创建时间 */
  @ApiModelProperty(notes = "创建时间")
  private LocalDateTime createdTime;
  /** 更新时间 */
  @ApiModelProperty(notes = "更新时间")
  private LocalDateTime updatedTime;
  /** 逻辑删除 */
  @ApiModelProperty(notes = "逻辑删除")
  @TableLogic
  private String isDeleted;
}

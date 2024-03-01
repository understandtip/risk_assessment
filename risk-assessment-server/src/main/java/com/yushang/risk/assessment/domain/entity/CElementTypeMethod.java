package com.yushang.risk.assessment.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 因素类型方式
 *
 * @author zlp
 * @since 2024-02-23
 */
@Data
@ApiModel(value = "因素类型方式", description = "")
@TableName("c_element_type_method")
public class CElementTypeMethod implements Serializable, Cloneable {
  /** 主键id */
  @ApiModelProperty(notes = "主键id")
  @TableId
  private Integer id;
  /** 方式名称 */
  @ApiModelProperty(notes = "方式名称")
  private String name;
  /** 排序 */
  @ApiModelProperty(notes = "排序")
  private Integer sort;
  /** 类型id */
  @ApiModelProperty(notes = "类型id")
  private Integer typeId;
  /** 逻辑删除;1:删除 0:未删除 */
  @ApiModelProperty(notes = "逻辑删除 1:删除   0:未删除")
  private String isDeleted;

  private String isEnhance;
  private Integer pid;
  /** 创建时间 */
  @TableField("created_time")
  private LocalDateTime createdTime;

  /** 更新时间 */
  @TableField("updated_time")
  private LocalDateTime updatedTime;
}

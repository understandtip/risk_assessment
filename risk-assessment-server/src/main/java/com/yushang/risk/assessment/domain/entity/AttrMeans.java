package com.yushang.risk.assessment.domain.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 属性防护手段
 *
 * @author zlp
 * @since 2023-12-19
 */
@Data
@ApiModel(value = "属性防护手段", description = "")
@TableName("attr_means")
public class AttrMeans implements Serializable, Cloneable {
  /** 主键id */
  @ApiModelProperty(notes = "主键id")
  @TableId
  private Integer id;
  /** 防护手段名称 */
  @ApiModelProperty(notes = "防护手段名称")
  private String name;
  /** 防护手段描述 */
  @ApiModelProperty(notes = "防护手段描述")
  @TableField("`desc`")
  private String desc;
  /** 防护手段路径 */
  @ApiModelProperty(notes = "防护手段路径")
  private String url;
  /** 属性id */
  @ApiModelProperty(notes = "属性id")
  private Integer attrId;

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

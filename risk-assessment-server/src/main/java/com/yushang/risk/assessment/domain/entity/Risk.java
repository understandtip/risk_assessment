package com.yushang.risk.assessment.domain.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 风险
 *
 * @author zlp
 * @since 2023-12-18
 */
@Data
@ApiModel(value = "风险", description = "")
@TableName("risk")
public class Risk implements Serializable, Cloneable {
  /** id */
  @ApiModelProperty(notes = "id")
  @TableId
  private Integer id;
  /** 风险标题 */
  @ApiModelProperty(notes = "风险标题")
  private String title;
  /** 风险描述 */
  @ApiModelProperty(notes = "风险描述")
  @TableField("`desc`")
  private String desc;
  /** 是否增强;1:增强 0:不增强 */
  @ApiModelProperty(notes = "是否增强1:增强   0:不增强")
  private String isEnhance;
  /** 类别id */
  @ApiModelProperty(notes = "类别id")
  private Integer categoryId;
  /** 属性id */
  @ApiModelProperty(notes = "属性id")
  private Integer attrId;
  /** 周期id */
  @ApiModelProperty(notes = "周期id")
  private Integer cycleId;
  /** 父风险id */
  @ApiModelProperty(notes = "父风险id")
  private Integer parentId;

  /** 额外信息 */
  @ApiModelProperty(notes = "额外信息")
  private String extraInfo;
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

package com.yushang.risk.assessment.domain.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 属性对应威胁
 *
 * @author zlp
 * @since 2023-12-19
 */
@Data
@ApiModel(value = "属性对应威胁", description = "")
@TableName("attr_menace")
public class AttrMenace implements Serializable, Cloneable {
  /** 主键id */
  @ApiModelProperty(notes = "主键id")
  @TableId
  private Integer id;
  /** 威胁标题 */
  @ApiModelProperty(notes = "威胁标题")
  private String title;
  /** 威胁定义 */
  @ApiModelProperty(notes = "威胁定义")
  private String definition;
  /** 威胁描述 */
  @ApiModelProperty(notes = "威胁描述")
  @TableField("`desc`")
  private String desc;
  /** 威胁示例 */
  @ApiModelProperty(notes = "威胁示例")
  private String example;
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

package com.yushang.risk.assessment.domain.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 类别
 *
 * @author zlp
 * @since 2023-12-18
 */
@Data
@ApiModel(value = "类别", description = "")
@TableName("category")
public class Category implements Serializable, Cloneable {
  /** id */
  @ApiModelProperty(notes = "id")
  @TableId
  private Integer id;
  /** 类别名称 */
  @ApiModelProperty(notes = "类别名称")
  private String name;
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

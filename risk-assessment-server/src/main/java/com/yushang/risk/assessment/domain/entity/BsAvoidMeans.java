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
 * 规避手段
 *
 * @author zlp
 * @since 2024-03-05
 */
@Data
@ApiModel(value = "规避手段", description = "")
@TableName("bs_avoid_means")
public class BsAvoidMeans implements Serializable, Cloneable {
  /** 主键id */
  @ApiModelProperty(value = "主键id", notes = "")
  @TableId
  private Integer id;
  /** 标题 */
  @ApiModelProperty(value = "标题", notes = "")
  private String title;
  /** 信息 */
  @ApiModelProperty(value = "信息", notes = "")
  private String info;
  /** 描述 */
  @ApiModelProperty(value = "描述", notes = "")
  private String description;
  /** 局限性 */
  @ApiModelProperty(value = "局限性", notes = "")
  private String boundedness;
  /** 参考 */
  @ApiModelProperty(value = "参考", notes = "")
  private String reference;
  /** 逻辑删除;1:删除 0:未删除 */
  @ApiModelProperty(value = "逻辑删除", notes = "1:删除   0:未删除")
  private String isDeleted;

  /** 创建时间 */
  @TableField("created_time")
  private LocalDateTime createdTime;

  /** 更新时间 */
  @TableField("updated_time")
  private LocalDateTime updatedTime;
}

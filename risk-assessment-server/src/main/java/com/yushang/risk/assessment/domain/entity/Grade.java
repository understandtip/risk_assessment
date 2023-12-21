package com.yushang.risk.assessment.domain.entity;

import java.math.BigDecimal;
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
 * 评分等级
 *
 * @author zlp
 * @since 2023-12-20
 */
@Data
@ApiModel(value = "评分等级", description = "")
@TableName("grade")
public class Grade implements Serializable, Cloneable {
  /** 主键id */
  @ApiModelProperty(notes = "主键id")
  @TableId
  private Integer id;
  /** 等级名称 */
  @ApiModelProperty(notes = "等级名称")
  private String name;
  /** 等级分值比例 */
  @ApiModelProperty(notes = "等级分值比例")
  private Double ratio;
  /** 创建时间 */
  @ApiModelProperty(notes = "创建时间")
  private LocalDateTime createdTime;
  /** 更新时间 */
  @ApiModelProperty(notes = "更新时间")
  private LocalDateTime updatedTime;
}

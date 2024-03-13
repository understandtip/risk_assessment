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
 * 风险种类关系表
 *
 * @author zlp
 * @since 2024-03-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "风险种类关系表", description = "")
@TableName("bs_risk_classification_rel")
public class BsRiskClassificationRel implements Serializable, Cloneable {
  /** 主键id */
  @ApiModelProperty(value = "主键id", notes = "")
  @TableId
  private Integer id;
  /** 风险id */
  @ApiModelProperty(value = "风险id", notes = "")
  private Integer riskId;
  /** 种类id */
  @ApiModelProperty(value = "种类id", notes = "")
  private Integer categoryId;

  /** 逻辑删除;1:删除 0:未删除 */
  @TableField("is_deleted")
  private String isDeleted;

  /** 创建时间 */
  @TableField("created_time")
  private LocalDateTime createdTime;

  /** 更新时间 */
  @TableField("updated_time")
  private LocalDateTime updatedTime;
}

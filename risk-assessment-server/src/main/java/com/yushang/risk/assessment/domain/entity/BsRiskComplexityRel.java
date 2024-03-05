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
 * 风险复杂度关系
 *
 * @author zlp
 * @since 2024-03-05
 */
@Data
@ApiModel(value = "风险复杂度关系", description = "")
@TableName("bs_risk_complexity_rel")
public class BsRiskComplexityRel implements Serializable, Cloneable {
  /** 主键id */
  @ApiModelProperty(value = "主键id", notes = "")
  @TableId
  private Integer id;
  /** 风险id */
  @ApiModelProperty(value = "风险id", notes = "")
  private Integer riskId;
  /** 复杂度id */
  @ApiModelProperty(value = "复杂度id", notes = "")
  private Integer complexityId;
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

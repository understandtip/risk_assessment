package com.yushang.risk.assessment.domain.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 风险评分表
 *
 * @author zlp
 * @since 2023-12-20
 */
@Data
@ApiModel(value = "风险评分表", description = "")
@TableName("risk_grade")
public class RiskGrade implements Serializable, Cloneable {
  /** 主键id */
  @ApiModelProperty(notes = "主键id")
  @TableId
  private Integer id;
  /** 风险id */
  @ApiModelProperty(notes = "风险id")
  private Integer riskId;
  /** 等级id */
  @ApiModelProperty(notes = "等级id")
  private Integer gradeId;
  /** 得分 */
  @ApiModelProperty(notes = "得分")
  private Integer score;
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

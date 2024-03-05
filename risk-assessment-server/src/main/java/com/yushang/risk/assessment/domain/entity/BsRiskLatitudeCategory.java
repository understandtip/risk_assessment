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
 * 风险纬度分类关系
 *
 * @author zlp
 * @since 2024-03-05
 */
@Data
@ApiModel(value = "风险纬度分类关系", description = "")
@TableName("bs_risk_latitude_category")
public class BsRiskLatitudeCategory implements Serializable, Cloneable {
  /** 主键id */
  @ApiModelProperty(value = "主键id", notes = "")
  @TableId
  private Integer id;
  /** 纬度id */
  @ApiModelProperty(value = "纬度id", notes = "")
  private Integer latitudeId;
  /** 风险分类id */
  @ApiModelProperty(value = "风险分类id", notes = "")
  private Integer riskCategoryId;
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

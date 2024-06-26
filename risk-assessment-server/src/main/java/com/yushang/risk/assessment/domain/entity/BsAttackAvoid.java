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
 * 工具规避关系
 *
 * @author zlp
 * @since 2024-03-01
 */
@Data
@ApiModel(value = "工具规避关系", description = "")
@TableName("bs_attack_avoid")
public class BsAttackAvoid implements Serializable, Cloneable {
  /** 主键id */
  @ApiModelProperty(value = "主键id")
  @TableId
  private Integer id;
  /** 攻击工具id */
  @ApiModelProperty(value = "攻击工具id")
  private Integer attackId;
  /** 规避id */
  @ApiModelProperty(value = "规避id")
  private Integer avoidId;

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

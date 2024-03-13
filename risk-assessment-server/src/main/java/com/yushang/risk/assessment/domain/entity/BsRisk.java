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
 * 业务风险
 *
 * @author zlp
 * @since 2024-03-05
 */
@Data
@ApiModel(value = "业务风险", description = "")
@TableName("bs_risk")
public class BsRisk implements Serializable, Cloneable {
  /** 主键id */
  @ApiModelProperty(value = "主键id", notes = "")
  @TableId
  private Integer id;
  /** 标题 */
  @ApiModelProperty(value = "标题", notes = "")
  private String title;
  /** 定义 */
  @ApiModelProperty(value = "定义", notes = "")
  private String definition;
  /** 描述 */
  @ApiModelProperty(value = "描述", notes = "")
  private String description;
  /** 影响 */
  @ApiModelProperty(value = "影响", notes = "")
  private String affect;
  /** 参考 */
  @ApiModelProperty(value = "参考", notes = "")
  private String reference;
  /** 关系图 */
  @ApiModelProperty(value = "关系图", notes = "")
  private String relationSchema;
  /** 父id */
  @ApiModelProperty(value = "父id", notes = "")
  private Integer pid;

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

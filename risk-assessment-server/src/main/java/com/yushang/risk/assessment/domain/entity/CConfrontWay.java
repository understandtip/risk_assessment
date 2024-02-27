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
 * 对抗方式
 *
 * @author zlp
 * @since 2024-02-23
 */
@Data
@ApiModel(value = "对抗方式", description = "")
@TableName("c_confront_way")
public class CConfrontWay implements Serializable, Cloneable {
  /** 主键id */
  @ApiModelProperty(notes = "主键id")
  @TableId
  private Integer id;
  /** 方式名称 */
  @ApiModelProperty(notes = "方式名称")
  private String name;
  /** 排序 */
  @ApiModelProperty(notes = "排序")
  private String sort;
  /** 方式id */
  @ApiModelProperty(notes = "方式id")
  private Integer methodId;
  /** 分数 */
  @ApiModelProperty(notes = "分数")
  private Double score;
  /** 逻辑删除;1:删除 0:未删除 */
  @ApiModelProperty(notes = "逻辑删除 1:删除   0:未删除")
  private String isDeleted;

  /** 创建时间 */
  @TableField("created_time")
  private LocalDateTime createdTime;

  /** 更新时间 */
  @TableField("updated_time")
  private LocalDateTime updatedTime;
}

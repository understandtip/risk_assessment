package com.yushang.risk.domain.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 漏洞选择
 *
 * @author zlp
 * @since 2024-01-15
 */
@Data
@ApiModel(value = "漏洞选择", description = "")
@TableName("s_bug_opt")
public class SBugOpt implements Serializable, Cloneable {
  /** 主键 */
  @ApiModelProperty(notes = "主键")
  @TableId
  private Integer id;
  /** 渗透功能 */
  @ApiModelProperty(notes = "渗透功能")
  @TableField("`function`")
  private String function;
  /** 单位 */
  @ApiModelProperty(notes = "单位")
  private String unit;
  /** 单价 */
  @ApiModelProperty(notes = "单价")
  private String unitPrice;
  /** 数量 */
  @ApiModelProperty(notes = "数量")
  private String number;
  /** 逻辑删除;1:删除 0:未删除 */
  @ApiModelProperty(notes = "逻辑删除 1:删除   0:未删除")
  @TableLogic
  private String isDeleted;
  /** 创建时间 */
  @ApiModelProperty(notes = "创建时间")
  private LocalDateTime createdTime;
  /** 更新时间 */
  @ApiModelProperty(notes = "更新时间")
  private LocalDateTime updatedTime;
}

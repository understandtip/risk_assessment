package com.yushang.risk.domain.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 安全服务漏洞选择
 *
 * @author zlp
 * @since 2024-01-18
 */
@Data
@ApiModel(value = "安全服务漏洞选择", description = "")
@TableName("s_service_bug_opt")
public class SServiceBugOpt implements Serializable, Cloneable {
  /** 主键 */
  @ApiModelProperty(notes = "主键")
  @TableId
  private Integer id;
  /** 服务id */
  @ApiModelProperty(notes = "服务id")
  private Integer serviceId;
  /** 漏洞选择id */
  @ApiModelProperty(notes = "漏洞选择id")
  private Integer bugOptId;
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

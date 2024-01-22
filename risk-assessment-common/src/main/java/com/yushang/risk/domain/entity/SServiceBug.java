package com.yushang.risk.domain.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 服务漏洞
 *
 * @author zlp
 * @since 2024-01-15
 */
@Data
@ApiModel(value = "服务漏洞", description = "")
@TableName("s_service_bug")
public class SServiceBug implements Serializable, Cloneable {
  /** 主键 */
  @ApiModelProperty(notes = "主键")
  @TableId
  private Integer id;
  /** 安全服务id */
  @ApiModelProperty(notes = "安全服务id")
  private Integer serviceId;
  /** 漏洞id */
  @ApiModelProperty(notes = "漏洞id")
  private Integer bugId;
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

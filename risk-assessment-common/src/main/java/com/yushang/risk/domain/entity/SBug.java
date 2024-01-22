package com.yushang.risk.domain.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 漏洞
 *
 * @author zlp
 * @since 2024-01-15
 */
@Data
@ApiModel(value = "漏洞", description = "")
@TableName("s_bug")
public class SBug implements Serializable, Cloneable {
  /** 主键 */
  @ApiModelProperty(notes = "主键")
  @TableId
  private Integer id;
  /** 漏洞名称 */
  @ApiModelProperty(notes = "漏洞名称")
  private String name;
  /** 漏洞原理 */
  @ApiModelProperty(notes = "漏洞原理")
  private String theory;
  /** 危害 */
  @ApiModelProperty(notes = "危害")
  private String harm;
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

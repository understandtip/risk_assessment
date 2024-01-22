package com.yushang.risk.domain.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户提交记录
 *
 * @author zlp
 * @since 2024-01-16
 */
@Data
@ApiModel(value = "用户提交记录", description = "")
@TableName("s_user_record")
public class SUserRecord implements Serializable, Cloneable {
  /** 主键 */
  @ApiModelProperty(notes = "主键")
  @TableId(type = IdType.AUTO)
  private Integer id;
  /** 用户id */
  @ApiModelProperty(notes = "用户id")
  private Integer userId;
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

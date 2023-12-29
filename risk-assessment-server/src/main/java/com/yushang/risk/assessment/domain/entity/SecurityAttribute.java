package com.yushang.risk.assessment.domain.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 安全属性
 *
 * @author zlp
 * @since 2023-12-18
 */
@Data
@ApiModel(value = "安全属性", description = "")
@TableName("security_attribute")
public class SecurityAttribute implements Serializable, Cloneable {
  /** id */
  @ApiModelProperty(notes = "id")
  @TableId
  private Integer id;
  /** 属性名称 */
  @ApiModelProperty(notes = "属性名称")
  private String name;
  /** 英文名称 */
  @ApiModelProperty(notes = "英文名称")
  private String engName;
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

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
 * 漏洞报价关联
 *
 * @author zlp
 * @since 2024-01-31
 */
@Data
@ApiModel(value = "漏洞报价关联", description = "")
@TableName("s_bug_opt_rel")
public class SBugOptRel implements Serializable, Cloneable {
  /** 主键id */
  @ApiModelProperty(notes = "主键id")
  @TableId
  private Integer id;
  /** 漏洞id */
  @ApiModelProperty(notes = "漏洞id")
  private Integer bugId;
  /** 报价id */
  @ApiModelProperty(notes = "报价id")
  private Integer optId;
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

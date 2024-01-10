package com.yushang.risk.assessment.domain.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 报告记录
 *
 * @author zlp
 * @since 2023-12-29
 */
@Data
@ApiModel(value = "报告记录", description = "")
@TableName("generate_record")
public class GenerateRecord implements Serializable, Cloneable {
  /** 主键id */
  @ApiModelProperty(notes = "主键id")
  @TableId(type = IdType.AUTO)
  private Integer id;
  /** 项目id */
  @ApiModelProperty(notes = "项目id")
  private Integer projectId;
  /** 报告名称 */
  @ApiModelProperty(notes = "项目名称")
  private String projectName;
  /** 报告名称 */
  @ApiModelProperty(notes = "报告名称")
  private String name;
  /** 制作人id */
  @ApiModelProperty(notes = "制作人id")
  private Integer authorId;
  /** 测评报告名称 */
  @ApiModelProperty(notes = "测评报告名称")
  private String fileName;
  /** 逻辑删除;1:删除 0:未删除 */
  @ApiModelProperty(notes = "逻辑删除  1:删除   0:未删除")
  @TableLogic
  private String isDeleted;
  /** 创建时间 */
  @ApiModelProperty(notes = "创建时间")
  private LocalDateTime createdTime;
  /** 更新时间 */
  @ApiModelProperty(notes = "更新时间")
  private LocalDateTime updatedTime;
}

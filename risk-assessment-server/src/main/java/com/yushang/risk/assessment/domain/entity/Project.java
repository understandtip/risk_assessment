package com.yushang.risk.assessment.domain.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 项目表
 *
 * @author zlp
 * @since 2023-12-29
 */
@Data
@ApiModel(value = "项目表", description = "")
@TableName("project")
public class Project implements Serializable, Cloneable {
  /** 主键id */
  @ApiModelProperty(notes = "主键id")
  @TableId(type = IdType.AUTO)
  private Integer id;
  /** 项目名称 */
  @ApiModelProperty(notes = "项目名称")
  private String name;
  /** 单位名称 */
  @ApiModelProperty(notes = "单位名称")
  private String companyName;
  /** 密级 */
  @ApiModelProperty(notes = "密级")
  private String classification;
  /** 版本编号 */
  @ApiModelProperty(notes = "版本编号")
  private Double version;
  /** logo图片地址 */
  @ApiModelProperty(notes = "logo图片地址")
  private String logo;
  /** 说明信息 */
  @ApiModelProperty(notes = "说明信息")
  @TableField("`explain`")
  private String explain;
  /** 制作人姓名 */
  @ApiModelProperty(notes = "制作人姓名")
  private String authorName;
  /** 制作人id */
  @ApiModelProperty(notes = "制作人id")
  private Integer authorId;
  /** 涉及系统 */
  @ApiModelProperty(notes = "涉及系统")
  private String referenceSystem;
  /** 测试公司 */
  @ApiModelProperty(notes = "测试公司")
  private String testingCompany;
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

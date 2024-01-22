package com.yushang.risk.domain.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 安全服务
 *
 * @author zlp
 * @since 2024-01-15
 */
@Data
@ApiModel(value = "安全服务", description = "")
@TableName("s_security_service")
public class SSecurityService implements Serializable, Cloneable {
  /** 主键 */
  @ApiModelProperty(notes = "主键")
  @TableId
  private Integer id;
  /** 安全服务名称 */
  @ApiModelProperty(notes = "安全服务名称")
  private String name;
  /** 图片 */
  @ApiModelProperty(notes = "图片")
  private String img;
  /** 排序 */
  @ApiModelProperty(notes = "排序")
  private Integer sort;
  /** 介绍 */
  @ApiModelProperty(notes = "介绍")
  private String info;
  /** 服务简介 */
  @ApiModelProperty(notes = "服务简介")
  private String intro;
  /** 价格 */
  @ApiModelProperty(notes = "价格")
  private String price;
  /** 联系电话 */
  @ApiModelProperty(notes = "联系电话")
  private String phone;
  /** 售后范围 */
  @ApiModelProperty(notes = "售后范围")
  private String scope;
  /** 交付时间 */
  @ApiModelProperty(notes = "交付时间")
  private String leadTime;
  /** 报价说明 */
  @ApiModelProperty(notes = "报价说明")
  private String tenderClarification;
  /** 服务流程(image) */
  @ApiModelProperty(notes = "服务流程(image)")
  private String serviceProcess;
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

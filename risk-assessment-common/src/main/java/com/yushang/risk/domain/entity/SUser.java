package com.yushang.risk.domain.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 安全服务用户
 *
 * @author zlp
 * @since 2024-01-16
 */
@Data
@ApiModel(value = "安全服务用户", description = "")
@TableName("s_user")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SUser implements Serializable, Cloneable {
  /** 主键 */
  @ApiModelProperty(notes = "主键")
  @TableId(type = IdType.AUTO)
  private Integer id;
  /** 姓名 */
  @ApiModelProperty(notes = "姓名")
  private String name;
  /** 手机号 */
  @ApiModelProperty(notes = "手机号")
  private String phone;
  /** 邮箱 */
  @ApiModelProperty(notes = "邮箱")
  private String email;
  /** 生成报告名称 */
  @ApiModelProperty(notes = "生成报告名称")
  private String portName;
  /** 安全服务id */
  @ApiModelProperty(notes = "安全服务id")
  private Integer serviceId;

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

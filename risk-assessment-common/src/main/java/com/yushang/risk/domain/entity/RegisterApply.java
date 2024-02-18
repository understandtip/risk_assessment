package com.yushang.risk.domain.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 注册申请
 *
 * @author zlp
 * @since 2024-02-01
 */
@Data
@ApiModel(value = "注册申请", description = "")
@TableName("register_apply")
public class RegisterApply implements Serializable, Cloneable {
  /** 主键id */
  @ApiModelProperty(notes = "主键id")
  @TableId(type = IdType.AUTO)
  private Integer id;
  /** 用户名 */
  @ApiModelProperty(notes = "用户名")
  private String username;
  /** 密码 */
  @ApiModelProperty(notes = "密码")
  private String password;
  /** 手机号 */
  @ApiModelProperty(notes = "手机号")
  private String phone;
  /** 邮箱 */
  @ApiModelProperty(notes = "邮箱")
  private String email;
  /** 真实姓名 */
  @ApiModelProperty(notes = "真实姓名")
  private String realName;
  /** 邀请码 */
  @ApiModelProperty(notes = "邀请码")
  private String invitationCode;
  /** 使用的邀请码 */
  @ApiModelProperty(notes = "使用的邀请码")
  private String useCode;
  /** 状态;1:批准 0:待批准 -1:拒绝 */
  @ApiModelProperty(notes = "状态  1:批准  0:待批准  -1:拒绝")
  private Integer state;
  /** 批准人id */
  @ApiModelProperty(notes = "批准人id")
  private Integer applyId;
  /** 逻辑删除;1:删除 0:未删除 */
  @ApiModelProperty(notes = "逻辑删除 1:删除   0:未删除")
  @TableLogic
  private String isDeleted;

  /** 创建时间 */
  @TableField("created_time")
  private LocalDateTime createdTime;

  /** 更新时间 */
  @TableField("updated_time")
  private LocalDateTime updatedTime;
}

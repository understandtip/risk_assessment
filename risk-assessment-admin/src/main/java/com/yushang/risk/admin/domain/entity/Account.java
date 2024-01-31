package com.yushang.risk.admin.domain.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;

/**
 * 账户
 *
 * @author zlp
 * @since 2024-01-30
 */
@Data
@ApiModel(value = "账户", description = "")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("account")
public class Account implements Serializable, Cloneable {
  /** 主键id */
  @ApiModelProperty(notes = "主键id")
  @TableId
  private Integer id;
  /** 用户名 */
  @ApiModelProperty(notes = "用户名")
  private String username;
  /** 密码 */
  @ApiModelProperty(notes = "密码")
  private String password;
  /** 真实姓名 */
  @ApiModelProperty(notes = "真实姓名")
  private String realName;
  /** 手机 */
  @ApiModelProperty(notes = "手机")
  private String phone;
  /** 邮箱 */
  @ApiModelProperty(notes = "邮箱")
  private String email;
  /** 状态;1:正常 0:封禁 */
  @ApiModelProperty(notes = "状态 1:正常   0:封禁")
  private String state;
  /** 登录时间 */
  @ApiModelProperty(notes = "登录时间")
  private LocalDateTime loginTime;
  /** 退出时间 */
  @ApiModelProperty(notes = "退出时间")
  private LocalDateTime exitTime;
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

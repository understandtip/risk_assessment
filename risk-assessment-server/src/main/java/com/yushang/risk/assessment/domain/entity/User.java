package com.yushang.risk.assessment.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户
 *
 * @author zlp
 * @since 2023-12-21
 */
@Data
@ApiModel(value = "用户", description = "")
@TableName("users")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable, Cloneable {
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
  /** 用户真实姓名 */
  @ApiModelProperty(notes = "用户真实姓名")
  private String realName;
  /** 手机 */
  @ApiModelProperty(notes = "手机")
  private String phone;
  /** 邮件 */
  @ApiModelProperty(notes = "邮件")
  private String email;
  /** 账户状态;1:可用 0:账户禁用 */
  @ApiModelProperty(notes = "账户状态   1:可用   0:账户禁用")
  private String status;
  /** 登录时间 */
  @ApiModelProperty(notes = "登录时间")
  private LocalDateTime loginTime;
  /** 邀请码 */
  @ApiModelProperty(notes = "邀请码")
  private String invitationCode;
  /** 使用的邀请码 */
  @ApiModelProperty(notes = "使用的邀请码")
  private String useCode;
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

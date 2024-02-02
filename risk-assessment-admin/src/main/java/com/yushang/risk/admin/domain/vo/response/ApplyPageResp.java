package com.yushang.risk.admin.domain.vo.response;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.checkerframework.checker.tainting.qual.MethodTaintingParam;

/**
 * @Author：zlp @Package：com.yushang.risk.admin.domain.vo.response @Project：risk_assessment
 *
 * @name：ApplyPageResp @Date：2024/2/1 14:52 @Filename：ApplyPageResp
 */
@Data
public class ApplyPageResp {
  /** 主键id */
  @ApiModelProperty(notes = "主键id")
  private Integer id;
  /** 用户名 */
  @ApiModelProperty(notes = "用户名")
  private String username;
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
  @ApiModelProperty(notes = "批准人")
  private String applyName;
}

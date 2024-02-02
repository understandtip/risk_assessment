package com.yushang.risk.admin.domain.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author：zlp @Package：com.yushang.risk.admin.domain.vo.request @Project：risk_assessment
 *
 * @name：ApplyPageReq @Date：2024/2/1 14:48 @Filename：ApplyPageReq
 */
@Data
public class ApplyPageReq {
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

  /** 状态;1:批准 0:待批准 -1:拒绝 */
  @ApiModelProperty(notes = "状态  1:批准  0:待批准  -1:拒绝")
  private Integer state;
  /** 批准人 */
  @ApiModelProperty(notes = "批准人姓名")
  private String applyName;
}

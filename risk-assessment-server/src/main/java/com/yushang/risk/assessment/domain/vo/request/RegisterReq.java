package com.yushang.risk.assessment.domain.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @Author：zlp @Package：com.yushang.risk.assessment.domain.vo.request @Project：risk_assessment
 *
 * @name：LoginVo @Date：2023/12/21 14:10 @Filename：LoginVo
 */
@Data
@ApiModel("入驻前端传递对象")
public class RegisterReq {
  @ApiModelProperty(notes = "用户名")
  @Pattern(regexp = "^[a-zA-Z0-9_]{5,15}$", message = "用户名必须是数字/字母/下划线,长度5-15")
  private String userName;

  @ApiModelProperty(notes = "密码")
  @NotNull
  private String password;

  @ApiModelProperty(notes = "邀请码")
  @NotNull
  private String useCode;

  /** 手机号 */
  @ApiModelProperty(notes = "手机号")
  @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式错误")
  private String phone;
  /** 邮箱 */
  @ApiModelProperty(notes = "邮箱")
  @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "邮箱格式错误")
  private String email;
  /** 真实姓名 */
  @NotNull
  @ApiModelProperty(notes = "真实姓名")
  private String realName;

  @ApiModelProperty(notes = "验证码")
  @NotNull
  private String code;
}

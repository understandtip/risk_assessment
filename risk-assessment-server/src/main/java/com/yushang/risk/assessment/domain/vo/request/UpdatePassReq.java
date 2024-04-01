package com.yushang.risk.assessment.domain.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @Author：zlp @Package：com.yushang.risk.assessment.domain.vo.request @Project：risk_assessment
 *
 * @name：updatePassReq @Date：2023/12/29 15:26 @Filename：updatePassReq
 */
@Data
public class UpdatePassReq {
  @ApiModelProperty(notes = "旧密码")
  @NotNull
  private String password;

  @ApiModelProperty(notes = "新密码")
  @Pattern(regexp = "^[A-Z][a-zA-Z0-9]{7,15}$", message = "密码格式错误")
  private String newPassword;

  @ApiModelProperty(notes = "验证码")
  @NotNull
  private String code;
}

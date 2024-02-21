package com.yushang.risk.admin.domain.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UpdatePassReq {
  @ApiModelProperty(notes = "旧密码")
  @NotNull
  private String password;

  @ApiModelProperty(notes = "新密码")
  @NotNull
  private String newPassword;

  @ApiModelProperty(notes = "验证码")
  @NotNull
  private String code;
}

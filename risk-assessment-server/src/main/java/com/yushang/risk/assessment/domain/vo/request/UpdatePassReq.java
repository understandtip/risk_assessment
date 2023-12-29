package com.yushang.risk.assessment.domain.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

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
  @NotNull
  private String newPassword;

  @ApiModelProperty(notes = "验证码")
  @NotNull
  private String code;
}

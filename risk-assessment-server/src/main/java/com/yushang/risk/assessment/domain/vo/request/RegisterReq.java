package com.yushang.risk.assessment.domain.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author：zlp @Package：com.yushang.risk.assessment.domain.vo.request @Project：risk_assessment
 *
 * @name：LoginVo @Date：2023/12/21 14:10 @Filename：LoginVo
 */
@Data
@ApiModel("注册前端传递对象")
public class RegisterReq {
  @ApiModelProperty(notes = "用户名")
  @NotNull
  private String userName;

  @ApiModelProperty(notes = "密码")
  @NotNull
  private String password;

  @ApiModelProperty(notes = "邀请码")
  @NotNull
  private String invitationCode;

  @ApiModelProperty(notes = "验证码")
  @NotNull
  private String code;
}

package com.yushang.risk.admin.domain.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.Data;

/**
 * @Author：zlp @Package：com.yushang.risk.assessment.domain.vo.request @Project：risk_assessment
 *
 * @name：LoginVo @Date：2023/12/21 16:07 @Filename：LoginVo
 */
@Data
@ApiModel("登录前端传递对象")
public class LoginReq {
  @ApiModelProperty(notes = "用户名")
  @NotNull
  private String userName;

  @ApiModelProperty(notes = "密码")
  @NotNull
  private String password;

  @ApiModelProperty(notes = "验证码")
  @NotNull
  private String code;
}

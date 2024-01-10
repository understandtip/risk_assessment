package com.yushang.risk.assessment.domain.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author：zlp @Package：com.yushang.risk.assessment.domain.vo.request @Project：risk_assessment
 *
 * @name：UserReq @Date：2024/1/4 11:17 @Filename：UserReq
 */
@Data
public class UserReq {
  /** 用户真实姓名 */
  @ApiModelProperty(notes = "用户真实姓名")
  private String realName;
  /** 手机 */
  @ApiModelProperty(notes = "手机")
  private String phone;
  /** 邮件 */
  @ApiModelProperty(notes = "邮箱")
  private String email;
}

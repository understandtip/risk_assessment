package com.yushang.risk.assessment.domain.vo.response;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author：zlp @Package：com.yushang.risk.assessment.domain.vo.response @Project：risk_assessment
 *
 * @name：UserResp @Date：2023/12/29 9:08 @Filename：UserResp
 */
@Data
public class UserResp {

  /** 用户名 */
  @ApiModelProperty(notes = "用户名")
  private String username;
  /** 用户真实姓名 */
  @ApiModelProperty(notes = "用户真实姓名")
  private String realName;
  /** 手机 */
  @ApiModelProperty(notes = "手机")
  private String phone;
  /** 邮件 */
  @ApiModelProperty(notes = "邮箱")
  private String email;
  /** 邀请码 */
  @ApiModelProperty(notes = "邀请码")
  private String invitationCode;
  /** 使用的邀请码 */
  @ApiModelProperty(notes = "使用的邀请码")
  private String useCode;
}

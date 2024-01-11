package com.yushang.risk.admin.domain.vo.response;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @Author：zlp @Package：com.yushang.risk.assessment.domain.vo.response @Project：risk_assessment
 *
 * @name：LoginUserResp @Date：2023/12/21 16:19 @Filename：LoginUserResp
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("登录完成返回对象")
public class LoginUserResp {
  /** 主键id */
  @ApiModelProperty(notes = "主键id")
  @TableId
  private Integer id;
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
  @ApiModelProperty(notes = "邮件")
  private String email;

  /** 登录时间 */
  @ApiModelProperty(notes = "登录时间")
  private LocalDateTime loginTime;
  /** 邀请码 */
  @ApiModelProperty(notes = "邀请码")
  private String invitationCode;
  /** 使用的邀请码 */
  @ApiModelProperty(notes = "使用的邀请码")
  private String useCode;
  /** 登录成功返回token */
  @ApiModelProperty(notes = "token")
  private String token;
}

package com.yushang.risk.admin.domain.vo.response;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.checkerframework.checker.tainting.qual.MethodTaintingParam;

import java.time.LocalDateTime;

/**
 * @Author：zlp @Package：com.yushang.risk.admin.domain.vo.response @Project：risk_assessment
 *
 * @name：AccountInfoResp @Date：2024/2/21 16:57 @Filename：AccountInfoResp
 */
@Data
public class AccountInfoResp {
  /** 主键id */
  @ApiModelProperty(notes = "主键id")
  private Integer id;
  /** 用户名 */
  @ApiModelProperty(notes = "用户名")
  private String username;
  /** 真实姓名 */
  @ApiModelProperty(notes = "真实姓名")
  private String realName;
  /** 手机 */
  @ApiModelProperty(notes = "手机")
  private String phone;
  /** 邮箱 */
  @ApiModelProperty(notes = "邮箱")
  private String email;
  /** 邀请码 */
  @ApiModelProperty(notes = "邀请码")
  private String invitationCode;
  /** 登录时间 */
  @ApiModelProperty(notes = "登录时间")
  private LocalDateTime loginTime;
  /** 退出时间 */
  @ApiModelProperty(notes = "退出时间")
  private LocalDateTime exitTime;
  /** 创建时间 */
  @ApiModelProperty(notes = "创建时间")
  private LocalDateTime createdTime;
  /** 更新时间 */
  @ApiModelProperty(notes = "更新时间")
  private LocalDateTime updatedTime;
}

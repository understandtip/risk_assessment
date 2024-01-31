package com.yushang.risk.admin.domain.vo.response;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author：zlp @Package：com.yushang.risk.admin.domain.vo.response @Project：risk_assessment
 *
 * @name：AccountPageResp @Date：2024/1/30 15:07 @Filename：AccountPageResp
 */
@Data
public class AccountPageResp {
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
  /** 状态;1:正常 0:封禁 */
  @ApiModelProperty(notes = "状态 1:正常   0:封禁")
  private String state;
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

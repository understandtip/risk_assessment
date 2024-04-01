package com.yushang.risk.admin.domain.vo.request;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

/**
 * @Author：zlp @Package：com.yushang.risk.admin.domain.vo.request @Project：risk_assessment
 *
 * @name：UserReq @Date：2024/1/23 14:51 @Filename：UserReq
 */
@Data
public class UserReq {
  /** 主键id */
  @ApiModelProperty(notes = "主键id")
  @NotNull
  private Integer id;
  /** 用户名 */
  @ApiModelProperty(notes = "用户名")
  @Pattern(regexp = "^[a-zA-Z0-9_]{5,15}$", message = "用户名必须是数字/字母/下划线,长度5-15")
  private String username;
  /** 用户真实姓名 */
  @ApiModelProperty(notes = "用户真实姓名")
  private String realName;
  /** 手机 */
  @ApiModelProperty(notes = "手机")
  @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式错误")
  private String phone;
  /** 邮件 */
  @ApiModelProperty(notes = "邮件")
  @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "邮箱格式错误")
  private String email;
}

package com.yushang.risk.admin.domain.vo.request;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @Author：zlp @Package：com.yushang.risk.admin.domain.vo.request @Project：risk_assessment
 *
 * @name：AccountReq @Date：2024/1/30 16:16 @Filename：AccountReq
 */
@Data
public class AccountReq {
  /** 主键id */
  @ApiModelProperty(notes = "主键id")
  private Integer id;

  private Integer roleId;
  /** 用户名 */
  @ApiModelProperty(notes = "用户名")
  @NotNull
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
}

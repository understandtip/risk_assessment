package com.yushang.risk.admin.domain.vo.request;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

/**
 * @Author：zlp @Package：com.yushang.risk.admin.domain.vo.request @Project：risk_assessment
 *
 * @name：AccountInfoReq @Date：2024/2/22 10:00 @Filename：AccountInfoReq
 */
@Data
public class AccountInfoReq {
  /** 真实姓名 */
  @ApiModelProperty(notes = "真实姓名")
  @Pattern(regexp = "^[A-Za-z]+(?: [A-Za-z]+)?$", message = "姓名格式错误")
  private String realName;

  @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式错误")
  private String phone;

  @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "邮箱格式错误")
  private String email;
}

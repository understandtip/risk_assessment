package com.yushang.risk.admin.domain.vo.request;

import lombok.Data;
import org.aspectj.lang.annotation.DeclareAnnotation;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @Author：zlp @Package：com.yushang.risk.admin.domain.vo.request @Project：risk_assessment
 *
 * @name：SecurityServiceRecordPageReq @Date：2024/1/16 11:16 @Filename：SecurityServiceRecordPageReq
 */
@Data
public class SecurityServiceRecordPageReq {
  private String name;

  private String serviceId;

  private String phone;

  private String email;
}

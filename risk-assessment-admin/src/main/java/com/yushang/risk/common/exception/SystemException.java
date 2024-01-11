package com.yushang.risk.common.exception;

import lombok.Data;

/**
 * @Author：zlp @Package：com.yushang.risk.common.exception @Project：risk_assessment
 *
 * @name：SystemException @Date：2024/1/10 16:55 @Filename：SystemException
 */
@Data
public class SystemException extends RuntimeException {
  private Integer errorCode;

  private String errorMsg;

  public SystemException(String errorMsg) {
    super(errorMsg);
    this.errorCode = CommonErrorEnum.BUSINESS_ERROR.getErrorCode();
    this.errorMsg = errorMsg;
  }

  public SystemException(Integer errorCode, String errorMsg) {
    super();
    this.errorCode = errorCode;
    this.errorMsg = errorMsg;
  }

  public SystemException(CommonErrorEnum errorEnum) {
    super();
    this.errorCode = errorEnum.getErrorCode();
    this.errorMsg = errorEnum.getErrorMsg();
  }
}

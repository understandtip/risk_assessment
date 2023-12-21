package com.yushang.risk.common.exception;

import lombok.Data;

/**
 * @author zlp
 */
@Data
public class BusinessException extends RuntimeException {

  private Integer errorCode;

  private String errorMsg;

  public BusinessException(String errorMsg) {
    super(errorMsg);
    this.errorCode = CommonErrorEnum.BUSINESS_ERROR.getErrorCode();
    this.errorMsg = errorMsg;
  }

  public BusinessException(Integer errorCode, String errorMsg) {
    super();
    this.errorCode = errorCode;
    this.errorMsg = errorMsg;
  }
}

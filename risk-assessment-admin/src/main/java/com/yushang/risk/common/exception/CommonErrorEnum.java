package com.yushang.risk.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zlp
 */
@Getter
@AllArgsConstructor
public enum CommonErrorEnum implements ErrorEnum {
  /** */
  BUSINESS_ERROR(0, "{0}"),
  SYSTEM_ERROR(-1, "系统未知异常"),
  PARAM_INVALID(-2, "参数校验失败"),
  LOCK_LIMIT(-3, "请求太频繁了,触发了LOCK_LIMIT--分布式锁获取失败"),
  FREQUENCY_LIMIT(-4, "请求太频繁了,达到了限流标准");
  private final Integer code;
  private final String msg;

  @Override
  public Integer getErrorCode() {
    return code;
  }

  @Override
  public String getErrorMsg() {
    return msg;
  }
}

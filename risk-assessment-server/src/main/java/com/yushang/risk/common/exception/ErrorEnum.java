package com.yushang.risk.common.exception;

/**
 * @author zlp
 */
public interface ErrorEnum {
  /**
   * 获取错误码
   *
   * @return
   */
  Integer getErrorCode();

  /**
   * 获取错误信息
   *
   * @return
   */
  String getErrorMsg();
}

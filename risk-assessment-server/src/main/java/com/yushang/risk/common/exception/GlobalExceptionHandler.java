package com.yushang.risk.common.exception;

import com.yushang.risk.common.domain.vo.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Author：zlp
 *
 * @name：GlobalExceptionHandler
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

  /**
   * 使用Validated对参数进行校验的时候,抛出的MethodArgumentNotValidException异常,进行处理
   *
   * @param e
   * @return
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ApiResult<?> methodArgumentNotValidException(MethodArgumentNotValidException e) {
    StringBuilder errorMsg = new StringBuilder();
    e.getFieldErrors()
        .forEach(
            fieldError -> {
              errorMsg
                  .append(fieldError.getField())
                  .append("，")
                  .append(fieldError.getDefaultMessage())
                  .append("，");
            });
    String msg = errorMsg.substring(0, errorMsg.length() - 1);
    log.error("参数格式错误:::{}", e);
    return ApiResult.fail(CommonErrorEnum.PARAM_INVALID.getCode(), msg);
  }

  /**
   * 自定义业务异常捕获
   *
   * @param e
   * @return
   */
  @ExceptionHandler(BusinessException.class)
  public ApiResult<?> businessException(BusinessException e) {
    log.error("自定义业务异常--->{}", e);
    return ApiResult.fail(e.getErrorCode(), e.getErrorMsg());
  }

  @ExceptionHandler(Throwable.class)
  public ApiResult<?> throwable(Throwable e) {
    log.error("系统未知异常--->{}", e);
    return ApiResult.fail(CommonErrorEnum.SYSTEM_ERROR);
  }
}

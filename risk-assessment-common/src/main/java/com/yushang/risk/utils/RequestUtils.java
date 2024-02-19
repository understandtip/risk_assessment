package com.yushang.risk.utils;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author：zlp @Package：com.yushang.risk.utils @Project：risk_assessment
 *
 * @name：RequestUtils @Date：2024/2/4 14:58 @Filename：RequestUtils
 */
public class RequestUtils {
  /**
   * 获取request请求对象
   *
   * @return
   */
  public static HttpServletRequest getRequest() {
    ServletRequestAttributes attributes =
        (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
    HttpServletRequest request;
    // 获取 HttpServletRequest 对象
    request = attributes.getRequest();
    return request;
  }
}

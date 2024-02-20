package com.yushang.risk.admin.domain.dto;

import lombok.Data;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 线程池HttpServletRequest数据使用对象 @Author：zlp @Package：com.yushang.risk.admin.domain.dto @Project：risk_assessment
 *
 * @name：RequestDataDto @Date：2024/2/20 10:35 @Filename：RequestDataDto
 */
@Data
public class RequestDataDto {
  private Map<String, String> headers = new HashMap<>();
  private HttpSession session;
  private String ip;

  public void setHeaders(HttpServletRequest request) {
    Enumeration<String> headerNames = request.getHeaderNames();
    while (headerNames.hasMoreElements()) {
      String headerName = headerNames.nextElement();
      String headerValue = request.getHeader(headerName);
      headers.put(headerName, headerValue);
    }
  }

  public String getHeader(String headerName) {
    return headers.get(headerName);
  }
}

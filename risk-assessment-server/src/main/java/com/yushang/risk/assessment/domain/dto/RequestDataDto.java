package com.yushang.risk.assessment.domain.dto;

import lombok.Data;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zlp
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

package com.yushang.risk.common.util;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zlp
 */
public class BrowserFingerprint {

  /**
   * 获取浏览器指纹
   *
   * @param request
   * @return
   */
  public static String getFingerprint(HttpServletRequest request) {
    String userAgent = request.getHeader("User-Agent");
    String acceptLanguage = request.getHeader("Accept-Language");
    String acceptEncoding = request.getHeader("Accept-Encoding");
    String acceptCharset = request.getHeader("Accept-Charset");

    // 这里可以对收集的信息进行进一步处理和加密

    // 拼接浏览器指纹
    return userAgent + acceptLanguage + acceptEncoding + acceptCharset;
  }
}

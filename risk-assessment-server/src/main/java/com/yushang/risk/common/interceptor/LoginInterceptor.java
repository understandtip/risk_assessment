package com.yushang.risk.common.interceptor;

import com.yushang.risk.assessment.domain.dto.RequestDataInfo;
import com.yushang.risk.assessment.domain.enums.HttpErrorEnum;
import com.yushang.risk.assessment.service.LoginService;
import com.yushang.risk.common.util.JwtUtils;
import com.yushang.risk.common.util.RequestHolder;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.function.FailableIntBinaryOperator;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author：zlp @Package：com.yushang.risk.common.interceptor @Project：risk_assessment
 *
 * @name：LoginInterceptor @Date：2023/12/25 9:28 @Filename：LoginInterceptor
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {
  @Resource private LoginService loginService;
  /** token在请求头中对应的key */
  public static final String AUTHORIZATION = "Authorization";

  public static final String AUTHORIZATION_SCHAME = "yushang";

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws IOException {
    Integer uid = this.parseToken(request);
    if (uid == null) {
      // 未登录
      if (isPublicUri(request.getPathInfo())) {
        return true;
      }
      HttpErrorEnum.ACCESS_DENIED.sendHttpError(response);
      return false;
    }
    String ip = getClientIpAddress(request);
    // token正确
    RequestDataInfo info = new RequestDataInfo();
    info.setUid(uid);
    info.setIp(ip);
    RequestHolder.set(info);
    return true;
  }

  /**
   * 请求或者说线程执行完之后,将ThreadLocal进行移除
   *
   * @param request
   * @param response
   * @param handler
   * @param ex
   */
  @Override
  public void afterCompletion(
      HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
    RequestHolder.remove();
  }

  /**
   * 是否是公开资源
   *
   * @param pathInfo
   * @return
   */
  private boolean isPublicUri(String pathInfo) {
    return false;
  }

  /**
   * 从请求中获取token
   *
   * @param request
   * @return
   */
  private Integer parseToken(HttpServletRequest request) {
    String authorization = request.getHeader(AUTHORIZATION);
    if (StringUtils.isEmpty(authorization)) return null;
    // 使用空格作为分隔符进行分割
    String[] split = authorization.split("\\s+");
    if (!split[0].equals(AUTHORIZATION_SCHAME) || StringUtils.isEmpty(split[1])) return null;

    return loginService.getValidUid(split[1]);
  }

  /**
   * 获取请求ip地址
   *
   * @param request
   * @return
   */
  public String getClientIpAddress(HttpServletRequest request) {
    String ipAddress = request.getHeader("X-Forwarded-For");

    if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
      ipAddress = request.getHeader("Proxy-Client-IP");
    }
    if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
      ipAddress = request.getHeader("WL-Proxy-Client-IP");
    }
    if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
      ipAddress = request.getHeader("HTTP_CLIENT_IP");
    }
    if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
      ipAddress = request.getHeader("HTTP_X_FORWARDED_FOR");
    }
    if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
      ipAddress = request.getRemoteAddr();
    }

    return ipAddress;
  }
}

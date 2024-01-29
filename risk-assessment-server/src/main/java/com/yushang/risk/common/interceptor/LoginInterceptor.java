package com.yushang.risk.common.interceptor;

import com.yushang.risk.assessment.dao.UsersDao;
import com.yushang.risk.assessment.domain.dto.RequestDataInfo;
import com.yushang.risk.domain.entity.User;
import com.yushang.risk.assessment.domain.enums.HttpErrorEnum;
import com.yushang.risk.assessment.service.LoginService;
import com.yushang.risk.common.util.AssertUtils;
import com.yushang.risk.common.util.IpUtils;
import com.yushang.risk.common.util.RequestHolder;
import org.apache.commons.lang3.StringUtils;
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
  @Resource private UsersDao usersDao;
  /** token在请求头中对应的key */
  public static final String AUTHORIZATION = "Authorization";

  public static final String AUTHORIZATION_SCHAME = "yushang";

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws IOException {
    if (isPublicUri(request.getServletPath())) {
      return true;
    }
    Integer uid = this.parseToken(request);
    if (uid == null) {
      // 未登录
      HttpErrorEnum.ACCESS_DENIED.sendHttpError(response);
      return false;
    }
    User user = usersDao.getById(uid);
    AssertUtils.isNotEmpty(user, "用户不存在");
    String ip = IpUtils.getClientIpAddress(request);
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
    return pathInfo.contains("getSecurityModel")
        || pathInfo.contains("getModelDetail")
        || pathInfo.contains("submitBugReport");
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
}

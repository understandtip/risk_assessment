package com.yushang.risk.common.interceptor;

import com.yushang.risk.assessment.domain.dto.RequestDataInfo;
import com.yushang.risk.common.constant.RedisKey;
import com.yushang.risk.common.util.IpUtils;
import com.yushang.risk.common.util.RedisUtils;
import com.yushang.risk.common.util.RequestHolder;
import com.yushang.risk.constant.RedisCommonKey;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

/**
 * @Author：zlp @Package：com.yushang.risk.common.interceptor @Project：risk_assessment
 *
 * @name：VisitIntercepter @Date：2024/1/11 15:09 @Filename：VisitIntercepter
 */
@Component
@Order(0)
public class VisitInterceptor implements HandlerInterceptor {
  @Override
  public boolean preHandle(
      HttpServletRequest request, HttpServletResponse response, Object handler) {
    String path = request.getRequestURI();
    if (path.contains("api/risk/getCategoryList")) {
      RedisUtils.zAdd(
          RedisCommonKey.USER_VISIT_PROJECT_KEY,
          IpUtils.getClientIpAddress(request),
          System.currentTimeMillis());

      RedisUtils.inc(RedisKey.USER_VISIT, 24, TimeUnit.HOURS);
      return true;
    }
    return true;
  }
}

package com.yushang.risk.common.config;

import com.yushang.risk.common.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * @Author：zlp @Package：com.yushang.risk.common.config @Project：risk_assessment
 *
 * @name：FilterConfig @Date：2023/12/25 9:20 @Filename：FilterConfig
 */
// @Configuration
public class WebMvcConfig implements WebMvcConfigurer {
  @Resource private LoginInterceptor loginInterceptor;

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry
        .addInterceptor(loginInterceptor)
        .addPathPatterns("/api/**")
        .excludePathPatterns("/api/user/getCode", "/api/user/register", "/api/user/login");
  }
}

package com.yushang.risk.common.config;

import com.yushang.risk.common.filter.LoginFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * @Author：zlp @Package：com.yushang.risk.common.config @Project：risk_assessment
 *
 * @name：MyFilterConfig @Date：2024/1/26 14:41 @Filename：MyFilterConfig
 */
@Configuration
public class MyFilterConfig {

  @Bean
  @Order(0)
  public FilterRegistrationBean<LoginFilter> myFilter() {
    FilterRegistrationBean<LoginFilter> registrationBean = new FilterRegistrationBean<>();
    registrationBean.setFilter(new LoginFilter());
    // 指定URL模式
    registrationBean.addUrlPatterns("/*");

    return registrationBean;
  }
}

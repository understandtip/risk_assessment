package com.yushang.risk.common.config;

import com.yushang.risk.common.filter.LoginFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.Header;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.Arrays;

/**
 * @author zlp
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
  @Resource private LoginFilter loginFilter;

  @Override
  public void configure(WebSecurity web) {
    // 放行swagger
    web.ignoring()
        .antMatchers(
            HttpMethod.GET,
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html/**",
            "/webjars/**");
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.formLogin()
        .disable()
        // 其他配置...
        .authorizeRequests()
        .mvcMatchers("/doc.html/**", "capi/user/getCode", "capi/user/login")
        .permitAll()
        .anyRequest()
        .authenticated()
        .and()
        .csrf()
        .disable()
        .headers()
        .addHeaderWriter(
            new StaticHeadersWriter(
                Arrays.asList(
                    new Header("Access-Control-Allow-Origin", "*"),
                    new Header("Access-Control-Expose-Headers", "Authorization"))))
        .and()
        .cors();

    http.addFilterBefore(loginFilter, UsernamePasswordAuthenticationFilter.class);
  }

  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowCredentials(true);
    configuration.setAllowedOrigins(Arrays.asList("*"));
    configuration.setAllowedMethods(Arrays.asList("*"));
    configuration.setAllowedHeaders(Arrays.asList("*"));
    configuration.setMaxAge(Duration.ofHours(1));
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }
}

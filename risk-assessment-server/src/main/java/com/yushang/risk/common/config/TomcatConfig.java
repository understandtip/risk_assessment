package com.yushang.risk.common.config;

import org.apache.catalina.connector.Connector;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zlp
 */
@Configuration
public class TomcatConfig {

  @Bean
  public ServletWebServerFactory servletWebServerFactory() {
    TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
    Connector connector = new Connector(TomcatServletWebServerFactory.DEFAULT_PROTOCOL);
    connector.setPort(8081);
    tomcat.addAdditionalTomcatConnectors(connector);
    return tomcat;
  }
}

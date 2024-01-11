package com.yushang.risk;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author：zlp @Package：com.yushang.risk @Project：risk_assessment
 *
 * @name：RiskAssessmentApplication @Date：2023/12/18 14:18 @Filename：RiskAssessmentApplication
 */
@SpringBootApplication
@MapperScan("com.yushang.risk.admin.**.mapper")
@Slf4j
public class RiskAdminApplication {
  public static void main(String[] args) {
    SpringApplication.run(RiskAdminApplication.class, args);
    log.info("后台项目启动完成...");
  }
}

package com.yushang.risk;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @Author：zlp @Package：com.yushang.risk @Project：risk_assessment
 *
 * @name：RiskAssessmentApplication @Date：2023/12/18 14:18 @Filename：RiskAssessmentApplication
 */
@SpringBootApplication
@MapperScan("com.yushang.risk.assessment.**.mapper")
@EnableScheduling
@Slf4j
public class RiskAssessmentApplication {
  public static void main(String[] args) {
    SpringApplication.run(RiskAssessmentApplication.class, args);
    log.info("项目启动完成...");
  }
}

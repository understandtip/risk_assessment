package com.yushang.risk.common.aspect;

import com.yushang.risk.assessment.domain.dto.RequestDataInfo;
import com.yushang.risk.assessment.service.handler.AbstractOptLogHandler;
import com.yushang.risk.assessment.service.handler.OptLogHandlerFactory;
import com.yushang.risk.common.annotation.OptLog;
import com.yushang.risk.common.config.ThreadPoolConfig;
import com.yushang.risk.common.util.RequestHolder;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author：zlp @Package：com.yushang.risk.common.aspect @Project：risk_assessment
 *
 * @name：LogAspect @Date：2024/1/8 11:38 @Filename：LogAspect
 */
@Aspect
@Component
public class LogAspect {
  @Qualifier(ThreadPoolConfig.COMMON_EXECUTOR)
  @Resource
  private ThreadPoolTaskExecutor threadPoolTaskExecutor;

  @Around("@annotation(optLog)")
  public Object logAspect(ProceedingJoinPoint joinPoint, OptLog optLog) throws Throwable {
    Object proceed = joinPoint.proceed();
    RequestDataInfo dataInfo = RequestHolder.get();
    threadPoolTaskExecutor.execute(
        () -> {
          RequestHolder.set(dataInfo);
          // 异步记录日志
          OptLog.Target target = optLog.target();
          AbstractOptLogHandler handler = OptLogHandlerFactory.get(target.getCode());
          handler.log();
        });
    return proceed;
  }
}

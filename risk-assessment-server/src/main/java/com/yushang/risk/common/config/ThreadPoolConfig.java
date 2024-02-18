package com.yushang.risk.common.config;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

import com.yushang.risk.common.thread.MyThreadFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * @author zlp
 */
@Configuration
@EnableAsync
public class ThreadPoolConfig implements AsyncConfigurer {
  /** 项目共用线程池 */
  public static final String COMMON_EXECUTOR = "commonExecutor";
  /** 安全服务线程池 */
  public static final String SECURITY_SERVICE_EXECUTOR = "securityServiceExecutor";
  /** ip解析线程池 */
  public static final String IP_DETAIL_EXECUTOR = "ipDetailExecutor";

  @Override
  public Executor getAsyncExecutor() {
    return commonExecutor();
  }

  @Bean(COMMON_EXECUTOR)
  @Primary
  public ThreadPoolTaskExecutor commonExecutor() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setWaitForTasksToCompleteOnShutdown(true);
    executor.setCorePoolSize(20);
    executor.setMaxPoolSize(30);
    executor.setQueueCapacity(200);
    // 设置创建线程的名称前缀,方便排查问题
    executor.setThreadNamePrefix("risk-executor-");
    // 满了调用线程执行，认为重要任务
    executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
    executor.setThreadFactory(new MyThreadFactory(executor));
    executor.initialize();
    return executor;
  }

  @Bean(SECURITY_SERVICE_EXECUTOR)
  @Primary
  public ThreadPoolTaskExecutor securityServiceExecutor() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setWaitForTasksToCompleteOnShutdown(true);
    executor.setCorePoolSize(1);
    executor.setMaxPoolSize(1);
    executor.setQueueCapacity(50);
    // 设置创建线程的名称前缀,方便排查问题
    executor.setThreadNamePrefix("security-service-executor-");
    // 满了调用线程执行，认为重要任务
    executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
    executor.setThreadFactory(new MyThreadFactory(executor));
    executor.initialize();
    return executor;
  }

  @Bean(IP_DETAIL_EXECUTOR)
  @Primary
  public ThreadPoolTaskExecutor ipDetailExecutor() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setWaitForTasksToCompleteOnShutdown(true);
    executor.setCorePoolSize(1);
    executor.setMaxPoolSize(1);
    executor.setQueueCapacity(50);
    // 设置创建线程的名称前缀,方便排查问题
    executor.setThreadNamePrefix("ip-detail-executor-");
    // 满了丢弃
    executor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());
    executor.setThreadFactory(new MyThreadFactory(executor));
    executor.initialize();
    return executor;
  }
}

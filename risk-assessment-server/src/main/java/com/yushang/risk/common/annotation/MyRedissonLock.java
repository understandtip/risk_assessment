package com.yushang.risk.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * @Author：zlp @Package：com.yushang.risk.common.annotation @Project：risk_assessment
 *
 * @name：RedissonLock @Date：2023/12/25 14:32 @Filename：RedissonLock
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface MyRedissonLock {
  /**
   * 分布式锁前缀,如果不指定,就默认取方法的全限定类名
   *
   * @return
   */
  String prefix() default "";
  /**
   * Redisson的key,支持SPEL表达式
   *
   * @return
   */
  String key();

  /**
   * 锁等待时间
   *
   * @return
   */
  long waitTime() default -1;

  /**
   * 等待时间单位
   *
   * @return
   */
  TimeUnit timeUnit() default TimeUnit.MILLISECONDS;
}

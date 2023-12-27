package com.yushang.risk.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * @Author：zlp @Package：com.yushang.risk.common.annotation @Project：risk_assessment
 *
 * @name：FrequencyControl @Date：2023/12/26 14:30 @Filename：FrequencyControl
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FrequencyControl {
  /**
   * 锁前缀
   *
   * @return
   */
  String prefix() default "";

  /**
   * 支持spel取值
   *
   * @return
   */
  String spel() default "";

  /**
   * 时间范围
   *
   * @return
   */
  long time();

  /**
   * 次数
   *
   * @return
   */
  long count();

  /**
   * 频控纬度
   *
   * @return
   */
  Target target();
  /**
   * 时间单位
   *
   * @return
   */
  TimeUnit timeUnit() default TimeUnit.SECONDS;

  /** 频控纬度 */
  enum Target {
    //
    UID,
    IP,
    PUBLIC;
  }
}

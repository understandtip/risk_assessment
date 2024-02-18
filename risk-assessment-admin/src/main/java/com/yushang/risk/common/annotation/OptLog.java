package com.yushang.risk.common.annotation;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface OptLog {

  Target target();

  /** */
  @Getter
  @AllArgsConstructor
  enum Target {
    //
    LOGIN(1, "登录"),
    EXIT(0, "退出");
    private final Integer code;
    private final String desc;
  }
}

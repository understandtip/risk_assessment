package com.yushang.risk.common.annotation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author：zlp @Package：com.yushang.risk.common.annotation @Project：risk_assessment
 *
 * @name：UserLog @Date：2024/1/8 11:37 @Filename：UserLog
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface OptLog {

  Target target();

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

package com.yushang.risk.common.util;

import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * @Author：zlp @Package：com.yushang.risk.common.util @Project：risk_assessment
 *
 * @name：SpelUtils @Date：2023/12/25 14:58 @Filename：SpelUtils
 */
public class SpelUtils {
  private static final SpelExpressionParser PARSER = new SpelExpressionParser();
  /**
   * 获取方法的默认全限定类名
   *
   * @param method
   * @return
   */
  public static String getDefaultPrefix(Method method) {
    return method.getDeclaringClass().getName() + "#" + method.getName();
  }

  /**
   * 解析SpEl表达式
   *
   * @param spelKey
   * @param args
   * @param method
   * @return
   */
  public static String parseSpel(String spelKey, Object[] args, Method method) {
    StandardEvaluationContext context = new StandardEvaluationContext();
    Parameter[] parameters = method.getParameters();
    for (int i = 0; i < parameters.length; i++) {
      context.setVariable(parameters[i].getName(), args[i]);
    }
    Expression expression = PARSER.parseExpression(spelKey);
    Object value = expression.getValue(context);
    return String.valueOf(value);
  }
}

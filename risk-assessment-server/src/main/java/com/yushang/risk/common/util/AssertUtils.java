package com.yushang.risk.common.util;

import cn.hutool.core.util.ObjectUtil;
import java.text.MessageFormat;
import java.util.*;

import com.yushang.risk.common.exception.BusinessException;
import com.yushang.risk.common.exception.CommonErrorEnum;
import com.yushang.risk.common.exception.ErrorEnum;
import org.hibernate.validator.HibernateValidator;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

/**
 * 断言异常工具类
 *
 * @name：AssertUtils @Date：2023/11/4 8:48 @Filename：AssertUtils
 */
public class AssertUtils {
  /**
   * 断言对象不为null
   *
   * @param o
   * @param msg
   */
  public static void assertNotNull(Object o, String msg) {
    if (o == null) {
      throw new BusinessException(msg);
    }
  }

  public static void assertNotZero(Integer o, String msg) {
    if (o == null || o == 0) {
      throw new BusinessException(msg);
    }
  }

  public static void assertIsZero(Integer o, String msg) {
    if (o != 0) {
      throw new BusinessException(msg);
    }
  }
  /** 校验到失败就结束 */
  private static Validator failFastValidator =
      Validation.byProvider(HibernateValidator.class)
          .configure()
          .failFast(true)
          .buildValidatorFactory()
          .getValidator();

  /** 全部校验 */
  private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

  /**
   * 注解验证参数(校验到失败就结束)
   *
   * @param obj
   */
  public static <T> void fastFailValidate(T obj) {
    Set<ConstraintViolation<T>> constraintViolations = failFastValidator.validate(obj);
    if (constraintViolations.size() > 0) {
      throwException(
          CommonErrorEnum.PARAM_INVALID, constraintViolations.iterator().next().getMessage());
    }
  }

  /**
   * 注解验证参数(全部校验,抛出异常)
   *
   * @param obj
   */
  public static <T> void allCheckValidateThrow(T obj) {
    Set<ConstraintViolation<T>> constraintViolations = validator.validate(obj);
    if (constraintViolations.size() > 0) {
      StringBuilder errorMsg = new StringBuilder();
      Iterator<ConstraintViolation<T>> iterator = constraintViolations.iterator();
      while (iterator.hasNext()) {
        ConstraintViolation<T> violation = iterator.next();
        // 拼接异常信息
        errorMsg
            .append(violation.getPropertyPath().toString())
            .append(":")
            .append(violation.getMessage())
            .append(",");
      }
      // 去掉最后一个逗号
      throwException(
          CommonErrorEnum.PARAM_INVALID, errorMsg.toString().substring(0, errorMsg.length() - 1));
    }
  }

  /**
   * 注解验证参数(全部校验,返回异常信息集合)
   *
   * @param obj
   */
  public static <T> Map<String, String> allCheckValidate(T obj) {
    Set<ConstraintViolation<T>> constraintViolations = validator.validate(obj);
    if (constraintViolations.size() > 0) {
      Map<String, String> errorMessages = new HashMap<>();
      Iterator<ConstraintViolation<T>> iterator = constraintViolations.iterator();
      while (iterator.hasNext()) {
        ConstraintViolation<T> violation = iterator.next();
        errorMessages.put(violation.getPropertyPath().toString(), violation.getMessage());
      }
      return errorMessages;
    }
    return new HashMap<>();
  }

  // 如果不是true，则抛异常
  public static void isTrue(boolean expression, String msg) {
    if (!expression) {
      throwException(msg);
    }
  }

  public static void isTrue(boolean expression, ErrorEnum errorEnum, Object... args) {
    if (!expression) {
      throwException(errorEnum, args);
    }
  }

  // 如果是true，则抛异常
  public static void isFalse(boolean expression, String msg) {
    if (expression) {
      throwException(msg);
    }
  }

  // 如果是true，则抛异常
  public static void isFalse(boolean expression, ErrorEnum errorEnum, Object... args) {
    if (expression) {
      throwException(errorEnum, args);
    }
  }

  // 如果不是非空对象，则抛异常
  public static void isNotEmpty(Object obj, String msg) {
    if (isEmpty(obj)) {
      throwException(msg);
    }
  }
  // 如果不是非空对象，则抛异常
  public static void isNotEmpty(List<?> list, String msg) {
    if (list == null || list.isEmpty()) {
      throwException(msg);
    }
  }

  // 如果不是非空对象，则抛异常
  public static void isNotEmpty(Object obj, ErrorEnum errorEnum, Object... args) {
    if (isEmpty(obj)) {
      throwException(errorEnum, args);
    }
  }

  // 如果不是非空对象，则抛异常
  public static void isEmpty(Object obj, String msg) {
    if (!isEmpty(obj)) {
      throwException(msg);
    }
  }

  public static void equal(Object o1, Object o2, String msg) {
    if (!ObjectUtil.equal(o1, o2)) {
      throwException(msg);
    }
  }

  public static void notEqual(Object o1, Object o2, String msg) {
    if (ObjectUtil.equal(o1, o2)) {
      throwException(msg);
    }
  }

  private static boolean isEmpty(Object obj) {
    return ObjectUtil.isEmpty(obj);
  }

  private static void throwException(String msg) {
    throwException(null, msg);
  }

  private static void throwException(ErrorEnum errorEnum, Object... arg) {
    if (Objects.isNull(errorEnum)) {
      errorEnum = CommonErrorEnum.BUSINESS_ERROR;
    }
    throw new BusinessException(
        errorEnum.getErrorCode(), MessageFormat.format(errorEnum.getErrorMsg(), arg));
  }
}

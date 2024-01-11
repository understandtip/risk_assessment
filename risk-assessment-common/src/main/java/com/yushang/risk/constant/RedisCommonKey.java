package com.yushang.risk.constant;
/**
 * @Author：zlp @Package：com.yushang.risk.constant @Project：risk_assessment
 *
 * @name：RedisCommonKey @Date：2024/1/11 14:57 @Filename：RedisCommonKey
 */
public class RedisCommonKey {

  private static final String PREFIX = "common:";
  /** */
  public static final String USER_REDIS_CODE_PREFIX = PREFIX + "user:visit";
  /**
   * 组装Redis的key
   *
   * @param format
   * @param args
   * @return
   */
  public static String getKey(String format, Object... args) {
    return String.format(format, args);
  }
}

package com.yushang.risk.common.constant;
/**
 * @Author：zlp @Package：com.yushang.risk.common.constant @Project：risk_assessment
 *
 * @name：RedisConstant @Date：2023/12/21 14:02 @Filename：RedisConstant
 */
public class RedisKey {
  /** 用户获取随机验证码，验证码存入Redis中对应的key 的前缀 */
  public static final String USER_REDIS_CODE_PREFIX = "user:code:ip_%s";
  /** 用户token放入Redis中key 的前缀 */
  public static final String USER_REDIS_TOKEN_PREFIX = "user:token:uid_%d";

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

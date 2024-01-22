package com.yushang.risk.constant;
/**
 * @Author：zlp @Package：com.yushang.risk.constant @Project：risk_assessment
 *
 * @name：RedisCommonKey @Date：2024/1/11 14:57 @Filename：RedisCommonKey
 */
public class RedisCommonKey {

  private static final String PREFIX = "common:";

  /** 用户访问key */
  public static final String USER_VISIT_PROJECT_KEY = PREFIX + "user:visit";
  /** 用户下载报告key */
  public static final String USER_DOWNLOAD_FILE_KEY = PREFIX + "user:download:all";
  /** 用户每天的下载报告数 */
  public static final String USER_DOWNLOAD_FILE_DAY_KEY = PREFIX + "user:download:day";
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

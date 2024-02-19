package com.yushang.risk.constant;

import net.sf.jsqlparser.expression.operators.conditional.OrExpression;

/**
 * @Author：zlp @Package：com.yushang.risk.constant @Project：risk_assessment
 *
 * @name：RedisCommonKey @Date：2024/1/11 14:57 @Filename：RedisCommonKey
 */
public class RedisCommonKey {

  private static final String PREFIX = "common:";
  /** 后台redis前缀 */
  public static final String ADMIN_PREFIX = "admin:";
  /** 前台redis前缀 */
  public static final String FRONT_PREFIX = "risk:";
  /** 用户token */
  public static final String USER_REDIS_TOKEN_PREFIX = "user:token:uid_%d";
  /** 在线用户key */
  public static final String USER_REDIS_ONLINE = "user:online:uid_%d";

  /** 用户访问key */
  public static final String USER_VISIT_PROJECT_KEY = PREFIX + "user:visit";
  /** 用户下载报告key */
  public static final String USER_DOWNLOAD_FILE_KEY = PREFIX + "user:download:all";
  /** 用户每天的下载报告数 */
  public static final String USER_DOWNLOAD_FILE_DAY_KEY = PREFIX + "user:download:day";
  /** 用户每天生成报告数 */
  public static final String USER_GENERATE_PORT_DAY_KEY = PREFIX + "user:generate_port:day";
  /** 用户生成报告总数 */
  public static final String USER_GENERATE_PORT_KEY = PREFIX + "user:generate_port";
  /** 用户访问每小时记录的key */
  public static final String USER_VISIT_HOUR_KEY = PREFIX + "user:visit:hour";
  /** 用户每天的访问数据 */
  public static final String USER_VISIT_DAY_KEY = PREFIX + "user:visit:day";
  /** 用户生成报告每小时记录的key */
  public static final String USER_PORT_HOUR_KEY = PREFIX + "user:port:hour";
  /** 用户每天的生成报告数据 */
  public static final String USER_PORT_DAY_KEY = PREFIX + "user:port:day";
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

package com.yushang.risk.common.task;

import com.yushang.risk.common.constant.RedisKey;
import com.yushang.risk.common.util.RedisUtils;
import com.yushang.risk.constant.RedisCommonKey;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author：zlp @Package：com.yushang.risk.common.task @Project：risk_assessment
 *
 * @name：UserVisitTask @Date：2024/1/22 11:09 @Filename：UserVisitTask
 */
@Component
@Slf4j
public class UserTrendTask {
  @Scheduled(cron = "0 0 6-23 * * ?")
  public void userVisitTask() {
    log.info("用户趋势定时任务执行...");
    // 处理用户访问趋势
    String str = RedisUtils.getStr(RedisKey.USER_VISIT);
    String str2 = RedisUtils.getStr(RedisCommonKey.USER_GENERATE_PORT_DAY_KEY);
    int hour = LocalDateTime.now().getHour();
    RedisUtils.hset(RedisCommonKey.USER_VISIT_HOUR_KEY, String.valueOf(hour), str);
    // 记录每小时报告
    RedisUtils.hset(
        RedisCommonKey.USER_PORT_HOUR_KEY,
        String.valueOf(hour),
        StringUtils.isBlank(str2) ? String.valueOf(0) : str2);
    if (hour == 23) {
      log.info("准备清零");
      // 清零
      RedisUtils.del(RedisKey.USER_VISIT);
      // 取小时值
      Map<String, Object> hmget = new HashMap<>();
      RedisUtils.hmget(RedisCommonKey.USER_VISIT_HOUR_KEY)
          .forEach(
              (hourK, value) -> {
                hmget.put(String.valueOf(hourK), value);
              });
      // 设置值--->供查询
      RedisUtils.hmset(RedisCommonKey.USER_VISIT_DAY_KEY, hmget);
      // 清零
      RedisUtils.del(RedisCommonKey.USER_VISIT_HOUR_KEY);
      // 处理生成报告趋势
      // 取小时值
      Map<String, Object> hmgetPort = new HashMap<>();
      RedisUtils.hmget(RedisCommonKey.USER_PORT_HOUR_KEY)
          .forEach(
              (hourK, value) -> {
                hmgetPort.put(String.valueOf(hourK), value);
              });
      // 设置值--->供查询
      RedisUtils.hmset(RedisCommonKey.USER_PORT_DAY_KEY, hmgetPort);
      // 清零
      RedisUtils.del(RedisCommonKey.USER_PORT_HOUR_KEY);
    }
  }
}

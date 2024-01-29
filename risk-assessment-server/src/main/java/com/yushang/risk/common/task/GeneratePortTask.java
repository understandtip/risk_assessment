package com.yushang.risk.common.task;

import com.yushang.risk.constant.RedisCommonKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * @Author：zlp @Package：com.yushang.risk.common.task @Project：risk_assessment
 *
 * @name：GenaratePortTask @Date：2024/1/22 15:56 @Filename：GenaratePortTask
 */
@Component
@Slf4j
public class GeneratePortTask {
  @Resource private StringRedisTemplate stringRedisTemplate;

  @Scheduled(cron = "0 0 0 * * ?")
  public void dealRedisKey() {
    log.error("生成报告-->定时任务触发");

    // 执行 lua 脚本
    DefaultRedisScript<Long> defaultRedisScript = new DefaultRedisScript<>();
    // 指定返回类型
    defaultRedisScript.setResultType(Long.class);
    // 指定 lua 脚本
    defaultRedisScript.setScriptSource(
        new ResourceScriptSource(new ClassPathResource("lua/GeneratePortLua.lua")));
    stringRedisTemplate.execute(
        defaultRedisScript,
        Arrays.asList(
            RedisCommonKey.USER_GENERATE_PORT_DAY_KEY, RedisCommonKey.USER_GENERATE_PORT_KEY));
  }
}

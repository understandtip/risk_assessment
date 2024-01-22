package com.yushang.risk.common.task;

import com.yushang.risk.common.util.RedisUtils;
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
import java.util.Collections;

/**
 * @Author：zlp @Package：com.yushang.risk.common.task @Project：risk_assessment
 *
 * @name：FileDownLoadTask @Date：2024/1/12 14:07 @Filename：FileDownLoadTask
 */
@Component
@Slf4j
public class FileDownLoadTask {
  @Resource private StringRedisTemplate stringRedisTemplate;

  // @Scheduled(cron = "0 59 23 * * ?")
  @Scheduled(cron = "30 0 0 * * ?")
  public void dealRedisKey() {
    log.error("定时任务触发");
    // 执行 lua 脚本
    DefaultRedisScript<Long> defaultRedisScript = new DefaultRedisScript<>();
    // 指定返回类型
    defaultRedisScript.setResultType(Long.class);
    // 指定 lua 脚本
    defaultRedisScript.setScriptSource(
        new ResourceScriptSource(new ClassPathResource("lua/FileDownCountLua.lua")));
    stringRedisTemplate.execute(
        defaultRedisScript,
        Arrays.asList(
            RedisCommonKey.USER_DOWNLOAD_FILE_DAY_KEY, RedisCommonKey.USER_DOWNLOAD_FILE_KEY));
  }
}

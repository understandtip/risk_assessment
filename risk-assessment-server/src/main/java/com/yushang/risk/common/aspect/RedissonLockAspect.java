package com.yushang.risk.common.aspect;

import com.yushang.risk.common.annotation.MyRedissonLock;
import com.yushang.risk.common.exception.CommonErrorEnum;
import com.yushang.risk.common.util.AssertUtils;
import com.yushang.risk.common.util.SpelUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;

/**
 * @Author：zlp @Package：com.yushang.risk.common.aspect @Project：risk_assessment
 *
 * @name：RedissonLockAspect @Date：2023/12/25 14:45 @Filename：RedissonLockAspect
 */
@Aspect
@Component
@Slf4j
public class RedissonLockAspect {
  @Resource private RedissonClient redissonClient;

  @SneakyThrows
  @Around(value = "@annotation(myRedissonLock)")
  public Object aroundRedissonLock(ProceedingJoinPoint joinPoint, MyRedissonLock myRedissonLock) {
    Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
    // 组装Redisson的key
    String prefix = myRedissonLock.prefix();
    if (StringUtils.isEmpty(prefix)) {
      prefix = SpelUtils.getDefaultPrefix(method);
    }
    String key = myRedissonLock.key();
    // 解析key
    String spelKey = SpelUtils.parseSpel(key, joinPoint.getArgs(), method);
    key = prefix + ":" + spelKey;
    RLock lock = redissonClient.getLock(key);
    boolean b = lock.tryLock(myRedissonLock.waitTime(), myRedissonLock.timeUnit());
    AssertUtils.isTrue(b, CommonErrorEnum.LOCK_LIMIT);
    try {
      return joinPoint.proceed();
    } finally {
      lock.unlock();
    }
  }
}

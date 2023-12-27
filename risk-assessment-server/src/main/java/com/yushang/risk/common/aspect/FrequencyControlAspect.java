package com.yushang.risk.common.aspect;

import com.yushang.risk.common.annotation.FrequencyControl;
import com.yushang.risk.common.domain.vo.ApiResult;
import com.yushang.risk.common.exception.BusinessException;
import com.yushang.risk.common.exception.CommonErrorEnum;
import com.yushang.risk.common.util.RequestHolder;
import com.yushang.risk.common.util.SpelUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Collections;

/**
 * @Author：zlp @Package：com.yushang.risk.common.aspect @Project：risk_assessment
 *
 * @name：FrequencyControlAspect @Date：2023/12/26 14:32 @Filename：FrequencyControlAspect
 */
@Aspect
@Slf4j
@Component
public class FrequencyControlAspect {
  @Resource private StringRedisTemplate stringRedisTemplate;
  /**
   * 自定义频控注解切面处理
   *
   * @param joinPoint
   * @param frequencyControl
   * @return
   * @throws Throwable
   */
  @Around(value = "@annotation(frequencyControl)")
  public Object frequencyControlAround(
      ProceedingJoinPoint joinPoint, FrequencyControl frequencyControl) throws Throwable {
    // 组装key
    Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
    String prefix = frequencyControl.prefix();
    if (StringUtils.isEmpty(prefix)) {
      prefix = SpelUtils.getDefaultPrefix(method);
    }
    String spelValue = null;
    if (StringUtils.isNotBlank(frequencyControl.spel())) {
      spelValue = ":" + SpelUtils.parseSpel(frequencyControl.spel(), joinPoint.getArgs(), method);
    }
    String finalKey = prefix + spelValue;
    switch (frequencyControl.target()) {
      case UID:
        finalKey = finalKey + ":" + RequestHolder.get().getUid();
        break;
      case IP:
        finalKey = finalKey + ":" + RequestHolder.get().getIp();
        break;
      default:
        // PUBLIC
        // 获取 HttpServletRequest 对象
        ServletRequestAttributes attributes =
            (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        finalKey = finalKey + ":" + request.getSession().getId();
        break;
    }
    // 执行 lua 脚本
    DefaultRedisScript<Long> defaultRedisScript = new DefaultRedisScript<>();
    // 指定返回类型
    defaultRedisScript.setResultType(Long.class);
    // 指定 lua 脚本
    defaultRedisScript.setScriptSource(
        new ResourceScriptSource(new ClassPathResource("lua/FrequencyControlLua.lua")));
    Long execute =
        stringRedisTemplate.execute(
            defaultRedisScript,
            Collections.singletonList(finalKey),
            String.valueOf(frequencyControl.timeUnit().toSeconds(frequencyControl.time())),
            String.valueOf(frequencyControl.count()));
    if (execute == null || execute == 0) {
      throw new BusinessException(CommonErrorEnum.FREQUENCY_LIMIT);
    }
    return joinPoint.proceed();
  }
}

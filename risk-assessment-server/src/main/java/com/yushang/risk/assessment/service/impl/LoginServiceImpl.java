package com.yushang.risk.assessment.service.impl;

import com.yushang.risk.assessment.service.LoginService;
import com.yushang.risk.common.annotation.MyRedissonLock;
import com.yushang.risk.common.constant.RedisKey;
import com.yushang.risk.common.util.JwtUtils;
import com.yushang.risk.common.util.RedisUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @Author：zlp @Package：com.yushang.risk.assessment.service.impl @Project：risk_assessment
 *
 * @name：LoginServiceImpl @Date：2023/12/25 10:10 @Filename：LoginServiceImpl
 */
@Service
public class LoginServiceImpl implements LoginService {
  public static final int REDIS_TOKEN_EXPIRE_TIME = 3;
  @Resource private JwtUtils jwtUtils;
  /**
   * 登录成功,获取token(todo:分布式锁保证线程安全)
   *
   * @param uid
   * @return
   */
  @Override
  @MyRedissonLock(key = "#uid")
  public String login(Integer uid) {
    String token = jwtUtils.createToken(uid);
    // 删除之前的token,防止多账号同时登录
    String key = RedisKey.getKey(RedisKey.USER_REDIS_TOKEN_PREFIX, uid);
    try {
      Thread.sleep(50000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    RedisUtils.del(key);
    RedisUtils.set(key, token, REDIS_TOKEN_EXPIRE_TIME, TimeUnit.DAYS);
    return token;
  }

  /**
   * 从token中解析出uid(并且查看Redis中的token有没有过期)
   *
   * @param token
   * @return
   */
  @Override
  public Integer getValidUid(String token) {
    Integer uid = jwtUtils.getUidOrNull(token);
    if (uid == null) return null;
    String redisToken = RedisUtils.getStr(RedisKey.getKey(RedisKey.USER_REDIS_TOKEN_PREFIX, uid));
    if (StringUtils.isEmpty(redisToken) || !token.equals(redisToken)) return null;
    return uid;
  }

  /**
   * 刷新token的有效期
   *
   * @param token
   */
  @Override
  public void renewalTokenIfNecessary(String token) {
    Integer validUid = this.getValidUid(token);
    if (validUid == null) return;
    String key = RedisKey.getKey(RedisKey.USER_REDIS_TOKEN_PREFIX, validUid);
    Long expire = RedisUtils.getExpire(key);
    // Redis中的token已经过期了
    if (expire == -2) return;
    RedisUtils.expire(key, REDIS_TOKEN_EXPIRE_TIME, TimeUnit.DAYS);
  }
}

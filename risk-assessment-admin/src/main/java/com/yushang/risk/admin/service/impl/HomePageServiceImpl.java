package com.yushang.risk.admin.service.impl;

import com.yushang.risk.admin.service.HomePageService;
import com.yushang.risk.common.constant.NormalConstant;
import com.yushang.risk.common.util.RedisUtils;
import com.yushang.risk.constant.RedisCommonKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Author：zlp @Package：com.yushang.risk.admin.service.impl @Project：risk_assessment
 *
 * @name：HomePageServiceImpl @Date：2024/1/11 15:53 @Filename：HomePageServiceImpl
 */
@Service
@Slf4j
public class HomePageServiceImpl implements HomePageService {
  /**
   * 查询访问量
   *
   * @return
   */
  @Override
  public Long getVisitNum() {
    return RedisUtils.zCount(
        RedisCommonKey.USER_REDIS_CODE_PREFIX,
        System.currentTimeMillis() - NormalConstant.VISIT_COUNT_TIME_MSEC,
        System.currentTimeMillis());
  }
}

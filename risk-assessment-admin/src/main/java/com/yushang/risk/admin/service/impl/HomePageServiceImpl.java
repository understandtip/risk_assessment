package com.yushang.risk.admin.service.impl;

import com.yushang.risk.admin.dao.UsersDao;
import com.yushang.risk.admin.service.HomePageService;
import com.yushang.risk.common.constant.NormalConstant;
import com.yushang.risk.common.util.RedisUtils;
import com.yushang.risk.constant.RedisCommonKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @Author：zlp @Package：com.yushang.risk.admin.service.impl @Project：risk_assessment
 *
 * @name：HomePageServiceImpl @Date：2024/1/11 15:53 @Filename：HomePageServiceImpl
 */
@Service
@Slf4j
public class HomePageServiceImpl implements HomePageService {
  @Resource private UsersDao usersDao;
  /**
   * 查询访问量
   *
   * @return
   */
  @Override
  public Long getVisitNum() {
    return RedisUtils.zCount(
        RedisCommonKey.USER_VISIT_PROJECT_KEY,
        System.currentTimeMillis() - NormalConstant.MONTH_TIME_MSEC,
        System.currentTimeMillis());
  }

  @Override
  public Long getVisitNumAll() {
    return RedisUtils.zCount(RedisCommonKey.USER_VISIT_PROJECT_KEY, 0, System.currentTimeMillis());
  }

  /**
   * 获取月增用户数
   *
   * @return
   */
  @Override
  public Long getAddedUser() {
    return usersDao.getAddedUser();
  }

  /**
   * 获取总增用户数
   *
   * @return
   */
  @Override
  public Long getAddedUserAll() {
    return usersDao.getAddedUserAll();
  }

  /**
   * 获取年报告下载数
   *
   * @return
   */
  @Override
  public Long getDownLoadOfYear() {
    Set<String> set =
        RedisUtils.zRange(
            RedisCommonKey.USER_DOWNLOAD_FILE_KEY,
            System.currentTimeMillis() - NormalConstant.YEAR_TIME_MSEC,
            System.currentTimeMillis());
    AtomicReference<Long> res = new AtomicReference<>(0L);
    set.forEach(
        val -> {
          long l = Long.parseLong(val);
          res.updateAndGet(v -> v + l);
        });

    return res.get();
  }

  /**
   * 获取总报告下载数
   *
   * @return
   */
  @Override
  public Long getDownLoadAll() {
    Set<String> set =
        RedisUtils.zRange(RedisCommonKey.USER_DOWNLOAD_FILE_KEY, 0, System.currentTimeMillis());
    AtomicReference<Long> res = new AtomicReference<>(0L);
    set.forEach(
        val -> {
          long l = Long.parseLong(val);
          res.updateAndGet(v -> v + l);
        });
    return res.get();
  }
}

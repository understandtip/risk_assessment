package com.yushang.risk.admin.service;
/**
 * @Author：zlp @Package：com.yushang.risk.admin.service @Project：risk_assessment
 *
 * @name：HomePageService @Date：2024/1/11 15:52 @Filename：HomePageService
 */
public interface HomePageService {
  /**
   * 查询访问量
   *
   * @return
   */
  Long getVisitNum();

  /**
   * 查询总访问量
   *
   * @return
   */
  Long getVisitNumAll();

  /**
   * 获取月增用户数
   *
   * @return
   */
  Long getAddedUser();

  /**
   * 获取总增用户数
   *
   * @return
   */
  Long getAddedUserAll();

  /**
   * 获取年报告下载数
   *
   * @return
   */
  Long getDownLoadOfYear();

  /**
   * 获取总报告下载数
   *
   * @return
   */
  Long getDownLoadAll();
}

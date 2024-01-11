package com.yushang.risk.admin.service;

import org.springframework.stereotype.Service;

/**
 * @Author：zlp @Package：com.yushang.risk.admin.service @Project：risk_assessment
 *
 * @name：LoginService @Date：2024/1/11 11:17 @Filename：LoginService
 */
public interface LoginService {
  /**
   * 用户登录
   *
   * @param id
   * @return
   */
  String login(Integer id);

  /**
   * 从token中获取用户uid
   *
   * @param s
   * @return
   */
  Integer getValidUid(String s);
}

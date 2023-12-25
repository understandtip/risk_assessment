package com.yushang.risk.assessment.service;
/**
 * @Author：zlp @Package：com.yushang.risk.assessment.service @Project：risk_assessment
 *
 * @name：LoginService @Date：2023/12/25 10:07 @Filename：LoginService
 */
public interface LoginService {
  /**
   * 登录成功,获取token
   *
   * @param uid
   * @return
   */
  String login(Integer uid);

  /**
   * 如果token有效,解析出uid
   *
   * @param token
   * @return
   */
  Integer getValidUid(String token);
  /**
   * 刷新token有效期(续期操作)
   *
   * @param token
   */
  void renewalTokenIfNecessary(String token);
}

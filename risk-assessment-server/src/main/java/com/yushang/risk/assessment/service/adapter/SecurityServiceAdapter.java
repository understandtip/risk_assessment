package com.yushang.risk.assessment.service.adapter;

import com.yushang.risk.domain.entity.SUser;
import com.yushang.risk.assessment.domain.vo.request.SecurityServiceBugBugReq;

/**
 * @Author：zlp @Package：com.yushang.risk.assessment.service.adapter @Project：risk_assessment
 *
 * @name：SecurityServiceAdapter @Date：2024/1/16 9:49 @Filename：SecurityServiceAdapter
 */
public class SecurityServiceAdapter {
  /**
   * 构建安全服务提交漏洞项目时的用户对象
   *
   * @param bugReq
   * @return
   */
  public static SUser buildSUser(SecurityServiceBugBugReq bugReq) {
    return SUser.builder()
        .serviceId(bugReq.getId())
        .name(bugReq.getName())
        .phone(bugReq.getPhone())
        .email(bugReq.getEmail())
        .build();
  }
}

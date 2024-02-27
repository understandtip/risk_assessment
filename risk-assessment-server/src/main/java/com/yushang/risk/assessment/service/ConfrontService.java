package com.yushang.risk.assessment.service;

import com.yushang.risk.assessment.domain.vo.response.ConfrontInfoResp;

/**
 * @Author：zlp @Package：com.yushang.risk.assessment.service @Project：risk_assessment
 *
 * @name：ConfrontService @Date：2024/2/23 15:25 @Filename：ConfrontService
 */
public interface ConfrontService {
  /**
   * 获取对抗数据
   *
   * @return
   */
  ConfrontInfoResp getConfrontInfo();
}

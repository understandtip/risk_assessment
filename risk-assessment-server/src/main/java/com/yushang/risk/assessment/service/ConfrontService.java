package com.yushang.risk.assessment.service;

import com.yushang.risk.assessment.domain.vo.request.ConfrontSubReq;
import com.yushang.risk.assessment.domain.vo.response.ConfrontInfoResp;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

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
   * @param isEnhance
   */
  List<ConfrontInfoResp> getConfrontInfo(boolean isEnhance);

  /**
   * 生成报告
   *
   * @param confrontSubReq
   * @param outputStream
   */
  void submitPort(ConfrontSubReq confrontSubReq, ByteArrayOutputStream outputStream)
      throws IOException;
}

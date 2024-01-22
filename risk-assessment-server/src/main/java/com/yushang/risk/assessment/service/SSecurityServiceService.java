package com.yushang.risk.assessment.service;

import com.yushang.risk.assessment.domain.vo.request.SecurityServiceBugBugReq;
import com.yushang.risk.assessment.domain.vo.response.SecurityModelDetailResp;
import com.yushang.risk.assessment.domain.vo.response.SecurityModelResp;
import com.yushang.risk.common.event.domaih.dto.SUserDto;

import java.util.List;

/**
 * 安全服务 服务类
 *
 * @author zlp
 * @since 2024-01-15
 */
public interface SSecurityServiceService {
  /**
   * 获取安全服务模块列表
   *
   * @return
   */
  List<SecurityModelResp> getSecurityModel();

  /**
   * 查看模块详细信息
   *
   * @return
   * @param modelId
   */
  SecurityModelDetailResp getModelDetail(Integer modelId);

  /**
   * 提交漏洞项目
   *
   * @param bugReq
   */
  void submitBugReport(SecurityServiceBugBugReq bugReq);

  /**
   * 生成漏洞报告
   *
   * @param dto
   * @return
   */
  String generateBugReport(SUserDto dto);
}

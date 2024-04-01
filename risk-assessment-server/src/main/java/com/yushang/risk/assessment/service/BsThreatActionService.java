package com.yushang.risk.assessment.service;

import com.yushang.risk.assessment.domain.entity.BsThreatAction;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yushang.risk.assessment.domain.vo.response.BsAllThreatActionInfoResp;

import java.util.List;

/**
 * 威胁行为 服务类
 *
 * @author zlp
 * @since 2024-03-20
 */
public interface BsThreatActionService {
  /**
   * 获取所有威胁行为
   *
   * @return
   */
  List<BsAllThreatActionInfoResp> getThreatInfo();
}

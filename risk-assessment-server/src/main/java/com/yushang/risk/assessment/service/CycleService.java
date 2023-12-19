package com.yushang.risk.assessment.service;

import com.yushang.risk.assessment.domain.entity.Cycle;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yushang.risk.assessment.domain.vo.response.CycleResp;

import java.util.List;

/**
 * 周期 服务类
 *
 * @author zlp
 * @since 2023-12-18
 */
public interface CycleService {
  /**
   * 获取所有周期集合
   *
   * @return
   */
  List<CycleResp> getCycleList();
}

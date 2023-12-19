package com.yushang.risk.assessment.service.impl;

import com.yushang.risk.assessment.dao.CycleDao;
import com.yushang.risk.assessment.domain.entity.Cycle;
import com.yushang.risk.assessment.domain.vo.response.CycleResp;
import com.yushang.risk.assessment.service.CycleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author：zlp @Package：com.yushang.risk.assessment.service.impl @Project：risk_assessment
 *
 * @name：CycleServiceImpl @Date：2023/12/18 16:25 @Filename：CycleServiceImpl
 */
@Service
public class CycleServiceImpl implements CycleService {
  @Resource private CycleDao cycleDao;
  /**
   * 获取所有周期集合
   *
   * @return
   */
  @Override
  public List<CycleResp> getCycleList() {
    return cycleDao.getCycleList();
  }
}

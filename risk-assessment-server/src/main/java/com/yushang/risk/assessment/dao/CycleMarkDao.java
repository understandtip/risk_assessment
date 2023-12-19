package com.yushang.risk.assessment.dao;

import com.yushang.risk.assessment.domain.entity.CycleMark;
import com.yushang.risk.assessment.mapper.CycleMarkMapper;
import com.yushang.risk.assessment.service.CycleMarkService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 周期标记 服务实现类
 *
 * @author zlp
 * @since 2023-12-19
 */
@Service
public class CycleMarkDao extends ServiceImpl<CycleMarkMapper, CycleMark> {
  /**
   * 根据周期id查询对应的标志信息
   *
   * @param cycleIds
   * @return
   */
  public List<CycleMark> getMarkById(List<Integer> cycleIds) {
    return this.lambdaQuery().in(CycleMark::getCycleId, cycleIds).list();
  }
}

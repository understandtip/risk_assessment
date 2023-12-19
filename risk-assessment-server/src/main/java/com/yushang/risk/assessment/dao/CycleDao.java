package com.yushang.risk.assessment.dao;

import com.yushang.risk.assessment.domain.entity.Cycle;
import com.yushang.risk.assessment.domain.entity.CycleMark;
import com.yushang.risk.assessment.domain.vo.response.CycleResp;
import com.yushang.risk.assessment.mapper.CycleMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yushang.risk.assessment.service.adapter.CycleAdapter;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 周期 服务实现类
 *
 * @author zlp
 * @since 2023-12-18
 */
@Service
public class CycleDao extends ServiceImpl<CycleMapper, Cycle> {
  @Resource private CycleMarkDao cycleMarkDao;
  /**
   * 获取周期集合
   *
   * @return
   */
  public List<CycleResp> getCycleList() {
    List<Cycle> cycleList = this.list();
    List<Integer> cycleIds = cycleList.stream().map(Cycle::getId).collect(Collectors.toList());
    List<CycleMark> markList = cycleMarkDao.getMarkById(cycleIds);
    List<CycleResp> cycleRespList = CycleAdapter.buildCycleResp(cycleList, markList);
    return cycleRespList;
  }
}

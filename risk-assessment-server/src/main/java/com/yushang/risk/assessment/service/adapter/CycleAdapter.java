package com.yushang.risk.assessment.service.adapter;

import com.yushang.risk.assessment.domain.entity.Cycle;
import com.yushang.risk.assessment.domain.entity.CycleMark;
import com.yushang.risk.assessment.domain.vo.response.CycleResp;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author：zlp @Package：com.yushang.risk.assessment.service.adapter @Project：risk_assessment
 *
 * @name：CycleAdapter @Date：2023/12/19 9:20 @Filename：CycleAdapter
 */
public class CycleAdapter {

  /**
   * 构建CycleResp返回对象
   *
   * @param cycleList
   * @param markList
   * @return
   */
  public static List<CycleResp> buildCycleResp(List<Cycle> cycleList, List<CycleMark> markList) {
    List<CycleResp> list = new ArrayList<>();
    cycleList.forEach(
        cycle -> {
          CycleResp cycleResp = new CycleResp();
          BeanUtils.copyProperties(cycle, cycleResp);
          List<String> marks = new ArrayList<>();
          markList.forEach(
              mark -> {
                if (mark.getCycleId().equals(cycle.getId())) {
                  marks.add(mark.getName());
                }
              });
          cycleResp.setCycleMarkList(marks);
          list.add(cycleResp);
        });
    return list;
  }
}

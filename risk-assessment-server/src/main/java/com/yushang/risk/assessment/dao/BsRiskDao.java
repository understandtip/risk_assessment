package com.yushang.risk.assessment.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yushang.risk.assessment.domain.entity.BsRisk;
import com.yushang.risk.assessment.mapper.BsRiskMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 业务风险 服务实现类
 *
 * @author zlp
 * @since 2024-03-05
 */
@Service
public class BsRiskDao extends ServiceImpl<BsRiskMapper, BsRisk> {
  /**
   * 查询pid为0的Risk
   *
   * @return
   */
  public List<BsRisk> listForPid0() {
    LambdaQueryWrapper<BsRisk> wrapper = new LambdaQueryWrapper<>();
    wrapper.eq(BsRisk::getPid, 0);
    return this.list(wrapper);
  }
  /**
   * @return
   */
  public List<BsRisk> listByOrder() {
    return this.lambdaQuery().orderByAsc(BsRisk::getId).list();
  }
}

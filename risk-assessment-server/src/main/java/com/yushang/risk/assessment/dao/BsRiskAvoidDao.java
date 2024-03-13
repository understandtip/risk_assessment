package com.yushang.risk.assessment.dao;

import com.yushang.risk.assessment.domain.entity.BsAvoidMeans;
import com.yushang.risk.assessment.domain.entity.BsRiskAvoid;
import com.yushang.risk.assessment.mapper.BsRiskAvoidMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 风险规避关系 服务实现类
 *
 * @author zlp
 * @since 2024-03-05
 */
@Service
public class BsRiskAvoidDao extends ServiceImpl<BsRiskAvoidMapper, BsRiskAvoid> {
  @Resource private BsAvoidMeansDao bsAvoidMeansDao;

  /**
   * 根据风险id获取风险规避
   *
   * @param risk 风险id
   * @return 风险规避列表，如果风险id为null或查询结果为空，则返回空列表
   */
  public List<BsAvoidMeans> getByRiskId(Integer risk) {
    // 验证输入参数risk是否为null，如果是则直接返回空列表
    if (risk == null) {
      return Collections.emptyList();
    }

    List<Integer> avoidIds =
        this.lambdaQuery().eq(BsRiskAvoid::getRiskId, risk).list().stream()
            .map(BsRiskAvoid::getAvoidId)
            .collect(Collectors.toList());

    // 如果avoidIds为空，直接返回空列表，避免二次查询
    if (avoidIds.isEmpty()) {
      return Collections.emptyList();
    }

    return bsAvoidMeansDao.lambdaQuery().in(BsAvoidMeans::getId, avoidIds).list();
  }
}

package com.yushang.risk.assessment.dao;

import com.yushang.risk.assessment.domain.entity.BsAttackAvoid;
import com.yushang.risk.assessment.domain.entity.BsAvoidMeans;
import com.yushang.risk.assessment.mapper.BsAttackAvoidMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 工具规避关系 服务实现类
 *
 * @author zlp
 * @since 2024-03-01
 */
@Service
public class BsAttackAvoidDao extends ServiceImpl<BsAttackAvoidMapper, BsAttackAvoid> {
  @Resource private BsAvoidMeansDao bsAvoidMeansDao;
  /**
   * 根据工具id获取规避信息
   *
   * @param toolId
   * @return
   */
  public List<BsAvoidMeans> getByToolId(Integer toolId) {
    List<Integer> avoidIds =
        this.lambdaQuery().eq(BsAttackAvoid::getAttackId, toolId).list().stream()
            .map(BsAttackAvoid::getAvoidId)
            .collect(Collectors.toList());
    return bsAvoidMeansDao.listByIds(avoidIds);
  }
}

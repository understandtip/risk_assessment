package com.yushang.risk.assessment.dao;

import com.yushang.risk.assessment.domain.entity.BsAttackTool;
import com.yushang.risk.assessment.domain.entity.BsRisk;
import com.yushang.risk.assessment.domain.entity.BsRiskTool;
import com.yushang.risk.assessment.mapper.BsRiskToolMapper;
import com.yushang.risk.assessment.service.IBsRiskToolService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 风险工具关系 服务实现类
 *
 * @author zlp
 * @since 2024-03-11
 */
@Service
public class BsRiskToolDao extends ServiceImpl<BsRiskToolMapper, BsRiskTool> {
  @Resource private BsAttackToolDao bsAttackToolDao;
  @Resource private BsRiskDao bsRiskDao;

  public List<BsAttackTool> getByRiskId(Integer riskId) {
    List<Integer> toolIds =
        this.lambdaQuery().eq(BsRiskTool::getRiskId, riskId).list().stream()
            .map(BsRiskTool::getToolId)
            .collect(Collectors.toList());
    return bsAttackToolDao.lambdaQuery().in(BsAttackTool::getId, toolIds).list();
  }

  /**
   * 根据工具id获取风险列表
   *
   * @param toolId
   * @return
   */
  public List<BsRisk> getByToolId(Integer toolId) {
    List<Integer> riskIds =
        this.lambdaQuery().eq(BsRiskTool::getToolId, toolId).list().stream()
            .map(BsRiskTool::getRiskId)
            .collect(Collectors.toList());
    return bsRiskDao.lambdaQuery().in(BsRisk::getId, riskIds).list();
  }
}

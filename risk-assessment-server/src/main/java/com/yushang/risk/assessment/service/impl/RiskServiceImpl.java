package com.yushang.risk.assessment.service.impl;

import com.yushang.risk.assessment.dao.RiskDao;
import com.yushang.risk.assessment.domain.entity.Risk;
import com.yushang.risk.assessment.domain.vo.response.RiskResp;
import com.yushang.risk.assessment.service.RiskService;
import com.yushang.risk.common.constant.RiskConstant;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.xml.ws.ResponseWrapper;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author：zlp @Package：com.yushang.risk.assessment.service.impl @Project：risk_assessment
 *
 * @name：RiskServiceImpl @Date：2023/12/18 16:13 @Filename：RiskServiceImpl
 */
@Service
public class RiskServiceImpl implements RiskService {
  @Resource private RiskDao riskDao;
  /**
   * 查询指定分类下的风险集合
   *
   * @param categoryId
   * @return
   */
  @Override
  public List<RiskResp> getRiskList(Integer categoryId) {
    List<Risk> riskList = riskDao.getRiskFromCategory(categoryId);
    return riskList.stream().map(this::dealRiskId).collect(Collectors.toList());
  }

  /**
   * 获取所有风险集合
   *
   * @return
   */
  @Override
  public List<RiskResp> getRiskList() {
    List<Risk> riskList = riskDao.list();
    return riskList.stream().map(this::dealRiskId).collect(Collectors.toList());
  }

  /**
   * 处理风险id
   *
   * @param risk
   */
  private RiskResp dealRiskId(Risk risk) {
    RiskResp riskResp = new RiskResp();
    BeanUtils.copyProperties(risk, riskResp);
    String s = String.format("%04d", risk.getId());
    riskResp.setRiskId(RiskConstant.RISK_ID_PRE + s);
    return riskResp;
  }
}

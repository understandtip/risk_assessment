package com.yushang.risk.assessment.service.impl;

import cn.hutool.core.lang.Assert;
import com.yushang.risk.assessment.dao.*;
import com.yushang.risk.assessment.domain.entity.*;
import com.yushang.risk.assessment.domain.vo.response.BsAllRiskInfoResp;
import com.yushang.risk.assessment.domain.vo.response.BsAttackToolInfoResp;
import com.yushang.risk.assessment.domain.vo.response.BsAvoidInfoResp;
import com.yushang.risk.assessment.domain.vo.response.BsRiskInfoResp;
import com.yushang.risk.assessment.service.BsRiskService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.actuate.autoconfigure.health.HealthEndpointProperties;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.xml.ws.WebServiceRef;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Author：zlp @Package：com.yushang.risk.assessment.service.impl @Project：risk_assessment
 *
 * @name：BsRiskServiceImpl @Date：2024/3/11 10:58 @Filename：BsRiskServiceImpl
 */
@Service
@Slf4j
public class BsRiskServiceImpl implements BsRiskService {
  @Resource private BsRiskDao bsRiskDao;
  @Resource private BsRiskAvoidDao bsRiskAvoidDao;
  @Resource private BsRiskToolDao bsRiskToolDao;
  @Resource private BsAvoidMeansDao bsAvoidMeansDao;
  @Resource private BsAttackToolDao bsAttackToolDao;
  @Resource private BsRiskComplexityDao bsRiskComplexityDao;
  @Resource private BsAttackAvoidDao bsAttackAvoidDao;
  /**
   * 获取风险信息
   *
   * @param riskId 风险id
   * @return 风险信息
   */
  @Override
  public BsRiskInfoResp getRiskInfo(Integer riskId) {
    Assert.notNull(riskId, "riskId cannot be null");

    BsRiskInfoResp resp = new BsRiskInfoResp();
    BsRisk risk = bsRiskDao.getById(riskId);
    if (risk == null) {
      return resp;
    }

    try {
      ModelMapper modelMapper = new ModelMapper();
      modelMapper.map(risk, resp);
      resp.setComplexity(bsRiskComplexityDao.getById(risk.getComplexityId()).getName());
      resp.setAvoidInfoList(convertToAvoidInfoList(bsRiskAvoidDao.getByRiskId(riskId)));
      resp.setAttackInfoList(convertToAttackInfoList(bsRiskToolDao.getByRiskId(riskId)));
    } catch (Exception e) {
      // 异常处理逻辑，比如记录日志等
      log.error("根据风险id获取风险信息失败", e);
    }
    return resp;
  }

  /**
   * 将避免手段列表转换为避免信息列表。
   *
   * @param avoids 需要转换的避免手段列表，类型为BsAvoidMeans的List。
   * @return 转换后的避免信息列表，类型为BsRiskInfoResp.AvoidInfo的List。
   */
  private List<BsRiskInfoResp.AvoidInfo> convertToAvoidInfoList(List<BsAvoidMeans> avoids) {
    return avoids.stream().map(this::mapToAvoidInfo).collect(Collectors.toList());
  }

  /**
   * 将攻击工具列表转换为攻击信息列表。
   *
   * @param tools 需要转换的攻击工具列表，类型为BsAttackTool的List。
   * @return 转换后的攻击信息列表，类型为BsRiskInfoResp.AttackInfo的List。
   */
  private List<BsRiskInfoResp.AttackInfo> convertToAttackInfoList(List<BsAttackTool> tools) {
    return tools.stream().map(this::mapToAttackInfo).collect(Collectors.toList());
  }

  /**
   * 将单个避免手段对象转换为避免信息对象。
   *
   * @param avoid 需要转换的避免手段对象，类型为BsAvoidMeans。
   * @return 转换后的避免信息对象，类型为BsRiskInfoResp.AvoidInfo。
   */
  private BsRiskInfoResp.AvoidInfo mapToAvoidInfo(BsAvoidMeans avoid) {
    BsRiskInfoResp.AvoidInfo avoidInfo = new BsRiskInfoResp.AvoidInfo();
    avoidInfo.setId(avoid.getId());
    avoidInfo.setName(avoid.getTitle());
    // 这里根据需要添加其他属性的映射
    return avoidInfo;
  }

  /**
   * 将单个攻击工具对象转换为攻击信息对象。
   *
   * @param tool 需要转换的攻击工具对象，类型为BsAttackTool。
   * @return 转换后的攻击信息对象，类型为BsRiskInfoResp.AttackInfo。
   */
  private BsRiskInfoResp.AttackInfo mapToAttackInfo(BsAttackTool tool) {
    BsRiskInfoResp.AttackInfo attackInfo = new BsRiskInfoResp.AttackInfo();
    attackInfo.setId(tool.getId());
    attackInfo.setName(tool.getTitle());
    // 这里根据需要添加其他属性的映射
    return attackInfo;
  }
  /**
   * 获取规避信息
   *
   * @param avoidId 规避id
   * @return 规避信息
   */
  @Override
  public BsAvoidInfoResp getAvoidInfo(Integer avoidId) {
    Assert.notNull(avoidId, "avoidId cannot be null");
    BsAvoidInfoResp resp = new BsAvoidInfoResp();
    BsAvoidMeans avoid = bsAvoidMeansDao.getById(avoidId);
    if (avoid != null) {
      BeanUtils.copyProperties(avoid, resp);
    }
    return resp;
  }
  /**
   * 获取攻击工具信息
   *
   * @param toolId 攻击工具id
   * @return 攻击工具信息
   */
  @Override
  public BsAttackToolInfoResp getToolInfo(Integer toolId) {
    // 验证toolId不为null
    Assert.notNull(toolId, "toolId cannot be null");

    BsAttackTool tool = bsAttackToolDao.getById(toolId);

    // 对tool进行空检查
    if (tool == null) {
      throw new IllegalArgumentException("Tool not found for ID: " + toolId);
    }

    BsAttackToolInfoResp resp = new BsAttackToolInfoResp();
    // 使用BeanUtils进行对象属性复制，考虑未来可能的属性排除或指定
    BeanUtils.copyProperties(tool, resp);

    // 初始化avoidInfoList
    List<BsAttackToolInfoResp.AvoidInfo> avoidInfoList = new ArrayList<>();

    // 使用Stream API优化处理AvoidInfoList
    bsAttackAvoidDao.getByToolId(toolId).stream()
        .map(
            avoid -> {
              BsAttackToolInfoResp.AvoidInfo avoidInfo = new BsAttackToolInfoResp.AvoidInfo();
              avoidInfo.setId(avoid.getId());
              avoidInfo.setName(avoid.getTitle());
              return avoidInfo;
            })
        .forEach(avoidInfoList::add);

    // 条件检查避免不必要的设置
    if (!avoidInfoList.isEmpty()) {
      resp.setAvoidInfoList(avoidInfoList);
    }

    return resp;
  }
  /**
   * 获取所有风险信息。
   *
   * @return 返回包含所有风险信息的列表。
   */
  @Override
  public List<BsAllRiskInfoResp> getAllRisk() {
    List<BsAllRiskInfoResp> resp = new ArrayList<>();
    try {
      // 获取复杂度id和名称的映射
      Map<Integer, String> complexityIdAndNameMap = getComplexityIdAndNameMap();
      // 获取风险及其规避措施的映射
      Map<Integer, List<BsRiskAvoid>> riskAndAvoidMap = getRiskAndAvoidMap();
      // 将风险与规避措施id关系转换为映射
      Map<Integer, List<Integer>> riskAndAvoidIdRelMap = convertRiskToAvoidIdMap(riskAndAvoidMap);
      // 获取规避方法的映射
      Map<Integer, BsAvoidMeans> avoidMeansMap = getAvoidMeansMap();

      // 处理每个风险信息并添加到响应列表中
      bsRiskDao
          .listByOrder()
          .forEach(
              risk ->
                  processRiskInfoResp(
                      resp, complexityIdAndNameMap, riskAndAvoidIdRelMap, avoidMeansMap, risk));
    } catch (Exception e) {
      // 记录异常
      log.error("获取所有风险失败", e);
    }

    return resp;
  }

  /**
   * 获取复杂度id和名称的映射。
   *
   * @return 返回复杂度id和名称的映射。
   */
  private Map<Integer, String> getComplexityIdAndNameMap() {
    return bsRiskComplexityDao.list().stream()
        .collect(Collectors.toMap(BsRiskComplexity::getId, BsRiskComplexity::getName));
  }

  /**
   * 获取风险及其规避措施的映射。
   *
   * @return 返回风险id与规避措施列表的映射。
   */
  private Map<Integer, List<BsRiskAvoid>> getRiskAndAvoidMap() {
    return bsRiskAvoidDao.list().stream().collect(Collectors.groupingBy(BsRiskAvoid::getRiskId));
  }

  /**
   * 将风险与规避措施id关系转换为映射。
   *
   * @param riskAndAvoidMap 风险与规避措施的映射。
   * @return 返回风险id与规避措施id列表的映射。
   */
  private Map<Integer, List<Integer>> convertRiskToAvoidIdMap(
      Map<Integer, List<BsRiskAvoid>> riskAndAvoidMap) {
    Map<Integer, List<Integer>> riskAndAvoidIdRelMap = new HashMap<>(32);
    riskAndAvoidMap.forEach(
        (riskId, avoidList) -> {
          List<Integer> avoidIds =
              avoidList.stream().map(BsRiskAvoid::getAvoidId).collect(Collectors.toList());
          riskAndAvoidIdRelMap.put(riskId, avoidIds);
        });
    return riskAndAvoidIdRelMap;
  }

  /**
   * 获取规避方法的映射。
   *
   * @return 返回规避方法id与规避方法对象的映射。
   */
  private Map<Integer, BsAvoidMeans> getAvoidMeansMap() {
    return bsAvoidMeansDao.list().stream()
        .collect(Collectors.toMap(BsAvoidMeans::getId, Function.identity()));
  }

  /**
   * 处理风险信息响应对象。
   *
   * @param resp 风险信息响应列表。
   * @param complexityIdAndNameMap 复杂度id和名称的映射。
   * @param riskAndAvoidIdRelMap 风险与规避措施id关系的映射。
   * @param avoidMeansMap 规避方法的映射。
   * @param risk 风险对象。
   */
  private void processRiskInfoResp(
      List<BsAllRiskInfoResp> resp,
      Map<Integer, String> complexityIdAndNameMap,
      Map<Integer, List<Integer>> riskAndAvoidIdRelMap,
      Map<Integer, BsAvoidMeans> avoidMeansMap,
      BsRisk risk) {
    BsAllRiskInfoResp riskInfoResp = new BsAllRiskInfoResp();
    BeanUtils.copyProperties(risk, riskInfoResp);

    // 设置风险复杂度
    riskInfoResp.setComplexity(complexityIdAndNameMap.getOrDefault(risk.getComplexityId(), "N/A"));
    // 设置规避信息列表
    List<BsAllRiskInfoResp.AvoidInfo> avoidInfoList = new ArrayList<>();
    riskAndAvoidIdRelMap
        .getOrDefault(risk.getId(), Collections.emptyList())
        .forEach(
            avoidId -> {
              BsAvoidMeans avoidMeans = avoidMeansMap.get(avoidId);
              if (avoidMeans != null) {
                BsAllRiskInfoResp.AvoidInfo avoidInfo = new BsAllRiskInfoResp.AvoidInfo();
                avoidInfo.setName(avoidMeans.getTitle());
                avoidInfo.setId(avoidId);
                avoidInfoList.add(avoidInfo);
              }
            });
    riskInfoResp.setAvoidInfoList(avoidInfoList);
    resp.add(riskInfoResp);
  }

  /**
   * 获取所有风险(小)
   *
   * @return 所有风险(小)
   */
  @Override
  public Map<Integer, String> getAllRiskSmall() {
    try {
      // 尝试从DAO层获取所有风险数据并进行流式处理
      return bsRiskDao.listByOrder().stream()
          .collect(Collectors.toMap(BsRisk::getId, BsRisk::getTitle));
    } catch (Exception e) {
      log.error("获取低风险数据时发生异常：", e);
      return Collections.emptyMap();
    }
  }
}

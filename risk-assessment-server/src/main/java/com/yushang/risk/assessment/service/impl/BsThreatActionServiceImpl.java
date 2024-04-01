package com.yushang.risk.assessment.service.impl;

import com.yushang.risk.assessment.dao.*;
import com.yushang.risk.assessment.domain.entity.*;
import com.yushang.risk.assessment.domain.enums.ThreatToolTypeEnum;
import com.yushang.risk.assessment.domain.vo.response.BsAllThreatActionInfoResp;
import com.yushang.risk.assessment.service.BsThreatActionService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author：zlp @Package：com.yushang.risk.assessment.service.impl @Project：risk_assessment
 *
 * @name：BsThreatActionServiceImpl @Date：2024/3/20 14:51 @Filename：BsThreatActionServiceImpl
 */
@Service
public class BsThreatActionServiceImpl implements BsThreatActionService {
  @Resource private BsThreatActionDao bsThreatActionDao;
  @Resource private BsThreatToolRelDao bsThreatToolRelDao;
  @Resource private BsThreaRiskRelDao bsThreaRiskRelDao;
  @Resource private BsAttackToolDao bsAttackToolDao;
  @Resource private BsRiskDao bsRiskDao;

  /**
   * 获取所有威胁行为的信息列表。
   *
   * <p>这个方法通过查询数据库获取所有的威胁行为及其相关的工具和风险信息，然后进行处理和组织，最终返回一个
   * 威胁行为信息的列表。这个方法通过一次性获取所有需要的数据来减少数据库查询的次数，以提高性能。
   *
   * @return 返回包含所有威胁行为信息的列表，每个行为信息包括其相关的工具和风险。
   */
  @Override
  public List<BsAllThreatActionInfoResp> getThreatInfo() {
    // 优化：一次性获取所有工具和风险数据，减少数据库查询次数
    Map<Integer, BsAttackTool> toolById = mapById(bsAttackToolDao.list());
    Map<Integer, BsRisk> riskById = mapById(bsRiskDao.list());

    // 根据威胁ID分组，获取威胁与风险的关系列表
    Map<Integer, List<BsThreaRiskRel>> threatRiskIdMap =
        bsThreaRiskRelDao.list().stream()
            .collect(Collectors.groupingBy(BsThreaRiskRel::getThreatId));

    // 根据威胁ID分组，获取威胁与工具的关系列表
    Map<Integer, List<BsThreatToolRel>> threatToolIdMap =
        bsThreatToolRelDao.list().stream()
            .collect(Collectors.groupingBy(BsThreatToolRel::getThreatId));

    List<BsThreatAction> threatActionList = bsThreatActionDao.list();

    // 筛选出父级威胁行为
    List<BsThreatAction> pid0ThreatList =
        threatActionList.stream().filter(t -> t.getPid() == 0).collect(Collectors.toList());

    // 处理威胁行为数据，包括其子级行为、相关的工具和风险信息
    return processThreatActions(
        threatActionList, pid0ThreatList, toolById, riskById, threatRiskIdMap, threatToolIdMap);
  }

  /** 将列表转换为id到对象的映射 */
  private <T> Map<Integer, T> mapById(List<T> dataList) {
    return dataList.stream().collect(Collectors.toMap(this::getIdFieldValue, data -> data));
  }

  private <T> int getIdFieldValue(T entity) {
    try {
      Field idField = entity.getClass().getDeclaredField("id");
      idField.setAccessible(true);
      return (int) idField.get(entity);
    } catch (NoSuchFieldException | IllegalAccessException e) {
      throw new RuntimeException("Failed to retrieve 'id' field from the entity", e);
    }
  }

  /**
   * 处理威胁行为及其关联信息。
   *
   * @param threatActionList 威胁行为列表。
   * @param pid0ThreatList 父级ID为0的威胁行为列表。
   * @param toolById 工具ID映射到工具实体的映射。
   * @param riskById 风险ID映射到风险实体的映射。
   * @param threatRiskIdMap 威胁与风险关系ID的映射。
   * @param threatToolIdMap 威胁与工具关系ID的映射。
   * @return 处理后的威胁行为信息响应列表。
   */
  private List<BsAllThreatActionInfoResp> processThreatActions(
      List<BsThreatAction> threatActionList,
      List<BsThreatAction> pid0ThreatList,
      Map<Integer, BsAttackTool> toolById,
      Map<Integer, BsRisk> riskById,
      Map<Integer, List<BsThreaRiskRel>> threatRiskIdMap,
      Map<Integer, List<BsThreatToolRel>> threatToolIdMap) {

    List<BsAllThreatActionInfoResp> finalList = new ArrayList<>();
    for (BsThreatAction threatP0 : pid0ThreatList) {
      BsAllThreatActionInfoResp resp = new BsAllThreatActionInfoResp();
      BeanUtils.copyProperties(threatP0, resp);

      // 获取威胁行为使用的工具信息
      List<BsAllThreatActionInfoResp.ToolUse> toolUseList =
          getToolUses(threatToolIdMap, threatP0.getId(), toolById);
      // 获取威胁行为构建的工具信息
      List<BsAllThreatActionInfoResp.ToolBuild> toolBuildList =
          getToolBuilds(threatToolIdMap, threatP0.getId(), toolById);
      // 获取威胁行为相关的风险信息
      List<BsAllThreatActionInfoResp.RiskBuild> riskBuildList =
          getRiskBuilds(threatRiskIdMap, threatP0.getId(), riskById);

      // 处理子威胁行为（使用相同的方法）
      List<BsThreatAction> childThreats = findChildThreats(threatActionList, threatP0.getId());
      List<BsAllThreatActionInfoResp> childThreatList =
          processChildThreats(childThreats, toolById, riskById, threatRiskIdMap, threatToolIdMap);

      resp.setToolBuildList(toolBuildList);
      resp.setToolUseList(toolUseList);
      resp.setRiskBuildList(riskBuildList);
      resp.setChildAttackList(childThreatList);
      finalList.add(resp);
    }
    return finalList;
  }

  /**
   * 获取子威胁行为。
   *
   * @param allThreats 所有威胁行为列表。
   * @param parentId 父级威胁行为ID。
   * @return 子威胁行为列表。
   */
  private List<BsThreatAction> findChildThreats(List<BsThreatAction> allThreats, Integer parentId) {
    return allThreats.stream()
        .filter(t -> t.getPid().equals(parentId))
        .collect(Collectors.toList());
  }

  /**
   * 获取工具使用信息。
   *
   * @param threatToolIdMap 威胁与工具关系ID的映射。
   * @param threatId 威胁ID。
   * @param toolById 工具ID映射到工具实体的映射。
   * @return 工具使用信息列表。
   */
  private List<BsAllThreatActionInfoResp.ToolUse> getToolUses(
      Map<Integer, List<BsThreatToolRel>> threatToolIdMap,
      Integer threatId,
      Map<Integer, BsAttackTool> toolById) {
    return Optional.ofNullable(threatToolIdMap.get(threatId))
        .map(
            rels ->
                rels.stream()
                    .filter(toolRel -> toolRel.getType().equals(ThreatToolTypeEnum.USE.getCode()))
                    .map(toolRel -> toolById.get(toolRel.getToolId()))
                    .filter(Objects::nonNull)
                    .map(this::convertToToolUse)
                    .collect(Collectors.toList()))
        .orElse(Collections.emptyList());
  }

  /**
   * 获取工具构建信息。
   *
   * @param threatToolIdMap 威胁与工具关系ID的映射。
   * @param threatId 威胁ID。
   * @param toolById 工具ID映射到工具实体的映射。
   * @return 工具构建信息列表。
   */
  private List<BsAllThreatActionInfoResp.ToolBuild> getToolBuilds(
      Map<Integer, List<BsThreatToolRel>> threatToolIdMap,
      Integer threatId,
      Map<Integer, BsAttackTool> toolById) {
    return Optional.ofNullable(threatToolIdMap.get(threatId))
        .map(
            rels ->
                rels.stream()
                    .filter(toolRel -> toolRel.getType().equals(ThreatToolTypeEnum.BUILD.getCode()))
                    .map(toolRel -> toolById.get(toolRel.getToolId()))
                    .filter(Objects::nonNull)
                    .map(this::convertToToolBuild)
                    .collect(Collectors.toList()))
        .orElse(Collections.emptyList());
  }

  /**
   * 获取风险构建信息。
   *
   * @param threatRiskIdMap 威胁与风险关系ID的映射。
   * @param threatId 威胁ID。
   * @param riskById 风险ID映射到风险实体的映射。
   * @return 风险构建信息列表。
   */
  private List<BsAllThreatActionInfoResp.RiskBuild> getRiskBuilds(
      Map<Integer, List<BsThreaRiskRel>> threatRiskIdMap,
      Integer threatId,
      Map<Integer, BsRisk> riskById) {
    return Optional.ofNullable(threatRiskIdMap.get(threatId))
        .map(
            rels ->
                rels.stream()
                    .map(riskRel -> riskById.get(riskRel.getRiskId()))
                    .map(this::convertToRiskBuild)
                    .collect(Collectors.toList()))
        .orElse(Collections.emptyList());
  }

  /**
   * 将AttackTool实体转换为ToolUse响应对象。
   *
   * @param tool AttackTool实体。
   * @return ToolUse响应对象。
   */
  private BsAllThreatActionInfoResp.ToolUse convertToToolUse(BsAttackTool tool) {
    BsAllThreatActionInfoResp.ToolUse toolUse = new BsAllThreatActionInfoResp.ToolUse();
    BeanUtils.copyProperties(tool, toolUse);
    return toolUse;
  }

  /**
   * 将AttackTool实体转换为ToolBuild响应对象。
   *
   * @param tool AttackTool实体。
   * @return ToolBuild响应对象。
   */
  private BsAllThreatActionInfoResp.ToolBuild convertToToolBuild(BsAttackTool tool) {
    BsAllThreatActionInfoResp.ToolBuild toolBuild = new BsAllThreatActionInfoResp.ToolBuild();
    BeanUtils.copyProperties(tool, toolBuild);
    return toolBuild;
  }

  /**
   * 将Risk实体转换为RiskBuild响应对象。
   *
   * @param risk Risk实体。
   * @return RiskBuild响应对象。
   */
  private BsAllThreatActionInfoResp.RiskBuild convertToRiskBuild(BsRisk risk) {
    BsAllThreatActionInfoResp.RiskBuild riskBuild = new BsAllThreatActionInfoResp.RiskBuild();
    BeanUtils.copyProperties(risk, riskBuild);
    return riskBuild;
  }

  /**
   * 处理子威胁行为及其关联信息
   *
   * @param childThreats 子威胁行为列表
   * @param toolById 攻击工具ID与信息的映射
   * @param riskById 风险ID与信息的映射
   * @param threatRiskIdMap 威胁与风险ID关联的映射
   * @param threatToolIdMap 威胁与工具ID关联的映射
   * @return 处理后的子威胁信息列表，包括其关联的工具和风险信息
   */
  private List<BsAllThreatActionInfoResp> processChildThreats(
      List<BsThreatAction> childThreats,
      Map<Integer, BsAttackTool> toolById,
      Map<Integer, BsRisk> riskById,
      Map<Integer, List<BsThreaRiskRel>> threatRiskIdMap,
      Map<Integer, List<BsThreatToolRel>> threatToolIdMap) {

    // 初始化子威胁信息响应列表
    List<BsAllThreatActionInfoResp> childThreatList = new ArrayList<>();
    // 遍历子威胁行为，为每个子威胁构建详细信息响应对象
    for (BsThreatAction ct : childThreats) {
      BsAllThreatActionInfoResp childResp = new BsAllThreatActionInfoResp();
      // 复制基本威胁信息到响应对象
      BeanUtils.copyProperties(ct, childResp);

      // 获取并设置威胁使用的工具信息
      List<BsAllThreatActionInfoResp.ToolUse> chToolUseList =
          getToolUses(threatToolIdMap, ct.getId(), toolById);
      // 获取并设置威胁构建所用的工具信息
      List<BsAllThreatActionInfoResp.ToolBuild> chToolBuildList =
          getToolBuilds(threatToolIdMap, ct.getId(), toolById);
      // 获取并设置威胁相关的风险构建信息
      List<BsAllThreatActionInfoResp.RiskBuild> chRiskBuildList =
          getRiskBuilds(threatRiskIdMap, ct.getId(), riskById);

      // 设置威胁的工具使用、工具构建和风险构建详细信息
      childResp.setToolBuildList(chToolBuildList);
      childResp.setToolUseList(chToolUseList);
      childResp.setRiskBuildList(chRiskBuildList);
      // 将处理后的威胁信息添加到列表中
      childThreatList.add(childResp);
    }
    // 返回处理后的子威胁信息列表
    return childThreatList;
  }
}

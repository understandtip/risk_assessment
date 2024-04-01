package com.yushang.risk.assessment.service.impl;

import cn.hutool.core.lang.Assert;
import com.yushang.risk.assessment.dao.*;
import com.yushang.risk.assessment.domain.entity.*;
import com.yushang.risk.assessment.domain.vo.response.*;
import com.yushang.risk.assessment.service.BsRiskService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Var;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.actuate.autoconfigure.health.HealthEndpointProperties;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.xml.ws.WebServiceRef;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author：zlp @Package：com.yushang.risk.assessment.service.impl @Project：risk_assessment
 *
 * 
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
  @Resource private BsAvoidCategoryDao bsAvoidCategoryDao;

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
   * 获取所有规避手段的信息列表。 这个方法通过查询数据库，获取所有规避分类及其对应的规避手段，并将这些信息包装成响应对象列表返回。
   *
   * @return 返回包含所有规避手段信息的响应对象列表。如果查询结果为空，则返回空列表。
   */
  @Override
  public List<BsAllAvoidInfoResp> getAllAvoid() {
    List<BsAllAvoidInfoResp> resp = new ArrayList<>();
    // 从数据库获取规避分类列表
    List<BsAvoidCategory> categoryList = bsAvoidCategoryDao.list();
    // 从数据库获取规避手段列表
    List<BsAvoidMeans> avoidMeansList = bsAvoidMeansDao.list();

    // 对获取的数据进行空值检查
    if (categoryList == null
        || categoryList.isEmpty()
        || avoidMeansList == null
        || avoidMeansList.isEmpty()) {
      // 如果数据为空，则直接返回空列表
      return resp;
    }

    // 根据规避手段的父ID，将规避手段列表进行分组
    Map<Integer, List<BsAvoidMeans>> avoidMeansMap = partitionAvoidMeans(avoidMeansList);

    // 遍历规避分类列表，为每个分类生成对应的响应对象
    categoryList.forEach(
        category -> {
          BsAllAvoidInfoResp infoResp = new BsAllAvoidInfoResp();
          // 将分类信息复制到响应对象中
          BeanUtils.copyProperties(category, infoResp);
          List<BsAllAvoidInfoResp.AvoidInfoResp> avoidList = new ArrayList<>();

          // 获取当前分类下的所有规避手段
          Optional<List<BsAvoidMeans>> pid0AvoidListOptional =
              Optional.ofNullable(avoidMeansMap.get(category.getId()));
          pid0AvoidListOptional.ifPresent(
              pid0AvoidList -> {
                // 遍历规避手段，生成相应的响应对象，并添加到列表中
                pid0AvoidList.forEach(
                    avoid -> {
                      BsAllAvoidInfoResp.AvoidInfoResp avoidInfoResp =
                          new BsAllAvoidInfoResp.AvoidInfoResp();
                      BeanUtils.copyProperties(avoid, avoidInfoResp);
                      // 为每个规避手段生成子规避手段列表
                      avoidInfoResp.setChildAvoidList(
                          generateChildAvoidList(avoidMeansMap, avoid.getId()));
                      avoidList.add(avoidInfoResp);
                    });
              });
          // 设置规避信息列表
          infoResp.setAvoidInfoList(avoidList);
          // 将当前分类的规避手段信息添加到响应对象列表中
          resp.add(infoResp);
        });
    return resp;
  }

  /**
   * 将避免手段列表根据类型ID和父ID进行分区。
   *
   * @param avoidMeansList 需要分区的避免手段列表。
   * @return 返回一个映射，其中键是类型ID或父ID，值是相应的避免手段列表。
   */
  private Map<Integer, List<BsAvoidMeans>> partitionAvoidMeans(List<BsAvoidMeans> avoidMeansList) {
    // 创建一个空映射以存储结果
    Map<Integer, List<BsAvoidMeans>> avoidMeansMap = new HashMap<>();

    // 首先，将所有父ID为0的避免手段按照类型ID分组
    avoidMeansMap.putAll(
        avoidMeansList.stream()
            .filter(a -> 0 == a.getPid())
            .collect(Collectors.groupingBy(BsAvoidMeans::getTypeId)));

    // 接着，将所有非父ID（即父ID不为0）的避免手段按照父ID分组
    avoidMeansMap.putAll(
        avoidMeansList.stream()
            .filter(a -> 0 != a.getPid())
            .collect(Collectors.groupingBy(BsAvoidMeans::getPid)));

    return avoidMeansMap;
  }

  /**
   * 根据给定的父ID，生成子避免列表。
   *
   * @param avoidMeansMap 包含避免手段的映射，键是类型ID或父ID，值是相应的避免手段列表。
   * @param parentId 父ID，用于查询子避免手段。
   * @return 返回一个列表，包含指定父ID下的所有子避免手段信息。
   */
  private List<BsAllAvoidInfoResp.AvoidInfoResp> generateChildAvoidList(
      Map<Integer, List<BsAvoidMeans>> avoidMeansMap, Integer parentId) {
    // 尝试获取与给定父ID匹配的避免手段列表，如果不存在，则返回空列表
    return Optional.ofNullable(avoidMeansMap.get(parentId))
        .map(
            list ->
                // 对获取到的列表中的每个避免手段，创建一个响应对象并填充数据
                list.stream()
                    .map(
                        avoidMeans -> {
                          BsAllAvoidInfoResp.AvoidInfoResp childAvoid =
                              new BsAllAvoidInfoResp.AvoidInfoResp();
                          // 使用BeanUtils复制属性值，从避免手段对象到响应对象
                          BeanUtils.copyProperties(avoidMeans, childAvoid);
                          return childAvoid;
                        })
                    .collect(Collectors.toList()))
        .orElse(new ArrayList<>());
  }

  /**
   * 获取所有攻击工具的信息列表。
   *
   * @return 返回一个包含所有攻击工具信息的列表。每个工具的信息包括基本属性、风险、避免方法和子工具列表（如果有）。
   */
  @Override
  public List<BsAllAttackToolInfoResp> getAllAttackTool() {
    List<BsAllAttackToolInfoResp> resp = new ArrayList<>();
    try {
      // 从数据库获取攻击工具列表
      List<BsAttackTool> attackToolList = bsAttackToolDao.list();
      // 根据父ID将攻击工具分组，以便后续处理
      Map<Integer, List<BsAttackTool>> childPidMap = groupByParentId(attackToolList);

      // 遍历攻击工具列表，筛选出顶级工具，并为每个顶级工具构建响应对象
      attackToolList.stream()
          .filter(tool -> tool.getPid() == 0)
          .forEach(tool -> resp.add(populateResponse(tool, childPidMap)));
    } catch (Exception e) {
      // 记录异常信息
      log.error("获取所有攻击工具信息失败", e);
    }
    return resp;
  }

  /**
   * 根据攻击工具信息填充响应对象。
   *
   * @param tool 攻击工具的基本信息。
   * @param childPidMap 用于根据父ID查找子工具的映射。
   * @return 返回填充好的攻击工具详细信息响应对象。
   */
  private BsAllAttackToolInfoResp populateResponse(
      BsAttackTool tool, Map<Integer, List<BsAttackTool>> childPidMap) {
    BsAllAttackToolInfoResp allAttackToolInfoResp = new BsAllAttackToolInfoResp();
    BeanUtils.copyProperties(tool, allAttackToolInfoResp);

    // 获取并设置工具的风险信息
    List<BsAllAttackToolInfoResp.ToolRiskInfoResp> risks = getRisks(tool.getId());
    List<BsAllAttackToolInfoResp.ToolAvoidInfoResp> avoids = getAvoids(tool.getId());

    allAttackToolInfoResp.setRiskList(risks);
    allAttackToolInfoResp.setAvoidList(avoids);
    // 设置子工具列表
    allAttackToolInfoResp.setChildAttackToolList(getChildTools(tool.getId(), childPidMap));

    return allAttackToolInfoResp;
  }

  /**
   * 根据工具ID获取风险信息。
   *
   * @param toolId 工具的ID。
   * @return 返回一个包含工具风险信息的列表。
   */
  private List<BsAllAttackToolInfoResp.ToolRiskInfoResp> getRisks(Integer toolId) {
    List<BsRisk> riskList = bsRiskToolDao.getByToolId(toolId);
    return riskList.stream()
        .map(
            risk -> {
              BsAllAttackToolInfoResp.ToolRiskInfoResp riskInfoResp =
                  new BsAllAttackToolInfoResp.ToolRiskInfoResp();
              BeanUtils.copyProperties(risk, riskInfoResp);
              return riskInfoResp;
            })
        .collect(Collectors.toList());
  }

  /**
   * 根据工具ID获取避免方法信息。
   *
   * @param toolId 工具的ID。
   * @return 返回一个包含工具避免方法信息的列表。
   */
  private List<BsAllAttackToolInfoResp.ToolAvoidInfoResp> getAvoids(Integer toolId) {
    List<BsAvoidMeans> avoidList = bsAttackAvoidDao.getByToolId(toolId);
    return avoidList.stream()
        .map(
            avoid -> {
              BsAllAttackToolInfoResp.ToolAvoidInfoResp avoidInfoResp =
                  new BsAllAttackToolInfoResp.ToolAvoidInfoResp();
              BeanUtils.copyProperties(avoid, avoidInfoResp);
              return avoidInfoResp;
            })
        .collect(Collectors.toList());
  }

  /**
   * 根据父工具ID和工具分组映射，获取子工具列表。
   *
   * @param parentId 父工具的ID。
   * @param childPidMap 根据父ID查找子工具的映射。
   * @return 返回一个包含子工具信息的列表。每个子工具的信息也包括风险和避免方法等。
   */
  private List<BsAllAttackToolInfoResp> getChildTools(
      Integer parentId, Map<Integer, List<BsAttackTool>> childPidMap) {
    List<BsAllAttackToolInfoResp> childAttackToolList = new ArrayList<>();
    // 根据父ID获取子工具列表
    List<BsAttackTool> childToolList = childPidMap.get(parentId);
    if (childToolList != null) {
      // 遍历子工具列表，为每个子工具创建响应对象，并递归获取其子工具的信息
      childToolList.forEach(
          childTool -> {
            BsAllAttackToolInfoResp childAttackToolInfoResp = new BsAllAttackToolInfoResp();
            BeanUtils.copyProperties(childTool, childAttackToolInfoResp);
            childAttackToolInfoResp.setRiskList(getRisks(childTool.getId())); // 递归处理子工具的风险信息
            childAttackToolInfoResp.setAvoidList(getAvoids(childTool.getId()));
            childAttackToolList.add(childAttackToolInfoResp);
          });
    }
    return childAttackToolList;
  }

  /**
   * 将攻击工具列表根据其父ID进行分组。
   *
   * @param attackToolList 攻击工具的列表。
   * @return 返回一个映射，键是父工具的ID，值是该父工具的所有子工具列表。
   */
  private Map<Integer, List<BsAttackTool>> groupByParentId(List<BsAttackTool> attackToolList) {
    return attackToolList.stream()
        .filter(tool -> tool.getPid() != 0) // 过滤掉没有父工具的工具
        .collect(Collectors.groupingBy(BsAttackTool::getPid));
  }
}

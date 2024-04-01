package com.yushang.risk.assessment.service.impl;

import com.yushang.risk.assessment.dao.*;
import com.yushang.risk.assessment.domain.entity.*;
import com.yushang.risk.assessment.domain.vo.response.BsRiskResp;
import com.yushang.risk.assessment.service.BsCategoryService;
import com.yushang.risk.assessment.service.adapter.BsRiskAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Author：zlp @Package：com.yushang.risk.assessment.service.impl @Project：risk_assessment
 *
 * @name：BsCategoryServiceImpl @Date：2024/3/5 10:25 @Filename：BsCategoryServiceImpl
 */
@Service
@Slf4j
public class BsCategoryServiceImpl implements BsCategoryService {
  @Resource private BsCategoryDao bsCategoryDao;
  @Resource private BsRiskCategoryDao bsRiskCategoryDao;
  @Resource private BsRiskDao bsRiskDao;
  @Resource private BsCategoryLatitudeDao bsCategoryLatitudeDao;
  @Resource private BsRiskLatitudeCategoryDao bsRiskLatitudeCategoryDao;
  @Resource private BsRiskCategoryRelDao bsRiskCategoryRelDao;

  /**
   * 获取指定分类下的风险响应对象
   *
   * @param categoryId 风险分类ID
   * @return 包含风险纬度、风险分类和风险信息的响应对象
   */
  @Override
  @Cacheable(cacheNames = "BSRiskList", key = "#categoryId")
  public BsRiskResp getBSRisk(Integer categoryId) {
    BsRiskResp resp = new BsRiskResp();
    try {
      // 分步处理，每一步都在try-catch内，便于异常捕获和处理
      List<BsRiskLatitude> latitudeList = getLatitudeList(categoryId);
      Map<Integer, BsRiskCategory> riskCategoryMap = buildRiskCategoryMap();
      Map<Integer, BsRisk> bsRiskMap = buildBsRiskMap();
      Map<Integer, List<Integer>> riskAndCategoryIdMap = getRiskAndCategoryIdMap(categoryId);
      Map<Integer, List<Integer>> latitudeAndRiskCategoryIdMap = getLatitudeAndRiskCategoryIdMap();
      List<BsRisk> bsRisks = bsRiskDao.list();
      Map<Integer, List<BsRisk>> riskPidMap = BsRiskAdapter.dealRiskPidToMap(bsRisks);
      this.buildCategoryLatitudeList(
          resp,
          latitudeList,
          riskCategoryMap,
          bsRiskMap,
          riskAndCategoryIdMap,
          latitudeAndRiskCategoryIdMap,
          riskPidMap);
    } catch (Exception e) {
      // 异常处理逻辑，可以是日志记录、事务回滚等
      log.error("获取风险数据时发生异常", e);
    }
    return resp;
  }

  /**
   * 根据分类ID获取风险纬度列表
   *
   * @param categoryId 风险分类ID
   * @return 风险纬度列表
   */
  private List<BsRiskLatitude> getLatitudeList(Integer categoryId) {
    return bsCategoryLatitudeDao.getByLatitude(categoryId);
  }

  /**
   * 构建风险分类映射表（ID -> 对象）
   *
   * @return 风险分类映射表
   */
  private Map<Integer, BsRiskCategory> buildRiskCategoryMap() {
    List<BsRiskCategory> riskCategoryList = bsRiskCategoryDao.list();
    return riskCategoryList.stream()
        .collect(Collectors.toMap(BsRiskCategory::getId, Function.identity()));
  }

  /**
   * 构建风险映射表（ID -> 对象）
   *
   * @return 风险映射表
   */
  private Map<Integer, BsRisk> buildBsRiskMap() {
    List<BsRisk> bsRiskList = bsRiskDao.listForPid0();
    return bsRiskList.stream().collect(Collectors.toMap(BsRisk::getId, Function.identity()));
  }

  /**
   * 获取风险与风险分类关联关系映射表（风险分类ID -> 风险ID列表）
   *
   * @return 关联关系映射表
   * @param categoryId
   */
  private Map<Integer, List<Integer>> getRiskAndCategoryIdMap(Integer categoryId) {
    return bsRiskCategoryRelDao.getRelMap(categoryId);
  }

  /**
   * 获取风险纬度与风险分类关联关系映射表（风险纬度ID -> 风险分类ID列表）
   *
   * @return 关联关系映射表
   */
  private Map<Integer, List<Integer>> getLatitudeAndRiskCategoryIdMap() {
    return bsRiskLatitudeCategoryDao.getRelMap();
  }

  /**
   * 根据给定的数据构建风险响应中的风险纬度及下属信息
   *
   * @param resp 目标响应对象
   * @param latitudeList 风险纬度列表
   * @param riskCategoryMap 风险分类映射表
   * @param bsRiskMap 风险映射表
   * @param riskAndCategoryIdMap 风险与风险分类关联关系映射表
   * @param latitudeAndRiskCategoryIdMap 风险纬度与风险分类关联关系映射表
   * @param riskPidMap
   */
  private void buildCategoryLatitudeList(
      BsRiskResp resp,
      List<BsRiskLatitude> latitudeList,
      Map<Integer, BsRiskCategory> riskCategoryMap,
      Map<Integer, BsRisk> bsRiskMap,
      Map<Integer, List<Integer>> riskAndCategoryIdMap,
      Map<Integer, List<Integer>> latitudeAndRiskCategoryIdMap,
      Map<Integer, List<BsRisk>> riskPidMap) {
    List<BsRiskResp.CategoryLatitude> categoryLatitudes = new ArrayList<>();
    latitudeList.forEach(
        bsRiskLatitude -> {
          // BsRiskResp.CategoryLatitude
          BsRiskResp.CategoryLatitude categoryLatitude = new BsRiskResp.CategoryLatitude();
          categoryLatitude.setLatitudeName(bsRiskLatitude.getName());
          // BsRiskResp.RiskCategory
          List<BsRiskResp.RiskCategory> riskCategoryList = new ArrayList<>();

          // 获取当前风险纬度关联的风险分类ID列表
          List<Integer> riskCategoryIds =
              Optional.ofNullable(latitudeAndRiskCategoryIdMap.get(bsRiskLatitude.getId()))
                  .orElse(new ArrayList<>());

          // 根据风险分类ID列表获取风险分类对象列表
          List<BsRiskCategory> riskCategories =
              riskCategoryIds.stream()
                  .map(riskCategoryMap::get)
                  .filter(Objects::nonNull)
                  .collect(Collectors.toList());

          riskCategories.forEach(
              rc -> {
                // BsRiskResp.RiskCategory
                BsRiskResp.RiskCategory riskCategory = new BsRiskResp.RiskCategory();
                riskCategory.setRiskCategoryName(rc.getName());
                // BsRiskResp.Risk
                List<BsRiskResp.Risk> riskArrayList = new ArrayList<>();

                // 获取当前风险分类关联的风险ID列表
                List<Integer> riskIds =
                    Optional.ofNullable(riskAndCategoryIdMap.get(rc.getId()))
                        .orElse(new ArrayList<>());

                // 根据风险ID列表获取风险对象列表
                List<BsRisk> riskList =
                    riskIds.stream()
                        .map(bsRiskMap::get)
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList());

                riskList.forEach(
                    r -> {
                      BsRiskResp.Risk risk = new BsRiskResp.Risk();
                      BsRiskAdapter.buildBsRiskResp(risk, r);
                      BsRiskAdapter.buildRsRiskChild(riskPidMap, risk);
                      riskArrayList.add(risk);
                    });
                riskCategory.setRiskList(riskArrayList);
                riskCategoryList.add(riskCategory);
                categoryLatitude.setRiskCategoryList(riskCategoryList);
              });
          categoryLatitudes.add(categoryLatitude);
        });
    resp.setCategoryLatitudeList(categoryLatitudes);
  }

  /**
   * 分类数据
   *
   * @return
   */
  @Override
  public List<BsCategory> getCategory() {
    return bsCategoryDao.listByOrder();
  }
}

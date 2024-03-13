package com.yushang.risk.assessment.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yushang.risk.assessment.domain.entity.BsRiskCategoryRel;
import com.yushang.risk.assessment.domain.entity.BsRiskLatitudeCategory;
import com.yushang.risk.assessment.mapper.BsRiskLatitudeCategoryMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 风险纬度分类关系 服务实现类
 *
 * @author zlp
 * @since 2024-03-05
 */
@Service
public class BsRiskLatitudeCategoryDao
    extends ServiceImpl<BsRiskLatitudeCategoryMapper, BsRiskLatitudeCategory> {
  /**
   * 根据纬度id查询风险分类id
   *
   * @return
   */
  public List<Integer> getByLatitudeId(Integer latitudeId) {
    LambdaQueryWrapper<BsRiskLatitudeCategory> wrapper = new LambdaQueryWrapper<>();
    wrapper.eq(BsRiskLatitudeCategory::getLatitudeId, latitudeId);
    return this.list().stream()
        .map(BsRiskLatitudeCategory::getRiskCategoryId)
        .collect(Collectors.toList());
  }

  /**
   * 搜集id对应map
   *
   * @return
   */
  public Map<Integer, List<Integer>> getRelMap() {
    Map<Integer, List<Integer>> finalMap = new HashMap<>();
    List<BsRiskLatitudeCategory> list = this.list();
    Map<Integer, List<BsRiskLatitudeCategory>> collect =
        list.stream().collect(Collectors.groupingBy(BsRiskLatitudeCategory::getLatitudeId));

    collect.forEach(
        (k, v) -> {
          List<Integer> riskIds =
              v.stream()
                  .map(BsRiskLatitudeCategory::getRiskCategoryId)
                  .collect(Collectors.toList());
          finalMap.put(k, riskIds);
        });
    return finalMap;
  }
}

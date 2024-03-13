package com.yushang.risk.assessment.dao;

import com.yushang.risk.assessment.domain.entity.BsRiskCategoryRel;
import com.yushang.risk.assessment.mapper.BsRiskCategoryRelMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 风险与分类关系表 服务实现类
 *
 * @author zlp
 * @since 2024-03-05
 */
@Service
public class BsRiskCategoryRelDao extends ServiceImpl<BsRiskCategoryRelMapper, BsRiskCategoryRel> {
  @Resource private BsRiskClassificationRelDao bsRiskClassificationRelDao;
  /**
   * 搜集id对应map
   *
   * @return
   * @param categoryId
   */
  public Map<Integer, List<Integer>> getRelMap(Integer categoryId) {
    // 查出当前分类id具有哪些风险id集合
    List<Integer> riskToClassificationIds = bsRiskClassificationRelDao.getByCategoryId(categoryId);
    Map<Integer, List<Integer>> finalMap = new HashMap<>();
    List<BsRiskCategoryRel> list = this.list();
    Map<Integer, List<BsRiskCategoryRel>> collect =
        list.stream()
            .filter(
                bsRiskCategoryRel ->
                    riskToClassificationIds.contains(bsRiskCategoryRel.getRiskId()))
            .collect(Collectors.groupingBy(BsRiskCategoryRel::getCategoryId));

    collect.forEach(
        (k, v) -> {
          List<Integer> riskIds =
              v.stream().map(BsRiskCategoryRel::getRiskId).collect(Collectors.toList());
          finalMap.put(k, riskIds);
        });
    return finalMap;
  }
}

package com.yushang.risk.assessment.dao;

import com.yushang.risk.assessment.domain.entity.Risk;
import com.yushang.risk.assessment.mapper.RiskMapper;
import com.yushang.risk.assessment.service.RiskService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 风险 服务实现类
 *
 * @author zlp
 * @since 2023-12-18
 */
@Service
public class RiskDao extends ServiceImpl<RiskMapper, Risk> {
  /**
   * 查询指定分类下的风险集合
   *
   * @param categoryId
   * @return
   */
  public List<Risk> getRiskFromCategory(Integer categoryId) {
    return this.lambdaQuery().eq(Risk::getCategoryId, categoryId).eq(Risk::getParentId, 0).list();
  }

  /**
   * 判断有没有子风险
   *
   * @param id
   * @return
   */
  public boolean checkChild(Integer id) {
    Integer count = this.lambdaQuery().eq(Risk::getParentId, id).count();
    return count != 0;
  }

  /**
   * 查询子风险记录
   *
   * @param id
   * @return
   */
  public List<Risk> getChild(Integer id) {
    return this.lambdaQuery().eq(Risk::getParentId, id).list();
  }
}

package com.yushang.risk.assessment.dao;

import com.yushang.risk.assessment.domain.entity.AttrMeans;
import com.yushang.risk.assessment.mapper.AttrMeansMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 属性防护手段 服务实现类
 *
 * @author zlp
 * @since 2023-12-19
 */
@Service
public class AttrMeansDao extends ServiceImpl<AttrMeansMapper, AttrMeans> {
  /**
   * 根据属性id获取防护手段信息
   *
   * @param attrIds
   * @return
   */
  public List<AttrMeans> getByAttrId(List<Integer> attrIds) {
    return this.lambdaQuery().in(AttrMeans::getAttrId, attrIds).list();
  }
}

package com.yushang.risk.assessment.dao;

import com.yushang.risk.assessment.domain.entity.AttrMenace;
import com.yushang.risk.assessment.mapper.AttrMenaceMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 属性对应威胁 服务实现类
 *
 * @author zlp
 * @since 2023-12-19
 */
@Service
public class AttrMenaceDao extends ServiceImpl<AttrMenaceMapper, AttrMenace> {
  /**
   * 根据属性id查询威胁信息
   *
   * @param attrIds
   * @return
   */
  public List<AttrMenace> getByAttrId(List<Integer> attrIds) {
    return this.lambdaQuery().in(AttrMenace::getAttrId, attrIds).list();
  }
}

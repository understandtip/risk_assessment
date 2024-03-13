package com.yushang.risk.assessment.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yushang.risk.assessment.domain.entity.BsCategory;
import com.yushang.risk.assessment.mapper.BsCategoryMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 种类 服务实现类
 *
 * @author zlp
 * @since 2024-03-05
 */
@Service
public class BsCategoryDao extends ServiceImpl<BsCategoryMapper, BsCategory> {
  public List<BsCategory> listByOrder() {
    LambdaQueryWrapper<BsCategory> wrapper = new LambdaQueryWrapper<>();
    wrapper.orderByAsc(BsCategory::getSort);
    return this.list(wrapper);
  }
}

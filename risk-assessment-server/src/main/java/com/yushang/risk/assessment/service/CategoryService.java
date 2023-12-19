package com.yushang.risk.assessment.service;

import com.yushang.risk.assessment.domain.entity.Category;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 类别 服务类
 *
 * @author zlp
 * @since 2023-12-18
 */
public interface CategoryService {
  /**
   * 获取分类集合
   *
   * @return
   */
  List<Category> getCategoryList();
}

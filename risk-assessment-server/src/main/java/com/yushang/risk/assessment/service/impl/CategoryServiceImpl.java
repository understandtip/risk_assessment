package com.yushang.risk.assessment.service.impl;

import com.yushang.risk.assessment.dao.CategoryDao;
import com.yushang.risk.assessment.domain.entity.Category;
import com.yushang.risk.assessment.service.CategoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author：zlp @Package：com.yushang.risk.assessment.service.impl @Project：risk_assessment
 *
 * @name：CategoryServiceImpl @Date：2023/12/18 15:23 @Filename：CategoryServiceImpl
 */
@Service
public class CategoryServiceImpl implements CategoryService {
  @Resource private CategoryDao categoryDao;
  /**
   * 获取分类集合
   *
   * @return
   */
  @Override
  public List<Category> getCategoryList() {
    return categoryDao.list();
  }
}

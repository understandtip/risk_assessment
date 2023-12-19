package com.yushang.risk.assessment.dao;

import com.yushang.risk.assessment.domain.entity.Category;
import com.yushang.risk.assessment.mapper.CategoryMapper;
import com.yushang.risk.assessment.service.CategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 类别 服务实现类
 *
 * @author zlp
 * @since 2023-12-18
 */
@Service
public class CategoryDao extends ServiceImpl<CategoryMapper, Category> {}

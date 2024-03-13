package com.yushang.risk.assessment.dao;

import com.yushang.risk.assessment.domain.entity.BsAvoidCategory;
import com.yushang.risk.assessment.mapper.BsAvoidCategoryMapper;
import com.yushang.risk.assessment.service.IBsAvoidCategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 规避分类 服务实现类
 *
 * @author zlp
 * @since 2024-03-11
 */
@Service
public class BsAvoidCategoryDao extends ServiceImpl<BsAvoidCategoryMapper, BsAvoidCategory> {}

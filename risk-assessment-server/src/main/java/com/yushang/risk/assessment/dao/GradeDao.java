package com.yushang.risk.assessment.dao;

import com.yushang.risk.assessment.domain.entity.Grade;
import com.yushang.risk.assessment.mapper.GradeMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 评分等级 服务实现类
 *
 * @author zlp
 * @since 2023-12-20
 */
@Service
public class GradeDao extends ServiceImpl<GradeMapper, Grade> {}

package com.yushang.risk.assessment.dao;

import com.yushang.risk.assessment.domain.entity.CElementTypeMethod;
import com.yushang.risk.assessment.mapper.CElementTypeMethodMapper;
import com.yushang.risk.assessment.service.ICElementTypeMethodService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 因素类型方式 服务实现类
 *
 * @author zlp
 * @since 2024-02-23
 */
@Service
public class CElementTypeMethodDao
    extends ServiceImpl<CElementTypeMethodMapper, CElementTypeMethod> {}

package com.yushang.risk.assessment.dao;

import com.yushang.risk.assessment.domain.entity.CElementType;
import com.yushang.risk.assessment.mapper.CElementTypeMapper;
import com.yushang.risk.assessment.service.ICElementTypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 因素类型 服务实现类
 *
 * @author zlp
 * @since 2024-02-23
 */
@Service
public class CElementTypeDao extends ServiceImpl<CElementTypeMapper, CElementType> {}

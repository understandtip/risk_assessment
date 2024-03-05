package com.yushang.risk.assessment.dao;

import com.yushang.risk.assessment.domain.entity.BsCategoryLatitude;
import com.yushang.risk.assessment.mapper.BsCategoryLatitudeMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 分类纬度表关系 服务实现类
 *
 * @author zlp
 * @since 2024-03-05
 */
@Service
public class BsCategoryLatitudeDao
    extends ServiceImpl<BsCategoryLatitudeMapper, BsCategoryLatitude> {}

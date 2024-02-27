package com.yushang.risk.assessment.dao;

import com.yushang.risk.assessment.domain.entity.CElement;
import com.yushang.risk.assessment.mapper.CElementMapper;
import com.yushang.risk.assessment.service.ICElementService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 因素 服务实现类
 *
 * @author zlp
 * @since 2024-02-23
 */
@Service
public class CElementDao extends ServiceImpl<CElementMapper, CElement> {}

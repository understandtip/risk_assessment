package com.yushang.risk.assessment.dao;

import com.yushang.risk.assessment.domain.entity.BsThreatAction;
import com.yushang.risk.assessment.mapper.BsThreatActionMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 威胁行为 服务实现类
 *
 * @author zlp
 * @since 2024-03-20
 */
@Service
public class BsThreatActionDao extends ServiceImpl<BsThreatActionMapper, BsThreatAction> {}

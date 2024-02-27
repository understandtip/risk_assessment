package com.yushang.risk.assessment.dao;

import com.yushang.risk.assessment.domain.entity.CConfrontWay;
import com.yushang.risk.assessment.mapper.CConfrontWayMapper;
import com.yushang.risk.assessment.service.ICConfrontWayService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 对抗方式 服务实现类
 *
 * @author zlp
 * @since 2024-02-23
 */
@Service
public class CConfrontWayDao extends ServiceImpl<CConfrontWayMapper, CConfrontWay> {}

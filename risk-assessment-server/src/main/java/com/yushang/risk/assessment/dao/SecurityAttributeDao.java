package com.yushang.risk.assessment.dao;

import com.yushang.risk.assessment.domain.entity.SecurityAttribute;
import com.yushang.risk.assessment.mapper.SecurityAttributeMapper;
import com.yushang.risk.assessment.service.SecurityAttributeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 安全属性 服务实现类
 *
 * @author zlp
 * @since 2023-12-18
 */
@Service
public class SecurityAttributeDao extends ServiceImpl<SecurityAttributeMapper, SecurityAttribute> {}

package com.yushang.risk.assessment.dao;

import com.yushang.risk.domain.entity.RegisterApply;
import com.yushang.risk.assessment.mapper.RegisterApplyMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 注册申请 服务实现类
 *
 * @author zlp
 * @since 2024-02-01
 */
@Service
public class RegisterApplyDao extends ServiceImpl<RegisterApplyMapper, RegisterApply> {}

package com.yushang.risk.assessment.dao;

import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.yushang.risk.domain.entity.RegisterApply;
import com.yushang.risk.assessment.mapper.RegisterApplyMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yushang.risk.domain.entity.User;
import org.springframework.stereotype.Service;

/**
 * 注册申请 服务实现类
 *
 * @author zlp
 * @since 2024-02-01
 */
@Service
public class RegisterApplyDao extends ServiceImpl<RegisterApplyMapper, RegisterApply> {
  /**
   * 根据指定字段查询用户
   *
   * @param value
   * @return
   */
  public RegisterApply getByField(SFunction<RegisterApply, ?> function, String value) {
    return this.lambdaQuery().eq(function, value).one();
  }
}

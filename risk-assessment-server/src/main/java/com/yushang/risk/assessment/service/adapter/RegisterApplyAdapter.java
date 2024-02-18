package com.yushang.risk.assessment.service.adapter;

import com.yushang.risk.assessment.domain.vo.request.RegisterReq;
import com.yushang.risk.domain.entity.RegisterApply;
import org.springframework.beans.BeanUtils;

/**
 * @Author：zlp @Package：com.yushang.risk.assessment.service.adapter @Project：risk_assessment
 *
 * @name：RegisterApplyAdapter @Date：2024/2/1 10:02 @Filename：RegisterApplyAdapter
 */
public class RegisterApplyAdapter {
  /**
   * 构建保存入驻申请
   *
   * @param registerReq
   * @param password
   * @return
   */
  public static RegisterApply buildApply(RegisterReq registerReq, String password) {
    RegisterApply apply = new RegisterApply();
    BeanUtils.copyProperties(registerReq, apply);
    apply.setUsername(registerReq.getUserName());
    apply.setPassword(password);
    return apply;
  }
}

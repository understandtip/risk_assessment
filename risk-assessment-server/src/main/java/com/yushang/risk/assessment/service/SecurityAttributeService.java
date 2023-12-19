package com.yushang.risk.assessment.service;

import com.yushang.risk.assessment.domain.entity.Cycle;
import com.yushang.risk.assessment.domain.entity.SecurityAttribute;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yushang.risk.assessment.domain.vo.response.AttrResp;

import java.util.List;

/**
 * 安全属性 服务类
 *
 * @author zlp
 * @since 2023-12-18
 */
public interface SecurityAttributeService {
  /**
   * 获取所有安全属性集合
   *
   * @return
   */
  List<AttrResp> getAttrList();
}

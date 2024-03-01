package com.yushang.risk.assessment.dao;

import com.yushang.risk.assessment.domain.entity.CElementTypeMethod;
import com.yushang.risk.assessment.domain.enums.ConfrontEnhanceEnum;
import com.yushang.risk.assessment.mapper.CElementTypeMethodMapper;
import com.yushang.risk.assessment.service.ICElementTypeMethodService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 因素类型方式 服务实现类
 *
 * @author zlp
 * @since 2024-02-23
 */
@Service
public class CElementTypeMethodDao
    extends ServiceImpl<CElementTypeMethodMapper, CElementTypeMethod> {
  /**
   * 根据是否增强查询类型方法数据
   *
   * @param isEnhance
   * @return
   */
  public List<CElementTypeMethod> listByEnhance(boolean isEnhance) {
    String code;
    if (isEnhance) {
      return this.lambdaQuery().orderByAsc(CElementTypeMethod::getSort).list();
    } else {
      code = ConfrontEnhanceEnum.NO_ENHANCE.getCode();
      return this.lambdaQuery()
          .eq(CElementTypeMethod::getIsEnhance, code)
          .orderByAsc(CElementTypeMethod::getSort)
          .list();
    }
  }
}

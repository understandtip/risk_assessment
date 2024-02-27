package com.yushang.risk.assessment.service.impl;

import com.yushang.risk.assessment.dao.CConfrontWayDao;
import com.yushang.risk.assessment.dao.CElementDao;
import com.yushang.risk.assessment.dao.CElementTypeDao;
import com.yushang.risk.assessment.dao.CElementTypeMethodDao;
import com.yushang.risk.assessment.domain.entity.CConfrontWay;
import com.yushang.risk.assessment.domain.entity.CElement;
import com.yushang.risk.assessment.domain.entity.CElementType;
import com.yushang.risk.assessment.domain.entity.CElementTypeMethod;
import com.yushang.risk.assessment.domain.vo.response.ConfrontInfoResp;
import com.yushang.risk.assessment.service.ConfrontService;
import lombok.Data;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author：zlp @Package：com.yushang.risk.assessment.service.impl @Project：risk_assessment
 *
 * @name：ConfrontServiceImpl @Date：2024/2/23 15:25 @Filename：ConfrontServiceImpl
 */
@Service
public class ConfrontServiceImpl implements ConfrontService {
  @Resource private CConfrontWayDao cConfrontWayDao;
  @Resource private CElementTypeMethodDao cElementTypeMethodDao;
  @Resource private CElementTypeDao cElementTypeDao;
  @Resource private CElementDao cElementDao;

  /**
   * 获取对抗数据
   *
   * @return
   */
  @Override
  @Cacheable()
  public ConfrontInfoResp getConfrontInfo() {
    List<CElement> cElements = cElementDao.list();
    List<CElementType> cElementTypes = cElementTypeDao.list();
    List<CElementTypeMethod> cElementTypeMethods = cElementTypeMethodDao.list();
    List<CConfrontWay> cConfrontWays = cConfrontWayDao.list();
    // cElementTypes
    Map<Integer, List<CElementType>> cElementTypeMap =
        cElementTypes.stream().collect(Collectors.groupingBy(CElementType::getElementId));
    cElements.forEach(e -> {});
    return null;
  }
}

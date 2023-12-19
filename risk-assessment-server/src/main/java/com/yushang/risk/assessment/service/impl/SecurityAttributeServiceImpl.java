package com.yushang.risk.assessment.service.impl;

import com.yushang.risk.assessment.dao.AttrMeansDao;
import com.yushang.risk.assessment.dao.AttrMenaceDao;
import com.yushang.risk.assessment.dao.SecurityAttributeDao;
import com.yushang.risk.assessment.domain.entity.AttrMeans;
import com.yushang.risk.assessment.domain.entity.AttrMenace;
import com.yushang.risk.assessment.domain.entity.Cycle;
import com.yushang.risk.assessment.domain.entity.SecurityAttribute;
import com.yushang.risk.assessment.domain.vo.response.AttrResp;
import com.yushang.risk.assessment.service.SecurityAttributeService;
import com.yushang.risk.assessment.service.adapter.AttrAdapter;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author：zlp @Package：com.yushang.risk.assessment.service.impl @Project：risk_assessment
 *
 * @name：SecurityAttributeServiceImpl @Date：2023/12/18 16:29 @Filename：SecurityAttributeServiceImpl
 */
@Service
public class SecurityAttributeServiceImpl implements SecurityAttributeService {
  @Resource private SecurityAttributeDao securityAttributeDao;
  @Resource private AttrMeansDao attrMeansDao;
  @Resource private AttrMenaceDao attrMenaceDao;
  /**
   * 获取所有安全属性集合
   *
   * @return
   */
  @Override
  public List<AttrResp> getAttrList() {
    List<SecurityAttribute> attrList = securityAttributeDao.list();
    List<Integer> attrIds =
        attrList.stream().map(SecurityAttribute::getId).collect(Collectors.toList());
    List<AttrMeans> attrMeans = attrMeansDao.getByAttrId(attrIds);
    List<AttrMenace> attrMenaces = attrMenaceDao.getByAttrId(attrIds);
    return AttrAdapter.buildAttrResp(attrList, attrMeans, attrMenaces);
  }
}

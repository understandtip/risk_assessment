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
import org.springframework.cache.annotation.Cacheable;
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
   * 获取所有安全属性集合。
   * 此方法通过查询数据库获取所有安全属性，并将它们转换为更适用于前端展示的格式。
   *
   * @return 返回一个包含所有安全属性信息的列表，每个属性包括其ID、名称、含义等信息。
   */
  @Override
  @Cacheable(cacheNames = "attrList")
  public List<AttrResp> getAttrList() {
      // 从数据库中列出所有安全属性
      List<SecurityAttribute> attrList = securityAttributeDao.list();

      // 提取所有属性的ID，供后续查询关联信息使用 
      List<Integer> attrIds =
          attrList.stream().map(SecurityAttribute::getId).collect(Collectors.toList());

      // 根据属性ID查询其关联的属性手段和属性威胁信息
      List<AttrMeans> attrMeans = attrMeansDao.getByAttrId(attrIds);
      List<AttrMenace> attrMenaces = attrMenaceDao.getByAttrId(attrIds);

      // 将获取到的安全属性及其关联信息转换为前端所需的格式
      return AttrAdapter.buildAttrResp(attrList, attrMeans, attrMenaces);
  }
}

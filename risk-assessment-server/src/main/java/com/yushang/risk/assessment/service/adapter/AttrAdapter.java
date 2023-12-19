package com.yushang.risk.assessment.service.adapter;

import com.yushang.risk.assessment.domain.entity.AttrMeans;
import com.yushang.risk.assessment.domain.entity.AttrMenace;
import com.yushang.risk.assessment.domain.entity.SecurityAttribute;
import com.yushang.risk.assessment.domain.vo.response.AttrResp;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @Author：zlp @Package：com.yushang.risk.assessment.service.adapter @Project：risk_assessment
 *
 * @name：AttrAdapter @Date：2023/12/19 9:49 @Filename：AttrAdapter
 */
public class AttrAdapter {
  /**
   * 构建AttrResp返回对象
   *
   * @param attrList
   * @param attrMeans
   * @param attrMenaces
   * @return
   */
  public static List<AttrResp> buildAttrResp(
      List<SecurityAttribute> attrList, List<AttrMeans> attrMeans, List<AttrMenace> attrMenaces) {
    List<AttrResp> attrRespList = new ArrayList<>();
    attrList.forEach(
        attr -> {
          AttrResp attrResp = new AttrResp();
          BeanUtils.copyProperties(attr, attrResp);
          // 处理防护防护手段
          List<AttrMeans> means = new ArrayList<>();
          attrMeans.forEach(
              attrMeans1 -> {
                if (attrMeans1.getAttrId().equals(attr.getId())) {
                  means.add(attrMeans1);
                }
              });
          // 处理威胁信息
          AtomicReference<AttrMenace> menace = new AtomicReference<>();
          attrMenaces.forEach(
              attrMenace -> {
                if (attrMenace.getAttrId().equals(attr.getId())) {
                  menace.set(attrMenace);
                }
              });
          attrResp.setMenace(menace.get());
          attrResp.setMeansProtectionList(means);
          attrRespList.add(attrResp);
        });
    return attrRespList;
  }
}

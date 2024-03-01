package com.yushang.risk.assessment.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.data.Pictures;
import com.deepoove.poi.plugin.table.LoopRowTableRenderPolicy;
import com.yushang.risk.assessment.dao.CConfrontWayDao;
import com.yushang.risk.assessment.dao.CElementDao;
import com.yushang.risk.assessment.dao.CElementTypeDao;
import com.yushang.risk.assessment.dao.CElementTypeMethodDao;
import com.yushang.risk.assessment.domain.entity.CConfrontWay;
import com.yushang.risk.assessment.domain.entity.CElement;
import com.yushang.risk.assessment.domain.entity.CElementType;
import com.yushang.risk.assessment.domain.entity.CElementTypeMethod;
import com.yushang.risk.assessment.domain.vo.request.ConfrontSubReq;
import com.yushang.risk.assessment.domain.vo.response.ConfrontInfoResp;
import com.yushang.risk.assessment.service.ConfrontService;
import com.yushang.risk.assessment.service.MinioService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
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
  @Resource private MinioService minioService;

  /**
   * 获取对抗数据
   *
   * @return
   * @param isEnhance
   */
  @Override
  @Cacheable(cacheNames = "confrontData")
  public List<ConfrontInfoResp> getConfrontInfo(boolean isEnhance) {
    List<ConfrontInfoResp> respList = new LinkedList<>();
    List<CElement> cElements = cElementDao.listBySort();
    List<CElementType> cElementTypes = cElementTypeDao.listBySort();
    List<CElementTypeMethod> cElementTypeMethods = cElementTypeMethodDao.listByEnhance(isEnhance);
    List<CConfrontWay> cConfrontWays = cConfrontWayDao.listBySort();
    // cElementTypes
    Map<Integer, List<CElementType>> cElementTypeMap =
        cElementTypes.stream().collect(Collectors.groupingBy(CElementType::getElementId));
    // cConfrontWays
    Map<Integer, List<CConfrontWay>> cConfrontWayMap =
        cConfrontWays.stream().collect(Collectors.groupingBy(CConfrontWay::getMethodId));
    // cElementTypeMethods
    Map<Integer, List<CElementTypeMethod>> cElementTypeMethodMap =
        cElementTypeMethods.stream()
            .filter(c -> 0 == c.getPid())
            .collect(Collectors.groupingBy(CElementTypeMethod::getTypeId));
    Map<Integer, List<CElementTypeMethod>> typeMethodPidMap =
        cElementTypeMethods.stream().collect(Collectors.groupingBy(CElementTypeMethod::getPid));
    // cElements
    if (cElements != null && !cElements.isEmpty()) {
      cElements.forEach(
          e -> {
            ConfrontInfoResp resp = new ConfrontInfoResp();
            Integer elementId = e.getId();
            List<CElementType> elementTypes = cElementTypeMap.get(elementId);
            resp.setCElement(e);
            List<ConfrontInfoResp.ElementTypeResp> typeRespList = new LinkedList<>();
            // elementTypes
            if (elementTypes != null && !elementTypes.isEmpty()) {
              elementTypes.forEach(
                  cet -> {
                    ConfrontInfoResp.ElementTypeResp typeResp =
                        new ConfrontInfoResp.ElementTypeResp();
                    Integer cetId = cet.getId();
                    List<CElementTypeMethod> elementTypeMethods = cElementTypeMethodMap.get(cetId);
                    typeResp.setCElementType(cet);
                    List<ConfrontInfoResp.ElementTypeMethodResp> typeMethodRespList =
                        new LinkedList<>();
                    // elementTypeMethods
                    if (elementTypeMethods != null && !elementTypeMethods.isEmpty()) {
                      elementTypeMethods.forEach(
                          etm -> {
                            ConfrontInfoResp.ElementTypeMethodResp typeMethodResp =
                                new ConfrontInfoResp.ElementTypeMethodResp();
                            Integer etmId = etm.getId();
                            List<CConfrontWay> confrontWays = cConfrontWayMap.get(etmId);
                            typeMethodResp.setCElementTypeMethod(etm);
                            typeMethodResp.setCConfrontWayList(confrontWays);
                            typeMethodRespList.add(typeMethodResp);
                            if (isEnhance) {
                              // 增强,查出子typeMethod
                              List<CElementTypeMethod> typeMethodPidList =
                                  typeMethodPidMap.get(etm.getId());
                              List<ConfrontInfoResp.ElementTypeMethodResp> typeMethodRespList2 =
                                  new LinkedList<>();

                              if (typeMethodPidList != null && !typeMethodPidList.isEmpty()) {
                                typeMethodPidList.forEach(
                                    etm2 -> {
                                      ConfrontInfoResp.ElementTypeMethodResp typeMethodResp2 =
                                          new ConfrontInfoResp.ElementTypeMethodResp();
                                      Integer etmId2 = etm2.getId();
                                      List<CConfrontWay> confrontWays2 =
                                          cConfrontWayMap.get(etmId2);
                                      typeMethodResp2.setCElementTypeMethod(etm2);
                                      typeMethodResp2.setCConfrontWayList(confrontWays2);
                                      typeMethodRespList2.add(typeMethodResp2);
                                    });
                              }
                              typeMethodResp.setEnHanceTypeMethodList(typeMethodRespList2);
                            }
                          });
                    }
                    typeResp.setElementTypeMethodResp(typeMethodRespList);
                    typeRespList.add(typeResp);
                  });
            }
            resp.setElementTypeRespList(typeRespList);
            respList.add(resp);
          });
    }
    return respList;
  }

  /**
   * 生成报告
   *
   * @param confrontSubReq
   * @param outputStream
   */
  @Override
  public void submitPort(ConfrontSubReq confrontSubReq, ByteArrayOutputStream outputStream)
      throws IOException {
    Map<String, Object> finalMap = new HashMap<>();
    List<CConfrontWay> confrontWays = cConfrontWayDao.listByIds(confrontSubReq.getConfrontWayIds());
    List<Integer> methodIds =
        confrontWays.stream().map(CConfrontWay::getMethodId).collect(Collectors.toList());
    Map<Integer, String> methodMap =
        cElementTypeMethodDao.listByIds(methodIds).stream()
            .collect(Collectors.toMap(CElementTypeMethod::getId, CElementTypeMethod::getName));
    List<Object> var1List = new LinkedList<>();
    confrontWays.forEach(
        way -> {
          Map<String, Object> map = new HashMap<>();
          String methodName = methodMap.get(way.getMethodId());
          map.put("typeMethod", methodName);
          map.put("confrontWay", way.getName());
          map.put("score", way.getScore());
          var1List.add(map);
        });
    finalMap.put("var1", var1List);

    finalMap.put(
        "pic1",
        Pictures.ofUrl(minioService.getFilePath(confrontSubReq.getPic1FileName()))
            .fitSize()
            .create());
    finalMap.put(
        "pic2",
        Pictures.ofUrl(minioService.getFilePath(confrontSubReq.getPic2FileName()))
            .fitSize()
            .create());
    // 渲染数据
    ClassPathResource classPathResource = new ClassPathResource("template/攻防对抗矩阵模板.docx");
    /* XWPFTemplate.compile(classPathResource.getInputStream())
    .render(finalMap)
    .writeToFile("D:\\对抗数据.docx"); */
    XWPFTemplate.compile(classPathResource.getInputStream()).render(finalMap).write(outputStream);
  }
}

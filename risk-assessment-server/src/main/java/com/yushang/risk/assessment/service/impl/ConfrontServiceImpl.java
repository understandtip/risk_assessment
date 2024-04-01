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
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author：zlp @Package：com.yushang.risk.assessment.service.impl @Project：risk_assessment
 *
 * @name：ConfrontServiceImpl @Date：2024/2/23 15:25 @Filename：ConfrontServiceImpl
 */
@Service
@Slf4j
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
    try {
      List<ConfrontInfoResp> respList = new LinkedList<>();

      Map<Integer, List<CElement>> elementMap = loadCElements();
      Map<Integer, List<CElementType>> elementTypeMap = loadCElementTypes();
      Map<Integer, List<CElementTypeMethod>> elementTypeMethodMap =
          loadCElementTypeMethods(isEnhance);
      Map<Integer, List<CConfrontWay>> confrontWayMap = loadCConfrontWays();

      elementMap.forEach(
          (elementId, elements) ->
              elements.forEach(
                  element ->
                      processElement(
                          element,
                          elementTypeMap,
                          elementTypeMethodMap,
                          confrontWayMap,
                          isEnhance,
                          respList)));
      return respList;
    } catch (Exception e) {
      // 适当的异常处理逻辑，例如记录日志或转换异常
      log.error("加载对抗数据失败！", e);
      return new LinkedList<>();
    }
  }

  /**
   * 加载CElement类型的元素集合，并按元素ID进行分组。
   *
   * @return 返回一个Map，键为CElement的ID，值为该ID对应的CElement元素列表。
   */
  private Map<Integer, List<CElement>> loadCElements() {
    // 通过排序获取CElement类型的元素列表
    List<CElement> cElements = cElementDao.listBySort();
    // 按ID进行分组
    return cElements.stream().collect(Collectors.groupingBy(CElement::getId));
  }

  /**
   * 加载CElementType类型的元素集合，并按元素ID进行分组。
   *
   * @return 返回一个Map，键为CElementType的elementID，值为该elementID对应的CElementType元素列表。
   */
  private Map<Integer, List<CElementType>> loadCElementTypes() {
    // 通过排序获取CElementType类型的元素列表
    List<CElementType> cElementTypes = cElementTypeDao.listBySort();
    // 按elementID进行分组
    return cElementTypes.stream().collect(Collectors.groupingBy(CElementType::getElementId));
  }

  /**
   * 加载CElementTypeMethod类型的元素集合，并按类型ID进行分组。 此方法根据是否增强加载策略，过滤出特定条件的数据。
   *
   * @param isEnhance 指示是否采用增强加载策略的布尔值。
   * @return 返回一个Map，键为CElementTypeMethod的typeID，值为该typeID对应的CElementTypeMethod元素列表。
   */
  private Map<Integer, List<CElementTypeMethod>> loadCElementTypeMethods(boolean isEnhance) {
    // 根据是否增强加载策略获取CElementTypeMethod类型的元素列表
    List<CElementTypeMethod> cElementTypeMethods = cElementTypeMethodDao.listByEnhance(isEnhance);
    return cElementTypeMethods.stream()
        // 根据是否增强加载以及父ID是否为0进行过滤
        .filter(c -> isEnhance || c.getPid() == 0)
        // 按typeID进行分组
        .collect(Collectors.groupingBy(CElementTypeMethod::getTypeId));
  }

  /**
   * 加载CConfrontWay类型的元素集合，并按方法ID进行分组。
   *
   * @return 返回一个Map，键为CConfrontWay的方法ID，值为该方法ID对应的CConfrontWay元素列表。
   */
  private Map<Integer, List<CConfrontWay>> loadCConfrontWays() {
    // 通过排序获取CConfrontWay类型的元素列表
    List<CConfrontWay> cConfrontWays = cConfrontWayDao.listBySort();
    // 按方法ID进行分组
    return cConfrontWays.stream().collect(Collectors.groupingBy(CConfrontWay::getMethodId));
  }

  /**
   * 处理元素信息，将其映射到响应列表中。
   *
   * @param element 要处理的CElement元素
   * @param elementTypeMap 元素类型映射，键为元素ID，值为元素类型列表
   * @param elementTypeMethodMap 元素类型方法映射，键为元素类型ID，值为元素类型方法列表
   * @param confrontWayMap 对抗方式映射，键为元素类型方法ID，值为对抗方式列表
   * @param isEnhance 是否增强处理，如果为true，则会递归处理子元素类型方法
   * @param respList 存储处理结果的响应列表
   */
  private void processElement(
      CElement element,
      Map<Integer, List<CElementType>> elementTypeMap,
      Map<Integer, List<CElementTypeMethod>> elementTypeMethodMap,
      Map<Integer, List<CConfrontWay>> confrontWayMap,
      boolean isEnhance,
      List<ConfrontInfoResp> respList) {

    // 创建一个新的ConfrontInfoResp对象，并设置元素信息
    ConfrontInfoResp resp = new ConfrontInfoResp();
    resp.setCElement(element);
    List<ConfrontInfoResp.ElementTypeResp> typeRespList = new LinkedList<>();

    // 获取并处理元素的所有类型
    List<CElementType> elementTypes =
        elementTypeMap.getOrDefault(element.getId(), Collections.emptyList());
    elementTypes.forEach(
        cElementType -> {
          ConfrontInfoResp.ElementTypeResp typeResp = new ConfrontInfoResp.ElementTypeResp();
          typeResp.setCElementType(cElementType);

          // 获取并处理元素类型的全部方法
          List<CElementTypeMethod> elementTypeMethods =
              elementTypeMethodMap.getOrDefault(cElementType.getId(), Collections.emptyList());
          List<ConfrontInfoResp.ElementTypeMethodResp> typeMethodRespList = new LinkedList<>();

          elementTypeMethods.forEach(
              etm -> {
                ConfrontInfoResp.ElementTypeMethodResp typeMethodResp =
                    new ConfrontInfoResp.ElementTypeMethodResp();
                typeMethodResp.setCElementTypeMethod(etm);

                // 获取并设置对抗方式
                List<CConfrontWay> confrontWays =
                    confrontWayMap.getOrDefault(etm.getId(), Collections.emptyList());
                typeMethodResp.setCConfrontWayList(confrontWays);

                // 如果是增强处理，则递归处理子元素类型方法
                if (isEnhance) {
                  List<CElementTypeMethod> childElementTypeMethods =
                      elementTypeMethodMap.get(etm.getId());
                  if (childElementTypeMethods != null && !childElementTypeMethods.isEmpty()) {
                    List<ConfrontInfoResp.ElementTypeMethodResp> enhanceTypeMethodList =
                        new LinkedList<>();
                    childElementTypeMethods.forEach(
                        etm2 -> {
                          ConfrontInfoResp.ElementTypeMethodResp typeMethodResp2 =
                              new ConfrontInfoResp.ElementTypeMethodResp();
                          typeMethodResp2.setCElementTypeMethod(etm2);

                          // 获取并设置子元素类型的对抗方式
                          List<CConfrontWay> confrontWays2 =
                              confrontWayMap.getOrDefault(etm2.getId(), Collections.emptyList());
                          typeMethodResp2.setCConfrontWayList(confrontWays2);
                          enhanceTypeMethodList.add(typeMethodResp2);
                        });
                    typeMethodResp.setEnHanceTypeMethodList(enhanceTypeMethodList);
                  }
                }

                typeMethodRespList.add(typeMethodResp);
              });

          typeResp.setElementTypeMethodResp(typeMethodRespList);
          typeRespList.add(typeResp);
        });

    resp.setElementTypeRespList(typeRespList);
    respList.add(resp);
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

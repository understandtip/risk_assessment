package com.yushang.risk.assessment.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.data.Pictures;
import com.deepoove.poi.plugin.table.LoopRowTableRenderPolicy;
import com.yushang.risk.assessment.dao.*;
import com.yushang.risk.assessment.domain.dto.RiskReqDto;
import com.yushang.risk.assessment.domain.entity.*;
import com.yushang.risk.assessment.domain.vo.request.GenerateReportReq;
import com.yushang.risk.assessment.domain.vo.response.GradeVo;
import com.yushang.risk.assessment.domain.vo.response.RiskResp;
import com.yushang.risk.assessment.service.MinioService;
import com.yushang.risk.assessment.service.RiskService;
import com.yushang.risk.common.config.ThreadPoolConfig;
import com.yushang.risk.common.config.minio.MinioProp;
import com.yushang.risk.common.constant.RiskConstant;
import com.yushang.risk.common.event.GeneratePortEvent;
import com.yushang.risk.common.event.domaih.dto.GeneratePortDto;
import com.yushang.risk.domain.entity.Project;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * @Author：zlp @Package：com.yushang.risk.assessment.service.impl @Project：risk_assessment
 *
 * @name：RiskServiceImpl @Date：2023/12/18 16:13 @Filename：RiskServiceImpl
 */
@Service
@Slf4j
public class RiskServiceImpl implements RiskService {
  @Resource private RiskDao riskDao;
  @Resource private RiskGradeDao riskGradeDao;
  @Resource private GradeDao gradeDao;
  @Resource private CycleDao cycleDao;
  @Resource private MinioService minioService;
  @Resource private GenerateRecordDao generateRecordDao;
  @Resource private ProjectDao projectDao;
  @Resource private MinioProp minioProp;
  @Resource private ApplicationEventPublisher applicationEventPublisher;

  @Qualifier(ThreadPoolConfig.COMMON_EXECUTOR)
  @Resource
  private ThreadPoolTaskExecutor threadPoolTaskExecutor;
  /**
   * 查询指定分类下的风险集合
   *
   * @param categoryId
   * @return
   */
  @Override
  @Cacheable(cacheNames = "categoryRisk", key = "#categoryId")
  public List<RiskResp> getRiskList(Integer categoryId) {
    List<Risk> riskList = riskDao.getRiskFromCategory(categoryId);
    return riskList.stream()
        .map(
            (risk -> {
              try {
                return threadPoolTaskExecutor.submit(() -> this.dealRiskToRiskResp(risk)).get();
              } catch (Exception e) {
                log.error("查询指定分类下的风险集合 报错了", e);
                return null;
              }
            }))
        .collect(Collectors.toList());
  }

  /**
   * 获取所有风险集合
   *
   * @return
   */
  @Override
  @Cacheable(cacheNames = "riskList")
  public List<RiskResp> getRiskList() {
    List<Risk> riskList = riskDao.list(new LambdaQueryWrapper<Risk>().eq(Risk::getParentId, 0));
    return riskList.stream()
        .map(
            (risk -> {
              try {
                return threadPoolTaskExecutor.submit(() -> this.dealRiskToRiskResp(risk)).get();
              } catch (Exception e) {
                log.error("查询指定分类下的风险集合 报错了", e);
                return null;
              }
            }))
        .collect(Collectors.toList());
  }

  /**
   * 生成测评报告
   *
   * @param reportReq
   * @param outputStream
   */
  @Override
  public void generateReport(GenerateReportReq reportReq, ByteArrayOutputStream outputStream)
      throws IOException {
    Project project = projectDao.getById(reportReq.getProjectId());

    HashMap<String, Object> finalMap = new HashMap<>(64);
    // 填充数据
    finalMap.put("projectName", project.getName());
    finalMap.put("version", project.getVersion());
    finalMap.put("classification", project.getClassification());
    finalMap.put("producer", project.getAuthorName());
    Date generatedTime = new Date();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    String nowTime = dateFormat.format(generatedTime);
    finalMap.put("generatedTime", nowTime);
    // 变更记录
    List<Map<String, Object>> tabList = new ArrayList<>();
    Map<String, Object> tabMap = new HashMap<>();
    tabMap.put("historyTime", nowTime);
    tabMap.put("historyVersion", project.getVersion());
    tabMap.put("historyExplain", project.getExplain());
    tabMap.put("historyAuthor", project.getAuthorName());
    tabList.add(tabMap);
    finalMap.put("tab", tabList);
    finalMap.put("testingCompany", project.getTestingCompany());
    // logo

    finalMap.put(
        "logo0", Pictures.ofUrl(minioService.getFilePath(project.getLogo())).size(80, 30).create());
    finalMap.put(
        "logo", Pictures.ofUrl(minioService.getFilePath(project.getLogo())).size(250, 70).create());
    finalMap.put(
        "gradePic",
        Pictures.ofUrl(minioService.getFilePath(reportReq.getPicFileName())).fitSize().create());
    // 处理请求数据
    List<GenerateReportReq.riskReq> riskList = reportReq.getRiskList();
    Map<Integer, List<RiskReqDto>> riskDataMap = this.dealRiskReqData(riskList);

    Integer cycleId1 = this.getCycleIdLikeName("数据采集");
    Integer cycleId2 = this.getCycleIdLikeName("数据传输");
    Integer cycleId3 = this.getCycleIdLikeName("数据存储");
    Integer cycleId4 = this.getCycleIdLikeName("数据处理");
    Integer cycleId5 = this.getCycleIdLikeName("数据交换");
    Integer cycleId6 = this.getCycleIdLikeName("数据销毁");
    // 收集好周期id集合
    List<Integer> cycleIdList =
        Arrays.asList(cycleId1, cycleId2, cycleId3, cycleId4, cycleId5, cycleId6);

    // 周期风险数据的处理
    cycleIdList.forEach(
        cycleId -> {
          List<Map<String, Object>> varList = new ArrayList<>();
          List<RiskReqDto> riskReqDtoList = riskDataMap.get(cycleId);
          if (riskReqDtoList != null && !riskReqDtoList.isEmpty()) {
            riskReqDtoList.forEach(
                riskReqDto -> {
                  Map<String, Object> varMap = new HashMap<>();
                  varMap.put("riskId" + cycleId, riskReqDto.getRiskId());
                  varMap.put("title" + cycleId, riskReqDto.getTitle());
                  varMap.put("desc" + cycleId, riskReqDto.getDesc());
                  varMap.put("option" + cycleId, riskReqDto.getOptionName());
                  varMap.put("score" + cycleId, riskReqDto.getScore());
                  varMap.put("adv" + cycleId, riskReqDto.getAdvise());
                  varList.add(varMap);
                });
            finalMap.put("var" + cycleId, varList);
          }
        });
    Configure configure = Configure.builder().bind("tab", new LoopRowTableRenderPolicy()).build();
    // 渲染数据
    ClassPathResource classPathResource = new ClassPathResource("template/数据安全检查模板.docx");
    XWPFTemplate.compile(classPathResource.getInputStream(), configure)
        .render(finalMap)
        .write(outputStream);
    byte[] bytes = outputStream.toByteArray();
    outputStream.close();
    // 文档生成事件发布
    applicationEventPublisher.publishEvent(
        new GeneratePortEvent(
            this,
            GeneratePortDto.builder().bytes(bytes).projectId(reportReq.getProjectId()).build()));
  }

  /**
   * 根据周期名称模糊查询id
   *
   * @param cycleName
   * @return
   */
  private Integer getCycleIdLikeName(String cycleName) {
    return cycleDao.getCycleIdLikeName(cycleName);
  }

  /**
   * 处理请求数据
   *
   * @param riskList
   * @return key:categoryId value:RiskReqDto
   */
  private Map<Integer, List<RiskReqDto>> dealRiskReqData(List<GenerateReportReq.riskReq> riskList) {
    List<RiskReqDto> list =
        riskList.stream()
            .map(
                riskReq -> {
                  String riskIdStr = riskReq.getRiskId();
                  // 处理id:去R
                  Integer riskId = removeR(riskIdStr);
                  Risk risk = riskDao.getById(riskId);
                  RiskGrade riskGrade = riskGradeDao.getById(riskReq.getRiskGradeId());
                  Grade grade = gradeDao.getById(riskGrade.getGradeId());
                  RiskReqDto dto =
                      RiskReqDto.builder()
                          .cycleId(risk.getCycleId())
                          .riskId(riskIdStr)
                          .title(risk.getTitle())
                          .desc(risk.getDesc())
                          .score(riskGrade.getScore())
                          .optionName(grade.getName())
                          .build();
                  return dto;
                })
            .collect(Collectors.toList());
    Map<Integer, List<RiskReqDto>> collect =
        list.stream().collect(Collectors.groupingBy(RiskReqDto::getCycleId));
    return collect;
  }

  /**
   * 取出前端传过来的风险R前缀
   *
   * @param riskIdStr
   * @return
   */
  private Integer removeR(String riskIdStr) {
    String s = riskIdStr.replaceFirst("^" + RiskConstant.RISK_ID_PRE, "").replaceFirst("^0+", "");
    return Integer.parseInt(s);
  }

  /**
   * 处理风险对象，将其转换为RiskResp对象。
   *
   * @param risk 输入的风险对象。
   * @return 转换后的RiskResp对象，包含了风险的详细信息及其子风险列表和对应的风险等级信息。
   */
  private RiskResp dealRiskToRiskResp(Risk risk) {
    RiskResp riskResp = new RiskResp();
    // 使用BeanUtils工具类将risk对象的属性值复制到riskResp对象中
    BeanUtils.copyProperties(risk, riskResp);
    // 生成风险ID字符串，并确保其长度为4位，不足4位前面补0
    String s = String.format("%04d", risk.getId());
    riskResp.setRiskId(RiskConstant.RISK_ID_PRE + s);

    // 检查当前风险是否有子风险
    boolean b = riskDao.checkChild(risk.getId());
    if (b) {
      // 如果有子风险，则查询子风险信息
      List<Risk> childRisk = riskDao.getChild(risk.getId());
      List<RiskResp> childRespList = new ArrayList<>();
      // 异步处理每个子风险，将其转换为RiskResp对象并添加到列表中
      childRisk.forEach(
          child ->
              CompletableFuture.runAsync(
                  () -> childRespList.add(dealRiskToRiskResp(child)), threadPoolTaskExecutor));

      // 设置子风险列表到riskResp对象中
      riskResp.setChildrenRiskList(childRespList);
    }

    // 查询当前风险对应的等级分数情况
    List<RiskGrade> riskGradeList = riskGradeDao.getGradeByRiskId(risk.getId());
    List<Grade> gradeList = gradeDao.list();
    // 处理每个风险等级，将其转换为GradeVo对象，并收集到列表中
    List<GradeVo> gradeVoList =
        riskGradeList.stream()
            .map(
                riskGrade -> {
                  GradeVo gradeVo = new GradeVo();
                  gradeVo.setId(riskGrade.getId());
                  String gradeName = "";
                  // 查找等级名称
                  for (Grade grade : gradeList) {
                    if (grade.getId().equals(riskGrade.getGradeId())) {
                      gradeName = grade.getName();
                      break;
                    }
                  }
                  gradeVo.setName(gradeName);
                  gradeVo.setScore(riskGrade.getScore());
                  return gradeVo;
                })
            .collect(Collectors.toList());
    // 设置风险等级列表到riskResp对象中
    riskResp.setGradeVoList(gradeVoList);
    return riskResp;
  }
}

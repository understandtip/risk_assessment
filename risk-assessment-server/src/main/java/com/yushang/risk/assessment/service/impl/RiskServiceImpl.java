package com.yushang.risk.assessment.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.plugin.table.LoopRowTableRenderPolicy;
import com.deepoove.poi.policy.ListRenderPolicy;
import com.yushang.risk.assessment.dao.CycleDao;
import com.yushang.risk.assessment.dao.GradeDao;
import com.yushang.risk.assessment.dao.RiskDao;
import com.yushang.risk.assessment.dao.RiskGradeDao;
import com.yushang.risk.assessment.domain.dto.RiskReqDto;
import com.yushang.risk.assessment.domain.entity.Grade;
import com.yushang.risk.assessment.domain.entity.Risk;
import com.yushang.risk.assessment.domain.entity.RiskGrade;
import com.yushang.risk.assessment.domain.vo.request.GenerateReportReq;
import com.yushang.risk.assessment.domain.vo.response.GradeVo;
import com.yushang.risk.assessment.domain.vo.response.RiskResp;
import com.yushang.risk.assessment.service.RiskService;
import com.yushang.risk.common.constant.RiskConstant;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author：zlp @Package：com.yushang.risk.assessment.service.impl @Project：risk_assessment
 *
 * @name：RiskServiceImpl @Date：2023/12/18 16:13 @Filename：RiskServiceImpl
 */
@Service
public class RiskServiceImpl implements RiskService {
  @Resource private RiskDao riskDao;
  @Resource private RiskGradeDao riskGradeDao;
  @Resource private GradeDao gradeDao;
  @Resource private CycleDao cycleDao;
  /**
   * 查询指定分类下的风险集合
   *
   * @param categoryId
   * @return
   */
  @Override
  public List<RiskResp> getRiskList(Integer categoryId) {
    List<Risk> riskList = riskDao.getRiskFromCategory(categoryId);
    return riskList.stream().map(this::dealRiskToRiskResp).collect(Collectors.toList());
  }

  /**
   * 获取所有风险集合
   *
   * @return
   */
  @Override
  public List<RiskResp> getRiskList() {
    List<Risk> riskList = riskDao.list(new LambdaQueryWrapper<Risk>().eq(Risk::getParentId, 0));
    return riskList.stream().map(this::dealRiskToRiskResp).collect(Collectors.toList());
  }

  /**
   * 生成测评报告
   *
   * @param reportReq
   * @param response
   */
  @Override
  public void generateReport(GenerateReportReq reportReq, HttpServletResponse response)
      throws IOException {
    ServletOutputStream outputStream = response.getOutputStream();
    // 设置响应内容类型
    response.setContentType("application/octet-stream");
    response.setHeader("Content-Disposition", "attachment; filename=generated.docx");
    HashMap<String, Object> finalMap = new HashMap<>(64);
    // 填充数据
    finalMap.put("projectName", "测试项目");
    finalMap.put("version", "V1.0");
    finalMap.put("classification", "A级");
    finalMap.put("producer", "测试用户");
    Date generatedTime = new Date();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    String nowTime = dateFormat.format(generatedTime);
    finalMap.put("generatedTime", nowTime);
    // 变更记录
    List<Map<String, Object>> tabList = new ArrayList<>();
    Map<String, Object> tabMap = new HashMap<>();
    tabMap.put("historyTime", nowTime);
    tabMap.put("historyVersion", "V1.0");
    tabMap.put("historyExplain", "说明文字");
    tabMap.put("historyAuthor", "测试用户");
    tabList.add(tabMap);
    finalMap.put("tab", tabList);
    // logo
    finalMap.put("logo", "logo图片");
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
    XWPFTemplate.compile("C:\\Users\\zlp\\Desktop\\公司材料\\数据安全检查模板.docx", configure)
        .render(finalMap)
        .writeToFile("C:\\Users\\zlp\\Desktop\\公司材料\\target.docx");
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
   * 处理风险对象,转换为RiskResp
   *
   * @param risk
   */
  private RiskResp dealRiskToRiskResp(Risk risk) {
    RiskResp riskResp = new RiskResp();
    BeanUtils.copyProperties(risk, riskResp);
    String s = String.format("%04d", risk.getId());
    riskResp.setRiskId(RiskConstant.RISK_ID_PRE + s);
    // 判断有没有子风险
    boolean b = riskDao.checkChild(risk.getId());
    if (b) {
      // 查出子风险
      List<Risk> childRisk = riskDao.getChild(risk.getId());
      List<RiskResp> childRespList = new ArrayList<>();

      childRisk.forEach(child -> childRespList.add(dealRiskToRiskResp(child)));

      riskResp.setChildrenRiskList(childRespList);
    }
    // 查询风险对应分数情况
    List<RiskGrade> riskGradeList = riskGradeDao.getGradeByRiskId(risk.getId());
    // 查询出等级名称
    List<GradeVo> gradeVoList =
        riskGradeList.stream()
            .map(
                riskGrade -> {
                  GradeVo gradeVo = new GradeVo();
                  gradeVo.setId(riskGrade.getId());
                  gradeVo.setName(gradeDao.getById(riskGrade.getGradeId()).getName());
                  gradeVo.setScore(riskGrade.getScore());
                  return gradeVo;
                })
            .collect(Collectors.toList());
    riskResp.setGradeVoList(gradeVoList);
    return riskResp;
  }
}

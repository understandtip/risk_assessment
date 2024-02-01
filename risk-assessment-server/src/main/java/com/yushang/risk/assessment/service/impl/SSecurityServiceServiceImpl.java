package com.yushang.risk.assessment.service.impl;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.data.Pictures;
import com.deepoove.poi.plugin.table.LoopRowTableRenderPolicy;
import com.yushang.risk.assessment.dao.*;
import com.yushang.risk.assessment.service.MinioService;
import com.yushang.risk.assessment.service.SServiceBugOptService;
import com.yushang.risk.common.event.GenerateBugReportEvent;
import com.yushang.risk.common.event.domaih.dto.SUserDto;
import com.yushang.risk.domain.entity.*;
import com.yushang.risk.assessment.domain.vo.request.SecurityServiceBugBugReq;
import com.yushang.risk.assessment.domain.vo.response.SecurityModelDetailResp;
import com.yushang.risk.assessment.domain.vo.response.SecurityModelResp;
import com.yushang.risk.assessment.service.SSecurityServiceService;
import com.yushang.risk.assessment.service.adapter.SecurityServiceAdapter;
import fr.opensagres.poi.xwpf.converter.pdf.PdfConverter;
import fr.opensagres.poi.xwpf.converter.pdf.PdfOptions;
import io.swagger.models.auth.In;
import javafx.scene.paint.Stop;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.BeanUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.yushang.risk.common.constant.RiskConstant.TEM_FILE_DIR;
import static com.yushang.risk.common.constant.RiskConstant.TEM_PIC_FILE_DIR;

/**
 * @Author：zlp @Package：com.yushang.risk.assessment.service.impl @Project：risk_assessment
 *
 * @name：SSecurityServiceServiceImpl @Date：2024/1/15 14:26 @Filename：SSecurityServiceServiceImpl
 */
@Service
@Slf4j
public class SSecurityServiceServiceImpl implements SSecurityServiceService {

  @Resource private SSecurityServiceDao sSecurityServiceDao;
  @Resource private SServiceBugDao sServiceBugDao;
  @Resource private SUserDao sUserDao;
  @Resource private SBugDao sBugDao;
  @Resource private SUserRecordDao sUserRecordDao;
  @Resource private ApplicationEventPublisher applicationEventPublisher;
  @Resource private SServiceBugOptDao sServiceBugOptDao;
  @Resource private SBugOptRelDao sBugOptRelDao;

  /**
   * 获取安全服务模块列表
   *
   * @return
   */
  @Override
  public List<SecurityModelResp> getSecurityModel() {
    return sSecurityServiceDao.list().stream()
        .map(
            service -> {
              SecurityModelResp resp = new SecurityModelResp();
              BeanUtils.copyProperties(service, resp);
              return resp;
            })
        .collect(Collectors.toList());
  }

  /**
   * 查看模块详细信息
   *
   * @return
   * @param modelId
   */
  @Override
  public SecurityModelDetailResp getModelDetail(Integer modelId) {
    SSecurityService byId = sSecurityServiceDao.getById(modelId);
    SecurityModelDetailResp resp = new SecurityModelDetailResp();
    BeanUtils.copyProperties(byId, resp);
    // bugInfo
    List<SecurityModelDetailResp.BugInfo> bugInfoList =
        sServiceBugDao.getBugInfoListByModelId(modelId);
    resp.setBugInfoList(bugInfoList);

    return resp;
  }

  /**
   * 提交漏洞项目
   *
   * @param bugReq
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public void submitBugReport(SecurityServiceBugBugReq bugReq) {
    SUser sUser = SecurityServiceAdapter.buildSUser(bugReq);
    sUserDao.save(sUser);
    // 保存对应漏洞信息
    sUserRecordDao.saveBatch(sUser, bugReq);
    applicationEventPublisher.publishEvent(
        new GenerateBugReportEvent(
            this,
            SUserDto.builder()
                .userId(sUser.getId())
                .serviceId(bugReq.getId())
                .bugIds(bugReq.getBugIds())
                .addBugs(bugReq.getAddBugs())
                .build()));
  }

  /**
   * 生成漏洞报告
   *
   * @param dto
   * @return
   */
  @Override
  public String generateBugReport(SUserDto dto) {
    SUser user = sUserDao.getById(dto.getUserId());
    List<SBug> bugs = sBugDao.listByIds(dto.getBugIds());
    // 新增的漏洞
    if (dto.getAddBugs() != null) {
      dto.getAddBugs()
          .forEach(
              addBug -> {
                SBug sBug = new SBug();
                BeanUtils.copyProperties(addBug, sBug);
                bugs.add(sBug);
              });
    }
    Integer serviceId = dto.getServiceId();
    List<Integer> ids = getBugsId(bugs);
    List<SBugOpt> optList = sBugOptRelDao.getOptListByBugIds(ids);
    // List<SBugOpt> optList = sServiceBugOptDao.getByServiceId(serviceId);
    Map<String, Object> map = new HashMap<>();
    map.put("portName", "羽觞安全报告");
    map.put("name", user.getName());
    map.put("phone", user.getPhone());
    map.put("email", user.getEmail());

    map.put("img1", Pictures.ofLocal(TEM_PIC_FILE_DIR + serviceId + "/a1.jpeg").fitSize().create());
    map.put("img2", Pictures.ofLocal(TEM_PIC_FILE_DIR + serviceId + "/a2.jpeg").fitSize().create());
    map.put("img3", Pictures.ofLocal(TEM_PIC_FILE_DIR + serviceId + "/a3.jpeg").fitSize().create());
    map.put("img4", Pictures.ofLocal(TEM_PIC_FILE_DIR + serviceId + "/a4.jpeg").fitSize().create());
    map.put(
        "img21", Pictures.ofLocal(TEM_PIC_FILE_DIR + serviceId + "/b1.jpeg").fitSize().create());
    map.put(
        "img22", Pictures.ofLocal(TEM_PIC_FILE_DIR + serviceId + "/b2.jpeg").fitSize().create());
    map.put(
        "img23", Pictures.ofLocal(TEM_PIC_FILE_DIR + serviceId + "/b3.jpeg").fitSize().create());
    List<Map<String, Object>> list = new ArrayList<>();
    bugs.forEach(
        bug -> {
          Map<String, Object> map1 = new HashMap<>();
          map1.put("name", bug.getName());
          map1.put("theory", bug.getTheory());
          map1.put("harm", bug.getHarm());
          list.add(map1);
        });
    map.put("tab1", list);

    List<Map<String, Object>> list2 = new ArrayList<>();
    optList.forEach(
        opt -> {
          Map<String, Object> map1 = new HashMap<>();
          map1.put("function", opt.getFunction());
          map1.put("unit", opt.getUnit());
          map1.put("unit_price", opt.getUnitPrice());
          map1.put("number", opt.getNumber());
          list2.add(map1);
        });

    map.put("tab2", list2);

    // 渲染数据
    Configure configure =
        Configure.builder()
            .bind("tab1", new LoopRowTableRenderPolicy())
            .bind("tab2", new LoopRowTableRenderPolicy())
            .build();
    ClassPathResource resource = new ClassPathResource("template/安全服务漏洞报告模板.docx");
    XWPFTemplate compile = null;
    FileInputStream fileInputStream = null;
    FileOutputStream fileOutputStream = null;
    try {
      compile = XWPFTemplate.compile(resource.getInputStream(), configure);
      long time = System.currentTimeMillis();
      String wordFileName = TEM_FILE_DIR + time + ".docx";
      compile.render(map).writeAndClose(new FileOutputStream(wordFileName));
      // 转pdf
      fileInputStream = new FileInputStream(wordFileName);
      XWPFDocument xwpfDocument = new XWPFDocument(fileInputStream);
      PdfOptions pdfOptions = PdfOptions.create();
      String pdfFileName = TEM_FILE_DIR + time + ".pdf";
      fileOutputStream = new FileOutputStream(pdfFileName);
      PdfConverter.getInstance().convert(xwpfDocument, fileOutputStream, pdfOptions);
      fileInputStream.close();
      System.out.println("new File(substring).delete() = " + new File(wordFileName).delete());
      return pdfFileName;
    } catch (IOException e) {
      log.error("生成漏洞报告失败.", e);
      return null;
    } finally {
      try {
        if (fileInputStream != null) fileInputStream.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
      try {
        if (fileOutputStream != null) fileOutputStream.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * 获取漏洞id集合
   *
   * @param bugs
   * @return
   */
  private List<Integer> getBugsId(List<SBug> bugs) {
    return bugs.stream()
        .filter(b -> b.getId() != null)
        .map(SBug::getId)
        .collect(Collectors.toList());
  }
}

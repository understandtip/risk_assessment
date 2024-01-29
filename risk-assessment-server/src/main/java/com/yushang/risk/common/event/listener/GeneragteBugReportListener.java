package com.yushang.risk.common.event.listener;

import com.yushang.risk.assessment.dao.SUserDao;
import com.yushang.risk.assessment.service.MinioService;
import com.yushang.risk.assessment.service.SSecurityServiceService;
import com.yushang.risk.common.constant.RiskConstant;
import com.yushang.risk.common.event.GenerateBugReportEvent;
import com.yushang.risk.common.event.domaih.dto.SUserDto;
import com.yushang.risk.domain.entity.SUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static com.yushang.risk.common.constant.RiskConstant.TEM_FILE_DIR;

/**
 * @Author：zlp @Package：com.yushang.risk.common.event.listener @Project：risk_assessment
 *
 * @name：Generagte @Date：2024/1/18 15:46 @Filename：Generagte
 */
@Component
@Slf4j
public class GeneragteBugReportListener {
  @Resource private SSecurityServiceService service;
  @Resource private MinioService minioService;
  @Resource private SUserDao sUserDao;

  @TransactionalEventListener(
      value = GenerateBugReportEvent.class,
      phase = TransactionPhase.AFTER_COMMIT)
  public void generateBugReport(GenerateBugReportEvent event) {
    log.info("开始生成pdf");
    SUserDto dto = event.getSUserDto();
    String pdfFilePath = service.generateBugReport(dto);
    if (StringUtils.isEmpty(pdfFilePath)) return;
    log.info("pdfFilePath-->{} ", pdfFilePath);
    try {
      // 把pdf上传
      String minioFileName = pdfFilePath.substring(pdfFilePath.lastIndexOf("tem/"));
      String url = minioService.upload(new FileInputStream(pdfFilePath), minioFileName);
      new File(pdfFilePath).delete();
      // 地址存数据库
      SUser user = SUser.builder().id(dto.getUserId()).portName(minioFileName).build();
      sUserDao.updateById(user);
    } catch (FileNotFoundException e) {
      log.error("pdf文件上传失败", e);
    }
  }
}

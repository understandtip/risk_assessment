package com.yushang.risk.common.event.listener;

import com.yushang.risk.assessment.dao.GenerateRecordDao;
import com.yushang.risk.assessment.dao.ProjectDao;
import com.yushang.risk.assessment.domain.dto.RequestDataInfo;
import com.yushang.risk.assessment.domain.entity.GenerateRecord;
import com.yushang.risk.assessment.domain.entity.Project;
import com.yushang.risk.assessment.service.MinioService;
import com.yushang.risk.assessment.service.adapter.RecordAdapter;
import com.yushang.risk.common.event.GeneratePortEvent;
import com.yushang.risk.common.event.domaih.dto.GeneratePortDto;
import com.yushang.risk.common.util.RedisUtils;
import com.yushang.risk.common.util.RequestHolder;
import com.yushang.risk.constant.RedisCommonKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.util.concurrent.TimeUnit;

/**
 * @Author：zlp @Package：com.yushang.risk.common.event.listener @Project：risk_assessment
 *
 * @name：GeneratePortListener @Date：2024/1/22 15:21 @Filename：GeneratePortListener
 */
@Component
@Slf4j
public class GeneratePortListener {
  @Resource private ProjectDao projectDao;
  @Resource private MinioService minioService;
  @Resource private GenerateRecordDao generateRecordDao;

  /**
   * 生成报告后,存到minio,保存数据到数据库
   *
   * @param generatePortEvent
   */
  @EventListener(classes = GeneratePortEvent.class)
  public void generatePortListener(GeneratePortEvent generatePortEvent) {
    GeneratePortDto portDto = generatePortEvent.getGeneratePortDto();
    byte[] bytes = portDto.getBytes();
    Integer projectId = portDto.getProjectId();
    Project project = projectDao.getById(projectId);

    RequestDataInfo dataInfo = RequestHolder.get();
    RequestHolder.set(dataInfo);
    // 保存报告到Minio
    String fileName = MinioService.MINIO_PORT + "/" + System.currentTimeMillis() + "_评测报告.docx";

    String url = minioService.upload(new ByteArrayInputStream(bytes), fileName);
    //  往生成报告记录表中插入数据
    GenerateRecord record =
        RecordAdapter.buildGenerateRecord(project, RequestHolder.get().getUid(), fileName);
    generateRecordDao.save(record);
  }

  /**
   * 生成报告后,记录日志
   *
   * @param generatePortEvent
   */
  @EventListener(classes = GeneratePortEvent.class)
  public void logGeneratePort(GeneratePortEvent generatePortEvent) {
    RedisUtils.inc(RedisCommonKey.USER_GENERATE_PORT_DAY_KEY, 24, TimeUnit.HOURS);
  }
}

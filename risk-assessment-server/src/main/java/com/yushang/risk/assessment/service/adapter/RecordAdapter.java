package com.yushang.risk.assessment.service.adapter;

import cn.hutool.core.date.LocalDateTimeUtil;
import com.yushang.risk.assessment.domain.entity.GenerateRecord;
import com.yushang.risk.domain.entity.Project;

/**
 * @Author：zlp @Package：com.yushang.risk.assessment.service.adapter @Project：risk_assessment
 *
 * @name：RecordAdapter @Date：2024/1/4 16:04 @Filename：RecordAdapter
 */
public class RecordAdapter {
  /**
   * 生成报告时,构建报告记录对象
   *
   * @param project
   * @param uid
   * @param url
   * @return
   */
  public static GenerateRecord buildGenerateRecord(Project project, Integer uid, String url) {
    GenerateRecord record = new GenerateRecord();
    record.setProjectId(project.getId());
    record.setProjectName(project.getName());
    record.setName(project.getName() + "_" + LocalDateTimeUtil.now() + "_测评报告");
    record.setFileName(url);
    record.setAuthorId(uid);
    return record;
  }
}

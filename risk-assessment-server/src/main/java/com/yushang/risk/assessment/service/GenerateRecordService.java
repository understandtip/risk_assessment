package com.yushang.risk.assessment.service;

import com.yushang.risk.assessment.domain.vo.request.RecordPageReq;
import com.yushang.risk.assessment.domain.vo.response.PageBaseResp;
import com.yushang.risk.assessment.domain.vo.response.RecordResp;

import java.util.List;

/**
 * 报告记录 服务类
 *
 * @author zlp
 * @since 2023-12-29
 */
public interface GenerateRecordService {
  /**
   * 分页查询生成报告记录
   *
   * @param uid
   * @param recordPageReq
   * @return
   */
  PageBaseResp<RecordResp> getListByPage(Integer uid, RecordPageReq recordPageReq);

  /**
   * @param recordId
   * @return
   */
  boolean remove(List<Integer> recordId);
}

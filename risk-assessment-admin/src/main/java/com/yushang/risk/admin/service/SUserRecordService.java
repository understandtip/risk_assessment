package com.yushang.risk.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yushang.risk.admin.domain.vo.request.PageBaseReq;
import com.yushang.risk.admin.domain.vo.request.SecurityServiceRecordPageReq;
import com.yushang.risk.admin.domain.vo.response.PageBaseResp;
import com.yushang.risk.admin.domain.vo.response.SecurityServiceRecordPageResp;
import com.yushang.risk.domain.entity.SUserRecord;

/**
 * 用户提交记录 服务类
 *
 * @author zlp
 * @since 2024-01-16
 */
public interface SUserRecordService {
  /**
   * 查询用户申请漏洞列表
   *
   * @param recordPageReq
   * @return
   */
  PageBaseResp<SecurityServiceRecordPageResp> getUserBugRecordList(
      PageBaseReq<SecurityServiceRecordPageReq> recordPageReq);
}

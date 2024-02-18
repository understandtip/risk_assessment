package com.yushang.risk.admin.service;

import com.yushang.risk.admin.domain.vo.request.LogPageReq;
import com.yushang.risk.admin.domain.vo.request.PageBaseReq;
import com.yushang.risk.admin.domain.vo.response.PageBaseResp;
import com.yushang.risk.domain.entity.SysLoginLog;

/**
 * 系统登录日志 服务类
 *
 * @author zlp
 * @since 2024-02-06
 */
public interface SysLoginLogService {
  /**
   * 获取日志记录
   *
   * @param baseReq
   * @return
   */
  PageBaseResp<SysLoginLog> getLogPageList(PageBaseReq<LogPageReq> baseReq);
}

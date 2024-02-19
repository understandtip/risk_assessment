package com.yushang.risk.admin.service;

import com.yushang.risk.admin.domain.vo.request.OnlineUserPageReq;
import com.yushang.risk.admin.domain.vo.request.PageBaseReq;
import com.yushang.risk.admin.domain.vo.response.PageBaseResp;
import com.yushang.risk.domain.entity.OnlineUser;

/**
 * @Author：zlp @Package：com.yushang.risk.admin.service @Project：risk_assessment
 *
 * @name：MonitorService @Date：2024/2/18 14:14 @Filename：MonitorService
 */
public interface MonitorService {
  /**
   * 强退
   *
   * @param userName
   * @param platformType
   */
  void forceExit(String userName, String platformType);

  /**
   * 查询在线用户
   *
   * @param pageBaseReq
   * @return
   */
  PageBaseResp<OnlineUser> getOnlineList(PageBaseReq<OnlineUserPageReq> pageBaseReq);
}

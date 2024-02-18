package com.yushang.risk.admin.service;

import com.yushang.risk.admin.domain.vo.request.PageBaseReq;
import com.yushang.risk.admin.domain.vo.request.ProjectPageReq;
import com.yushang.risk.admin.domain.vo.response.PageBaseResp;
import com.yushang.risk.admin.domain.vo.response.ProjectPageResp;

/**
 * 项目表 服务类
 *
 * @author zlp
 * @since 2024-02-02
 */
public interface ProjectService {
  /**
   * 查询项目
   *
   * @param baseReq
   * @return
   */
  PageBaseResp<ProjectPageResp> getProject(PageBaseReq<ProjectPageReq> baseReq);
}

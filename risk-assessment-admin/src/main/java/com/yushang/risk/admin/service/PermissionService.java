package com.yushang.risk.admin.service;

import com.yushang.risk.admin.domain.vo.request.PermissionReq;
import com.yushang.risk.admin.domain.vo.response.PermissionResp;

import java.util.List;

/**
 * 权限 服务类
 *
 * @author zlp
 * @since 2024-01-26
 */
public interface PermissionService {
  /**
   * 获取权限列表
   *
   * @return
   */
  List<PermissionResp> getPermissionList();
}

package com.yushang.risk.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yushang.risk.domain.entity.SUser;

/**
 * 安全服务用户 服务类
 *
 * @author zlp
 * @since 2024-01-16
 */
public interface SUserService {
  /**
   * 删除漏洞记录
   *
   * @param recordId
   * @return
   */
  boolean deleteBugRecord(Integer recordId);
}

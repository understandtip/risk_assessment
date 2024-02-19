package com.yushang.risk.admin.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yushang.risk.admin.domain.vo.request.LogPageReq;
import com.yushang.risk.domain.entity.SysLoginLog;
import com.yushang.risk.admin.mapper.SysLoginLogMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * 系统登录日志 服务实现类
 *
 * @author zlp
 * @since 2024-02-06
 */
@Service
public class SysLoginLogDao extends ServiceImpl<SysLoginLogMapper, SysLoginLog> {
  /**
   * 获取日志记录
   *
   * @param page
   * @param logPageReq
   * @return
   */
  public Page<SysLoginLog> getLogPageList(Page<SysLoginLog> page, LogPageReq logPageReq) {
    LambdaQueryWrapper<SysLoginLog> wrapper = new LambdaQueryWrapper<>();
    if (logPageReq != null) {
      wrapper.like(
          StringUtils.isNotBlank(logPageReq.getAddress()),
          SysLoginLog::getAddress,
          logPageReq.getAddress());
      wrapper.like(
          StringUtils.isNotBlank(logPageReq.getUsername()),
          SysLoginLog::getUsername,
          logPageReq.getUsername());
      wrapper.eq(
          StringUtils.isNotBlank(logPageReq.getState()),
          SysLoginLog::getState,
          logPageReq.getState());
      wrapper.ge(
          logPageReq.getCreatedTime() != null,
          SysLoginLog::getCreatedTime,
          logPageReq.getCreatedTime());
      wrapper.le(
          logPageReq.getEndTime() != null, SysLoginLog::getCreatedTime, logPageReq.getEndTime());
    }
    wrapper.orderByDesc(SysLoginLog::getCreatedTime);
    return this.page(page, wrapper);
  }
}

package com.yushang.risk.admin.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yushang.risk.admin.dao.AccountDao;
import com.yushang.risk.admin.dao.SysLoginLogDao;
import com.yushang.risk.admin.dao.UsersDao;
import com.yushang.risk.admin.domain.enums.SysLoginLogEnum;
import com.yushang.risk.admin.domain.vo.request.LogPageReq;
import com.yushang.risk.admin.domain.vo.request.PageBaseReq;
import com.yushang.risk.admin.domain.vo.response.PageBaseResp;
import com.yushang.risk.admin.service.SysLoginLogService;
import com.yushang.risk.common.exception.BusinessException;
import com.yushang.risk.common.util.AssertUtils;
import com.yushang.risk.common.util.RedisUtils;
import com.yushang.risk.constant.RedisCommonKey;
import com.yushang.risk.domain.entity.Account;
import com.yushang.risk.domain.entity.SysLoginLog;
import com.yushang.risk.domain.entity.User;
import org.apache.xmlbeans.impl.regex.REUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.Objects;

/**
 * @Author：zlp @Package：com.yushang.risk.admin.service.impl @Project：risk_assessment
 *
 * @name：SysLoginLogServiceImpl @Date：2024/2/6 12:43 @Filename：SysLoginLogServiceImpl
 */
@Service
public class SysLoginLogServiceImpl implements SysLoginLogService {
  @Resource private SysLoginLogDao sysLoginLogDao;
  @Resource private UsersDao usersDao;
  @Resource private AccountDao accountDao;
  /**
   * 获取日志记录
   *
   * @param baseReq
   * @return
   */
  @Override
  public PageBaseResp<SysLoginLog> getLogPageList(PageBaseReq<LogPageReq> baseReq) {
    Page<SysLoginLog> page = baseReq.plusPage();
    Page<SysLoginLog> logPage = sysLoginLogDao.getLogPageList(page, baseReq.getData());

    return PageBaseResp.init(logPage);
  }
}

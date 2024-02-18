package com.yushang.risk.common.aspect;

import com.yushang.risk.admin.dao.AccountDao;
import com.yushang.risk.admin.dao.SysLoginLogDao;
import com.yushang.risk.admin.dao.UsersDao;
import com.yushang.risk.admin.domain.dto.RequestDataInfo;
import com.yushang.risk.admin.service.adapter.AccountAdapter;
import com.yushang.risk.admin.service.adapter.UserAdapter;
import com.yushang.risk.common.annotation.OptLog;
import com.yushang.risk.common.config.ThreadPoolConfig;
import com.yushang.risk.common.util.RequestHolder;
import com.yushang.risk.domain.entity.Account;
import com.yushang.risk.domain.entity.SysLoginLog;
import com.yushang.risk.domain.entity.User;
import com.yushang.risk.utils.RequestUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import static com.yushang.risk.common.annotation.OptLog.Target.LOGIN;

@Aspect
@Component
@Slf4j
public class LogAspect {
  @Resource private AccountDao accountDao;
  @Resource private SysLoginLogDao sysLoginLogDao;

  @Qualifier(ThreadPoolConfig.IP_DETAIL_EXECUTOR)
  @Resource
  private ThreadPoolTaskExecutor threadPoolTaskExecutor;

  @Around("@annotation(optLog)")
  public Object logAspect(ProceedingJoinPoint joinPoint, OptLog optLog) {
    Object proceed = null;
    boolean flag = false;
    try {
      proceed = joinPoint.proceed();
      flag = true;
    } catch (Throwable e) {
      log.error("OptLog日志切面异常", e);
    } finally {
      RequestDataInfo dataInfo = RequestHolder.get();
      Account user = accountDao.getById(dataInfo.getUid());
      boolean finalFlag = flag;
      HttpServletRequest request = RequestUtils.getRequest();
      threadPoolTaskExecutor.execute(
          () -> {
            RequestHolder.set(dataInfo);
            // 异步记录日志
            OptLog.Target target = optLog.target();
            switch (target) {
              case LOGIN:
                SysLoginLog loginLog = AccountAdapter.buildLoginLog(request, user, finalFlag);
                sysLoginLogDao.save(loginLog);
                break;
              case EXIT:
                break;
              default:
                log.error("日志参数异常");
            }
          });
    }
    return proceed;
  }
}

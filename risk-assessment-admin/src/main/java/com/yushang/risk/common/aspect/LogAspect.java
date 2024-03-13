package com.yushang.risk.common.aspect;

import com.yushang.risk.admin.dao.AccountDao;
import com.yushang.risk.admin.dao.OnlineUserDao;
import com.yushang.risk.admin.dao.SysLoginLogDao;
import com.yushang.risk.admin.dao.UsersDao;
import com.yushang.risk.admin.domain.dto.RequestDataDto;
import com.yushang.risk.admin.domain.dto.RequestDataInfo;
import com.yushang.risk.admin.service.adapter.AccountAdapter;
import com.yushang.risk.admin.service.adapter.UserAdapter;
import com.yushang.risk.common.annotation.OptLog;
import com.yushang.risk.common.config.ThreadPoolConfig;
import com.yushang.risk.common.util.IpUtils;
import com.yushang.risk.common.util.RedisUtils;
import com.yushang.risk.common.util.RequestHolder;
import com.yushang.risk.constant.RedisCommonKey;
import com.yushang.risk.domain.entity.Account;
import com.yushang.risk.domain.entity.OnlineUser;
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
import org.springframework.web.context.request.RequestContextListener;

import javax.annotation.Resource;
import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.concurrent.TimeUnit;

@Aspect
@Component
@Slf4j
public class LogAspect {
  @Resource private AccountDao accountDao;
  @Resource private SysLoginLogDao sysLoginLogDao;
  @Resource private OnlineUserDao onlineUserDao;

  @Qualifier(ThreadPoolConfig.IP_DETAIL_EXECUTOR)
  @Resource
  private ThreadPoolTaskExecutor threadPoolTaskExecutor;

  @Around("@annotation(optLog)")
  public Object logAspect(ProceedingJoinPoint joinPoint, OptLog optLog) throws Throwable {
    Object proceed;
    boolean flag = false;
    try {
      proceed = joinPoint.proceed();
      flag = true;
    } finally {
      RequestDataInfo dataInfo = RequestHolder.get();
      HttpServletRequest request = RequestUtils.getRequest();
      RequestDataDto requestDataDto = new RequestDataDto();
      requestDataDto.setHeaders(request);
      requestDataDto.setSession(request.getSession());
      requestDataDto.setIp(IpUtils.getClientIpAddress(request));
      Account user = new Account();
      if (flag) {
        user = accountDao.getById(dataInfo.getUid());
      } else {
        user.setUsername(RequestHolder.get().getUserName());
      }
      boolean finalFlag = flag;
      Account finalUser = user;
      threadPoolTaskExecutor.execute(
          () -> {
            RequestHolder.set(dataInfo);
            // 异步记录日志
            OptLog.Target target = optLog.target();
            switch (target) {
              case LOGIN:
                // 将在线用户信息存
                OnlineUser onlineUser = AccountAdapter.buildOnlineUser(requestDataDto, finalUser);
                // 记录登录日志
                SysLoginLog loginLog =
                    AccountAdapter.buildLoginLog(requestDataDto, finalUser, finalFlag);
                onlineUserDao.removeByUserName(finalUser.getUsername());
                sysLoginLogDao.save(loginLog);
                onlineUserDao.save(onlineUser);
                break;
              case EXIT:
                // 清除在线用户信息
                RequestHolder.remove();
                onlineUserDao.removeByUserName(finalUser.getId());
                break;
              default:
                log.error("日志参数异常");
            }
          });
    }
    return proceed;
  }
}

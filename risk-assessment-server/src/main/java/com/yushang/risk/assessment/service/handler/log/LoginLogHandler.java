package com.yushang.risk.assessment.service.handler.log;

import cn.hutool.core.date.LocalDateTimeUtil;
import com.yushang.risk.assessment.dao.OnlineUserDao;
import com.yushang.risk.assessment.dao.SysLoginLogDao;
import com.yushang.risk.assessment.dao.UserLogDao;
import com.yushang.risk.assessment.dao.UsersDao;
import com.yushang.risk.assessment.domain.dto.RequestDataDto;
import com.yushang.risk.assessment.domain.dto.RequestDataInfo;
import com.yushang.risk.assessment.domain.entity.UserLog;
import com.yushang.risk.assessment.service.adapter.UserAdapter;
import com.yushang.risk.assessment.service.handler.AbstractOptLogHandler;
import com.yushang.risk.common.annotation.OptLog;
import com.yushang.risk.common.config.ThreadPoolConfig;
import com.yushang.risk.common.util.IpUtils;
import com.yushang.risk.common.util.RedisUtils;
import com.yushang.risk.common.util.RequestHolder;
import com.yushang.risk.constant.RedisCommonKey;
import com.yushang.risk.domain.entity.OnlineUser;
import com.yushang.risk.domain.entity.SysLoginLog;
import com.yushang.risk.domain.entity.User;
import com.yushang.risk.utils.RequestUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.concurrent.TimeUnit;

/**
 * @Author：zlp @Package：com.yushang.risk.assessment.service.handler.log @Project：risk_assessment
 *
 * @name：LoginLogHandler @Date：2024/1/8 14:50 @Filename：LoginLogHandler
 */
@Component
public class LoginLogHandler extends AbstractOptLogHandler {
  @Resource private UserLogDao userLogDao;
  @Resource private UsersDao usersDao;
  @Resource private SysLoginLogDao sysLoginLogDao;
  @Resource private OnlineUserDao onlineUserDao;

  @Qualifier(ThreadPoolConfig.IP_DETAIL_EXECUTOR)
  @Resource
  private ThreadPoolTaskExecutor threadPoolTaskExecutor;

  @Override
  public void log(HttpServletRequest request, boolean flag) {
    RequestDataInfo dataInfo = RequestHolder.get();
    Integer uid = dataInfo.getUid();
    RequestDataDto requestDataDto = new RequestDataDto();
    requestDataDto.setHeaders(request);
    requestDataDto.setSession(request.getSession());
    requestDataDto.setIp(IpUtils.getClientIpAddress(request));
    // 不管成功还是失败,都记录
    // 记录到数据库sys_login_log
    threadPoolTaskExecutor.execute(
        () -> {
          RequestHolder.set(dataInfo);
          User user = new User();
          user.setUsername(RequestHolder.get().getUserName());
          SysLoginLog loginLog = UserAdapter.buildLoginLog(requestDataDto, user, flag);
          sysLoginLogDao.save(loginLog);
        });
    // 登录成功才记录
    if (flag) {
      onlineUserDao.removeByUserName(uid);
      // 记录到文件
      StringBuilder str = new StringBuilder();
      str.append(LocalDateTimeUtil.now()).append("----------用户ID：").append(uid).append("登录成功");
      writeToFile(AbstractOptLogHandler.LOGIN_FILE_PATH, str.toString());
      // 记录到数据库user_log
      UserLog userLog = new UserLog();
      userLog.setUserId(uid);
      userLog.setLogType(this.getCode());
      // 处理登录信息存到redis--在线用户
      User user = new User();
      user.setUsername(RequestHolder.get().getUserName());
      OnlineUser onlineUser = UserAdapter.buildOnlineUser(requestDataDto, user);
      userLogDao.save(userLog);
      onlineUserDao.save(onlineUser);
    }
  }

  @Override
  protected Integer getCode() {
    return OptLog.Target.LOGIN.getCode();
  }
}

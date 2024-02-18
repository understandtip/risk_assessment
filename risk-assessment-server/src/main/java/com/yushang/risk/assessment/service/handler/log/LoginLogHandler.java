package com.yushang.risk.assessment.service.handler.log;

import cn.hutool.core.date.LocalDateTimeUtil;
import com.yushang.risk.assessment.dao.SysLoginLogDao;
import com.yushang.risk.assessment.dao.UserLogDao;
import com.yushang.risk.assessment.dao.UsersDao;
import com.yushang.risk.assessment.domain.entity.UserLog;
import com.yushang.risk.assessment.service.adapter.UserAdapter;
import com.yushang.risk.assessment.service.handler.AbstractOptLogHandler;
import com.yushang.risk.common.annotation.OptLog;
import com.yushang.risk.common.config.ThreadPoolConfig;
import com.yushang.risk.common.util.RequestHolder;
import com.yushang.risk.domain.entity.SysLoginLog;
import com.yushang.risk.domain.entity.User;
import org.apache.commons.lang3.function.FailableIntBinaryOperator;
import org.checkerframework.checker.guieffect.qual.UI;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.rmi.server.UID;

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

  @Qualifier(ThreadPoolConfig.IP_DETAIL_EXECUTOR)
  @Resource
  private ThreadPoolTaskExecutor threadPoolTaskExecutor;

  @Override
  public void log(boolean flag) {
    Integer uid = RequestHolder.get().getUid();
    // 登录成功才记录
    if (flag) {
      // 记录到文件
      StringBuilder str = new StringBuilder();
      str.append(LocalDateTimeUtil.now()).append("----------用户ID：").append(uid).append("登录成功");
      writeToFile(AbstractOptLogHandler.LOGIN_FILE_PATH, str.toString());
      // 记录到数据库user_log
      UserLog userLog = new UserLog();
      userLog.setUserId(uid);
      userLog.setLogType(this.getCode());
      userLogDao.save(userLog);
    }
    // 不管成功还是失败,都记录
    // 记录到数据库sys_login_log
    threadPoolTaskExecutor.execute(
        () -> {
          User user = usersDao.getById(uid);
          SysLoginLog loginLog = UserAdapter.buildLoginLog(user, flag);
          sysLoginLogDao.save(loginLog);
        });
  }

  @Override
  protected Integer getCode() {
    return OptLog.Target.LOGIN.getCode();
  }
}

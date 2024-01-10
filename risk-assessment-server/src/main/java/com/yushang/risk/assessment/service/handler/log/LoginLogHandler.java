package com.yushang.risk.assessment.service.handler.log;

import cn.hutool.core.date.LocalDateTimeUtil;
import com.yushang.risk.assessment.dao.UserLogDao;
import com.yushang.risk.assessment.domain.entity.UserLog;
import com.yushang.risk.assessment.service.handler.AbstractOptLogHandler;
import com.yushang.risk.common.annotation.OptLog;
import com.yushang.risk.common.util.RequestHolder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author：zlp @Package：com.yushang.risk.assessment.service.handler.log @Project：risk_assessment
 *
 * @name：LoginLogHandler @Date：2024/1/8 14:50 @Filename：LoginLogHandler
 */
@Component
public class LoginLogHandler extends AbstractOptLogHandler {
  @Resource private UserLogDao userLogDao;

  @Override
  public void log() {
    Integer uid = RequestHolder.get().getUid();
    // 记录到文件
    StringBuilder str = new StringBuilder();
    str.append(LocalDateTimeUtil.now()).append("----------用户ID：").append(uid).append("登录成功");
    writeToFile(AbstractOptLogHandler.LOGIN_FILE_PATH, str.toString());
    // 记录到数据库
    UserLog userLog = new UserLog();
    userLog.setUserId(uid);
    userLog.setLogType(this.getCode());
    userLogDao.save(userLog);
  }

  @Override
  protected Integer getCode() {
    return OptLog.Target.LOGIN.getCode();
  }
}

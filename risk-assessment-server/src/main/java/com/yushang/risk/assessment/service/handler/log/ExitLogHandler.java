package com.yushang.risk.assessment.service.handler.log;

import cn.hutool.core.date.LocalDateTimeUtil;
import com.yushang.risk.assessment.dao.OnlineUserDao;
import com.yushang.risk.assessment.dao.UserLogDao;
import com.yushang.risk.assessment.domain.entity.UserLog;
import com.yushang.risk.assessment.service.handler.AbstractOptLogHandler;
import com.yushang.risk.common.annotation.OptLog;
import com.yushang.risk.common.util.RedisUtils;
import com.yushang.risk.common.util.RequestHolder;
import com.yushang.risk.constant.RedisCommonKey;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @Author：zlp @Package：com.yushang.risk.assessment.service.handler.log @Project：risk_assessment
 *
 * @name：ExitLogHandler @Date：2024/1/8 14:51 @Filename：ExitLogHandler
 */
@Component
public class ExitLogHandler extends AbstractOptLogHandler {
  @Resource private UserLogDao userLogDao;
  @Resource private OnlineUserDao onlineUserDao;

  @Override
  public void log(HttpServletRequest request, boolean flag) {
    if (flag) {
      // 退出成功
      Integer uid = RequestHolder.get().getUid();
      // 记录到文件
      StringBuilder str = new StringBuilder();
      str.append(LocalDateTimeUtil.now()).append("----------用户ID：").append(uid).append("退出系统");
      writeToFile(AbstractOptLogHandler.EXIT_FILE_PATH, str.toString());
      // 记录到数据库
      UserLog userLog = new UserLog();
      userLog.setUserId(uid);
      userLog.setLogType(this.getCode());
      userLogDao.save(userLog);
      // 清除在线用户信息
      onlineUserDao.removeByUserName(uid);
    }
  }

  @Override
  protected Integer getCode() {
    return OptLog.Target.EXIT.getCode();
  }
}

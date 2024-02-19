package com.yushang.risk.common.task;

import com.yushang.risk.admin.dao.OnlineUserDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author：zlp @Package：com.yushang.risk.common.task @Project：risk_assessment
 *
 * @name：OnlineUserRemoveTask @Date：2024/2/19 15:37 @Filename：OnlineUserRemoveTask
 */
@Component
@Slf4j
public class OnlineUserRemoveTask {
  @Resource private OnlineUserDao onlineUserDao;

  @Scheduled(cron = "0 */3 * * *")
  public void removeOnlineUser() {
    log.info("清除在线用户记录定时任务执行");
    onlineUserDao.removeOnlineUser();
  }
}

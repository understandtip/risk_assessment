package com.yushang.risk.common.thread;

import lombok.extern.slf4j.Slf4j;

/**
 * @Author：zlp @Package：com.abin.mallchat.common.common.thread @Project：mallchat-zlp
 *
 * @name：MyCaughtExceptionHandler @Date：2023/10/31 14:11 @Filename：MyCaughtExceptionHandler
 */
@Slf4j
public class MyCaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
  @Override
  public void uncaughtException(Thread t, Throwable e) {
    // 子线程的异常处理
    log.error("子线程报错了", e);
  }
}

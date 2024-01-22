package com.yushang.risk.common.thread;

import lombok.AllArgsConstructor;

import java.util.concurrent.ThreadFactory;

/**
 * @author zlp
 */
@AllArgsConstructor
public class MyThreadFactory implements ThreadFactory {
  /** 自定义子线程异常捕获器 */
  private static final MyCaughtExceptionHandler MY_CAUGHT_EXCEPTION_HANDLER =
      new MyCaughtExceptionHandler();

  private ThreadFactory originThreadFactory;

  @Override
  public Thread newThread(Runnable r) {
    Thread thread = originThreadFactory.newThread(r);
    thread.setUncaughtExceptionHandler(MY_CAUGHT_EXCEPTION_HANDLER);
    return thread;
  }
}

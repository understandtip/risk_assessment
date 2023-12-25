package com.yushang.risk.common.util;

import com.yushang.risk.assessment.domain.dto.RequestDataInfo;

/**
 * @Author：zlp @Package：com.yushang.risk.assessment.domain.dto @Project：risk_assessment
 *
 * @name：RequestHolder @Date：2023/12/25 10:58 @Filename：RequestHolder
 */
public class RequestHolder {
  private static final ThreadLocal<RequestDataInfo> THREAD_LOCAL = new ThreadLocal<>();

  public static void set(RequestDataInfo dataInfo) {
    THREAD_LOCAL.set(dataInfo);
  }

  public static RequestDataInfo get() {
    return THREAD_LOCAL.get();
  }

  public static void remove(){
      THREAD_LOCAL.remove();
  }
}

package com.yushang.risk.assessment.service.handler;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author：zlp @Package：com.yushang.risk.assessment.service.handler.log @Project：risk_assessment
 *
 * @name：OptLogHandlerFactory @Date：2024/1/8 15:16 @Filename：OptLogHandlerFactory
 */
public class OptLogHandlerFactory {
  private static final Map<Integer, AbstractOptLogHandler> OPT_LOG_SERVICE_MAP = new HashMap<>();

  public static void of(Integer code, AbstractOptLogHandler handler) {
    OPT_LOG_SERVICE_MAP.put(code, handler);
  }

  public static AbstractOptLogHandler get(Integer code) {
    return OPT_LOG_SERVICE_MAP.get(code);
  }
}

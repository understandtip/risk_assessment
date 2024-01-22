package com.yushang.risk.common.event;

import com.yushang.risk.common.event.domaih.dto.SUserDto;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

import java.time.Clock;

/**
 * @Author：zlp @Package：com.yushang.risk.common.event @Project：risk_assessment
 *
 * @name：GenerateBugReportEvent @Date：2024/1/18 15:42 @Filename：GenerateBugReportEvent
 */
@Getter
public class GenerateBugReportEvent extends ApplicationEvent {
  private final SUserDto sUserDto;

  public GenerateBugReportEvent(Object source, SUserDto sUserDto) {
    super(source);
    this.sUserDto = sUserDto;
  }
}

package com.yushang.risk.common.event;

import com.yushang.risk.common.event.domaih.dto.GeneratePortDto;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * @Author：zlp @Package：com.yushang.risk.common.event @Project：risk_assessment
 *
 * @name：GeneratePortEvent @Date：2024/1/22 15:21 @Filename：GeneratePortEvent
 */
@Getter
public class GeneratePortEvent extends ApplicationEvent {
  private final GeneratePortDto generatePortDto;

  public GeneratePortEvent(Object source, GeneratePortDto generatePortDto) {
    super(source);
    this.generatePortDto = generatePortDto;
  }
}

package com.yushang.risk.common.event;

import com.yushang.risk.common.event.domaih.dto.FileDownDto;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * @Author：zlp @Package：com.yushang.risk.common.event @Project：risk_assessment
 *
 * @name：FileDownEvent @Date：2024/1/12 10:06 @Filename：FileDownEvent
 */
@Getter
public class FileDownEvent extends ApplicationEvent {
  private final FileDownDto fileDownDto;

  public FileDownEvent(Object source, FileDownDto fileDownDto) {
    super(source);
    this.fileDownDto = fileDownDto;
  }
}

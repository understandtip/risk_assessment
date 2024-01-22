package com.yushang.risk.common.event.listener;

import com.yushang.risk.common.event.FileDownEvent;
import com.yushang.risk.common.event.domaih.dto.FileDownDto;
import com.yushang.risk.common.util.RedisUtils;
import com.yushang.risk.constant.NormalCommonConstant;
import com.yushang.risk.constant.RedisCommonKey;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @Author：zlp @Package：com.yushang.risk.common.event.listener @Project：risk_assessment
 *
 * @name：FileDownListener @Date：2024/1/12 10:11 @Filename：FileDownListener
 */
@Component
public class FileDownListener {

  @Async
  @EventListener(classes = FileDownEvent.class)
  public void fileDownListener(FileDownEvent event) {
    FileDownDto downDto = event.getFileDownDto();
    if (downDto.getFileName().contains(NormalCommonConstant.MINIO_PORT)) {
      RedisUtils.inc(RedisCommonKey.USER_DOWNLOAD_FILE_DAY_KEY, 24, TimeUnit.HOURS);
    }
  }
}

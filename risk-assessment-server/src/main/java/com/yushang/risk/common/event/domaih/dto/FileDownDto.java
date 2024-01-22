package com.yushang.risk.common.event.domaih.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * @Author：zlp @Package：com.yushang.risk.common.event.domaih.dto @Project：risk_assessment
 *
 * @name：FileDownDto @Date：2024/1/12 10:07 @Filename：FileDownDto
 */
@Data
@Builder
@AllArgsConstructor
public class FileDownDto {
  private String fileName;
}

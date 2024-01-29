package com.yushang.risk.common.event.domaih.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

/**
 * @Author：zlp @Package：com.yushang.risk.common.event.domaih.dto @Project：risk_assessment
 *
 * @name：GeneratePortDto @Date：2024/1/22 15:19 @Filename：GeneratePortDto
 */
@Data
@Builder
public class GeneratePortDto {
  private byte[] bytes;
  private Integer projectId;
}

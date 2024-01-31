package com.yushang.risk.admin.domain.vo.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author：zlp @Package：com.yushang.risk.admin.domain.vo.request @Project：risk_assessment
 *
 * @name：AccountPageReq @Date：2024/1/30 15:05 @Filename：AccountPageReq
 */
@Data
public class AccountPageReq {
  private String userName;
  private String phone;
  private Integer roleId;
  /**
   * @see com.yushang.risk.domain.enums.UserStatusEnum
   */
  private Integer state;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime startTime;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime endTime;
}

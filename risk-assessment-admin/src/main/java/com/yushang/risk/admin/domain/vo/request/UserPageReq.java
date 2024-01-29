package com.yushang.risk.admin.domain.vo.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * @Author：zlp @Package：com.yushang.risk.admin.domain.vo.request @Project：risk_assessment
 *
 * @name：UserReq @Date：2024/1/23 14:19 @Filename：UserReq
 */
@Data
public class UserPageReq {
  private String userName;
  private String phone;
  /**
   * @see com.yushang.risk.domain.enums.UserStatusEnum
   */
  private Integer status;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime startTime;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime endTime;
}

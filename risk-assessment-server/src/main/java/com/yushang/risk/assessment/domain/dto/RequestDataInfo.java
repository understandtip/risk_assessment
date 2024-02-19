package com.yushang.risk.assessment.domain.dto;

import lombok.Data;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author：zlp @Package：com.yushang.risk.assessment.domain.dto @Project：risk_assessment
 *
 * @name：RequestDataInfo @Date：2023/12/25 10:59 @Filename：RequestDataInfo
 */
@Data
public class RequestDataInfo {
  private Integer uid;
  private String ip;
  private String userName;
}

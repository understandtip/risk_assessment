package com.yushang.risk.common.event.domaih.dto;

import com.yushang.risk.assessment.domain.vo.request.SecurityServiceBugBugReq;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @Author：zlp @Package：com.yushang.risk.common.event.domaih.dto @Project：risk_assessment
 *
 * @name：SUserDto @Date：2024/1/18 15:39 @Filename：SUserDto
 */
@Data
@Builder
@AllArgsConstructor
public class SUserDto {
  /** 主键 */
  private Integer userId;

  private Integer serviceId;

  @ApiModelProperty("提交的漏洞id集合")
  private List<Integer> bugIds;

  private List<SecurityServiceBugBugReq.AddBug> addBugs;
}

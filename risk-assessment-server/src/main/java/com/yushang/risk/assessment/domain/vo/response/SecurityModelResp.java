package com.yushang.risk.assessment.domain.vo.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author：zlp @Package：com.yushang.risk.assessment.domain.vo.response @Project：risk_assessment
 *
 * @name：SecurityModelResp @Date：2024/1/15 14:30 @Filename：SecurityModelResp
 */
@Data
public class SecurityModelResp {
  private Integer id;
  private String name;
  private String img;
}

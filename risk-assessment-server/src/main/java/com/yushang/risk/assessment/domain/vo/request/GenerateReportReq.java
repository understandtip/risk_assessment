package com.yushang.risk.assessment.domain.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Author：zlp @Package：com.yushang.risk.assessment.domain.vo.request @Project：risk_assessment
 *
 * @name：GenerageReportReq @Date：2023/12/20 11:23 @Filename：GenerageReportReq
 */
@Data
@ApiModel("生成报告前端传递对象")
@NoArgsConstructor
@AllArgsConstructor
public class GenerateReportReq {
  @ApiModelProperty(notes = "项目id,没有项目,就不传")
  private Integer projectId;

  @ApiModelProperty(notes = "风险集合")
  @NotNull
  private List<riskReq> riskList;

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class riskReq {
    @ApiModelProperty(notes = "风险id")
    private String riskId;

    @ApiModelProperty(notes = "风险得分id")
    private Integer riskGradeId;
  }
}

package com.yushang.risk.assessment.domain.vo.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author：zlp @Package：com.yushang.risk.assessment.domain.vo.response @Project：risk_assessment
 *
 * @name：BsAllRiskInfoResp @Date：2024/3/12 14:37 @Filename：BsAllRiskInfoResp
 */
@Data
public class BsAllRiskInfoResp {
  /** 主键id */
  @ApiModelProperty(value = "主键id", notes = "")
  private Integer id;
  /** 标题 */
  @ApiModelProperty(value = "标题", notes = "")
  private String title;
  /** 定义 */
  @ApiModelProperty(value = "定义", notes = "")
  private String definition;
  /** 描述 */
  @ApiModelProperty(value = "描述", notes = "")
  private String description;
  /** 影响 */
  @ApiModelProperty(value = "影响", notes = "")
  private String affect;
  /** 参考 */
  @ApiModelProperty(value = "参考", notes = "")
  private String reference;

  private String complexity;

  private List<BsAllRiskInfoResp.AvoidInfo> avoidInfoList;

  @Data
  public static class AvoidInfo {
    private String name;
    private Integer id;
  }

}

package com.yushang.risk.assessment.domain.vo.response;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author：zlp @Package：com.yushang.risk.assessment.domain.vo.response @Project：risk_assessment
 *
 * @name：BsRiskInfoResp @Date：2024/3/11 10:54 @Filename：BsRiskInfoResp
 */
@Data
public class BsRiskInfoResp {
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
  /** 关系图 */
  @ApiModelProperty(value = "关系图", notes = "")
  private String relationSchema;

  private List<AvoidInfo> avoidInfoList;
  private List<AttackInfo> attackInfoList;

  @Data
  public static class AvoidInfo {
    private String name;
    private Integer id;
  }

  @Data
  public static class AttackInfo {
    private String name;
    private Integer id;
  }
}

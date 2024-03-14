package com.yushang.risk.assessment.domain.vo.response;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author：zlp @Package：com.yushang.risk.assessment.domain.vo.response @Project：risk_assessment
 *
 * @name：BsAllAttackToolInfoResp @Date：2024/3/14 10:43 @Filename：BsAllAttackToolInfoResp
 */
@Data
public class BsAllAttackToolInfoResp {
  /** 主键id */
  @ApiModelProperty(value = "主键id", notes = "")
  private Integer id;
  /** 标题 */
  @ApiModelProperty(value = "标题", notes = "")
  private String title;
  /** 描述 */
  @ApiModelProperty(value = "描述", notes = "")
  private String description;
  /** 参考 */
  @ApiModelProperty(value = "参考", notes = "")
  private String reference;

  private List<BsAllAttackToolInfoResp> childAttackToolList;
  private List<ToolRiskInfoResp> riskList;
  private List<ToolAvoidInfoResp> avoidList;


  @Data
  public static class ToolRiskInfoResp {
    private Integer id;
    private String title;
  }

  @Data
  public static class ToolAvoidInfoResp {
    private Integer id;
    private String title;
  }
}

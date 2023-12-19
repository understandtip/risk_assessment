package com.yushang.risk.assessment.domain.vo.response;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author：zlp @Package：com.yushang.risk.assessment.domain.vo.response @Project：risk_assessment
 *
 * @name：RiskResp @Date：2023/12/19 10:12 @Filename：RiskResp
 */
@Data
@ApiModel(value = "风险返回对象", description = "")
public class RiskResp {
  /** id */
  @ApiModelProperty(notes = "风险id")
  private String riskId;
  /** 风险标题 */
  @ApiModelProperty(notes = "风险标题")
  private String title;
  /** 风险描述 */
  @ApiModelProperty(notes = "风险描述")
  @TableField("`desc`")
  private String desc;
  /** 是否增强;1:增强 0:不增强 */
  @ApiModelProperty(notes = "是否增强1:增强   0:不增强")
  private String isEnhance;
  /** 类别id */
  @ApiModelProperty(notes = "类别id")
  private Integer categoryId;
  /** 属性id */
  @ApiModelProperty(notes = "属性id")
  private Integer attrId;
  /** 周期id */
  @ApiModelProperty(notes = "周期id")
  private Integer cycleId;
  /** 额外信息 */
  @ApiModelProperty(notes = "额外信息")
  private String extraInfo;
}

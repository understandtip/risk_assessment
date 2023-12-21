package com.yushang.risk.assessment.domain.vo.response;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zlp
 */
@Data
@ApiModel(value = "风险得分返回对象", description = "")
public class GradeVo {
  /** 主键id */
  @ApiModelProperty(notes = "风险评分主键id")
  @TableId
  private Integer id;
  /** 等级名称 */
  @ApiModelProperty(notes = "等级名称")
  private String name;
  /** 得分 */
  @ApiModelProperty(notes = "得分")
  private Integer score;
}

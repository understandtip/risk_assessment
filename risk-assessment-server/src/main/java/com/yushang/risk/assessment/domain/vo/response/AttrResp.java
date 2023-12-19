package com.yushang.risk.assessment.domain.vo.response;

import com.baomidou.mybatisplus.annotation.TableId;
import com.yushang.risk.assessment.domain.entity.AttrMeans;
import com.yushang.risk.assessment.domain.entity.AttrMenace;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author：zlp @Package：com.yushang.risk.assessment.domain.vo.response @Project：risk_assessment
 *
 * @name：AttrResp @Date：2023/12/19 9:08 @Filename：AttrResp
 */
@Data
@ApiModel(value = "属性返回对象")
public class AttrResp {
  /** id */
  @ApiModelProperty(notes = "id")
  @TableId
  private Integer id;
  /** 属性名称 */
  @ApiModelProperty(notes = "属性名称")
  private String name;
  /** 英文名称 */
  @ApiModelProperty(notes = "英文名称")
  private String engName;
  /** 威胁 */
  @ApiModelProperty(notes = "威胁")
  private AttrMenace menace;
  /** 防护手段 */
  @ApiModelProperty(notes = "防护手段")
  private List<AttrMeans> meansProtectionList;
}

package com.yushang.risk.assessment.domain.vo.response;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author：zlp @Package：com.yushang.risk.assessment.domain.vo.response @Project：risk_assessment
 *
 * @name：BsAvoidInfoResp @Date：2024/3/11 15:37 @Filename：BsAvoidInfoResp
 */
@Data
public class BsAvoidInfoResp {
  /** 主键id */
  @ApiModelProperty(value = "主键id", notes = "")
  private Integer id;
  /** 标题 */
  @ApiModelProperty(value = "标题", notes = "")
  private String title;
  /** 信息 */
  @ApiModelProperty(value = "信息", notes = "")
  private String info;
  /** 描述 */
  @ApiModelProperty(value = "描述", notes = "")
  private String description;
  /** 局限性 */
  @ApiModelProperty(value = "局限性", notes = "")
  private String boundedness;
  /** 参考 */
  @ApiModelProperty(value = "参考", notes = "")
  private String reference;
}

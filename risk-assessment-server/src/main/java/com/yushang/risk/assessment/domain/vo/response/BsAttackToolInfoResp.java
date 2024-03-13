package com.yushang.risk.assessment.domain.vo.response;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author：zlp @Package：com.yushang.risk.assessment.domain.vo.response @Project：risk_assessment
 *
 * @name：BsAttackToolInfoResp @Date：2024/3/11 15:44 @Filename：BsAttackToolInfoResp
 */
@Data
public class BsAttackToolInfoResp {
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

  private List<AvoidInfo> avoidInfoList;

  @Data
  public static class AvoidInfo {
    /** 名称 */
    @ApiModelProperty(value = "名称", notes = "")
    private String name;
    /** id */
    @ApiModelProperty(value = "id", notes = "")
    private Integer id;
  }
}

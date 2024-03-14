package com.yushang.risk.assessment.domain.vo.response;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author：zlp @Package：com.yushang.risk.assessment.domain.vo.response @Project：risk_assessment
 *
 * @name：BsAllAvoidInfoResp @Date：2024/3/13 10:27 @Filename：BsAllAvoidInfoResp
 */
@Data
public class BsAllAvoidInfoResp {
  /** 主键id */
  private Integer id;

  /** 名称 */
  @TableField("name")
  private String name;

  private String eng;

  private String description;

  private List<AvoidInfoResp> avoidInfoList;

  @Data
  public static class AvoidInfoResp {
    /** 主键id */
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

    private List<AvoidInfoResp> childAvoidList;
  }
}

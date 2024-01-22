package com.yushang.risk.assessment.domain.vo.response;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author：zlp @Package：com.yushang.risk.assessment.domain.vo.response @Project：risk_assessment
 *
 * @name：SecurityModelDetailResp @Date：2024/1/15 14:38 @Filename：SecurityModelDetailResp
 */
@Data
public class SecurityModelDetailResp {
  /** 主键 */
  @ApiModelProperty(notes = "主键")
  private Integer id;
  /** 安全服务名称 */
  @ApiModelProperty(notes = "安全服务名称")
  private String name;
  /** 图片 */
  @ApiModelProperty(notes = "图片")
  private String img;
  /** 排序 */
  @ApiModelProperty(notes = "排序")
  private Integer sort;
  /** 介绍 */
  @ApiModelProperty(notes = "介绍")
  private String info;
  /** 服务简介 */
  @ApiModelProperty(notes = "服务简介")
  private String intro;
  /** 价格 */
  @ApiModelProperty(notes = "价格")
  private String price;
  /** 联系电话 */
  @ApiModelProperty(notes = "联系电话")
  private String phone;
  /** 售后范围 */
  @ApiModelProperty(notes = "售后范围")
  private String scope;
  /** 交付时间 */
  @ApiModelProperty(notes = "交付时间")
  private String leadTime;

  @ApiModelProperty("漏洞展示")
  private List<BugInfo> bugInfoList;

  /** 报价说明 */
  @ApiModelProperty(notes = "报价说明")
  private String tenderClarification;
  /** 服务流程(image) */
  @ApiModelProperty(notes = "服务流程(image)")
  private String serviceProcess;

  @Data
  public static class BugInfo {

    /** 主键 */
    @ApiModelProperty(notes = "主键")
    private Integer id;
    /** 漏洞名称 */
    @ApiModelProperty(notes = "漏洞名称")
    private String name;
    /** 漏洞原理 */
    @ApiModelProperty(notes = "漏洞原理")
    private String theory;
    /** 危害 */
    @ApiModelProperty(notes = "危害")
    private String harm;
  }
}

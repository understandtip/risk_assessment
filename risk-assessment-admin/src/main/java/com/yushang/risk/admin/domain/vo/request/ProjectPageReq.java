package com.yushang.risk.admin.domain.vo.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author：zlp @Package：com.yushang.risk.admin.domain.vo.request @Project：risk_assessment
 *
 * @name：ProjectPageReq @Date：2024/2/2 10:00 @Filename：ProjectPageReq
 */
@Data
public class ProjectPageReq {
  /** 项目名称 */
  @ApiModelProperty(notes = "项目名称")
  private String name;
  /** 单位名称 */
  @ApiModelProperty(notes = "单位名称")
  private String companyName;
  /** 密级 */
  @ApiModelProperty(notes = "密级")
  private String classification;
  /** 版本编号 */
  @ApiModelProperty(notes = "版本编号")
  private Double version;
  /** 制作人姓名 */
  @ApiModelProperty(notes = "制作人姓名")
  private String authorName;

  /** 涉及系统 */
  @ApiModelProperty(notes = "涉及系统")
  private String referenceSystem;
  /** 测试公司 */
  @ApiModelProperty(notes = "测试公司")
  private String testingCompany;
  /** 创建时间 */
  @ApiModelProperty(notes = "创建时间")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime createdTime;
  /** 更新时间 */
  @ApiModelProperty(notes = "结束时间")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime endTime;
}

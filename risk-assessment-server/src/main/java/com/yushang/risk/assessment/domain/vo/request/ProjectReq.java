package com.yushang.risk.assessment.domain.vo.request;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @Author：zlp @Package：com.yushang.risk.assessment.domain.vo.request @Project：risk_assessment
 *
 * @name：ProjectReq @Date：2023/12/29 10:32 @Filename：ProjectReq
 */
@Data
public class ProjectReq {
  /** 项目名称 */
  @ApiModelProperty(notes = "项目id")
  private Integer id;
  /** 项目名称 */
  @ApiModelProperty(notes = "项目名称")
  @NotNull
  private String name;
  /** 单位名称 */
  @NotNull
  @ApiModelProperty(notes = "单位名称")
  private String companyName;
  /** 密级 */
  @NotNull
  @ApiModelProperty(notes = "密级")
  private String classification;
  /** 版本编号 */
  @NotNull
  @ApiModelProperty(notes = "版本编号")
  private Double version;
  /** logo图片地址 */
  @NotNull
  @ApiModelProperty(notes = "logo图片地址")
  private String logo;
  /** 说明信息 */
  @ApiModelProperty(notes = "说明信息")
  private String explain;
  /** 涉及系统 */
  @NotNull
  @ApiModelProperty(notes = "涉及系统")
  private String referenceSystem;
  /** 测试公司 */
  @NotNull
  @ApiModelProperty(notes = "测试公司")
  private String testingCompany;
}

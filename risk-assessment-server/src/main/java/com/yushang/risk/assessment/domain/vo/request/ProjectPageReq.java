package com.yushang.risk.assessment.domain.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @Author：zlp @Package：com.yushang.risk.assessment.domain.vo.request @Project：risk_assessment
 *
 * @name：ProjectPageReq @Date：2024/1/3 10:41 @Filename：ProjectPageReq
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
  /** 制作人姓名 */
  @ApiModelProperty(notes = "制作人姓名")
  private String authorName;
  /** 测试公司 */
  @ApiModelProperty(notes = "测试公司")
  private String testingCompany;
  /** 创建时间 */
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @ApiModelProperty(notes = "创建时间")
  private LocalDateTime createdTime;

  @NotNull private Integer pageNum;

  @NotNull
  @Max(50)
  private Integer pageSize;
}

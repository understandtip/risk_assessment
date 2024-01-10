package com.yushang.risk.assessment.domain.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @Author：zlp @Package：com.yushang.risk.assessment.domain.vo.request @Project：risk_assessment
 *
 * @name：RecordReq @Date：2024/1/4 9:19 @Filename：RecordReq
 */
@Data
public class RecordPageReq {
  @NotNull private Integer pageNum;

  @NotNull
  @Max(50)
  private Integer pageSize;
  /** 项目名称 */
  @ApiModelProperty(notes = "项目id")
  private Integer projectId;
  /** 项目名称 */
  @ApiModelProperty(notes = "项目名称")
  private String projectName;
  /** 报告名称 */
  @ApiModelProperty(notes = "报告名称")
  private String name;
  /** 制作人姓名 */
  @ApiModelProperty(notes = "制作人姓名")
  private String author;

  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @ApiModelProperty(notes = "开始时间")
  private LocalDateTime startTime;
  /** 创建时间 */
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @ApiModelProperty(notes = "结束时间")
  private LocalDateTime endTime;
}

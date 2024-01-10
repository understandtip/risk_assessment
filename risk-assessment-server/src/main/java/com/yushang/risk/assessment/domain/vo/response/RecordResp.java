package com.yushang.risk.assessment.domain.vo.response;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author：zlp @Package：com.yushang.risk.assessment.domain.vo.response @Project：risk_assessment
 *
 * @name：RecordResp @Date：2024/1/3 14:15 @Filename：RecordResp
 */
@Data
public class RecordResp {
  /** 主键id */
  @ApiModelProperty(notes = "主键id")
  private Integer id;
  /** 项目名称 */
  @ApiModelProperty(notes = "项目名称")
  private String projectName;
  /** 报告名称 */
  @ApiModelProperty(notes = "报告名称")
  private String name;
  /** 制作人姓名 */
  @ApiModelProperty(notes = "制作人姓名")
  private String author;
  /** 测评报告名称 */
  @ApiModelProperty(notes = "测评报告名称")
  private String fileName;
  /** 创建时间 */
  @ApiModelProperty(notes = "创建时间")
  private LocalDateTime createdTime;
}

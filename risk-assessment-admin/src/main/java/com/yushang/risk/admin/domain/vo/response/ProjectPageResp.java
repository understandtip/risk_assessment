package com.yushang.risk.admin.domain.vo.response;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.checkerframework.checker.tainting.qual.MethodTaintingParam;

import java.time.LocalDateTime;

/**
 * @Author：zlp @Package：com.yushang.risk.admin.domain.vo.response @Project：risk_assessment
 *
 * @name：ProjectPageResp @Date：2024/2/2 10:02 @Filename：ProjectPageResp
 */
@Data
public class ProjectPageResp {
  /** 主键id */
  @ApiModelProperty(notes = "主键id")
  private Integer id;
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
  /** logo图片地址 */
  @ApiModelProperty(notes = "logo图片地址")
  private String logo;
  /** 说明信息 */
  @ApiModelProperty(notes = "说明信息")
  @TableField("`explain`")
  private String explain;
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
  private LocalDateTime createdTime;
  /** 更新时间 */
  @ApiModelProperty(notes = "更新时间")
  private LocalDateTime updatedTime;
}

package com.yushang.risk.assessment.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import io.github.classgraph.json.Id;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 周期标记
 *
 * @author zlp
 * @since 2023-12-19
 */
@Data
@ApiModel(value = "周期标记", description = "")
@TableName("cycle_mark")
public class CycleMark implements Serializable, Cloneable {
  /** 主键id */
  @TableId
  @ApiModelProperty(notes = "主键id")
  private Integer id;
  /** 标志名称 */
  @ApiModelProperty(notes = "标志名称")
  private String name;
  /** 周期id */
  @ApiModelProperty(notes = "周期id")
  private Integer cycleId;
  /** 创建时间 */
  @ApiModelProperty(notes = "创建时间")
  private LocalDateTime createdTime;
  /** 更新时间 */
  @ApiModelProperty(notes = "更新时间")
  private LocalDateTime updatedTime;
}

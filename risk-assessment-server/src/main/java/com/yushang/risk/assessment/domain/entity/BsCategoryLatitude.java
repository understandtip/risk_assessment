package com.yushang.risk.assessment.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 分类纬度表关系
 *
 * @author zlp
 * @since 2024-03-05
 */
@Data
@ApiModel(value = "分类纬度表关系", description = "")
@TableName("bs_category_latitude")
public class BsCategoryLatitude implements Serializable, Cloneable {
  /** 主键id */
  @ApiModelProperty(value = "主键id", notes = "")
  @TableId
  private Integer id;
  /** 分类id */
  @ApiModelProperty(value = "分类id", notes = "")
  private Integer categoryId;
  /** 纬度id */
  @ApiModelProperty(value = "纬度id", notes = "")
  private Integer latitudeId;
  /** 逻辑删除;1:删除 0:未删除 */
  @ApiModelProperty(value = "逻辑删除", notes = "1:删除   0:未删除")
  private String isDeleted;

  /** 创建时间 */
  @TableField("created_time")
  private LocalDateTime createdTime;

  /** 更新时间 */
  @TableField("updated_time")
  private LocalDateTime updatedTime;
}

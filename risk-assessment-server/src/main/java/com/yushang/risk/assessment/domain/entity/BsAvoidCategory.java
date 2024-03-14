package com.yushang.risk.assessment.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 规避分类
 *
 * @author zlp
 * @since 2024-03-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("bs_avoid_category")
public class BsAvoidCategory implements Serializable {

  private static final long serialVersionUID = 1L;

  /** 主键id */
  @TableId(value = "id", type = IdType.AUTO)
  private Integer id;

  /** 名称 */
  @TableField("name")
  private String name;

  /** 逻辑删除;1:删除 0:未删除 */
  @TableField("is_deleted")
  private String isDeleted;

  private String eng;

  private String description;

  /** 创建时间 */
  @TableField("created_time")
  private LocalDateTime createdTime;

  /** 更新时间 */
  @TableField("updated_time")
  private LocalDateTime updatedTime;
}

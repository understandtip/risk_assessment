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
 * 威胁行为
 *
 * @author zlp
 * @since 2024-03-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("bs_threat_action")
public class BsThreatAction implements Serializable {

  private static final long serialVersionUID = 1L;

  /** 主键id */
  @TableId(value = "id", type = IdType.AUTO)
  private Integer id;

  /** 标题 */
  @TableField("title")
  private String title;

  /** 描述 */
  @TableField("description")
  private String description;

  /** 参考 */
  @TableField("refer")
  private String refer;

  /** pid */
  @TableField("pid")
  private Integer pid;

  /** 逻辑删除;1:删除 0:未删除 */
  @TableField("is_deleted")
  private String isDeleted;

  /** 创建时间 */
  @TableField("created_time")
  private LocalDateTime createdTime;

  /** 更新时间 */
  @TableField("updated_time")
  private LocalDateTime updatedTime;
}

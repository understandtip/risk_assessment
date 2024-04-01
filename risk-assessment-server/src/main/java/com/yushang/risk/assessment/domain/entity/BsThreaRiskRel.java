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
 * 威胁风险关系
 *
 * @author zlp
 * @since 2024-03-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("bs_threa_risk_rel")
public class BsThreaRiskRel implements Serializable {

  private static final long serialVersionUID = 1L;

  /** 主键id */
  @TableId(value = "id", type = IdType.AUTO)
  private Integer id;

  /** 威胁id */
  @TableField("threat_id")
  private Integer threatId;

  /** 风险id */
  @TableField("risk_id")
  private Integer riskId;

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

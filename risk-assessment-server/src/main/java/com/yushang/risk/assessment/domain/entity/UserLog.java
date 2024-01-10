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
 * 用户日志
 *
 * @author zlp
 * @since 2024-01-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("user_log")
public class UserLog implements Serializable {

  private static final long serialVersionUID = 1L;

  /** 主键id */
  @TableId(value = "id", type = IdType.AUTO)
  private Integer id;

  /** 用户id */
  @TableField("user_id")
  private Integer userId;

  /** 日志类型;1:登录 0:退出 */
  @TableField("log_type")
  private Integer logType;

  /** 逻辑删除;1:删除 0:未删除 */
  @TableField("is_deleted")
  private String isDeleted;

  /** 创建时间 */
  @TableField("created_time")
  private LocalDateTime createdTime;
}

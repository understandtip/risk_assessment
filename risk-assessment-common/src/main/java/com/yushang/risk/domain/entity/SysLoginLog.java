package com.yushang.risk.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.*;

/**
 * 系统登录日志
 *
 * @author zlp
 * @since 2024-02-04
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "系统登录日志", description = "")
@TableName("sys_login_log")
public class SysLoginLog implements Serializable, Cloneable {
  /** 主键id */
  @ApiModelProperty(notes = "主键id")
  @TableId(type = IdType.AUTO)
  private Integer id;
  /** 用户名 */
  @ApiModelProperty(notes = "用户名")
  private String username;
  /** ip地址 */
  @ApiModelProperty(notes = "ip地址")
  private String ip;
  /** 地点 */
  @ApiModelProperty(notes = "地点")
  private String address;
  /** 浏览器 */
  @ApiModelProperty(notes = "浏览器")
  private String browser;
  /** 操作系统 */
  @ApiModelProperty(notes = "操作系统")
  private String os;
  /** 平台类型;0:前台 1:后台 */
  @ApiModelProperty(notes = "平台类型 0:前台  1:后台")
  private String platformType;
  /** 登录状态 */
  @ApiModelProperty(notes = "登录状态 0:失败  1:成功")
  private String state;
  /** 逻辑删除;1:删除 0:未删除 */
  @ApiModelProperty(notes = "逻辑删除 1:删除   0:未删除")
  @TableLogic
  private String isDeleted;

  /** 创建时间 */
  @TableField("created_time")
  private LocalDateTime createdTime;

  /** 更新时间 */
  @TableField("updated_time")
  private LocalDateTime updatedTime;
}

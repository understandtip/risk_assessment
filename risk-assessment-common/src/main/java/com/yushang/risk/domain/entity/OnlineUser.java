package com.yushang.risk.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.websocket.OnOpen;

/**
 * 在线用户
 *
 * @author zlp
 * @since 2024-02-19
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "在线用户", description = "")
@TableName("online_user")
public class OnlineUser implements Serializable, Cloneable {
  /** 主键id */
  @ApiModelProperty(notes = "主键id")
  @TableId(type = IdType.AUTO)
  private Integer id;
  /** 会话id */
  @ApiModelProperty(notes = "会话id")
  private String sessionId;
  /** 用户名 */
  @ApiModelProperty(notes = "用户名")
  private String userName;
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
  /** 创建时间 */
  @ApiModelProperty(notes = "创建时间")
  private LocalDateTime createdTime;
}

package com.yushang.risk.admin.domain.vo.request;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author：zlp @Package：com.yushang.risk.admin.domain.vo.request @Project：risk_assessment
 *
 * @name：LogPageReq @Date：2024/2/6 12:41 @Filename：LogPageReq
 */
@Data
public class LogPageReq {
  /** 用户名 */
  @ApiModelProperty(notes = "用户名")
  private String username;
  /** 地点 */
  @ApiModelProperty(notes = "地点")
  private String address;
  /** 登录状态 */
  @ApiModelProperty(notes = "登录状态 0:失败  1:成功")
  private String state;

  /** 创建时间 */
  @TableField("created_time")
  private LocalDateTime createdTime;

  /** 更新时间 */
  @TableField("updated_time")
  private LocalDateTime endTime;
}

package com.yushang.risk.admin.domain.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author：zlp @Package：com.yushang.risk.admin.domain.vo.request @Project：risk_assessment
 *
 * @name：OnlineUserReq @Date：2024/2/19 15:46 @Filename：OnlineUserReq
 */
@Data
public class OnlineUserPageReq {
  /** 用户名 */
  @ApiModelProperty(notes = "用户名")
  private String userName;
  /** ip地址 */
  @ApiModelProperty(notes = "ip地址")
  private String ip;
}

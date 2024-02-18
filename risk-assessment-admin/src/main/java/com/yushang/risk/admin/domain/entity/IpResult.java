package com.yushang.risk.admin.domain.entity;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Objects;
import lombok.Data;

@Data
public class IpResult<T> implements Serializable {
  @ApiModelProperty("错误码")
  private Integer code;

  @ApiModelProperty("错误消息")
  private String msg;

  @ApiModelProperty("返回对象")
  private T data;

  public boolean isSuccess() {
    return Objects.nonNull(this.code) && this.code == 0;
  }
}

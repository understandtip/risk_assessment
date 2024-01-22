package com.yushang.risk.admin.domain.vo.request;

import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * @Author：zlp @Package：com.yushang.risk.admin.domain.vo.request @Project：risk_assessment
 *
 * @name：PageBaseReq @Date：2024/1/16 14:05 @Filename：PageBaseReq
 */
@Data
@ApiModel("基础翻页请求")
public class PageBaseReq<T> {
  @ApiModelProperty("页面大小")
  @Min(0)
  @Max(50)
  private Integer pageSize;

  @ApiModelProperty("页面索引（从1开始）")
  private Integer pageNo;

  @ApiModelProperty("分页查询条件")
  private T data;
  /**
   * 获取mybatisPlus的page
   *
   * @return
   */
  public <S> Page<S> plusPage() {
    return new Page<>(pageNo, pageSize);
  }
}

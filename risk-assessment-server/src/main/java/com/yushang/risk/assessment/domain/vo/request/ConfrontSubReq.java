package com.yushang.risk.assessment.domain.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Author：zlp @Package：com.yushang.risk.assessment.domain.vo.request @Project：risk_assessment
 *
 * @name：ConfrontSubReq @Date：2024/2/28 9:24 @Filename：ConfrontSubReq
 */
@Data
public class ConfrontSubReq {
  @NotNull private List<Integer> confrontWayIds;

  @NotNull private String pic1FileName;
  @NotNull private String pic2FileName;
}

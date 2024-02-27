package com.yushang.risk.assessment.controller;

import com.yushang.risk.assessment.domain.vo.response.ConfrontInfoResp;
import com.yushang.risk.assessment.service.ConfrontService;
import com.yushang.risk.common.domain.vo.ApiResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author：zlp @Package：com.yushang.risk.assessment.controller @Project：risk_assessment
 *
 * @name：ConfrontController @Date：2024/2/23 15:03 @Filename：ConfrontController
 */
@Api(tags = "对抗接口")
@RequestMapping("/api/confront")
@RestController
public class ConfrontController {
  @Resource private ConfrontService confrontService;

  @GetMapping("/getConfrontInfo")
  @ApiOperation("获取对抗数据")
  public ApiResult<ConfrontInfoResp> getConfrontInfo() {
    ConfrontInfoResp resp = confrontService.getConfrontInfo();
    return ApiResult.success(resp);
  }
}

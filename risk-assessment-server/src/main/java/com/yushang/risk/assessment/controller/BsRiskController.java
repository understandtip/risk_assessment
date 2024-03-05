package com.yushang.risk.assessment.controller;

import com.yushang.risk.assessment.domain.entity.BsAttackAvoid;
import com.yushang.risk.common.domain.vo.ApiResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author：zlp @Package：com.yushang.risk.assessment.controller @Project：risk_assessment
 *
 * @name：BsRiskController @Date：2024/3/1 9:57 @Filename：BsRiskController
 */
@Api(tags = "业务风险")
@RestController
@RequestMapping("/api/bsr")
public class BsRiskController {

  @GetMapping("/getBSRisk")
  @ApiOperation("业务风险数据")
  public ApiResult<BsAttackAvoid> getBSRisk() {
    return ApiResult.success();
  }
}

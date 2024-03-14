package com.yushang.risk.assessment.controller;

import com.mysql.cj.x.protobuf.MysqlxCrud;
import com.yushang.risk.assessment.domain.entity.BsAttackAvoid;
import com.yushang.risk.assessment.domain.entity.BsCategory;
import com.yushang.risk.assessment.domain.vo.response.*;
import com.yushang.risk.assessment.service.BsCategoryService;
import com.yushang.risk.assessment.service.BsRiskService;
import com.yushang.risk.common.domain.vo.ApiResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Author：zlp @Package：com.yushang.risk.assessment.controller @Project：risk_assessment
 *
 * @name：BsRiskController @Date：2024/3/1 9:57 @Filename：BsRiskController TODO 使用缓存
 */
@Api(tags = "业务风险")
@RestController
@RequestMapping("/api/bsr")
public class BsRiskController {
  @Resource private BsCategoryService bsCategoryService;
  @Resource private BsRiskService bsRiskService;

  @GetMapping("/getBSRisk")
  @ApiOperation("业务风险数据")
  public ApiResult<BsRiskResp> getBSRisk(@RequestParam Integer categoryId) {
    BsRiskResp resp = bsCategoryService.getBSRisk(categoryId);
    return ApiResult.success(resp);
  }

  @GetMapping("/getCategory")
  @ApiOperation("分类数据")
  public ApiResult<List<BsCategory>> getCategory() {
    List<BsCategory> categoryList = bsCategoryService.getCategory();
    return ApiResult.success(categoryList);
  }

  @GetMapping("/getRiskInfo")
  @ApiOperation("获取风险信息")
  public ApiResult<BsRiskInfoResp> getRiskInfo(@RequestParam Integer riskId) {
    BsRiskInfoResp resp = bsRiskService.getRiskInfo(riskId);
    return ApiResult.success(resp);
  }

  @GetMapping("/getAvoidInfo")
  @ApiOperation("获取规避信息")
  public ApiResult<BsAvoidInfoResp> getAvoidInfo(@RequestParam Integer avoidId) {
    BsAvoidInfoResp resp = bsRiskService.getAvoidInfo(avoidId);
    return ApiResult.success(resp);
  }

  @GetMapping("/getToolInfo")
  @ApiOperation("获取攻击工具信息")
  public ApiResult<BsAttackToolInfoResp> getToolInfo(@RequestParam Integer toolId) {
    BsAttackToolInfoResp resp = bsRiskService.getToolInfo(toolId);
    return ApiResult.success(resp);
  }

  @GetMapping("/getAllRisk")
  @ApiOperation("所有风险")
  public ApiResult<List<BsAllRiskInfoResp>> getAllRisk() {
    List<BsAllRiskInfoResp> resp = bsRiskService.getAllRisk();
    return ApiResult.success(resp);
  }

  @GetMapping("/getAllAvoid")
  @ApiOperation("所有规避手段")
  public ApiResult<List<BsAllAvoidInfoResp>> getAllAvoid() {
    List<BsAllAvoidInfoResp> resp = bsRiskService.getAllAvoid();
    return ApiResult.success(resp);
  }

  @GetMapping("/getAllAttackTool")
  @ApiOperation("所有攻击工具")
  public ApiResult<List<BsAllAttackToolInfoResp>> getAllAttackTool() {
    List<BsAllAttackToolInfoResp> resp = bsRiskService.getAllAttackTool();
    return ApiResult.success(resp);
  }
}

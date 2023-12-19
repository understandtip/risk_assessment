package com.yushang.risk.assessment.controller;

import com.yushang.risk.assessment.domain.entity.Category;
import com.yushang.risk.assessment.domain.entity.Cycle;
import com.yushang.risk.assessment.domain.entity.Risk;
import com.yushang.risk.assessment.domain.entity.SecurityAttribute;
import com.yushang.risk.assessment.domain.vo.response.AttrResp;
import com.yushang.risk.assessment.domain.vo.response.CycleResp;
import com.yushang.risk.assessment.domain.vo.response.RiskResp;
import com.yushang.risk.assessment.service.CategoryService;
import com.yushang.risk.assessment.service.CycleService;
import com.yushang.risk.assessment.service.RiskService;
import com.yushang.risk.assessment.service.SecurityAttributeService;
import com.yushang.risk.common.domain.vo.ApiResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 风险枚举接口
 *
 * @author zlp
 * @since 2023-12-18
 */
@RestController
@RequestMapping("/api/risk")
@Api(tags = "风险枚举接口")
public class RiskEnumController {
  @Resource private CategoryService categoryService;
  @Resource private RiskService riskService;
  @Resource private CycleService cycleService;
  @Resource private SecurityAttributeService securityAttributeService;

  /**
   * 获取分类集合
   *
   * @return
   */
  @GetMapping("/getCategoryList")
  @ApiOperation(value = "获取所有分类集合")
  public ApiResult<List<Category>> getCategoryList() {
    List<Category> list = categoryService.getCategoryList();
    return ApiResult.success(list);
  }
  /**
   * 获取所有周期集合
   *
   * @return
   */
  @GetMapping("/getCycleList")
  @ApiOperation(value = "获取所有周期集合")
  public ApiResult<List<CycleResp>> getCycleList() {
    List<CycleResp> list = cycleService.getCycleList();
    return ApiResult.success(list);
  }
  /**
   * 获取所有安全属性集合
   *
   * @return
   */
  @GetMapping("/getAttrList")
  @ApiOperation(value = "获取所有安全属性集合")
  public ApiResult<List<AttrResp>> getAttrList() {
    List<AttrResp> list = securityAttributeService.getAttrList();
    return ApiResult.success(list);
  }
  /**
   * 获取所有风险集合
   *
   * @return
   */
  @GetMapping("/getRiskList")
  @ApiOperation(value = "获取所有风险集合")
  public ApiResult<List<RiskResp>> getRiskList() {
    List<RiskResp> list = riskService.getRiskList();
    return ApiResult.success(list);
  }
  /**
   * 查询指定分类下的风险集合
   *
   * @param categoryId
   * @return
   */
  @GetMapping("/getRiskList/{categoryId}")
  @ApiOperation("查询指定分类下的风险集合")
  public ApiResult<List<RiskResp>> getRiskFromCategory(@PathVariable Integer categoryId) {
    return ApiResult.success(riskService.getRiskList(categoryId));
  }
}

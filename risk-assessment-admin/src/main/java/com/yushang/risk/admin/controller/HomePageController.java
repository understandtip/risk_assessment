package com.yushang.risk.admin.controller;

import cn.hutool.log.Log;
import com.yushang.risk.admin.service.HomePageService;
import com.yushang.risk.common.domain.vo.ApiResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Author：zlp @Package：com.yushang.risk.admin.controller @Project：risk_assessment
 *
 * @name：HomePageController @Date：2024/1/11 15:39 @Filename：HomePageController
 */
@RestController
@RequestMapping("/capi/home")
@Api(tags = "后台首页接口")
public class HomePageController {
  @Resource private HomePageService homePageService;

  @GetMapping("/getVisitNum")
  @ApiOperation("获取月访问量")
  @PreAuthorize("@ss.hasPermi('sys:home')")
  public ApiResult<Long> getVisitNum() {
    Long count = homePageService.getVisitNum();
    return ApiResult.success(count);
  }

  @GetMapping("/getVisitNumAll")
  @ApiOperation("获取总访问量")
  @PreAuthorize("@ss.hasPermi('sys:home')")
  public ApiResult<Long> getVisitNumAll() {
    Long count = homePageService.getVisitNumAll();
    return ApiResult.success(count);
  }

  @GetMapping("/getAddedUser")
  @ApiOperation("获取月增用户数")
  @PreAuthorize("@ss.hasPermi('sys:home')")
  public ApiResult<Long> getAddedUser() {
    Long count = homePageService.getAddedUser();
    return ApiResult.success(count);
  }

  @GetMapping("/getAddedUserAll")
  @ApiOperation("获取总增用户数")
  @PreAuthorize("@ss.hasPermi('sys:home')")
  public ApiResult<Long> getAddedUserAll() {
    Long count = homePageService.getAddedUserAll();
    return ApiResult.success(count);
  }

  @GetMapping("/getDownLoadOfYear")
  @ApiOperation("获取年报告下载数")
  @PreAuthorize("@ss.hasPermi('sys:home')")
  public ApiResult<Long> getDownLoadOfYear() {
    Long count = homePageService.getDownLoadOfYear();
    return ApiResult.success(count);
  }

  @GetMapping("/getDownLoadAll")
  @ApiOperation("获取总报告下载数")
  @PreAuthorize("@ss.hasPermi('sys:home')")
  public ApiResult<Long> getDownLoadAll() {
    Long count = homePageService.getDownLoadAll();
    return ApiResult.success(count);
  }

  @GetMapping("/getGeneratePortCount")
  @ApiOperation("获取周报告生成数")
  @PreAuthorize("@ss.hasPermi('sys:home')")
  public ApiResult<Long> getGeneratePortCount() {
    Long count = homePageService.getGeneratePortCount();
    return ApiResult.success(count);
  }

  @GetMapping("/getGeneratePortCountAll")
  @ApiOperation("获取总报告生成数")
  @PreAuthorize("@ss.hasPermi('sys:home')")
  public ApiResult<Long> getGeneratePortCountAll() {
    Long count = homePageService.getGeneratePortCountAll();
    return ApiResult.success(count);
  }

  @GetMapping("/getVisitTrend")
  @ApiOperation("查询访问趋势")
  @PreAuthorize("@ss.hasPermi('sys:home')")
  public ApiResult<Map<Object, Object>> getVisitTrend() {
    Map<Object, Object> map = homePageService.getVisitTrend();
    return ApiResult.success(map);
  }

  @GetMapping("/getPortTrend")
  @ApiOperation("查询生成报告趋势")
  @PreAuthorize("@ss.hasPermi('sys:home')")
  public ApiResult<Map<Object, Object>> getPortTrend() {
    Map<Object, Object> map = homePageService.getPortTrend();
    return ApiResult.success(map);
  }
}

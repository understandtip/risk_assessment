package com.yushang.risk.admin.controller;

import com.yushang.risk.admin.service.HomePageService;
import com.yushang.risk.common.domain.vo.ApiResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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
  public ApiResult<Long> getVisitNum() {
    Long count = homePageService.getVisitNum();
    return ApiResult.success(count);
  }

  @GetMapping("/getVisitNumAll")
  @ApiOperation("获取总访问量")
  public ApiResult<Long> getVisitNumAll() {
    Long count = homePageService.getVisitNumAll();
    return ApiResult.success(count);
  }

  @GetMapping("/getAddedUser")
  @ApiOperation("获取月增用户数")
  public ApiResult<Long> getAddedUser() {
    Long count = homePageService.getAddedUser();
    return ApiResult.success(count);
  }

  @GetMapping("/getAddedUserAll")
  @ApiOperation("获取总增用户数")
  public ApiResult<Long> getAddedUserAll() {
    Long count = homePageService.getAddedUserAll();
    return ApiResult.success(count);
  }

  @GetMapping("/getDownLoadOfYear")
  @ApiOperation("获取年报告下载数")
  public ApiResult<Long> getDownLoadOfYear() {
    Long count = homePageService.getDownLoadOfYear();
    return ApiResult.success(count);
  }

  @GetMapping("/getDownLoadAll")
  @ApiOperation("获取总报告下载数")
  public ApiResult<Long> getDownLoadAll() {
    Long count = homePageService.getDownLoadAll();
    return ApiResult.success(count);
  }
}

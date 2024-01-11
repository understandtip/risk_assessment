package com.yushang.risk.admin.controller;

import com.sun.xml.internal.ws.developer.StreamingAttachment;
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
  @ApiOperation("获取访问量")
  public ApiResult<Long> getVisitNum() {
    Long count = homePageService.getVisitNum();
    return ApiResult.success(count);
  }
}

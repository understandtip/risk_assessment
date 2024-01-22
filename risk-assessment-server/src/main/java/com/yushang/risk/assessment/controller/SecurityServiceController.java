package com.yushang.risk.assessment.controller;

import com.yushang.risk.assessment.domain.vo.request.SecurityServiceBugBugReq;
import com.yushang.risk.assessment.domain.vo.response.SecurityModelDetailResp;
import com.yushang.risk.assessment.domain.vo.response.SecurityModelResp;
import com.yushang.risk.assessment.service.SSecurityServiceService;
import com.yushang.risk.common.annotation.OptLog;
import com.yushang.risk.common.domain.vo.ApiResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import sun.java2d.ReentrantContextProviderTL;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author：zlp @Package：com.yushang.risk.assessment.controller @Project：risk_assessment
 *
 * @name：SecurityServiceController @Date：2024/1/15 14:23 @Filename：SecurityServiceController
 */
@RestController
@RequestMapping("/api/security")
@Api(tags = "安全服务相关接口")
public class SecurityServiceController {

  @Resource private SSecurityServiceService sSecurityServiceService;

  /**
   * 获取安全服务模块列表
   *
   * @return
   */
  @GetMapping("/getSecurityModel")
  @ApiOperation("获取安全模块列表")
  public ApiResult<List<SecurityModelResp>> getSecurityModel() {
    List<SecurityModelResp> list = sSecurityServiceService.getSecurityModel();
    return ApiResult.success(list);
  }

  /**
   * 查看模块详细信息
   *
   * @return
   */
  @GetMapping("/getModelDetail")
  @ApiOperation("查看模块详细信息")
  public ApiResult<SecurityModelDetailResp> getModelDetail(
      @RequestParam("modelId") Integer modelId) {
    SecurityModelDetailResp resp = sSecurityServiceService.getModelDetail(modelId);
    return ApiResult.success(resp);
  }

  /**
   * 提交漏洞项目 TODO 流控/配置线程池——一个线程
   *
   * @return
   */
  @PostMapping("/submitBugReport")
  @ApiOperation("提交漏洞项目")
  public ApiResult<Void> submitBugReport(@RequestBody @Validated SecurityServiceBugBugReq bugReq) {
    sSecurityServiceService.submitBugReport(bugReq);
    return ApiResult.success();
  }
}

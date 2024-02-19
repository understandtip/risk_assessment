package com.yushang.risk.admin.controller;

import com.yushang.risk.admin.domain.vo.request.LogPageReq;
import com.yushang.risk.admin.domain.vo.request.PageBaseReq;
import com.yushang.risk.admin.domain.vo.response.PageBaseResp;
import com.yushang.risk.admin.service.SysLoginLogService;
import com.yushang.risk.common.domain.vo.ApiResult;
import com.yushang.risk.domain.entity.SysLoginLog;
import com.yushang.risk.utils.RequestUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @Author：zlp @Package：com.yushang.risk.admin.controller @Project：risk_assessment
 *
 * @name：SysLogController @Date：2024/2/6 11:36 @Filename：SysLogController
 */
@RestController
@RequestMapping("/capi/log")
@Api(tags = "日志接口")
public class SysLogController {
  @Resource private SysLoginLogService sysLoginLogService;

  @PostMapping("/getLogList")
  @ApiOperation("获取日志记录")
  public ApiResult<PageBaseResp<SysLoginLog>> getLogList(
      @RequestBody @Validated PageBaseReq<LogPageReq> baseReq) {
    PageBaseResp<SysLoginLog> resp = sysLoginLogService.getLogPageList(baseReq);
    return ApiResult.success(resp);
  }
}

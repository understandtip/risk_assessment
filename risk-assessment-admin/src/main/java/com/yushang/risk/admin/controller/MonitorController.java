package com.yushang.risk.admin.controller;

import com.yushang.risk.admin.domain.dto.Server;
import com.yushang.risk.admin.domain.vo.request.OnlineUserPageReq;
import com.yushang.risk.admin.domain.vo.request.PageBaseReq;
import com.yushang.risk.admin.domain.vo.response.PageBaseResp;
import com.yushang.risk.admin.service.MonitorService;
import com.yushang.risk.common.domain.vo.ApiResult;
import com.yushang.risk.domain.entity.OnlineUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author：zlp @Package：com.yushang.risk.admin.controller @Project：risk_assessment
 *
 * @name：MonitorController @Date：2024/2/18 14:11 @Filename：MonitorController
 */
@Api(tags = "监控接口")
@RequestMapping("/capi/mon")
@RestController
public class MonitorController {
  @Resource private MonitorService monitorService;

  @PreAuthorize("@ss.hasPermi('sys:mon:getOnlineUser')")
  @PostMapping("/getOnlineList")
  @ApiOperation("查询在线用户")
  public ApiResult<PageBaseResp<OnlineUser>> getOnlineList(
      @RequestBody @Validated PageBaseReq<OnlineUserPageReq> pageBaseReq) {
    PageBaseResp<OnlineUser> resp = monitorService.getOnlineList(pageBaseReq);
    return ApiResult.success(resp);
  }

  @PreAuthorize("@ss.hasPermi('sys:mon:forceExit')")
  @DeleteMapping("/forceExit")
  @ApiOperation("强退")
  public ApiResult<Void> forceExit(
      @RequestParam String userName, @RequestParam String platformType) {
    monitorService.forceExit(userName, platformType);
    return ApiResult.success();
  }

  @PreAuthorize("@ss.hasPermi('sys:mon:info')")
  @GetMapping("/getSysInfo")
  @ApiOperation("系统信息")
  public ApiResult<Server> getSysInfo() throws Exception {
    Server server = new Server();
    server.copyTo();
    return ApiResult.success(server);
  }
}

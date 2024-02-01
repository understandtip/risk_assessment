package com.yushang.risk.admin.controller;

import com.yushang.risk.admin.domain.vo.request.PageBaseReq;
import com.yushang.risk.admin.domain.vo.request.SecurityServiceRecordPageReq;
import com.yushang.risk.admin.domain.vo.response.PageBaseResp;
import com.yushang.risk.admin.domain.vo.response.SecurityServiceRecordPageResp;
import com.yushang.risk.admin.service.SUserRecordService;
import com.yushang.risk.admin.service.SUserService;
import com.yushang.risk.common.domain.vo.ApiResult;
import com.yushang.risk.common.exception.CommonErrorEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.annotation.Resource;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @Author：zlp @Package：com.yushang.risk.admin.controller @Project：risk_assessment
 *
 * @name：SecurityServiceController @Date：2024/1/16 10:44 @Filename：SecurityServiceController
 */
@RestController
@RequestMapping("/capi/security")
@Api(tags = "安全服务相关接口")
public class SecurityServiceController {
  @Resource private SUserRecordService sUserRecordService;
  @Resource private SUserService sUserService;

  /**
   * 查询用户申请漏洞列表
   *
   * @return
   */
  @PostMapping("/getUserBugRecordList")
  @ApiOperation("查询用户申请漏洞列表")
  @PreAuthorize("@ss.hasPermi('sys:ss:get')")
  public ApiResult<PageBaseResp<SecurityServiceRecordPageResp>> getUserBugRecordList(
      @RequestBody @Validated PageBaseReq<SecurityServiceRecordPageReq> recordPageReq) {
    PageBaseResp<SecurityServiceRecordPageResp> resp =
        sUserRecordService.getUserBugRecordList(recordPageReq);
    return ApiResult.success(resp);
  }

  /**
   * 删除漏洞记录
   *
   * @param userId 用户提交记录id
   * @return
   */
  @DeleteMapping("/deleteBugRecord")
  @ApiOperation("删除漏洞记录")
  @PreAuthorize("@ss.hasPermi('sys:ss:del')")
  public ApiResult<Void> deleteBugRecord(Integer userId) {
    boolean b = sUserService.deleteBugRecord(userId);
    return b
        ? ApiResult.success()
        : ApiResult.fail(CommonErrorEnum.BUSINESS_ERROR.getCode(), "文件删除失败");
  }
}

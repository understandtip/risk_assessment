package com.yushang.risk.admin.controller;

import com.yushang.risk.admin.domain.enums.ApplyStateEnum;
import com.yushang.risk.admin.domain.vo.request.ApplyPageReq;
import com.yushang.risk.admin.domain.vo.request.PageBaseReq;
import com.yushang.risk.admin.domain.vo.response.ApplyPageResp;
import com.yushang.risk.admin.domain.vo.response.PageBaseResp;
import com.yushang.risk.admin.service.AccountService;
import com.yushang.risk.admin.service.RegisterApplyService;
import com.yushang.risk.common.domain.vo.ApiResult;
import com.yushang.risk.common.exception.BusinessException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.xhtmlrenderer.css.parser.property.PrimitivePropertyBuilders;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author：zlp @Package：com.yushang.risk.admin.controller @Project：risk_assessment
 *
 * @name：ApplyController @Date：2024/2/1 14:43 @Filename：ApplyController
 */
@RequestMapping("/capi/apply")
@RestController
@Api(tags = "入驻管理")
public class ApplyController {
  @Resource private RegisterApplyService registerApplyService;

  @PostMapping("/getAllApply")
  @ApiOperation("查询申请")
  public ApiResult<PageBaseResp<ApplyPageResp>> getAllApply(
      @RequestBody @Validated PageBaseReq<ApplyPageReq> applyReq) {
    PageBaseResp<ApplyPageResp> resp = registerApplyService.getAllApplyByPage(applyReq);
    return ApiResult.success(resp);
  }

  @PutMapping("/upApplyState")
  @ApiOperation("修改状态")
  public ApiResult<Void> upApplyState(@RequestParam Integer applyId, @RequestParam Integer state) {
    if (!ApplyStateEnum.isInside(state)) throw new BusinessException("状态错误");
    registerApplyService.upApplyState(applyId, state);
    return ApiResult.success();
  }

  @DeleteMapping("/removeApply")
  @ApiOperation("删除记录")
  public ApiResult<Void> removeApply(@RequestBody List<Integer> appIds) {
    registerApplyService.removeApply(appIds);
    return ApiResult.success();
  }
}

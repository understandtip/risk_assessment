package com.yushang.risk.assessment.controller;

import com.yushang.risk.assessment.domain.vo.request.RecordPageReq;
import com.yushang.risk.assessment.domain.vo.response.PageBaseResp;
import com.yushang.risk.assessment.domain.vo.response.RecordResp;
import com.yushang.risk.assessment.service.GenerateRecordService;
import com.yushang.risk.common.domain.vo.ApiResult;
import com.yushang.risk.common.exception.CommonErrorEnum;
import com.yushang.risk.common.util.RequestHolder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author：zlp @Package：com.yushang.risk.assessment.controller @Project：risk_assessment
 *
 * @name：RecordController @Date：2024/1/3 14:09 @Filename：RecordController
 */
@RestController
@RequestMapping("/api/record")
@Api(tags = "报告记录相关接口")
public class RecordController {
  @Resource private GenerateRecordService generateRecordService;

  /**
   * 分页查询生成报告记录
   *
   * @param recordPageReq
   * @return
   */
  @GetMapping("/getListByPage")
  @ApiOperation("分页查询报告记录")
  public ApiResult<PageBaseResp<RecordResp>> getListByPage(@Validated RecordPageReq recordPageReq) {
    Integer uid = RequestHolder.get().getUid();
    PageBaseResp<RecordResp> page = generateRecordService.getListByPage(uid, recordPageReq);
    return ApiResult.success(page);
  }

  /**
   * 删除报告记录
   *
   * @param recordIds
   * @return
   */
  @DeleteMapping("/remove")
  @ApiOperation("删除报告记录")
  public ApiResult<Void> remove(@RequestBody List<Integer> recordIds) {
    boolean b = generateRecordService.remove(recordIds);
    return b
        ? ApiResult.success()
        : ApiResult.fail(CommonErrorEnum.BUSINESS_ERROR.getCode(), "删除失败，请重试");
  }
}

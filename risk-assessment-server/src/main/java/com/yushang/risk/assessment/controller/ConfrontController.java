package com.yushang.risk.assessment.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.log.Log;
import com.yushang.risk.assessment.domain.vo.request.ConfrontSubReq;
import com.yushang.risk.assessment.domain.vo.response.ConfrontInfoResp;
import com.yushang.risk.assessment.service.ConfrontService;
import com.yushang.risk.common.domain.vo.ApiResult;
import com.yushang.risk.common.exception.BusinessException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

/**
 * @Author：zlp @Package：com.yushang.risk.assessment.controller @Project：risk_assessment
 *
 * @name：ConfrontController @Date：2024/2/23 15:03 @Filename：ConfrontController
 */
@Api(tags = "对抗接口")
@RequestMapping("/api/confront")
@RestController
@Slf4j
public class ConfrontController {
  @Resource private ConfrontService confrontService;

  @GetMapping("/getConfrontInfo")
  @ApiOperation("获取对抗数据")
  public ApiResult<List<ConfrontInfoResp>> getConfrontInfo(@RequestParam boolean isEnhance) {
    List<ConfrontInfoResp> resp = confrontService.getConfrontInfo(isEnhance);
    return ApiResult.success(resp);
  }

  @PostMapping("/submitPort")
  @ApiOperation("提交报告")
  public byte[] submitPort(
      @RequestBody @Validated ConfrontSubReq confrontSubReq, HttpServletResponse response)
      throws UnsupportedEncodingException {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    // 设置响应内容类型
    response.setContentType("application/octet-stream");
    response.addHeader(
        "Content-Disposition",
        "attachment; filename=" + URLEncoder.encode(FileUtil.getName("对抗数据报告.docx"), "UTF-8"));
    try {
      confrontService.submitPort(confrontSubReq, outputStream);
      // 将输出流中的字节内容转换为字节数组
      return outputStream.toByteArray();
    } catch (IOException e) {
      log.error("生成报告失败", e);
      throw new BusinessException("生成报告失败");
    }
  }
}

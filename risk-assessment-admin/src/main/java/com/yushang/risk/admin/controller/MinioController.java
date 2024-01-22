package com.yushang.risk.admin.controller;

import com.yushang.risk.admin.service.MinioService;
import com.yushang.risk.common.domain.vo.ApiResult;
import com.yushang.risk.common.exception.CommonErrorEnum;
import io.swagger.annotations.*;
import java.io.IOException;
import java.util.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author zlp
 */
@Slf4j
@RestController
@RequestMapping("/capi/minio")
@Api(tags = "minio文件上传相关接口")
public class MinioController {
  @Resource private MinioService minioService;

  @PostMapping("/upload")
  @ApiOperation("minio文件上传")
  // TODO 限流频控
  public ApiResult<List<String>> upload(
      @RequestParam(name = "file", required = false) MultipartFile[] file) {
    if (file == null || file.length == 0) {
      return ApiResult.fail(CommonErrorEnum.BUSINESS_ERROR.getCode(), "文件不能为空");
    }
    List<String> orgfileNameList = new ArrayList<>(file.length);
    for (MultipartFile multipartFile : file) {
      try {
        String orgfileName = System.currentTimeMillis() + "_" + multipartFile.getOriginalFilename();
        String url = minioService.upload(multipartFile.getInputStream(), orgfileName);
        orgfileNameList.add(orgfileName);
      } catch (IOException e) {
        return ApiResult.fail(CommonErrorEnum.BUSINESS_ERROR.getCode(), "上传失败，请重试");
      }
    }
    return ApiResult.success(orgfileNameList);
  }

  @PostMapping("/download")
  @ApiOperation("minio下载文件")
  public void download(HttpServletResponse response, String fileName) {
    minioService.downLoad(fileName, response);
  }

  @DeleteMapping("/delete/{fileName}")
  @ApiOperation("minio删除文件")
  public ApiResult<Void> delete(@PathVariable("fileName") String fileName) {
    minioService.delete(fileName);
    return ApiResult.success();
  }
}

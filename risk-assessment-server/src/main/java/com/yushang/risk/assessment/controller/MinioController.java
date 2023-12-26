package com.yushang.risk.assessment.controller;

import com.alibaba.fastjson.JSON;
import com.yushang.risk.common.config.minio.MinioProp;
import com.yushang.risk.common.domain.vo.ApiResult;
import com.yushang.risk.common.exception.CommonErrorEnum;
import com.yushang.risk.common.util.RequestHolder;
import io.minio.MinioClient;
import io.minio.ObjectStat;
import io.minio.PutObjectOptions;
import io.minio.Result;
import io.minio.messages.Item;
import io.swagger.annotations.Api;
import io.swagger.annotations.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.filefilter.OrFileFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/api/minio")
@Api(tags = "minio文件上传相关接口")
public class MinioController {
  @Resource private MinioClient minioClient;
  @Resource private MinioProp minioProp;
  private static final String MINIO_BUCKET = "yushang";

  // @GetMapping("/list")
  // public List<Object> list(ModelMap map) throws Exception {
  //   Iterable<Result<Item>> myObjects = minioClient.listObjects(MINIO_BUCKET);
  //   Iterator<Result<Item>> iterator = myObjects.iterator();
  //   List<Object> items = new ArrayList<>();
  //   String format = "{'fileName':'%s','fileSize':'%s'}";
  //   while (iterator.hasNext()) {
  //     Item item = iterator.next().get();
  //     items.add(JSON.parse(String.format(format, item.objectName(),
  // formatFileSize(item.size()))));
  //   }
  //   return items;
  // }

  @PostMapping("/upload")
  public ApiResult<List<String>> upload(
      @RequestParam(name = "file", required = false) MultipartFile[] file) {

    if (file == null || file.length == 0) {
      return ApiResult.fail(CommonErrorEnum.BUSINESS_ERROR.getCode(), "文件不能为空");
    }

    List<String> orgfileNameList = new ArrayList<>(file.length);

    for (MultipartFile multipartFile : file) {
      // String orgfileName = RequestHolder.get().getUid() + "/" + (UUID.randomUUID());
      String orgfileName = "0/" + (UUID.randomUUID()) + "-" + multipartFile.getOriginalFilename();
      orgfileNameList.add(minioProp.getEndpoint() + "/" + MINIO_BUCKET + "/" + orgfileName);

      try {
        InputStream in = multipartFile.getInputStream();
        minioClient.putObject(
            MINIO_BUCKET, orgfileName, in, new PutObjectOptions(in.available(), -1));
        in.close();
      } catch (Exception e) {
        log.error("文件上传失败", e);
        return ApiResult.fail(CommonErrorEnum.BUSINESS_ERROR.getCode(), "上传失败，请重试");
      }
    }
    return ApiResult.success(orgfileNameList);
  }

  @PostMapping("/download/{fileName}")
  public void download(HttpServletResponse response, @PathVariable("fileName") String fileName) {
    InputStream in = null;
    try {
      ObjectStat stat = minioClient.statObject(MINIO_BUCKET, fileName);
      response.setContentType(stat.contentType());
      response.setHeader(
          "Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));

      in = minioClient.getObject(MINIO_BUCKET, fileName);
      IOUtils.copy(in, response.getOutputStream());
    } catch (Exception e) {
      log.error(e.getMessage());
    } finally {
      if (in != null) {
        try {
          in.close();
        } catch (IOException e) {
          log.error(e.getMessage());
        }
      }
    }
  }

  @DeleteMapping("/delete/{fileName}")
  public ApiResult<Void> delete(@PathVariable("fileName") String fileName) {
    try {
      minioClient.removeObject(MINIO_BUCKET, fileName);
    } catch (Exception e) {
      log.error("minio文件删除抛出异常", e);
      return ApiResult.fail(CommonErrorEnum.BUSINESS_ERROR.getCode(), "文件删除失败，请稍后重试");
    }
    return ApiResult.success();
  }

  private static String formatFileSize(long fileS) {
    DecimalFormat df = new DecimalFormat("#.00");
    String fileSizeString = "";
    String wrongSize = "0B";
    if (fileS == 0) {
      return wrongSize;
    }
    if (fileS < 1024) {
      fileSizeString = df.format((double) fileS) + " B";
    } else if (fileS < 1048576) {
      fileSizeString = df.format((double) fileS / 1024) + " KB";
    } else if (fileS < 1073741824) {
      fileSizeString = df.format((double) fileS / 1048576) + " MB";
    } else {
      fileSizeString = df.format((double) fileS / 1073741824) + " GB";
    }
    return fileSizeString;
  }
}

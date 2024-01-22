package com.yushang.risk.admin.service;

import com.yushang.risk.common.config.minio.MinioProp;
import com.yushang.risk.common.event.FileDownEvent;
import com.yushang.risk.common.event.domain.dto.FileDownDto;
import com.yushang.risk.common.exception.BusinessException;
import com.yushang.risk.common.util.RequestHolder;
import com.yushang.risk.constant.NormalCommonConstant;
import io.minio.MinioClient;
import io.minio.ObjectStat;
import io.minio.PutObjectOptions;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

/**
 * @Author：zlp @Package：com.yushang.risk.assessment.service @Project：risk_assessment
 *
 * @name：MinioService @Date：2024/1/4 15:10 @Filename：MinioService
 */
@Service
@Slf4j
public class MinioService {

  @Resource private MinioClient minioClient;
  @Resource private MinioProp minioProp;
  @Resource private ApplicationEventPublisher applicationEventPublisher;

  public static final String MINIO_BUCKET = "yushang";
  public static final String MINIO_PORT = NormalCommonConstant.MINIO_PORT;

  /**
   * 文件上传
   *
   * @param in
   * @param fileName
   */
  public String upload(InputStream in, String fileName) {
    try {
      String objectName;
      if (isPublicFile(fileName)) {
        objectName = fileName;
      } else {
        objectName = RequestHolder.get().getUid() + "/" + fileName;
      }
      minioClient.putObject(MINIO_BUCKET, objectName, in, new PutObjectOptions(in.available(), -1));
      in.close();
      // 发布下载事件
      applicationEventPublisher.publishEvent(
          new FileDownEvent(this, FileDownDto.builder().fileName(fileName).build()));
      return minioProp.getEndpoint() + "/" + MINIO_BUCKET + "/" + objectName;
    } catch (Exception e) {
      log.error("文件上传失败", e);
      throw new BusinessException("上传失败，请重试");
    }
  }

  /**
   * 是否是公开文件上传
   *
   * @return
   * @param fileName
   */
  private boolean isPublicFile(String fileName) {
    return fileName.contains("tem/");
  }

  /**
   * 文件下载
   *
   * @param fileName
   * @param response
   */
  public void downLoad(String fileName, HttpServletResponse response) {
    InputStream in = null;
    try {
      String objectName;
      if (isPublicFile(fileName)) {
        objectName = fileName;
      } else {
        objectName = RequestHolder.get().getUid() + "/" + fileName;
      }

      ObjectStat stat = minioClient.statObject(MINIO_BUCKET, objectName);
      response.setContentType(stat.contentType());
      response.setHeader(
          "Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
      in = minioClient.getObject(MINIO_BUCKET, objectName);
      IOUtils.copy(in, response.getOutputStream());
    } catch (Exception e) {
      log.error("文件下载错误", e);
    } finally {
      if (in != null) {
        try {
          in.close();
        } catch (IOException e) {
          log.error("下载输入流关闭失败", e);
        }
      }
    }
  }

  /**
   * 删除文件
   *
   * @param fileName
   */
  public void delete(String fileName) {
    try {
      String objectName;
      if (isPublicFile(fileName)) {
        objectName = fileName;
      } else {
        objectName = RequestHolder.get().getUid() + "/" + fileName;
      }
      minioClient.removeObject(MINIO_BUCKET, objectName);
      log.info("文件删除成功");
    } catch (Exception e) {
      log.error("minio文件删除抛出异常", e);
      throw new BusinessException("文件删除失败，请稍后重试");
    }
  }

  public String getFilePath(String fileName) {
    return minioProp.getEndpoint()
        + "/"
        + MinioService.MINIO_BUCKET
        + "/"
        + RequestHolder.get().getUid()
        + "/"
        + fileName;
  }

  public String getPublicFilePath(String fileName) {
    return minioProp.getEndpoint() + "/" + MinioService.MINIO_BUCKET + "/" + fileName;
  }
}

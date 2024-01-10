package com.yushang.risk.assessment.service;

import com.yushang.risk.common.config.minio.MinioProp;
import com.yushang.risk.common.domain.vo.ApiResult;
import com.yushang.risk.common.exception.BusinessException;
import com.yushang.risk.common.exception.CommonErrorEnum;
import com.yushang.risk.common.util.RequestHolder;
import io.minio.MinioClient;
import io.minio.ObjectStat;
import io.minio.PutObjectOptions;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;

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

  public static final String MINIO_BUCKET = "yushang";
  public static final String MINIO_PORT = "port";

  /**
   * 文件上传
   *
   * @param in
   * @param fileName
   */
  public String upload(InputStream in, String fileName) {
    try {
      minioClient.putObject(
          MINIO_BUCKET,
          RequestHolder.get().getUid() + "/" + fileName,
          in,
          new PutObjectOptions(in.available(), -1));
      in.close();
      return minioProp.getEndpoint()
          + "/"
          + MINIO_BUCKET
          + "/"
          + RequestHolder.get().getUid()
          + "/"
          + fileName;
    } catch (Exception e) {
      log.error("文件上传失败", e);
      throw new BusinessException("上传失败，请重试");
    }
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
      ObjectStat stat =
          minioClient.statObject(MINIO_BUCKET, RequestHolder.get().getUid() + "/" + fileName);
      response.setContentType(stat.contentType());
      response.setHeader(
          "Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
      in = minioClient.getObject(MINIO_BUCKET, RequestHolder.get().getUid() + "/" + fileName);
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
      minioClient.removeObject(MINIO_BUCKET, RequestHolder.get().getUid() + "/" + fileName);
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
}

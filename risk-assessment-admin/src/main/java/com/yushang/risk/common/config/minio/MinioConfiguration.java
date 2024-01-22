package com.yushang.risk.common.config.minio;

import io.minio.MinioClient;
import io.minio.errors.InvalidEndpointException;
import io.minio.errors.InvalidPortException;
import javax.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zlp
 */
@Configuration
public class MinioConfiguration {
  @Resource private MinioProp minioProp;

  @Bean
  public MinioClient minioClient() throws InvalidPortException, InvalidEndpointException {
    MinioClient client =
        new MinioClient(
            minioProp.getEndpoint(), minioProp.getAccesskey(), minioProp.getSecretKey());
    return client;
  }
}

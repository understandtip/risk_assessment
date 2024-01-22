package com.yushang.risk.common.config.minio;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author zlp
 */
@Data
@ConfigurationProperties(prefix = "minio")
@Component
public class MinioProp {
  private String endpoint;
  private String accesskey;
  private String secretKey;
}

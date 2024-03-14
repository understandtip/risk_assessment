package com.yushang.risk.common.aspect;

import com.alibaba.fastjson.JSON;
import com.yushang.risk.common.domain.vo.ApiResult;
import com.yushang.risk.common.util.AesUtil;
import com.yushang.risk.common.util.PasswordUtils;
import lombok.SneakyThrows;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import springfox.documentation.spring.web.json.Json;

import javax.xml.bind.DatatypeConverter;

@ControllerAdvice
public class DataEncryptionAdvice implements ResponseBodyAdvice<ApiResult> {

  @Override
  public boolean supports(MethodParameter returnType, Class converterType) {
    // 在这里可以定义哪些类型的响应需要被拦截和加密
    Class<?> responseType = returnType.getParameterType();
    return responseType.isAssignableFrom(ApiResult.class);
  }

  @SneakyThrows
  @Override
  public ApiResult beforeBodyWrite(
      ApiResult body,
      MethodParameter returnType,
      MediaType selectedContentType,
      Class selectedConverterType,
      ServerHttpRequest request,
      ServerHttpResponse response) {
    // 如果响应类型是MyResponse，进行类型转换并加密数据
    if (body == null) return null;
    String encryptedData = encrypt(JSON.toJSONString(body.getData()));
    body.setData(encryptedData);
    return body;
  }

  /**
   * 对数据加密
   *
   * @param data
   * @return
   */
  private String encrypt(String data) {
    return AesUtil.encryptData(data);
  }
}

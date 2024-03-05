package com.yushang.risk.common.aspect;

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
    String encryptedData = encrypt(String.valueOf(body.getData()));
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

  public static void main(String[] args) {
    String hexString =
        "cb451450cf10f9774d58d7286c1120f3b16932afa99c4b6f2488bc6fa01e2969145ac0bc4f5c8d483ed51b841a4236aee4a5551596c3257316253ba7a62b125ac96576a010de62bf1504216b0bda3e213efc76fedc5b690b5af78481f2083e80e424928b27cf28f1f16c37007299a98629b7546b7a24572795aa09a6a6decc163c3296114ca4d422de114ff1233e01f63504efb8bac2bb3d6772c22d8e5cbcef68243b7ab03bffca76a95a0ae27cf615ebf61ba5e63e58ad560d2aade530841e68371cf5db8f3f544df8a0c992e6030ab51dd4a0e9dc09853d8a0e7304b48ff420a652ed35a483741967e5166eae782693f39696b913613ed814df6bfbcf60c34e3a3438cff1aaed9bf8df54a761f33c9f6149a60672758bb9202a0398723fc729c47246551e4418d06e53789e7dcc014f557905c25f644f1d05be20ea72f09c1159eabb5449e4de98221d222a314d675b3b7e7ee6dbf9231bcd5b97b3ccd6b8c4d62a723bd7ebb57eb3f05ee5bd91d129559ad6114ebc2fffe8c71a690a52b2a303b72ddf91798d8b78b8a8ce7ac6bf7b7ea2fbc8532fd7cb8a4d9a3273612ae475429c509284a3de958e63349fcf6b";

    byte[] byteArray = DatatypeConverter.parseHexBinary(hexString);

    System.out.println("转换后的字节数组长度为: " + byteArray);
  }
}

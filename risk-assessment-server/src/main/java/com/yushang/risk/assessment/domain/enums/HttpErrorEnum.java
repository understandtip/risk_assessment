package com.yushang.risk.assessment.domain.enums;

import cn.hutool.http.ContentType;
import cn.hutool.json.JSONUtil;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import javax.servlet.http.HttpServletResponse;

import com.yushang.risk.common.domain.vo.ApiResult;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author：zlp
 *
 * @name：HttpErrorEnum @Date：2023/11/3 0:10 @Filename：HttpErrorEnum
 */
@Getter
@AllArgsConstructor
public enum HttpErrorEnum {
  /** 访问被拒绝 */
  ACCESS_DENIED(401, "登录失效，请重新登录。");

  private final Integer code;
  private final String desc;

  /**
   * 快速response响应错误
   *
   * @param response
   * @throws IOException
   */
  public void sendHttpError(HttpServletResponse response) throws IOException {
    response.setStatus(code);
    response.setContentType(ContentType.JSON.toString(StandardCharsets.UTF_8));
    ApiResult<Object> fail = ApiResult.fail(code, desc);
    response.getWriter().write(JSONUtil.toJsonStr(fail));
  }
}

package com.yushang.risk.admin.controller;

import com.yushang.risk.admin.domain.vo.request.LoginReq;
import com.yushang.risk.admin.domain.vo.response.LoginUserResp;
import com.yushang.risk.admin.service.UserService;
import com.yushang.risk.common.domain.vo.ApiResult;
import com.yushang.risk.common.util.IpUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @Author：zlp @Package：com.yushang.risk.admin.controller @Project：risk_assessment
 *
 * @name：UserController @Date：2024/1/11 10:11 @Filename：UserController
 */
@RestController
@RequestMapping("/capi/user")
@Api(tags = "用户接口")
public class UserController {
  @Resource private UserService userService;

  /**
   * 用户来获取随机验证码
   *
   * @param response
   * @return
   * @throws IOException
   */
  @GetMapping("/getCode")
  @ApiOperation("获取随机验证码")
  // TODO @FrequencyControl(time = 3, count = 3, target = FrequencyControl.Target.PUBLIC)
  public void getCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
    Object[] objs = userService.getCode(IpUtils.getClientIpAddress(request));
    // 将图片输出给浏览器
    BufferedImage image = (BufferedImage) objs[1];
    response.setContentType("image/png");
    OutputStream os = response.getOutputStream();
    ImageIO.write(image, "png", os);
    response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
    response.setHeader("Access-Control-Allow-Credentials", "true");
  }

  /**
   * 用户登录
   *
   * @param loginReq
   * @param request
   * @return
   */
  @PostMapping("/login")
  @ApiOperation("登录")
  public ApiResult<LoginUserResp> login(
      @RequestBody @Validated LoginReq loginReq, HttpServletRequest request) {
    LoginUserResp resp = userService.login(loginReq, request);
    return ApiResult.success(resp);
  }
}

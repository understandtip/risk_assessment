package com.yushang.risk.assessment.controller;

import com.yushang.risk.assessment.domain.vo.request.LoginReq;
import com.yushang.risk.assessment.domain.vo.request.RegisterReq;
import com.yushang.risk.assessment.domain.vo.response.LoginUserResp;
import com.yushang.risk.assessment.service.UsersService;
import com.yushang.risk.common.domain.vo.ApiResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @Author：zlp @Package：com.yushang.risk.assessment.controller @Project：risk_assessment
 *
 * @name：UserController @Date：2023/12/21 13:30 @Filename：UserController
 */
@RestController
@RequestMapping("/api/user")
@Api(tags = "用户相关接口")
public class UserController {
  @Resource private UsersService usersService;

  // todo使用频控注解实现
  /**
   * 用户来获取随机验证码
   *
   * @param session
   * @param response
   * @return
   * @throws IOException
   */
  @GetMapping("/getCode")
  @ApiOperation("获取随机验证码(以图片的形式展示)")
  public void getCode(HttpSession session, HttpServletResponse response) throws IOException {
    Object[] objs = usersService.getCode(session.getId());
    // 将图片输出给浏览器
    BufferedImage image = (BufferedImage) objs[1];
    response.setContentType("image/png");
    OutputStream os = response.getOutputStream();
    ImageIO.write(image, "png", os);
  }

  /**
   * 用户注册
   *
   * @param registerReq
   * @param session
   * @return
   */
  @PostMapping("/register")
  @ApiOperation("注册")
  public ApiResult<Void> register(
      @RequestBody @Validated RegisterReq registerReq, HttpSession session) {
    usersService.register(registerReq, session);
    return ApiResult.success();
  }

  /**
   * 用户登录
   *
   * @param loginReq
   * @param session
   * @return
   */
  @PostMapping("/login")
  @ApiOperation("登录")
  public ApiResult<LoginUserResp> login(
      @RequestBody @Validated LoginReq loginReq, HttpSession session) {
    LoginUserResp resp = usersService.login(loginReq, session);
    return ApiResult.success(resp);
  }
}

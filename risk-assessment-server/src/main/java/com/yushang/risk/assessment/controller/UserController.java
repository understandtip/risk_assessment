package com.yushang.risk.assessment.controller;

import com.yushang.risk.assessment.domain.vo.request.LoginReq;
import com.yushang.risk.assessment.domain.vo.request.RegisterReq;
import com.yushang.risk.assessment.domain.vo.request.UpdatePassReq;
import com.yushang.risk.assessment.domain.vo.request.UserReq;
import com.yushang.risk.assessment.domain.vo.response.LoginUserResp;
import com.yushang.risk.assessment.domain.vo.response.UserResp;
import com.yushang.risk.assessment.service.UsersService;
import com.yushang.risk.common.annotation.FrequencyControl;
import com.yushang.risk.common.domain.vo.ApiResult;
import com.yushang.risk.common.util.IpUtils;
import com.yushang.risk.common.util.RequestHolder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
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
 * @Author：zlp @Package：com.yushang.risk.assessment.controller @Project：risk_assessment
 *
 * @name：UserController @Date：2023/12/21 13:30 @Filename：UserController
 */
@RestController
@RequestMapping("/api/user")
@Api(tags = "用户相关接口")
@Slf4j
@CrossOrigin(originPatterns = "*", allowCredentials = "true")
public class UserController {
  @Resource private UsersService usersService;

  /**
   * 用户来获取随机验证码
   *
   * @param response
   * @return
   * @throws IOException
   */
  @GetMapping("/getCode")
  @ApiOperation("获取随机验证码(以图片的形式展示)")
  @FrequencyControl(time = 3, count = 3, target = FrequencyControl.Target.PUBLIC)
  public void getCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
    Object[] objs = usersService.getCode(IpUtils.getClientIpAddress(request));
    // 将图片输出给浏览器
    BufferedImage image = (BufferedImage) objs[1];
    response.setContentType("image/png");
    OutputStream os = response.getOutputStream();
    ImageIO.write(image, "png", os);
    response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
    response.setHeader("Access-Control-Allow-Credentials", "true");
  }

  /**
   * 用户注册
   *
   * @param registerReq
   * @return
   */
  @PostMapping("/register")
  @ApiOperation("注册")
  public ApiResult<Void> register(
      @RequestBody @Validated RegisterReq registerReq, HttpServletRequest request) {
    usersService.register(registerReq, request);
    return ApiResult.success();
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
    LoginUserResp resp = usersService.login(loginReq, request);
    return ApiResult.success(resp);
  }

  /**
   * 用户退出
   *
   * @return
   */
  @PutMapping("/exit")
  @ApiOperation("用户退出登录")
  public ApiResult<Void> exit() {
    usersService.exit();
    return ApiResult.success();
  }

  /**
   * 获取用户信息
   *
   * @return
   */
  @GetMapping("/getUserInfo")
  @ApiOperation("获取用户信息")
  public ApiResult<UserResp> getUserInfo() {
    Integer uid = RequestHolder.get().getUid();
    UserResp userResp = usersService.getUserInfo(uid);
    return ApiResult.success(userResp);
  }

  /**
   * 修改密码
   *
   * @param passReq
   * @return
   */
  @PutMapping("/updatePassword")
  @ApiOperation("修改密码")
  public ApiResult<Void> updatePassword(
      @RequestBody @Validated UpdatePassReq passReq, HttpServletRequest request) {
    usersService.updatePassword(passReq, request);
    return ApiResult.success();
  }

  /**
   * 修改用户个人信息
   *
   * @param userReq
   * @return
   */
  @PutMapping("/changeInfo")
  @ApiOperation("修改用户个人信息")
  public ApiResult<Void> changeInfo(@RequestBody UserReq userReq) {
    usersService.changeInfo(userReq);
    return ApiResult.success();
  }
}

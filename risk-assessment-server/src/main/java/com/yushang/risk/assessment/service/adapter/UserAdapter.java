package com.yushang.risk.assessment.service.adapter;

import cn.hutool.core.lang.TypeReference;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.yushang.risk.assessment.domain.entity.IpDetail;
import com.yushang.risk.assessment.domain.entity.IpResult;
import com.yushang.risk.common.util.IpUtils;
import com.yushang.risk.common.util.RequestHolder;
import com.yushang.risk.domain.entity.OnlineUser;
import com.yushang.risk.domain.entity.SysLoginLog;
import com.yushang.risk.domain.entity.User;
import com.yushang.risk.assessment.domain.vo.request.RegisterReq;
import com.yushang.risk.assessment.domain.vo.response.LoginUserResp;
import com.yushang.risk.domain.enums.LoginLogTypeEnum;
import com.yushang.risk.utils.RequestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.runtime.parser.node.ASTElseIfStatement;

import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.time.LocalDateTime;

/**
 * @Author：zlp @Package：com.yushang.risk.assessment.service.adapter @Project：risk_assessment
 *
 * @name：UserAdapter @Date：2023/12/21 15:12 @Filename：UserAdapter
 */
public class UserAdapter {

  /**
   * 构建注册时User保存对象
   *
   * @param registerReq
   * @param newPassword
   * @param myInvitationCode
   * @return
   */
  public static User buildSaveUser(
      RegisterReq registerReq, String newPassword, String myInvitationCode) {
    return User.builder()
        .username(registerReq.getUserName())
        .password(newPassword)
        .useCode(registerReq.getUseCode())
        .invitationCode(myInvitationCode)
        .build();
  }

  /**
   * 构建登录成功后返回对象
   *
   * @param user
   * @return
   */
  public static LoginUserResp buildLoginUserResp(User user, String token) {
    return LoginUserResp.builder()
        .id(user.getId())
        .username(user.getUsername())
        .realName(user.getRealName())
        .phone(user.getPhone())
        .email(user.getEmail())
        .loginTime(LocalDateTime.now())
        .invitationCode(user.getInvitationCode())
        .useCode(user.getUseCode())
        .token(token)
        .build();
  }

  /**
   * 构建前台系统登录时日志对象
   *
   * @param request
   * @param user
   * @param flag
   * @return
   */
  public static SysLoginLog buildLoginLog(HttpServletRequest request, User user, boolean flag) {
    // 操作系统
    String operatingSystem = getOperatingSystem(request);
    // 浏览器
    String browserName = getBrowserName(request);
    // 处理ip
    String ip;
    if (StringUtils.isNotBlank(RequestHolder.get().getIp())) {
      ip = RequestHolder.get().getIp();
    } else {
      ip = IpUtils.getClientIpAddress(request);
    }
    String add = null;
    for (int i = 0; i < 3; i++) {
      IpDetail ipDetail = getIpDetailOrNull(ip);
      if (ipDetail != null) {
        add =
            ipDetail.getCountry()
                + " "
                + ipDetail.getRegion()
                + " "
                + ipDetail.getCity()
                + " "
                + ipDetail.getIsp();
        break;
      }
    }

    return SysLoginLog.builder()
        .username(user.getUsername())
        .ip(ip)
        .address(add)
        .browser(browserName)
        .os(operatingSystem)
        .platformType(String.valueOf(LoginLogTypeEnum.FRONT.getType()))
        .state(String.valueOf(flag ? 1 : 0))
        .build();
  }

  /**
   * 获取ip归属地
   *
   * @param ip
   * @return
   */
  public static IpDetail getIpDetailOrNull(String ip) {
    if (StringUtils.isEmpty(ip)) return null;
    String body =
        HttpUtil.get("https://ip.taobao.com/outGetIpInfo?ip=" + ip + "&accessKey=alibaba-inc");
    try {
      IpResult<IpDetail> result =
          JSONUtil.toBean(body, new TypeReference<IpResult<IpDetail>>() {}, false);
      if (result.isSuccess()) {
        return result.getData();
      }
    } catch (Exception ignored) {
    }
    return null;
  }

  /**
   * 获取浏览器名称
   *
   * @param request
   * @return
   */
  public static String getBrowserName(HttpServletRequest request) {
    String userAgent = request.getHeader("User-Agent");

    if (userAgent != null) {
      if (userAgent.contains("Chrome")) {
        return "Google Chrome";
      } else if (userAgent.contains("Firefox")) {
        return "Mozilla Firefox";
      } else if (userAgent.contains("Safari")) {
        return "Apple Safari";
      } else if (userAgent.contains("Edge")) {
        return "Microsoft Edge";
      } else if (userAgent.contains("Opera")) {
        return "Opera";
      } else if (userAgent.contains("MSIE") || userAgent.contains("Trident/")) {
        return "Internet Explorer";
      } else if (userAgent.contains("Postman")) {
        return "Postman";
      } else {
        return "未知浏览器";
      }
    }
    return "未知浏览器";
  }

  /**
   * 获取操作系统名称
   *
   * @param request
   * @return
   */
  public static String getOperatingSystem(HttpServletRequest request) {
    String userAgent = request.getHeader("User-Agent");

    if (userAgent != null) {
      if (userAgent.contains("Windows")) {
        return "Windows";
      } else if (userAgent.contains("Mac OS X")) {
        return "Mac OS X";
      } else if (userAgent.contains("Linux")) {
        return "Linux";
      } else if (userAgent.contains("Android")) {
        return "Android";
      } else if (userAgent.contains("iOS")) {
        return "iOS";
      } else if (userAgent.contains("Postman")) {
        return "Postman";
      } else {
        return "未知操作系统";
      }
    }

    return "未知操作系统";
  }

  /**
   * 构建登录成功后的在线用户对象
   *
   * @param request
   * @param user
   * @return
   */
  public static OnlineUser buildOnlineUser(HttpServletRequest request, User user) {
    // 浏览器
    String browserName = getBrowserName(request);
    // 操作系统
    String operatingSystem = getOperatingSystem(request);
    String sessionId = request.getSession().getId();
    // 处理ip
    String ip = IpUtils.getClientIpAddress(request);
    String add = null;
    for (int i = 0; i < 3; i++) {
      IpDetail ipDetail = getIpDetailOrNull(ip);
      if (ipDetail != null) {
        add =
            ipDetail.getCountry()
                + " "
                + ipDetail.getRegion()
                + " "
                + ipDetail.getCity()
                + " "
                + ipDetail.getIsp();
        break;
      }
    }
    return OnlineUser.builder()
        .sessionId(sessionId)
        .userName(user.getUsername())
        .ip(ip)
        .address(add)
        .browser(browserName)
        .os(operatingSystem)
        .platformType(String.valueOf(LoginLogTypeEnum.FRONT.getType()))
        .build();
  }
}

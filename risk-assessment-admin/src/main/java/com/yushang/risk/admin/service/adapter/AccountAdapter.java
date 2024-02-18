package com.yushang.risk.admin.service.adapter;

import cn.hutool.core.lang.TypeReference;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.yushang.risk.admin.domain.entity.IpDetail;
import com.yushang.risk.admin.domain.entity.IpResult;
import com.yushang.risk.common.util.IpUtils;
import com.yushang.risk.domain.entity.Account;
import com.yushang.risk.admin.domain.vo.request.AccountReq;
import com.yushang.risk.admin.domain.vo.response.LoginUserResp;
import com.yushang.risk.domain.entity.SysLoginLog;
import com.yushang.risk.domain.enums.LoginLogTypeEnum;
import com.yushang.risk.utils.RequestUtils;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author：zlp @Package：com.yushang.risk.admin.service.adapter @Project：risk_assessment
 *
 * @name：AccountAdapter @Date：2024/1/30 14:49 @Filename：AccountAdapter
 */
public class AccountAdapter {

  /**
   * 构建登录成功后返回对象
   *
   * @param user
   * @param permissions
   * @return
   */
  public static LoginUserResp buildLoginUserResp(
      Account user, String token, List<String> permissions) {
    return LoginUserResp.builder()
        .id(user.getId())
        .username(user.getUsername())
        .realName(user.getRealName())
        .phone(user.getPhone())
        .email(user.getEmail())
        .loginTime(LocalDateTime.now())
        .permissionList(permissions)
        .token(token)
        .build();
  }

  /**
   * 构建添加账户对象
   *
   * @param accountReq
   * @return
   */
  public static Account buildAddAccount(AccountReq accountReq) {
    return Account.builder()
        .username(accountReq.getUsername())
        .realName(accountReq.getRealName())
        .phone(accountReq.getPhone())
        .email(accountReq.getEmail())
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
  public static SysLoginLog buildLoginLog(HttpServletRequest request, Account user, boolean flag) {
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
    // 浏览器
    String browserName = getBrowserName(request);
    // 操作系统
    String operatingSystem = getOperatingSystem(request);
    return SysLoginLog.builder()
        .username(user.getUsername())
        .ip(ip)
        .address(add)
        .browser(browserName)
        .os(operatingSystem)
        .platformType(String.valueOf(LoginLogTypeEnum.ADMIN.getType()))
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
      } else {
        return "未知操作系统";
      }
    }

    return "未知操作系统";
  }
}

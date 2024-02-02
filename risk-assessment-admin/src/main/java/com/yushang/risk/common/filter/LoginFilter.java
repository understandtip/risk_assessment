package com.yushang.risk.common.filter;

import com.yushang.risk.admin.dao.AccountDao;
import com.yushang.risk.admin.dao.RolePermissionDao;
import com.yushang.risk.admin.dao.UserRoleDao;
import com.yushang.risk.admin.domain.dto.RequestDataInfo;
import com.yushang.risk.admin.domain.dto.SecurityUser;
import com.yushang.risk.domain.entity.Account;
import com.yushang.risk.domain.entity.Role;
import com.yushang.risk.admin.domain.enums.HttpErrorEnum;
import com.yushang.risk.admin.domain.enums.UserRoleEnum;
import com.yushang.risk.admin.service.LoginService;
import com.yushang.risk.common.util.AssertUtils;
import com.yushang.risk.common.util.IpUtils;
import com.yushang.risk.common.util.RequestHolder;
import com.yushang.risk.domain.entity.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @Author：zlp @Package：com.yushang.risk.common.filter @Project：risk_assessment
 *
 * @name：LoginFilter @Date：2024/1/26 14:39 @Filename：LoginFilter
 */
@Component
public class LoginFilter extends OncePerRequestFilter {
  @Resource private LoginService loginService;
  @Resource private AccountDao accountDao;
  @Resource private UserRoleDao userRoleDao;
  @Resource private RolePermissionDao rolePermissionDao;
  /** token在请求头中对应的key */
  public static final String AUTHORIZATION = "Authorization";

  public static final String AUTHORIZATION_SCHAME = "yushang";

  /**
   * 登录--认证过滤器--缓存重灾区
   *
   * @param request
   * @param response
   * @param filterChain
   * @throws ServletException
   * @throws IOException
   */
  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    if (isPublicUri(request.getRequestURI())) {
      filterChain.doFilter(request, response);
      return;
    }
    Integer uid = this.parseToken(request);
    if (uid == null) {
      // 未登录
      HttpErrorEnum.ACCESS_DENIED.sendHttpError(response);
      return;
    }
    //   校验用户角色信息,是否是管理员
    Account user = accountDao.getById(uid);
    AssertUtils.isNotEmpty(user, "用户不存在");
    Role role = userRoleDao.getRoleByUserId(uid);
    AssertUtils.assertNotNull(role, "该账户无权限");
    if (role.getId().equals(UserRoleEnum.USER.getCode())) return;
    // TODO 权限做缓存
    // 拿权限
    List<String> permissions = rolePermissionDao.getByRoleId(role.getId());
    String ip = IpUtils.getClientIpAddress(request);
    // token正确
    RequestDataInfo info = new RequestDataInfo();
    info.setUid(uid);
    info.setIp(ip);
    RequestHolder.set(info);
    // 往SecurityContextHolder放用户认证数据
    SecurityUser securityUser = new SecurityUser();
    securityUser.setUser(Account.builder().username(user.getUsername()).build());
    securityUser.setPermissions(permissions);
    UsernamePasswordAuthenticationToken authenticationToken =
        new UsernamePasswordAuthenticationToken(securityUser, null, securityUser.getAuthorities());
    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    filterChain.doFilter(request, response);
    // 请求处理完了,释放ThreadLocal
    RequestHolder.remove();
  }

  /**
   * 是否是公开资源
   *
   * @param pathInfo
   * @return
   */
  private boolean isPublicUri(String pathInfo) {
    List<String> path =
        Arrays.asList(
            "capi/user/getCode",
            "capi/acc/login",
            "login",
            "doc.html",
            "webjars",
            "swagger-resources",
            "api-docs",
            "swagger");
    for (String p : path) {
      if (pathInfo.contains(p)) {
        return true;
      }
    }
    return false;
  }

  /**
   * 从请求中获取token
   *
   * @param request
   * @return
   */
  private Integer parseToken(HttpServletRequest request) {
    String authorization = request.getHeader(AUTHORIZATION);
    if (StringUtils.isEmpty(authorization)) return null;
    // 使用空格作为分隔符进行分割
    String[] split = authorization.split("\\s+");
    if (!split[0].equals(AUTHORIZATION_SCHAME) || StringUtils.isEmpty(split[1])) return null;
    return loginService.getValidUid(split[1]);
  }
}

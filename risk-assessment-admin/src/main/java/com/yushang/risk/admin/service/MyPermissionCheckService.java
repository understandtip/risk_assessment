package com.yushang.risk.admin.service;

import com.yushang.risk.admin.domain.dto.SecurityUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 自定义权限实现，ss取自SpringSecurity首字母
 *
 * @author ROCKY
 */
@Service("ss")
public class MyPermissionCheckService {

  /** 管理员角色权限标识 */
  private static final String SUPER_ADMIN = "sys";

  private static final String ROLE_DELIMETER = ",";
  private static final String PERMISSION_DELIMETER = ",";

  /**
   * 验证用户是否具备某权限
   *
   * @param permission 权限字符串
   * @return 用户是否具备某权限
   */
  public boolean hasPermi(String permission) {
    if (StringUtils.isEmpty(permission)) {
      return false;
    }
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    SecurityUser principal = (SecurityUser) authentication.getPrincipal();

    if (CollectionUtils.isEmpty(principal.getPermissions())) {
      return false;
    }
    return hasPermissions(principal.getPermissions(), permission);
  }

  /**
   * 判断是否包含权限
   *
   * @param permissions 权限列表
   * @param permission 权限字符串
   * @return 用户是否具备某权限
   */
  private boolean hasPermissions(List<String> permissions, String permission) {
    return permission.equals(SUPER_ADMIN) || permissions.contains(StringUtils.trim(permission));
  }
}

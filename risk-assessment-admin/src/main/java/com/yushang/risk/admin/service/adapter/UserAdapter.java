package com.yushang.risk.admin.service.adapter;

import cn.hutool.core.lang.TypeReference;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.yushang.risk.admin.domain.entity.IpDetail;
import com.yushang.risk.admin.domain.entity.IpResult;
import com.yushang.risk.admin.domain.entity.UserRole;
import com.yushang.risk.admin.domain.vo.request.UserReq;
import com.yushang.risk.admin.domain.vo.response.LoginUserResp;
import com.yushang.risk.common.util.IpUtils;
import com.yushang.risk.domain.entity.SysLoginLog;
import com.yushang.risk.domain.entity.User;
import com.yushang.risk.domain.enums.LoginLogTypeEnum;
import com.yushang.risk.utils.RequestUtils;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * @Author：zlp @Package：com.yushang.risk.admin.service.adapter @Project：risk_assessment
 *
 * @name：UserAdapter @Date：2024/1/11 14:38 @Filename：UserAdapter
 */
public class UserAdapter {
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
        .token(token)
        .build();
  }

  /**
   * 构建添加用户对象
   *
   * @param userReq
   * @return
   */
  public static User buildAddUser(UserReq userReq) {

    return User.builder()
        .username(userReq.getUsername())
        .realName(userReq.getRealName())
        .phone(userReq.getPhone())
        .email(userReq.getEmail())
        .build();
  }

  /**
   * 构建添加账户时构建的UserRole
   *
   * @param uid
   * @param roleId
   * @return
   */
  public static UserRole buildUserRole(Integer uid, Integer roleId) {
    UserRole userRole = new UserRole();
    userRole.setRoleId(roleId);
    userRole.setUserId(uid);
    return userRole;
  }
  
}

package com.yushang.risk.admin.domain.vo.response;

import lombok.Builder;
import lombok.Data;

/**
 * @Author：zlp @Package：com.yushang.risk.admin.domain.vo.response @Project：risk_assessment
 *
 * @name：UserAddResp @Date：2024/1/23 15:43 @Filename：UserAddResp
 */
@Data
@Builder
public class UserAddResp {
  private String userName;
  private String password;
}

package com.yushang.risk.admin.domain.vo.response;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author：zlp @Package：com.yushang.risk.admin.domain.vo.response @Project：risk_assessment
 *
 * @name：AccountAddResp @Date：2024/1/31 15:34 @Filename：AccountAddResp
 */
@Data
@Builder
public class AccountAddResp {
  private String username;
  private String password;
}

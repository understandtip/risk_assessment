package com.yushang.risk.assessment.domain.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

/**
 * @Author：zlp @Package：com.yushang.risk.assessment.domain.vo.request @Project：risk_assessment
 *
 * @name：SecurityServiceBugBugReq @Date：2024/1/15 16:14 @Filename：SecurityServiceBugBugReq
 */
@Data
public class SecurityServiceBugBugReq {
  @ApiModelProperty("安全服务id")
  @NotNull
  private Integer id;

  @NotNull private String name;

  @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式错误")
  private String phone;

  @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "邮箱格式错误")
  private String email;

  @NotNull
  @ApiModelProperty("提交的漏洞id集合")
  private List<Integer> bugIds;

  private List<AddBug> addBugs;

  @Data
  public static class AddBug {
    /** 漏洞名称 */
    @ApiModelProperty(notes = "漏洞名称")
    private String name;
    /** 漏洞原理 */
    @ApiModelProperty(notes = "漏洞原理")
    private String theory;
    /** 危害 */
    @ApiModelProperty(notes = "危害")
    private String harm;
  }
}

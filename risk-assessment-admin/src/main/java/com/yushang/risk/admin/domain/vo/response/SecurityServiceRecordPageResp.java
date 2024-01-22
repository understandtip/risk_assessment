package com.yushang.risk.admin.domain.vo.response;

import com.baomidou.mybatisplus.annotation.TableId;
import com.yushang.risk.domain.entity.SBug;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author：zlp @Package：com.yushang.risk.admin.domain.vo.response @Project：risk_assessment
 *
 * @name：SecurityServiceRecordPageResp @Date：2024/1/16 14:09 @Filename：SecurityServiceRecordPageResp
 */
@Data
public class SecurityServiceRecordPageResp {
  private Integer id;

  private String name;

  private String phone;

  private String email;

  private String serviceName;

  @ApiModelProperty("生成报告名称")
  private String portName;

  @ApiModelProperty("漏洞集合")
  private List<BugResp> bugList;

  @Data
  public static class BugResp {
    /** 主键 */
    @ApiModelProperty(notes = "主键")
    private Integer id;
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

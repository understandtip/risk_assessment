package com.yushang.risk.admin.domain.vo.response;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author：zlp @Package：com.yushang.risk.admin.domain.vo.response @Project：risk_assessment
 *
 * @name：PermissionResp @Date：2024/1/29 14:18 @Filename：PermissionResp
 */
@Data
public class PermissionResp {
  /** 主键id */
  @ApiModelProperty(notes = "主键id")
  private Integer id;
  /** 标题 */
  @ApiModelProperty(notes = "标题")
  private String title;
  /** 父id */
  @ApiModelProperty(notes = "父id")
  private Integer pid;
  /** 权限 */
  @ApiModelProperty(notes = "权限")
  private String permission;

  @ApiModelProperty("子权限信息")
  private List<PermissionResp> childPer;
}

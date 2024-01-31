package com.yushang.risk.admin.domain.vo.response;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author：zlp @Package：com.yushang.risk.admin.domain.vo.response @Project：risk_assessment
 *
 * @name：RoleResp @Date：2024/1/29 15:52 @Filename：RoleResp
 */
@Data
public class RoleResp {
  /** 主键id */
  @ApiModelProperty(notes = "主键id")
  private Integer id;
  /** 角色名称 */
  @ApiModelProperty(notes = "角色名称")
  private String name;

  @ApiModelProperty(notes = "角色状态(0:禁用  1:可用)")
  private Integer state;
  /** 创建时间 */
  @ApiModelProperty(notes = "创建时间")
  private LocalDateTime createdTime;
  /** 更新时间 */
  @ApiModelProperty(notes = "更新时间")
  private LocalDateTime updatedTime;
}

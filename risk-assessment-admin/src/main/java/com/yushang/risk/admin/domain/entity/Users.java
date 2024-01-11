package com.yushang.risk.admin.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户
 * </p>
 *
 * @author zlp
 * @since 2024-01-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("users")
public class Users implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户名
     */
    @TableField("username")
    private String username;

    /**
     * 密码
     */
    @TableField("password")
    private String password;

    /**
     * 用户真实姓名
     */
    @TableField("real_name")
    private String realName;

    /**
     * 手机
     */
    @TableField("phone")
    private String phone;

    /**
     * 邮件
     */
    @TableField("email")
    private String email;

    /**
     * 账户状态;1:可用   0:账户禁用
     */
    @TableField("status")
    private String status;

    /**
     * 登录时间
     */
    @TableField("login_time")
    private LocalDateTime loginTime;

    /**
     * 邀请码
     */
    @TableField("invitation_code")
    private String invitationCode;

    /**
     * 使用的邀请码
     */
    @TableField("use_code")
    private String useCode;

    /**
     * 创建时间
     */
    @TableField("created_time")
    private LocalDateTime createdTime;

    /**
     * 更新时间
     */
    @TableField("updated_time")
    private LocalDateTime updatedTime;

    /**
     * 退出时间
     */
    @TableField("exit_time")
    private LocalDateTime exitTime;

    /**
     * 1:删除   0:未删除
     */
    @TableField("is_deleted")
    private String isDeleted;


}

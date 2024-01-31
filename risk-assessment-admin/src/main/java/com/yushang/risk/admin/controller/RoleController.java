package com.yushang.risk.admin.controller;

import com.yushang.risk.admin.domain.vo.request.RoleAllotReq;
import com.yushang.risk.admin.domain.vo.request.RoleReq;
import com.yushang.risk.admin.domain.vo.response.PermissionResp;
import com.yushang.risk.admin.domain.vo.response.RolePerResp;
import com.yushang.risk.admin.domain.vo.response.RoleResp;
import com.yushang.risk.admin.service.PermissionService;
import com.yushang.risk.admin.service.RoleService;
import com.yushang.risk.common.domain.vo.ApiResult;
import com.yushang.risk.common.exception.BusinessException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 缓存重灾区 @Author：zlp @Package：com.yushang.risk.admin.controller @Project：risk_assessment
 *
 * @name：PermissionController @Date：2024/1/29 14:11 @Filename：PermissionController
 */
@Api(tags = "角色管理接口")
@RestController
@RequestMapping("/capi/per")
public class RoleController {
  @Resource private PermissionService permissionService;
  @Resource private RoleService roleService;

  @GetMapping("/getPermissionList")
  @ApiOperation("获取权限列表")
  public ApiResult<List<PermissionResp>> getPermissionList() {
    List<PermissionResp> list = permissionService.getPermissionList();
    return ApiResult.success(list);
  }

  @GetMapping("/getRoleList")
  @ApiOperation("查询角色信息")
  public ApiResult<List<RoleResp>> getRoleList() {
    List<RoleResp> list = roleService.getRoleList();
    return ApiResult.success(list);
  }

  @PutMapping("/upRoleState")
  @ApiOperation("修改角色状态")
  public ApiResult<Void> upRoleState(Integer roleId, boolean state) {
    if (roleId == null || roleId == 0) throw new BusinessException("请先选择角色.");
    roleService.upRoleState(roleId, state);
    return ApiResult.success();
  }

  @PutMapping("/upRoleInfo")
  @ApiOperation("修改角色信息")
  public ApiResult<Void> upRoleInfo(@RequestBody @Validated RoleReq roleReq) {
    roleService.upRoleInfo(roleReq);
    return ApiResult.success();
  }

  @PutMapping("/addRoleInfo")
  @ApiOperation("添加角色信息")
  public ApiResult<Void> addRoleInfo(@RequestBody @Validated RoleReq roleReq) {
    roleService.addRoleInfo(roleReq);
    return ApiResult.success();
  }

  @PostMapping("/allotPermissionByRole")
  @ApiOperation("角色分配权限")
  public ApiResult<Void> allotPermissionByRole(@RequestBody @Validated RoleAllotReq roleAllotReq) {
    roleService.allotPermissionByRole(roleAllotReq);
    return ApiResult.success();
  }

  @GetMapping("/getPerByRole")
  @ApiOperation("查询角色权限")
  public ApiResult<RolePerResp> getPerByRole(Integer roleId) {
    RolePerResp resp = roleService.getPerByRole(roleId);
    return ApiResult.success(resp);
  }
}

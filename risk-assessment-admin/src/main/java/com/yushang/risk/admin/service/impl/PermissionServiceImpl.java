package com.yushang.risk.admin.service.impl;

import com.yushang.risk.admin.dao.PermissionDao;
import com.yushang.risk.admin.domain.vo.request.PermissionReq;
import com.yushang.risk.admin.domain.vo.response.PermissionResp;
import com.yushang.risk.admin.service.PermissionService;
import com.yushang.risk.domain.entity.Permission;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.management.GarbageCollectorMXBean;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author：zlp @Package：com.yushang.risk.admin.service.impl @Project：risk_assessment
 *
 * @name：PermissionServiceImpl @Date：2024/1/29 14:15 @Filename：PermissionServiceImpl
 */
@Service
public class PermissionServiceImpl implements PermissionService {
  @Resource private PermissionDao permissionDao;

  /**
   * 获取权限列表
   *
   * @return 返回权限列表，其中包含权限及其子权限结构
   */
  @Override
  public List<PermissionResp> getPermissionList() {
      // 从数据库获取所有权限信息
      List<Permission> list = permissionDao.list();

      // 使用Stream API将权限信息转换为响应对象，并通过Map加速查找 
      Map<Integer, PermissionResp> permissionMap =
          list.stream()
              .map(
                  p -> {
                    PermissionResp resp = new PermissionResp();
                    BeanUtils.copyProperties(p, resp); // 使用BeanUtils复制属性
                    return new AbstractMap.SimpleEntry<>(p.getId(), resp);
                  })
              .collect(
                  Collectors.toMap(
                      AbstractMap.SimpleEntry::getKey, AbstractMap.SimpleEntry::getValue));

      List<PermissionResp> finalList = new ArrayList<>();
      // 构建顶层权限及其子权限结构
      for (Permission permission : list) {
        if (permission.getPid().equals(0)) { // 判断是否为顶层权限
          PermissionResp faResp = permissionMap.get(permission.getId());
          faResp.setChildPer(buildChildPermissions(permissionMap, faResp.getId())); // 设置子权限
          finalList.add(faResp);
        }
      }
      return finalList;
  }

  /**
   * 构建子权限列表
   *
   * @param permissionMap 权限映射表，用于快速查找权限
   * @param parentId 父权限ID
   * @return 返回指定父权限下的所有子权限列表
   */
  private List<PermissionResp> buildChildPermissions(
      Map<Integer, PermissionResp> permissionMap, Integer parentId) {
    List<PermissionResp> childList = new ArrayList<>();
    // 遍历所有权限响应对象，查找子权限
    for (PermissionResp permissionResp : permissionMap.values()) {
      if (permissionResp.getPid().equals(parentId)) {
        // 递归构建子权限结构
        permissionResp.setChildPer(buildChildPermissions(permissionMap, permissionResp.getId()));
        childList.add(permissionResp);
      }
    }
    return childList;
  }
}

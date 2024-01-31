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
import java.util.ArrayList;
import java.util.List;
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
   * @return
   */
  @Override
  public List<PermissionResp> getPermissionList() {
    List<PermissionResp> finalList = new ArrayList<>();
    List<Permission> list = permissionDao.list();

    List<PermissionResp> permissionRespList =
        list.stream()
            .map(
                p -> {
                  PermissionResp resp = new PermissionResp();
                  BeanUtils.copyProperties(p, resp);
                  return resp;
                })
            .collect(Collectors.toList());

    list.forEach(
        p -> {
          if (p.getPid().equals(0)) {
            PermissionResp faResp = new PermissionResp();
            BeanUtils.copyProperties(p, faResp);
            faResp.setChildPer(
                getPermissionList(faResp.getId(), permissionRespList, new ArrayList<>()));
            finalList.add(faResp);
          }
        });

    return finalList;
  }

  private List<PermissionResp> getPermissionList(
      Integer pid, List<PermissionResp> permissionRespList, List<PermissionResp> childList) {
    permissionRespList.forEach(
        p -> {
          if (p.getPid().equals(pid)) {
            List<PermissionResp> chList = new ArrayList<>();
            p.setChildPer(getPermissionList(p.getId(), permissionRespList, chList));
            childList.add(p);
          }
        });
    return childList;
  }
}

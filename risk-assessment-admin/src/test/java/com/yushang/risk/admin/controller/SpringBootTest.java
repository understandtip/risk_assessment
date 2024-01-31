package com.yushang.risk.admin.controller;

import com.yushang.risk.admin.domain.vo.response.PermissionResp;
import com.yushang.risk.admin.service.impl.PermissionServiceImpl;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author：zlp @Package：com.yushang.risk.admin.controller @Project：risk_assessment
 *
 * @name：SpringBootTest @Date：2024/1/29 14:40 @Filename：SpringBootTest
 */
@org.springframework.boot.test.context.SpringBootTest
public class SpringBootTest {
  @Resource private PermissionServiceImpl permissionService;

  @Test
  void test01() {
    List<PermissionResp> permissionList = permissionService.getPermissionList();
    System.out.println("permissionList = " + permissionList);
  }
}

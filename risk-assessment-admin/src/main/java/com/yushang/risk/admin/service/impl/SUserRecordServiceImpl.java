package com.yushang.risk.admin.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yushang.risk.admin.dao.SBugDao;
import com.yushang.risk.admin.dao.SSecurityServiceDao;
import com.yushang.risk.admin.dao.SUserDao;
import com.yushang.risk.admin.dao.SUserRecordDao;
import com.yushang.risk.admin.domain.vo.request.PageBaseReq;
import com.yushang.risk.admin.domain.vo.request.SecurityServiceRecordPageReq;
import com.yushang.risk.admin.domain.vo.response.PageBaseResp;
import com.yushang.risk.admin.domain.vo.response.SecurityServiceRecordPageResp;
import com.yushang.risk.admin.service.SUserRecordService;
import com.yushang.risk.domain.entity.SBug;
import com.yushang.risk.domain.entity.SSecurityService;
import com.yushang.risk.domain.entity.SUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Author：zlp @Package：com.yushang.risk.admin.service.impl @Project：risk_assessment
 *
 * @name：SUserRecordServiceImpl @Date：2024/1/16 14:11 @Filename：SUserRecordServiceImpl
 */
@Service
@Slf4j
public class SUserRecordServiceImpl implements SUserRecordService {
  @Resource private SUserRecordDao sUserRecordDao;
  @Resource private SUserDao sUserDao;
  @Resource private SBugDao sBugDao;
  @Resource private SSecurityServiceDao sSecurityServiceDao;
  /**
   * 查询用户申请漏洞列表
   *
   * @param recordPageReq
   * @return
   */
  @Override
  public PageBaseResp<SecurityServiceRecordPageResp> getUserBugRecordList(
      PageBaseReq<SecurityServiceRecordPageReq> recordPageReq) {
    Page<SUser> page = recordPageReq.plusPage();
    // 先查user
    Page<SUser> res = sUserDao.getUserByPage(page, recordPageReq.getData());
    List<SUser> userList = res.getRecords();
    if (userList == null || userList.isEmpty())
      return PageBaseResp.init(res, Collections.emptyList());

    // 收集一下,便于查询
    Map<Integer, SUser> userMap =
        userList.stream().collect(Collectors.toMap(SUser::getId, Function.identity()));

    // 服务信息
    List<SSecurityService> serviceList = sSecurityServiceDao.list();

    // 再查漏洞
    List<Integer> userIds = userList.stream().map(SUser::getId).collect(Collectors.toList());
    Map<Integer, List<Integer>> userAndBugIds = sUserRecordDao.getByUserIds(userIds);

    List<SBug> bugs = sBugDao.list();
    // 收集一下,便于查询
    Map<Integer, SBug> bugMap =
        bugs.stream().collect(Collectors.toMap(SBug::getId, Function.identity()));

    List<SecurityServiceRecordPageResp> respList = new ArrayList<>();

    userAndBugIds.forEach(
        (k, v) -> {
          SecurityServiceRecordPageResp resp = new SecurityServiceRecordPageResp();
          SUser sUser = userMap.get(k);
          BeanUtils.copyProperties(sUser, resp);
          // 查服务信息
          serviceList.forEach(
              service -> {
                if (service.getId().equals(sUser.getServiceId()))
                  resp.setServiceName(service.getName());
              });

          List<SecurityServiceRecordPageResp.BugResp> bugList = new ArrayList<>();
          v.forEach(
              v0 -> {
                SBug sBug = bugMap.get(v0);
                if (sBug != null) {
                  SecurityServiceRecordPageResp.BugResp bugResp =
                      new SecurityServiceRecordPageResp.BugResp();
                  BeanUtils.copyProperties(sBug, bugResp);
                  bugList.add(bugResp);
                }
              });
          resp.setBugList(bugList);
          respList.add(resp);
        });
    return PageBaseResp.init(res, respList);
  }
}

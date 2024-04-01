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
   * @param recordPageReq 分页和查询条件请求对象，封装了用户查询漏洞列表的分页信息和条件
   * @return 返回用户申请漏洞列表的分页响应对象，包含分页信息和查询结果列表
   */
  @Override
  public PageBaseResp<SecurityServiceRecordPageResp> getUserBugRecordList(
      PageBaseReq<SecurityServiceRecordPageReq> recordPageReq) {
    // 初始化分页查询
    Page<SUser> page = recordPageReq.plusPage();
    // 查询用户信息
    Page<SUser> res = sUserDao.getUserByPage(page, recordPageReq.getData());
    List<SUser> userList = res.getRecords();
    // 若用户列表为空，则直接返回空响应
    if (userList == null || userList.isEmpty())
      return PageBaseResp.init(res, Collections.emptyList());

    // 将用户信息收集到Map中，以用户ID为键，便于后续查询
    Map<Integer, SUser> userMap =
        userList.stream().collect(Collectors.toMap(SUser::getId, Function.identity()));

    // 查询所有服务信息
    List<SSecurityService> serviceList = sSecurityServiceDao.list();

    // 根据用户ID查询关联的漏洞ID列表
    List<Integer> userIds = userList.stream().map(SUser::getId).collect(Collectors.toList());
    Map<Integer, List<Integer>> userAndBugIds = sUserRecordDao.getByUserIds(userIds);
    Map<Integer, List<String>> extraBugMap = sUserRecordDao.getExtraBugByUids(userIds);
    // 查询所有漏洞信息，并收集到Map中，以漏洞ID为键
    List<SBug> bugs = sBugDao.list();
    Map<Integer, SBug> bugMap =
        bugs.stream().collect(Collectors.toMap(SBug::getId, Function.identity()));

    // 处理查询结果，转换为响应对象列表
    List<SecurityServiceRecordPageResp> respList = new ArrayList<>();

    // 遍历用户和其关联的漏洞，构建响应对象
    userAndBugIds.forEach(
        (uid, bugIdList) -> {
          SecurityServiceRecordPageResp resp = new SecurityServiceRecordPageResp();
          SUser sUser = userMap.get(uid);
          BeanUtils.copyProperties(sUser, resp);
          // 为响应对象设置服务名称
          serviceList.forEach(
              service -> {
                if (service.getId().equals(sUser.getServiceId()))
                  resp.setServiceName(service.getName());
              });

          // 构建漏洞响应列表
          List<SecurityServiceRecordPageResp.BugResp> bugList = new ArrayList<>();
          bugIdList.forEach(
              bugId -> {
                SBug sBug = bugMap.get(bugId);
                if (sBug != null) {
                  SecurityServiceRecordPageResp.BugResp bugResp =
                      new SecurityServiceRecordPageResp.BugResp();
                  BeanUtils.copyProperties(sBug, bugResp);
                  // 用户自定义的漏洞信息
                  bugResp.setExtraBug(extraBugMap.get(uid).get(0));
                  bugList.add(bugResp);
                }
              });
          resp.setBugList(bugList);
          respList.add(resp);
        });
    // 构建并返回分页响应对象
    return PageBaseResp.init(res, respList);
  }
}

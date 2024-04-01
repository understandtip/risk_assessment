package com.yushang.risk.admin.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yushang.risk.admin.dao.AccountDao;
import com.yushang.risk.admin.dao.RegisterApplyDao;
import com.yushang.risk.admin.dao.UsersDao;
import com.yushang.risk.admin.domain.enums.ApplyStateEnum;
import com.yushang.risk.admin.domain.vo.request.ApplyPageReq;
import com.yushang.risk.admin.domain.vo.request.PageBaseReq;
import com.yushang.risk.admin.domain.vo.response.ApplyPageResp;
import com.yushang.risk.admin.domain.vo.response.PageBaseResp;
import com.yushang.risk.admin.service.RegisterApplyService;
import com.yushang.risk.common.exception.BusinessException;
import com.yushang.risk.common.util.RequestHolder;
import com.yushang.risk.domain.entity.Account;
import com.yushang.risk.domain.entity.RegisterApply;
import com.yushang.risk.domain.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author：zlp @Package：com.yushang.risk.admin.service.impl @Project：risk_assessment
 *
 * @name：RegisterApplyService @Date：2024/2/1 14:47 @Filename：RegisterApplyService
 */
@Service
@Slf4j
public class RegisterApplyServiceImpl implements RegisterApplyService {
  @Resource private RegisterApplyDao registerApplyDao;
  @Resource private AccountDao accountDao;
  @Resource private UsersDao usersDao;
  /**
   * 根据条件分页查询申请信息。
   *
   * @param applyReq 包含分页信息和查询条件的请求对象。
   * @return 返回分页响应对象，其中包含查询到的申请信息列表。
   */
  @Override
  public PageBaseResp<ApplyPageResp> getAllApplyByPage(PageBaseReq<ApplyPageReq> applyReq) {
    // 初始化分页对象
    Page<RegisterApply> page = applyReq.plusPage();
    ApplyPageReq applyReqData = applyReq.getData();

    // 获取账户名称与ID的映射关系
    Map<Integer, String> accountMap = getAccountMap(applyReqData);

    // 尝试查询分页数据，失败则返回空列表或错误信息
    Page<RegisterApply> applyPage;
    try {
      applyPage = registerApplyDao.getAllApplyByPage(page, applyReqData, accountMap.keySet());
    } catch (Exception e) {
      log.error("获取分页数据失败", e);
      return PageBaseResp.init(page, Collections.emptyList());
    }

    // 处理查询结果，转换为响应对象列表
    List<ApplyPageResp> collect =
        applyPage.getRecords().stream()
            .map(
                registerApply -> {
                  ApplyPageResp resp = new ApplyPageResp();
                  BeanUtils.copyProperties(registerApply, resp);
                  // 如果账户映射存在，设置申请名称
                  if (accountMap.containsKey(registerApply.getApplyId())) {
                    resp.setApplyName(accountMap.get(registerApply.getApplyId()));
                  }
                  return resp;
                })
            .collect(Collectors.toList());

    return PageBaseResp.init(applyPage, collect);
  }

  /**
   * 根据申请名称查询或获取所有账户的名称与ID的映射关系。
   *
   * @param applyReqData 包含申请名称的请求数据对象，如果为空或名称不明确，则查询所有账户。
   * @return 返回账户ID与真实名称的映射关系Map。若查询失败，返回空Map。
   */
  private Map<Integer, String> getAccountMap(ApplyPageReq applyReqData) {
    if (applyReqData != null && StringUtils.isNotBlank(applyReqData.getApplyName())) {
      try {
        // 根据申请名称查询账户
        List<Account> accountList = accountDao.getAccListByName(applyReqData.getApplyName());
        return accountList.stream().collect(Collectors.toMap(Account::getId, Account::getRealName));
      } catch (Exception e) {
        log.error("查询账户信息失败", e);
        return Collections.emptyMap();
      }
    } else {
      try {
        // 获取所有账户
        List<Account> allAccountList = accountDao.list();
        return allAccountList.stream()
            .collect(Collectors.toMap(Account::getId, Account::getRealName));
      } catch (Exception e) {
        log.error("获取所有账户信息失败", e);
        return Collections.emptyMap();
      }
    }
  }

  /**
   * 修改注册申请的状态。
   *
   * @param applyId 申请的ID，用于标识需要修改状态的申请。
   * @param state 新的状态值，表示申请的最新状态。
   * @throws BusinessException 如果申请状态已经不是等待状态（即已被批准或拒绝），则抛出业务异常。
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public void upApplyState(Integer applyId, Integer state) {
    // 根据申请ID获取注册申请信息
    RegisterApply byId = registerApplyDao.getById(applyId);
    // 如果申请状态不是等待状态，则抛出异常
    if (!byId.getState().equals(ApplyStateEnum.WAIT.getState()))
      throw new BusinessException("改申请已被批准/拒绝");

    // 准备更新申请状态的信息
    RegisterApply apply = new RegisterApply();
    // 设置申请ID
    apply.setId(applyId);
    // 设置新的状态
    apply.setState(state);
    // 设置操作人的ID
    apply.setApplyId(RequestHolder.get().getUid());
    // 更新申请状态
    registerApplyDao.updateById(apply);

    // 如果新状态是批准状态，则将申请信息保存到user表中
    if (state.equals(ApplyStateEnum.APPROVE.getState())) {
      // 创建用户对象并复制申请信息到用户对象中，准备保存到user表
      User user = new User();
      BeanUtils.copyProperties(byId, user);
      usersDao.save(user);
    }
  }

  /**
   * 删除记录
   *
   * @param appIds
   */
  @Override
  public void removeApply(List<Integer> appIds) {
    registerApplyDao.removeByIds(appIds);
  }
}

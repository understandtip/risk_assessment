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
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
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
public class RegisterApplyServiceImpl implements RegisterApplyService {
  @Resource private RegisterApplyDao registerApplyDao;
  @Resource private AccountDao accountDao;
  @Resource private UsersDao usersDao;
  /**
   * 查询申请
   *
   * @param applyReq
   * @return
   */
  @Override
  public PageBaseResp<ApplyPageResp> getAllApplyByPage(PageBaseReq<ApplyPageReq> applyReq) {
    Page<RegisterApply> page = applyReq.plusPage();
    ApplyPageReq applyReqData = applyReq.getData();
    Set<Integer> ids = null;
    Map<Integer, String> map;
    if (applyReqData != null && StringUtils.isNotBlank(applyReqData.getApplyName())) {
      // 查批准人信息
      List<Account> accountList = accountDao.getAccListByName(applyReqData.getApplyName());
      map = accountList.stream().collect(Collectors.toMap(Account::getId, Account::getRealName));
      ids = map.keySet();
    } else {
      map =
          accountDao.list().stream()
              .collect(Collectors.toMap(Account::getId, Account::getRealName));
    }
    // 获取分页数据
    Page<RegisterApply> applyPage = registerApplyDao.getAllApplyByPage(page, applyReqData, ids);

    List<ApplyPageResp> collect =
        applyPage.getRecords().stream()
            .map(
                registerApply -> {
                  ApplyPageResp resp = new ApplyPageResp();
                  BeanUtils.copyProperties(registerApply, resp);
                  if (map.get(registerApply.getApplyId()) != null) {
                    resp.setApplyName(map.get(registerApply.getApplyId()));
                  }
                  return resp;
                })
            .collect(Collectors.toList());

    return PageBaseResp.init(applyPage, collect);
  }

  /**
   * 修改状态
   *
   * @param applyId
   * @param state
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public void upApplyState(Integer applyId, Integer state) {
    RegisterApply byId = registerApplyDao.getById(applyId);
    if (!byId.getState().equals(ApplyStateEnum.WAIT.getState()))
      throw new BusinessException("改申请已被批准/拒绝");
    RegisterApply apply = new RegisterApply();
    apply.setId(applyId);
    apply.setState(state);
    apply.setApplyId(RequestHolder.get().getUid());
    registerApplyDao.updateById(apply);
    if (state.equals(ApplyStateEnum.APPROVE.getState())) {
      // 存入user表
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

package com.yushang.risk.admin.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yushang.risk.admin.domain.enums.ApplyStateEnum;
import com.yushang.risk.admin.domain.vo.request.ApplyPageReq;
import com.yushang.risk.admin.mapper.RegisterApplyMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yushang.risk.domain.entity.RegisterApply;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * 注册申请 服务实现类
 *
 * @author zlp
 * @since 2024-02-01
 */
@Service
public class RegisterApplyDao extends ServiceImpl<RegisterApplyMapper, RegisterApply> {
  /**
   * 分页获取申请记录
   *
   * @param page
   * @param applyReq
   * @param ids
   * @return
   */
  public Page<RegisterApply> getAllApplyByPage(
      Page<RegisterApply> page, ApplyPageReq applyReq, Set<Integer> ids) {
    LambdaQueryWrapper<RegisterApply> wrapper = new LambdaQueryWrapper<>();
    if (applyReq != null) {
      wrapper.like(
          StringUtils.isNotBlank(applyReq.getUsername()),
          RegisterApply::getUsername,
          applyReq.getApplyName());
      wrapper.like(
          StringUtils.isNotBlank(applyReq.getPhone()),
          RegisterApply::getPhone,
          applyReq.getPhone());
      wrapper.like(
          StringUtils.isNotBlank(applyReq.getEmail()),
          RegisterApply::getEmail,
          applyReq.getEmail());
      wrapper.like(
          StringUtils.isNotBlank(applyReq.getRealName()),
          RegisterApply::getRealName,
          applyReq.getRealName());
      wrapper.eq(
          ApplyStateEnum.isInside(applyReq.getState()),
          RegisterApply::getState,
          applyReq.getState());
    }
    wrapper.in(ids != null && !ids.isEmpty(), RegisterApply::getApplyId, ids);

    return this.page(page, wrapper);
  }
}

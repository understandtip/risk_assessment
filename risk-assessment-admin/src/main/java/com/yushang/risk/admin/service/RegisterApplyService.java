package com.yushang.risk.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yushang.risk.admin.domain.vo.request.ApplyPageReq;
import com.yushang.risk.admin.domain.vo.request.PageBaseReq;
import com.yushang.risk.admin.domain.vo.response.ApplyPageResp;
import com.yushang.risk.admin.domain.vo.response.PageBaseResp;
import com.yushang.risk.domain.entity.RegisterApply;

import java.util.List;

/**
 * 注册申请 服务类
 *
 * @author zlp
 * @since 2024-02-01
 */
public interface RegisterApplyService {
  /**
   * 查询申请
   *
   * @param applyReq
   * @return
   */
  PageBaseResp<ApplyPageResp> getAllApplyByPage(PageBaseReq<ApplyPageReq> applyReq);

  /**
   * 修改状态
   *
   * @param applyId
   * @param state
   */
  void upApplyState(Integer applyId, Integer state);

  /**
   * 删除记录
   *
   * @param appIds
   */
  void removeApply(List<Integer> appIds);
}

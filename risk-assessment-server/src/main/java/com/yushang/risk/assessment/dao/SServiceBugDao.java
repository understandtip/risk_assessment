package com.yushang.risk.assessment.dao;

import com.yushang.risk.domain.entity.SServiceBug;
import com.yushang.risk.assessment.domain.vo.response.SecurityModelDetailResp;
import com.yushang.risk.assessment.mapper.SServiceBugMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 服务漏洞 服务实现类
 *
 * @author zlp
 * @since 2024-01-15
 */
@Service
public class SServiceBugDao extends ServiceImpl<SServiceBugMapper, SServiceBug> {
  @Resource private SBugDao sBugDao;
  /**
   * 根据模块id获取漏洞信息
   *
   * @param modelId
   * @return
   */
  public List<SecurityModelDetailResp.BugInfo> getBugInfoListByModelId(Integer modelId) {
    List<Integer> bugIds =
        this.lambdaQuery().eq(SServiceBug::getServiceId, modelId).list().stream()
            .map(SServiceBug::getBugId)
            .collect(Collectors.toList());

    if (bugIds.isEmpty()) return Collections.emptyList();

    return sBugDao.listByIds(bugIds).stream()
        .map(
            bug -> {
              SecurityModelDetailResp.BugInfo bugInfo = new SecurityModelDetailResp.BugInfo();
              BeanUtils.copyProperties(bug, bugInfo);
              return bugInfo;
            })
        .collect(Collectors.toList());
  }
}

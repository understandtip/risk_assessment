package com.yushang.risk.assessment.dao;

import com.yushang.risk.domain.entity.SBugOpt;
import com.yushang.risk.domain.entity.SServiceBugOpt;
import com.yushang.risk.assessment.mapper.SServiceBugOptMapper;
import com.yushang.risk.assessment.service.SServiceBugOptService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 安全服务漏洞选择 服务实现类
 *
 * @author zlp
 * @since 2024-01-18
 */
@Service
public class SServiceBugOptDao extends ServiceImpl<SServiceBugOptMapper, SServiceBugOpt> {
  @Resource private SBugOptDao sBugOptDao;
  /**
   * 根据服务id查询漏洞选择
   *
   * @param serviceId
   * @return
   */
  public List<SBugOpt> getByServiceId(Integer serviceId) {
    List<Integer> optIds =
        this.lambdaQuery().eq(SServiceBugOpt::getServiceId, serviceId).list().stream()
            .map(SServiceBugOpt::getBugOptId)
            .collect(Collectors.toList());
    return sBugOptDao.listByIds(optIds);
  }
}

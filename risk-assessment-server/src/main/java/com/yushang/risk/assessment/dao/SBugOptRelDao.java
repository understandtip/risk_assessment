package com.yushang.risk.assessment.dao;

import com.yushang.risk.assessment.domain.entity.SBugOptRel;
import com.yushang.risk.assessment.mapper.SBugOptRelMapper;
import com.yushang.risk.assessment.service.SBugOptRelService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yushang.risk.domain.entity.SBugOpt;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 漏洞报价关联 服务实现类
 *
 * @author zlp
 * @since 2024-01-31
 */
@Service
public class SBugOptRelDao extends ServiceImpl<SBugOptRelMapper, SBugOptRel> {
  @Resource private SBugOptDao sBugOptDao;
  /**
   * 根据漏洞id集合获取报价信息
   *
   * @param ids
   * @return
   */
  public List<SBugOpt> getOptListByBugIds(List<Integer> ids) {
    List<Integer> optIds =
        this.lambdaQuery()
            .in(ids != null && !ids.isEmpty(), SBugOptRel::getBugId, ids)
            .list()
            .stream()
            .map(SBugOptRel::getOptId)
            .collect(Collectors.toList());
    return sBugOptDao.listByIds(optIds);
  }
}

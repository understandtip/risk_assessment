package com.yushang.risk.assessment.dao;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.yushang.risk.assessment.domain.entity.GenerateRecord;
import com.yushang.risk.assessment.mapper.GenerateRecordMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 报告记录 服务实现类
 *
 * @author zlp
 * @since 2023-12-29
 */
@Service
public class GenerateRecordDao extends ServiceImpl<GenerateRecordMapper, GenerateRecord> {
  /**
   * 根据项目id删除报告生成记录
   *
   * @param projectId
   * @return
   */
  public boolean removeByProjectId(Integer projectId) {
    LambdaQueryChainWrapper<GenerateRecord> wrapper =
        this.lambdaQuery().eq(GenerateRecord::getProjectId, projectId);
    return this.remove(wrapper);
  }
}

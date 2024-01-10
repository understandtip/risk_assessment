package com.yushang.risk.assessment.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yushang.risk.assessment.domain.entity.GenerateRecord;
import com.yushang.risk.assessment.domain.entity.User;
import com.yushang.risk.assessment.domain.vo.request.RecordPageReq;
import com.yushang.risk.assessment.mapper.GenerateRecordMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 报告记录 服务实现类
 *
 * @author zlp
 * @since 2023-12-29
 */
@Service
public class GenerateRecordDao extends ServiceImpl<GenerateRecordMapper, GenerateRecord> {
  @Resource private UsersDao usersDao;
  /**
   * 根据项目id删除报告生成记录
   *
   * @param projectId
   * @return
   */
  public boolean removeByProjectId(List<Integer> projectId) {
    Integer count = this.lambdaQuery().in(GenerateRecord::getProjectId, projectId).count();
    if (count == 0) return true;
    LambdaQueryWrapper<GenerateRecord> wrapper = Wrappers.lambdaQuery();
    wrapper.in(GenerateRecord::getProjectId, projectId);
    return this.remove(wrapper);
  }

  /**
   * 分页查询生成报告记录
   *
   * @param uid
   * @param recordPageReq
   * @return
   */
  public Page<GenerateRecord> getListByPage(Integer uid, RecordPageReq recordPageReq) {

    Page<GenerateRecord> page = new Page<>(recordPageReq.getPageNum(), recordPageReq.getPageSize());
    LambdaQueryWrapper<GenerateRecord> wrapper = new LambdaQueryWrapper<>();
    wrapper.eq(GenerateRecord::getAuthorId, uid);

    if (recordPageReq.getProjectId() != null) {
      wrapper.eq(GenerateRecord::getProjectId, recordPageReq.getProjectId());
    }

    wrapper.like(
        StringUtils.isNotBlank(recordPageReq.getName()),
        GenerateRecord::getName,
        recordPageReq.getName());

    wrapper.like(
        StringUtils.isNotBlank(recordPageReq.getProjectName()),
        GenerateRecord::getProjectName,
        recordPageReq.getProjectName());

    if (StringUtils.isNotBlank(recordPageReq.getAuthor())) {
      List<Integer> ids =
          usersDao.getNormalByRealNameLike(recordPageReq.getAuthor()).stream()
              .map(User::getId)
              .collect(Collectors.toList());
      wrapper.in(GenerateRecord::getAuthorId, ids);
    }

    wrapper.ge(
        recordPageReq.getStartTime() != null,
        GenerateRecord::getCreatedTime,
        recordPageReq.getStartTime());

    wrapper.le(
        recordPageReq.getEndTime() != null,
        GenerateRecord::getCreatedTime,
        recordPageReq.getEndTime());

    return this.page(page, wrapper);
  }

  /**
   * 批量查询报告记录
   *
   * @param recordIds
   * @return
   */
  public List<GenerateRecord> getByIds(List<Integer> recordIds) {
    return this.lambdaQuery().in(GenerateRecord::getId, recordIds).list();
  }
}

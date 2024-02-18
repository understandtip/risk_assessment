package com.yushang.risk.admin.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yushang.risk.admin.domain.vo.request.ProjectPageReq;
import com.yushang.risk.domain.entity.Project;
import com.yushang.risk.admin.mapper.ProjectMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * 项目表 服务实现类
 *
 * @author zlp
 * @since 2024-02-02
 */
@Service
public class ProjectDao extends ServiceImpl<ProjectMapper, Project> {
  /**
   * 分页查询项目数据
   *
   * @param page
   * @param pageReq
   * @return
   */
  public Page<Project> getByPage(Page<Project> page, ProjectPageReq pageReq) {
    LambdaQueryWrapper<Project> wrapper = new LambdaQueryWrapper<>();
    if (pageReq != null) {
      wrapper.like(StringUtils.isNotBlank(pageReq.getName()), Project::getName, pageReq.getName());
      wrapper.like(
          StringUtils.isNotBlank(pageReq.getCompanyName()),
          Project::getCompanyName,
          pageReq.getCompanyName());
      wrapper.eq(
          StringUtils.isNotBlank(pageReq.getClassification()),
          Project::getClassification,
          pageReq.getClassification());
      wrapper.eq(pageReq.getVersion() != null, Project::getVersion, pageReq.getVersion());
      wrapper.like(
          StringUtils.isNotBlank(pageReq.getAuthorName()),
          Project::getAuthorName,
          pageReq.getAuthorName());
      wrapper.like(
          StringUtils.isNotBlank(pageReq.getReferenceSystem()),
          Project::getReferenceSystem,
          pageReq.getReferenceSystem());
      wrapper.like(
          StringUtils.isNotBlank(pageReq.getTestingCompany()),
          Project::getTestingCompany,
          pageReq.getTestingCompany());
      wrapper.gt(
          pageReq.getCreatedTime() != null, Project::getCreatedTime, pageReq.getCreatedTime());
      wrapper.lt(pageReq.getEndTime() != null, Project::getCreatedTime, pageReq.getEndTime());
    }

    return this.page(page, wrapper);
  }
}

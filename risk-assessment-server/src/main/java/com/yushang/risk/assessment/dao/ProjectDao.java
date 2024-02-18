package com.yushang.risk.assessment.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yushang.risk.domain.entity.Project;
import com.yushang.risk.assessment.domain.vo.request.ProjectPageReq;
import com.yushang.risk.assessment.mapper.ProjectMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 项目表 服务实现类
 *
 * @author zlp
 * @since 2023-12-29
 */
@Service
public class ProjectDao extends ServiceImpl<ProjectMapper, Project> {
  public Project getByField(SFunction<Project, ?> sFunction, Object value) {
    return this.lambdaQuery().eq(sFunction, value).one();
  }

  public List<Project> getListByField(SFunction<Project, ?> sFunction, Object value) {
    return this.lambdaQuery().eq(sFunction, value).list();
  }

  /**
   * 获取项目列表
   *
   * @param uid
   * @param projectPageReq
   * @return
   */
  public Page<Project> getListByPage(Integer uid, ProjectPageReq projectPageReq) {
    Page<Project> page = new Page<>(projectPageReq.getPageNum(), projectPageReq.getPageSize());
    LambdaQueryWrapper<Project> wrapper = new LambdaQueryWrapper<>();
    wrapper.eq(Project::getAuthorId, uid);
    wrapper.like(
        StringUtils.isNotBlank(projectPageReq.getName()),
        Project::getName,
        projectPageReq.getName());
    wrapper.like(
        StringUtils.isNotBlank(projectPageReq.getCompanyName()),
        Project::getCompanyName,
        projectPageReq.getCompanyName());
    wrapper.like(
        StringUtils.isNotBlank(projectPageReq.getClassification()),
        Project::getClassification,
        projectPageReq.getClassification());

    wrapper.like(
        StringUtils.isNotBlank(projectPageReq.getAuthorName()),
        Project::getAuthorName,
        projectPageReq.getAuthorName());
    wrapper.like(
        StringUtils.isNotBlank(projectPageReq.getTestingCompany()),
        Project::getTestingCompany,
        projectPageReq.getTestingCompany());
    wrapper.ge(
        projectPageReq.getCreatedTime() != null,
        Project::getCreatedTime,
        projectPageReq.getCreatedTime());
    wrapper.orderByDesc(Project::getCreatedTime);
    return this.page(page, wrapper);
  }
}

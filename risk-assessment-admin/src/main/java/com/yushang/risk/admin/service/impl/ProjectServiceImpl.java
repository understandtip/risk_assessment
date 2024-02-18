package com.yushang.risk.admin.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yushang.risk.admin.dao.ProjectDao;
import com.yushang.risk.admin.domain.vo.request.PageBaseReq;
import com.yushang.risk.admin.domain.vo.request.ProjectPageReq;
import com.yushang.risk.admin.domain.vo.response.PageBaseResp;
import com.yushang.risk.admin.domain.vo.response.ProjectPageResp;
import com.yushang.risk.admin.service.ProjectService;
import com.yushang.risk.domain.entity.Project;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author：zlp @Package：com.yushang.risk.admin.service.impl @Project：risk_assessment
 *
 * @name：ProjectServiceImpl @Date：2024/2/2 9:58 @Filename：ProjectServiceImpl
 */
@Service
public class ProjectServiceImpl implements ProjectService {
  @Resource private ProjectDao projectDao;
  /**
   * 查询项目
   *
   * @param baseReq
   * @return
   */
  @Override
  public PageBaseResp<ProjectPageResp> getProject(PageBaseReq<ProjectPageReq> baseReq) {
    Page<Project> page = baseReq.plusPage();
    Page<Project> projectPage = projectDao.getByPage(page, baseReq.getData());
    List<ProjectPageResp> collect =
        projectPage.getRecords().stream()
            .map(
                project -> {
                  ProjectPageResp resp = new ProjectPageResp();
                  BeanUtils.copyProperties(project, resp);
                  return resp;
                })
            .collect(Collectors.toList());
    return PageBaseResp.init(projectPage, collect);
  }
}

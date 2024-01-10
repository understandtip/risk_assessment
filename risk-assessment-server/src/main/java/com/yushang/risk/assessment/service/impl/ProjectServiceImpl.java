package com.yushang.risk.assessment.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yushang.risk.assessment.dao.GenerateRecordDao;
import com.yushang.risk.assessment.dao.ProjectDao;
import com.yushang.risk.assessment.dao.UsersDao;
import com.yushang.risk.assessment.domain.entity.Project;
import com.yushang.risk.assessment.domain.entity.User;
import com.yushang.risk.assessment.domain.vo.request.ProjectPageReq;
import com.yushang.risk.assessment.domain.vo.request.ProjectReq;
import com.yushang.risk.assessment.domain.vo.response.PageBaseResp;
import com.yushang.risk.assessment.domain.vo.response.ProjectResp;
import com.yushang.risk.assessment.service.ProjectService;
import com.yushang.risk.common.util.AssertUtils;
import com.yushang.risk.common.util.RequestHolder;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author：zlp @Package：com.yushang.risk.assessment.service.impl @Project：risk_assessment
 *
 * @name：ProjectServiceImpl @Date：2023/12/29 10:07 @Filename：ProjectServiceImpl
 */
@Service
public class ProjectServiceImpl implements ProjectService {
  @Resource private ProjectDao projectDao;
  @Resource private UsersDao usersDao;
  @Resource private GenerateRecordDao generateRecordDao;
  /**
   * 获取项目列表
   *
   * @param uid
   * @param projectPageReq
   * @return
   */
  @Override
  public PageBaseResp<ProjectResp> getListByPage(Integer uid, ProjectPageReq projectPageReq) {
    Page<Project> listByPage = projectDao.getListByPage(uid, projectPageReq);
    if (listByPage == null || listByPage.getRecords().isEmpty()) return PageBaseResp.empty();

    List<ProjectResp> collect =
        listByPage.getRecords().stream()
            .map(
                project -> {
                  ProjectResp resp = new ProjectResp();
                  BeanUtils.copyProperties(project, resp);
                  return resp;
                })
            .collect(Collectors.toList());

    return PageBaseResp.init(listByPage, collect);
  }

  /**
   * 新建项目
   *
   * @param projectReq
   */
  @Override
  public void newProject(ProjectReq projectReq) {
    Integer uid = RequestHolder.get().getUid();
    User user = usersDao.getById(uid);
    AssertUtils.isNotEmpty(user.getRealName(), "请先设置真实姓名");
    Project project = new Project();
    BeanUtils.copyProperties(projectReq, project);
    project.setAuthorId(uid);
    project.setAuthorName(user.getRealName());
    projectDao.save(project);
  }

  /**
   * 修改项目
   *
   * @param projectReq
   */
  @Override
  public void updateProject(ProjectReq projectReq) {
    Project project = new Project();
    BeanUtils.copyProperties(projectReq, project);
    projectDao.updateById(project);
  }

  /**
   * 删除项目
   *
   * @param projectIds
   * @return
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public boolean removeProject(List<Integer> projectIds) {
    boolean b = projectDao.removeByIds(projectIds);
    // 删除该项目下的报告记录
    boolean b1 = generateRecordDao.removeByProjectId(projectIds);
    return b && b1;
  }

  /**
   * 根据项目id获取项目详细信息
   *
   * @param projectId
   * @return
   */
  @Override
  public ProjectResp getByProjectId(Integer projectId) {
    Project project = projectDao.getById(projectId);
    ProjectResp resp = new ProjectResp();
    BeanUtils.copyProperties(project, resp);
    return resp;
  }
}

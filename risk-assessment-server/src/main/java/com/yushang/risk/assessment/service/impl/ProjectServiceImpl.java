package com.yushang.risk.assessment.service.impl;

import com.yushang.risk.assessment.dao.GenerateRecordDao;
import com.yushang.risk.assessment.dao.ProjectDao;
import com.yushang.risk.assessment.dao.UsersDao;
import com.yushang.risk.assessment.domain.entity.Project;
import com.yushang.risk.assessment.domain.entity.User;
import com.yushang.risk.assessment.domain.vo.request.ProjectReq;
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
   * @return
   */
  @Override
  public List<ProjectResp> getList(Integer uid) {
    List<Project> list = projectDao.getListByField(Project::getAuthorId, uid);
    return list.stream()
        .map(
            project -> {
              ProjectResp resp = new ProjectResp();
              BeanUtils.copyProperties(project, resp);
              return resp;
            })
        .collect(Collectors.toList());
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
   * @param projectId
   * @return
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public boolean removeProject(Integer projectId) {
    boolean b = projectDao.removeById(projectId);
    // 删除该项目下的报告记录
    boolean b1 = generateRecordDao.removeByProjectId(projectId);
    return b && b1;
  }
}

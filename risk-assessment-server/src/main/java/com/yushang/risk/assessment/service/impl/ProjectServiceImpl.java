package com.yushang.risk.assessment.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yushang.risk.assessment.dao.GenerateRecordDao;
import com.yushang.risk.assessment.dao.ProjectDao;
import com.yushang.risk.assessment.dao.UsersDao;
import com.yushang.risk.domain.entity.Project;
import com.yushang.risk.domain.entity.User;
import com.yushang.risk.assessment.domain.vo.request.ProjectPageReq;
import com.yushang.risk.assessment.domain.vo.request.ProjectReq;
import com.yushang.risk.assessment.domain.vo.response.PageBaseResp;
import com.yushang.risk.assessment.domain.vo.response.ProjectResp;
import com.yushang.risk.assessment.service.ProjectService;
import com.yushang.risk.common.util.AssertUtils;
import com.yushang.risk.common.util.RequestHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Author：zlp @Package：com.yushang.risk.assessment.service.impl @Project：risk_assessment
 *
 * @name：ProjectServiceImpl @Date：2023/12/29 10:07 @Filename：ProjectServiceImpl
 */
@Service
@Slf4j
public class ProjectServiceImpl implements ProjectService {
  @Resource private ProjectDao projectDao;
  @Resource private UsersDao usersDao;
  @Resource private GenerateRecordDao generateRecordDao;
  /**
   * 获取项目列表
   *
   * @param uid 用户ID，用于权限验证和数据过滤
   * @param projectPageReq 项目分页请求参数
   * @return 返回项目分页响应信息
   */
  @Override
  public PageBaseResp<ProjectResp> getListByPage(Integer uid, ProjectPageReq projectPageReq) {
    // 参数校验
    if (uid == null || projectPageReq == null) {
      throw new IllegalArgumentException("UID and ProjectPageReq cannot be null.");
    }

    try {
      Page<Project> listByPage = projectDao.getListByPage(uid, projectPageReq);

      // 判断查询结果是否为空
      if (listByPage == null || listByPage.getRecords().isEmpty()) {
        return PageBaseResp.empty();
      }

      List<ProjectResp> collect =
          listByPage.getRecords().stream()
              .map(
                  project -> {
                    ProjectResp resp = new ProjectResp();
                    // 这里假设BeanUtils.copyProperties不会抛出运行时异常，如果有可能，则应该添加相应的try-catch语句
                    BeanUtils.copyProperties(project, resp);
                    return resp;
                  })
              .collect(Collectors.toList());

      return PageBaseResp.init(listByPage, collect);
    } catch (Exception e) {
      // 异常处理，可以根据项目要求记录日志或者抛出自定义异常
      log.error("Error fetching project list", e);
      throw new RuntimeException("Error fetching project list", e);
    }
  }

  /**
   * 新建项目
   *
   * @param projectReq
   */
  @Override
  public void newProject(ProjectReq projectReq) {
    Integer uid = RequestHolder.get().getUid();

    // 空值检查
    if (uid == null) {
      log.error("User ID is null. Unable to create a new project.");
      throw new IllegalArgumentException("User ID cannot be null.");
    }

    User user = usersDao.getById(uid);

    // 用户存在性检查
    if (user == null) {
      log.error("User with ID {} does not exist. Unable to create a new project.", uid);
      throw new IllegalArgumentException("User does not exist.");
    }

    // 设置真实姓名为空判断
    AssertUtils.isNotEmpty(user.getRealName(), "请先设置真实姓名");

    Project project = new Project();
    BeanUtils.copyProperties(projectReq, project);
    project.setAuthorId(uid);
    project.setAuthorName(user.getRealName());

    try {
      projectDao.save(project);
      log.info("Project created successfully by user with ID: {}", uid);
    } catch (Exception e) {
      log.error(
          "Failed to create a new project for user with ID: {}. Error: {}", uid, e.getMessage());
      throw new RuntimeException("Failed to create project", e);
    }
  }
  /**
   * 修改项目
   *
   * @param projectReq
   */
  @Override
  public void updateProject(ProjectReq projectReq) {
    try {
      // 数据验证：确保传入的projectReq不为空，可以根据实际需求进一步扩充验证规则
      if (projectReq == null) {
        throw new IllegalArgumentException("项目请求参数不能为空");
      }

      Project project = new Project();
      // 基于项目请求参数，创建或更新项目实体
      BeanUtils.copyProperties(projectReq, project);

      // 检查ID有效性，确保不会更新一个无效的或不存在的项目
      if (project.getId() == null || project.getId() <= 0) {
        throw new IllegalArgumentException("项目ID无效");
      }

      // 执行数据库更新操作
      projectDao.updateById(project);

    } catch (Exception e) {
      // 处理其他未预期的异常，确保程序的健壮性
      log.error("项目更新过程中发生未知错误：", e);
      throw new RuntimeException("项目更新失败", e);
    }
  }

  /**
   * 删除项目
   *
   * @param projectIds
   * @return
   */
  @Override
  @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
  public boolean removeProject(List<Integer> projectIds) {
    // 参数校验
    if (projectIds == null || projectIds.isEmpty()) {
      log.error("项目ID列表不能为空");
      throw new IllegalArgumentException("项目ID列表不能为空");
    }
    // 防止传入null元素
    projectIds =
        Collections.unmodifiableList(
            projectIds.stream().filter(Objects::nonNull).collect(Collectors.toList()));

    boolean isProjectDeleted = projectDao.removeByIds(projectIds);

    if (!isProjectDeleted) {
      log.error("项目删除失败");
      return false;
    }

    // 删除该项目下的报告记录
    boolean areRecordsRemoved = generateRecordDao.removeByProjectId(projectIds);

    if (!areRecordsRemoved) {
      log.error("报告记录删除失败");
      // 这里根据业务需求决定是否需要抛出异常或如何处理这种失败情况
    }

    return areRecordsRemoved;
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

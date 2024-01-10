package com.yushang.risk.assessment.service;

import com.yushang.risk.assessment.domain.vo.request.ProjectPageReq;
import com.yushang.risk.assessment.domain.vo.request.ProjectReq;
import com.yushang.risk.assessment.domain.vo.response.PageBaseResp;
import com.yushang.risk.assessment.domain.vo.response.ProjectResp;

import java.util.List;

/**
 * 项目表 服务类
 *
 * @author zlp
 * @since 2023-12-29
 */
public interface ProjectService {
  /**
   * 查看项目列表
   *
   * @param uid
   * @param projectPageReq
   * @return
   */
  PageBaseResp<ProjectResp> getListByPage(Integer uid, ProjectPageReq projectPageReq);

  /**
   * 新建项目
   *
   * @param projectReq
   */
  void newProject(ProjectReq projectReq);

  /**
   * 修改项目
   *
   * @param projectReq
   */
  void updateProject(ProjectReq projectReq);

  /**
   * 删除项目
   *
   * @param projectIds
   * @return
   */
  boolean removeProject(List<Integer> projectIds);

  /**
   * 根据项目id获取项目详细信息
   *
   * @param projectId
   * @return
   */
  ProjectResp getByProjectId(Integer projectId);
}

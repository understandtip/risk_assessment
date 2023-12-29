package com.yushang.risk.assessment.service;

import com.yushang.risk.assessment.domain.vo.request.ProjectReq;
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
   * @return
   */
  List<ProjectResp> getList(Integer uid);

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
   * @param projectId
   * @return
   */
  boolean removeProject(Integer projectId);
}

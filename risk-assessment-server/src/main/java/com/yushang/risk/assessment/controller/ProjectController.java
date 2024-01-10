package com.yushang.risk.assessment.controller;

import com.yushang.risk.assessment.domain.vo.request.ProjectPageReq;
import com.yushang.risk.assessment.domain.vo.request.ProjectReq;
import com.yushang.risk.assessment.domain.vo.response.PageBaseResp;
import com.yushang.risk.assessment.domain.vo.response.ProjectResp;
import com.yushang.risk.assessment.service.ProjectService;
import com.yushang.risk.common.domain.vo.ApiResult;
import com.yushang.risk.common.exception.CommonErrorEnum;
import com.yushang.risk.common.util.RequestHolder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author：zlp @Package：com.yushang.risk.assessment.controller @Project：risk_assessment
 *
 * @name：ProjectController @Date：2023/12/29 9:51 @Filename：ProjectController
 */
@RestController
@RequestMapping("/api/project")
@Api(tags = "项目管理相关接口")
public class ProjectController {
  @Resource private ProjectService projectService;

  /**
   * 获取项目列表(分页)
   *
   * @return
   */
  @GetMapping("/getList")
  @ApiOperation("获取项目列表(分页)")
  public ApiResult<PageBaseResp<ProjectResp>> getList(@Validated ProjectPageReq projectPageReq) {
    Integer uid = RequestHolder.get().getUid();
    PageBaseResp<ProjectResp> page = projectService.getListByPage(uid, projectPageReq);
    return ApiResult.success(page);
  }

  /**
   * 根据项目id获取项目详细信息
   *
   * @param projectId
   * @return
   */
  @GetMapping("/getById/{id}")
  @ApiOperation("根据项目id获取项目详细信息")
  public ApiResult<ProjectResp> getByProjectId(@PathVariable("id") Integer projectId) {
    ProjectResp resp = projectService.getByProjectId(projectId);
    return ApiResult.success(resp);
  }

  /**
   * 新建项目
   *
   * @param projectReq
   * @return
   */
  @PostMapping("/newProject")
  @ApiOperation("新建项目")
  public ApiResult<Void> newProject(@RequestBody @Validated ProjectReq projectReq) {
    projectService.newProject(projectReq);
    return ApiResult.success();
  }

  /**
   * 修改项目
   *
   * @param projectReq
   * @return
   */
  @PutMapping("/updateProject")
  @ApiOperation("修改项目")
  public ApiResult<Void> updateProject(@RequestBody @Validated ProjectReq projectReq) {
    projectService.updateProject(projectReq);
    return ApiResult.success();
  }

  /**
   * 删除项目
   *
   * @param projectIds
   * @return
   */
  @DeleteMapping("/removeProject")
  @ApiOperation("删除项目")
  public ApiResult<Void> removeProject(@RequestBody List<Integer> projectIds) {
    boolean b = projectService.removeProject(projectIds);
    return b
        ? ApiResult.success()
        : ApiResult.fail(CommonErrorEnum.SYSTEM_ERROR.getCode(), "项目删除失败");
  }
}

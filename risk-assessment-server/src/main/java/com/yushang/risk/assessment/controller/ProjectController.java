package com.yushang.risk.assessment.controller;

import com.yushang.risk.assessment.domain.entity.Project;
import com.yushang.risk.assessment.domain.vo.request.ProjectReq;
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
   * 获取项目列表
   *
   * @return
   */
  @GetMapping("/getList")
  @ApiOperation("获取项目列表")
  public ApiResult<List<ProjectResp>> getList() {
    Integer uid = RequestHolder.get().getUid();
    List<ProjectResp> list = projectService.getList(uid);
    return ApiResult.success(list);
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
   * @param projectId
   * @return
   */
  @DeleteMapping("/removeProject/{id}")
  @ApiOperation("删除项目")
  public ApiResult<Void> removeProject(@PathVariable("id") Integer projectId) {
    boolean b = projectService.removeProject(projectId);
    return b
        ? ApiResult.success()
        : ApiResult.fail(CommonErrorEnum.SYSTEM_ERROR.getCode(), "项目删除失败");
  }
}

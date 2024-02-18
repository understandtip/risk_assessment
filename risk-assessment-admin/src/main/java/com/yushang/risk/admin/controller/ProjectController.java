package com.yushang.risk.admin.controller;

import com.yushang.risk.admin.domain.vo.request.PageBaseReq;
import com.yushang.risk.admin.domain.vo.request.ProjectPageReq;
import com.yushang.risk.admin.domain.vo.response.PageBaseResp;
import com.yushang.risk.admin.domain.vo.response.ProjectPageResp;
import com.yushang.risk.admin.service.ProjectService;
import com.yushang.risk.common.domain.vo.ApiResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author：zlp @Package：com.yushang.risk.admin.controller @Project：risk_assessment
 *
 * @name：ProjectController @Date：2024/2/2 9:41 @Filename：ProjectController
 */
@RestController
@RequestMapping("/capi/pro")
public class ProjectController {

  @Resource private ProjectService projectService;

  @PostMapping("/getProject")
  @ApiOperation("查询项目")
  public ApiResult<PageBaseResp<ProjectPageResp>> getProject(
      @RequestBody PageBaseReq<ProjectPageReq> baseReq) {
    PageBaseResp<ProjectPageResp> resp = projectService.getProject(baseReq);
    return ApiResult.success(resp);
  }
}

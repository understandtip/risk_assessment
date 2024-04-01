package com.yushang.risk.admin.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yushang.risk.admin.dao.ProjectDao;
import com.yushang.risk.admin.domain.vo.request.PageBaseReq;
import com.yushang.risk.admin.domain.vo.request.ProjectPageReq;
import com.yushang.risk.admin.domain.vo.response.PageBaseResp;
import com.yushang.risk.admin.domain.vo.response.ProjectPageResp;
import com.yushang.risk.admin.service.ProjectService;
import com.yushang.risk.domain.entity.Project;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author：zlp @Package：com.yushang.risk.admin.service.impl @Project：risk_assessment
 *
 * @name：ProjectServiceImpl @Date：2024/2/2 9:58 @Filename：ProjectServiceImpl
 */
@Service
@Slf4j
public class ProjectServiceImpl implements ProjectService {
  @Resource private ProjectDao projectDao;
  /**
   * 查询项目信息的接口。根据提供的分页请求信息，从数据库中检索项目信息，并返回分页响应。
   *
   * @param baseReq 包含分页请求参数和查询条件的请求对象。
   * @return 返回一个分页响应对象，其中包含查询到的项目信息列表。
   */
  @Override
  public PageBaseResp<ProjectPageResp> getProject(PageBaseReq<ProjectPageReq> baseReq) {
    // 校验输入参数是否为空
    if (baseReq == null || baseReq.getData() == null) {
      throw new IllegalArgumentException("请求参数不能为空");
    }

    try { 
      // 对输入的分页请求进行增强，以便更好地进行分页查询
      Page<Project> page = baseReq.plusPage();
      // 执行数据库查询操作，获取项目信息的分页结果
      Page<Project> projectPage = projectDao.getByPage(page, baseReq.getData());

      if (CollectionUtils.isEmpty(projectPage.getRecords())) {
        // 当查询结果为空时，直接返回空列表的分页响应
        return PageBaseResp.init(projectPage, Collections.emptyList());
      }

      // 将查询结果转换为对应的响应对象列表
      List<ProjectPageResp> collect =
          projectPage.getRecords().stream()
              .map(
                  project -> {
                    ProjectPageResp resp = new ProjectPageResp();
                    BeanUtils.copyProperties(project, resp);
                    return resp;
                  })
              .collect(Collectors.toList());

      // 构建并返回包含转换后的项目信息的分页响应
      return PageBaseResp.init(projectPage, collect);
    } catch (Exception e) {
      // 记录查询过程中发生的异常，并向上抛出运行时异常
      log.error("获取项目信息出错", e);
      throw new RuntimeException("获取项目信息出错", e);
    }
  }
}

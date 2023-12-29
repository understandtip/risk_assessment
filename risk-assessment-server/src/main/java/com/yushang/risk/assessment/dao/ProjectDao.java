package com.yushang.risk.assessment.dao;

import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.yushang.risk.assessment.domain.entity.Project;
import com.yushang.risk.assessment.mapper.ProjectMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 项目表 服务实现类
 *
 * @author zlp
 * @since 2023-12-29
 */
@Service
public class ProjectDao extends ServiceImpl<ProjectMapper, Project> {
  public Project getByField(SFunction<Project, ?> sFunction, Object value) {
    return this.lambdaQuery().eq(sFunction, value).one();
  }

  public List<Project> getListByField(SFunction<Project, ?> sFunction, Object value) {
    return this.lambdaQuery().eq(sFunction, value).list();
  }
}

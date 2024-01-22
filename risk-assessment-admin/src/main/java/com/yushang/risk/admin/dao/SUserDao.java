package com.yushang.risk.admin.dao;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yushang.risk.admin.domain.vo.request.SecurityServiceRecordPageReq;
import com.yushang.risk.admin.mapper.SUserMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yushang.risk.domain.entity.SUser;
import org.springframework.stereotype.Service;

/**
 * 安全服务用户 服务实现类
 *
 * @author zlp
 * @since 2024-01-16
 */
@Service
public class SUserDao extends ServiceImpl<SUserMapper, SUser> {
  /**
   * 分页查询用户提交的漏洞记录
   *
   * @param page
   * @param data
   * @return
   */
  public Page<SUser> getUserByPage(Page<SUser> page, SecurityServiceRecordPageReq data) {
    // 先查用户
    LambdaQueryWrapper<SUser> wrapper = new LambdaQueryWrapper<>();
    if (data != null) {
      wrapper.like(StrUtil.isNotEmpty(data.getName()), SUser::getName, data.getName());
      wrapper.like(StrUtil.isNotEmpty(data.getPhone()), SUser::getPhone, data.getPhone());
      wrapper.like(StrUtil.isNotEmpty(data.getEmail()), SUser::getEmail, data.getEmail());
      wrapper.eq(StrUtil.isNotEmpty(data.getServiceId()), SUser::getServiceId, data.getServiceId());
    }
    return this.page(page, wrapper);
  }
}

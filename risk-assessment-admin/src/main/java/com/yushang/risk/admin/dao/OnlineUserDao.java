package com.yushang.risk.admin.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yushang.risk.admin.domain.vo.request.OnlineUserPageReq;
import com.yushang.risk.admin.mapper.OnlineUserMapper;
import com.yushang.risk.admin.service.IOnlineUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yushang.risk.domain.entity.OnlineUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * 在线用户 服务实现类
 *
 * @author zlp
 * @since 2024-02-19
 */
@Service
public class OnlineUserDao extends ServiceImpl<OnlineUserMapper, OnlineUser> {
  @Resource private AccountDao accountDao;

  public void removeByUserName(Integer uid) {
    String username = accountDao.getById(uid).getUsername();
    this.lambdaUpdate().eq(OnlineUser::getUserName, username).remove();
  }

  /** 每隔一段时间器清除在线用户信息 */
  public void removeOnlineUser() {
    LocalDateTime now = LocalDateTime.now();
    LocalDateTime minusDays = now.minusDays(3L);
    this.lambdaUpdate().le(OnlineUser::getCreatedTime, minusDays).remove();
  }

  /**
   * 查询在线用户
   *
   * @param page
   * @param data
   * @return
   */
  public Page<OnlineUser> getOnlineList(Page<OnlineUser> page, OnlineUserPageReq data) {
    LambdaQueryWrapper<OnlineUser> wrapper = new LambdaQueryWrapper<>();
    if (data != null) {
      wrapper.like(StringUtils.isNotBlank(data.getIp()), OnlineUser::getIp, data.getIp());
      wrapper.like(
          StringUtils.isNotBlank(data.getUserName()), OnlineUser::getUserName, data.getUserName());
    }
    wrapper.orderByDesc(OnlineUser::getCreatedTime);
    return this.page(page, wrapper);
  }

  /**
   * 根据用户名删除记录
   *
   * @param userName
   */
  public void removeByUserName(String userName) {
    this.lambdaUpdate().eq(OnlineUser::getUserName, userName).remove();
  }
}

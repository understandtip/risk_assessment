package com.yushang.risk.assessment.dao;

import com.yushang.risk.domain.entity.OnlineUser;
import com.yushang.risk.assessment.mapper.OnlineUserMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 在线用户 服务实现类
 *
 * @author zlp
 * @since 2024-02-19
 */
@Service
public class OnlineUserDao extends ServiceImpl<OnlineUserMapper, OnlineUser> {
  @Resource private UsersDao usersDao;

  public void removeByUserName(Integer uid) {
    String username = usersDao.getById(uid).getUsername();
    this.lambdaUpdate().eq(OnlineUser::getUserName, username).remove();
  }
}

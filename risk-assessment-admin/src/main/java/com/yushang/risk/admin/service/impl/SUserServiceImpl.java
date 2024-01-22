package com.yushang.risk.admin.service.impl;

import com.yushang.risk.admin.dao.SUserDao;
import com.yushang.risk.admin.dao.SUserRecordDao;
import com.yushang.risk.admin.service.MinioService;
import com.yushang.risk.admin.service.SUserService;
import com.yushang.risk.domain.entity.SUser;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Var;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @Author：zlp @Package：com.yushang.risk.admin.service.impl @Project：risk_assessment
 *
 * @name：SUserServiceImpl @Date：2024/1/22 10:00 @Filename：SUserServiceImpl
 */
@Service
@Slf4j
public class SUserServiceImpl implements SUserService {
  @Resource private SUserDao sUserDao;
  @Resource private SUserRecordDao sUserRecordDao;
  @Resource private MinioService minioService;
  /**
   * 删除漏洞记录
   *
   * @param userId
   * @return
   */
  @Transactional(rollbackFor = Exception.class)
  @Override
  public boolean deleteBugRecord(Integer userId) {
    // 逻辑删除
    // SUser
    SUser user = sUserDao.getById(userId);
    String portName = user.getPortName();
    boolean b = sUserDao.removeById(userId);
    // SUserRecord
    boolean b1 = sUserRecordDao.removeByUserId(userId);
    // 删除文件
    minioService.delete(portName);
    return b == b1;
  }
}

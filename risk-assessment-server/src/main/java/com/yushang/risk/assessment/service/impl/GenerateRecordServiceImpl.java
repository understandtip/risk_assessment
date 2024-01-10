package com.yushang.risk.assessment.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yushang.risk.assessment.dao.GenerateRecordDao;
import com.yushang.risk.assessment.dao.UsersDao;
import com.yushang.risk.assessment.domain.entity.GenerateRecord;
import com.yushang.risk.assessment.domain.entity.User;
import com.yushang.risk.assessment.domain.vo.request.RecordPageReq;
import com.yushang.risk.assessment.domain.vo.response.PageBaseResp;
import com.yushang.risk.assessment.domain.vo.response.RecordResp;
import com.yushang.risk.assessment.service.GenerateRecordService;
import com.yushang.risk.common.exception.BusinessException;
import com.yushang.risk.common.util.AssertUtils;
import com.yushang.risk.common.util.RequestHolder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author：zlp @Package：com.yushang.risk.assessment.service.impl @Project：risk_assessment
 *
 * @name：GenerateRecordServiceImpl @Date：2023/12/29 15:00 @Filename：GenerateRecordServiceImpl
 */
@Service
public class GenerateRecordServiceImpl implements GenerateRecordService {
  @Resource private GenerateRecordDao generateRecordDao;
  @Resource private UsersDao usersDao;
  /**
   * 分页查询生成报告记录
   *
   * @param uid
   * @param recordPageReq
   * @return
   */
  @Override
  public PageBaseResp<RecordResp> getListByPage(Integer uid, RecordPageReq recordPageReq) {
    Map<Integer, String> map =
        usersDao.list().stream()
            .filter((user) -> StringUtils.isNotEmpty(user.getRealName()))
            .collect(Collectors.toMap(User::getId, User::getRealName));

    Page<GenerateRecord> page = generateRecordDao.getListByPage(uid, recordPageReq);
    List<RecordResp> collect =
        page.getRecords().stream()
            .map(
                generateRecord -> {
                  RecordResp resp = new RecordResp();
                  BeanUtils.copyProperties(generateRecord, resp);
                  String realName = map.get(generateRecord.getAuthorId());
                  resp.setAuthor(realName);
                  return resp;
                })
            .collect(Collectors.toList());

    return PageBaseResp.init(page, collect);
  }

  /**
   * 删除报告记录
   *
   * @param recordIds
   * @return
   */
  @Override
  public boolean remove(List<Integer> recordIds) {
    // 校验一下
    Set<Integer> authorIds =
        generateRecordDao.getByIds(recordIds).stream()
            .map(GenerateRecord::getAuthorId)
            .collect(Collectors.toSet());
    if (authorIds.size() != 1 || !authorIds.contains(RequestHolder.get().getUid()))
      throw new BusinessException("不可删除他人的报告");
    return generateRecordDao.removeByIds(recordIds);
  }
}

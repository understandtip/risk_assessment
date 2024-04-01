package com.yushang.risk.assessment.dao;

import com.alibaba.fastjson.JSON;
import com.yushang.risk.domain.entity.SUser;
import com.yushang.risk.domain.entity.SUserRecord;
import com.yushang.risk.assessment.domain.vo.request.SecurityServiceBugBugReq;
import com.yushang.risk.assessment.mapper.SUserRecordMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户提交记录 服务实现类
 *
 * @author zlp
 * @since 2024-01-16
 */
@Service
public class SUserRecordDao extends ServiceImpl<SUserRecordMapper, SUserRecord> {
  /**
   * 批量保存用户提交的漏洞信息
   *
   * @param sUser
   * @param bugReq
   */
  public void saveBatch(SUser sUser, SecurityServiceBugBugReq bugReq) {
    StringBuilder extraBug = new StringBuilder();
    if (!bugReq.getAddBugs().isEmpty()) {
      extraBug.append("[");
      bugReq
          .getAddBugs()
          .forEach(
              addBug -> {
                extraBug.append(JSON.toJSONString(addBug)).append(",");
              });
      extraBug.append("]");
      // 取出extraBug倒数第二个字符
      extraBug.deleteCharAt(extraBug.length() - 2);
    }
    Integer userId = sUser.getId();
    List<SUserRecord> collect =
        bugReq.getBugIds().stream()
            .map(
                id -> {
                  SUserRecord record = new SUserRecord();
                  record.setUserId(userId);
                  record.setBugId(id);
                  record.setExtraBug(extraBug.toString());
                  return record;
                })
            .collect(Collectors.toList());

    this.saveBatch(collect);
  }
}

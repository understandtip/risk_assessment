package com.yushang.risk.admin.dao;

import com.yushang.risk.admin.mapper.SUserRecordMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yushang.risk.domain.entity.SUserRecord;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 用户提交记录 服务实现类
 *
 * @author zlp
 * @since 2024-01-16
 */
@Service
public class SUserRecordDao extends ServiceImpl<SUserRecordMapper, SUserRecord> {
  @Resource private SUserDao sUserDao;

  /**
   * 根据用户id查询漏洞id
   *
   * @param userIds
   * @return
   */
  public Map<Integer, List<Integer>> getByUserIds(List<Integer> userIds) {
    List<SUserRecord> records = this.lambdaQuery().in(SUserRecord::getUserId, userIds).list();
    Map<Integer, List<SUserRecord>> collect =
        records.stream().collect(Collectors.groupingBy(SUserRecord::getUserId));
    Map<Integer, List<Integer>> map = new HashMap<>();
    collect.forEach(
        (uid, recordList) -> {
          List<Integer> bugIds =
              recordList.stream().map(SUserRecord::getBugId).collect(Collectors.toList());
          map.put(uid, bugIds);
        });
    return map;
  }

  /**
   * 根据用户id删除记录
   *
   * @param userId
   * @return
   */
  public boolean removeByUserId(Integer userId) {
    return this.lambdaUpdate().eq(SUserRecord::getUserId, userId).remove();
  }

  /**
   * 根据用户id查询用户自定义的漏洞信息
   *
   * @param userIds
   * @return
   */
  public Map<Integer, List<String>> getExtraBugByUids(List<Integer> userIds) {
    List<SUserRecord> records = this.lambdaQuery().in(SUserRecord::getUserId, userIds).list();
    Map<Integer, List<SUserRecord>> collect =
        records.stream().collect(Collectors.groupingBy(SUserRecord::getUserId));
    Map<Integer, List<String>> map = new HashMap<>();
    collect.forEach(
        (uid, recordList) -> {
          List<String> extraBugs =
              recordList.stream().map(SUserRecord::getExtraBug).collect(Collectors.toList());
          map.put(uid, extraBugs);
        });
    return map;
  }
}

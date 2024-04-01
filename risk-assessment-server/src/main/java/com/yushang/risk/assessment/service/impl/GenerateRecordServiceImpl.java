package com.yushang.risk.assessment.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yushang.risk.assessment.dao.GenerateRecordDao;
import com.yushang.risk.assessment.dao.UsersDao;
import com.yushang.risk.assessment.domain.entity.GenerateRecord;
import com.yushang.risk.domain.entity.User;
import com.yushang.risk.assessment.domain.vo.request.RecordPageReq;
import com.yushang.risk.assessment.domain.vo.response.PageBaseResp;
import com.yushang.risk.assessment.domain.vo.response.RecordResp;
import com.yushang.risk.assessment.service.GenerateRecordService;
import com.yushang.risk.common.exception.BusinessException;
import com.yushang.risk.common.util.RequestHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
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
@Slf4j
public class GenerateRecordServiceImpl implements GenerateRecordService {
  @Resource private GenerateRecordDao generateRecordDao;
  @Resource private UsersDao usersDao;
  /**
   * 分页查询生成报告记录
   *
   * @param uid 用户ID，用于查询与特定用户相关的报告记录
   * @param recordPageReq 分页请求参数，包含查询条件
   * @return 返回报告记录的分页响应信息，包含分页数据和总记录数等
   */
  @Override
  public PageBaseResp<RecordResp> getListByPage(Integer uid, RecordPageReq recordPageReq) {
    try {
      // 根据用户ID查询所有用户信息，并过滤掉真实姓名为空的用户，将用户ID映射到真实姓名
      Map<Integer, String> map =
          usersDao.list().stream()
              .filter((user) -> StringUtils.isNotEmpty(user.getRealName()))
              .collect(Collectors.toMap(User::getId, User::getRealName));

      // 根据用户ID和分页请求获取报告记录列表
      Page<GenerateRecord> page = generateRecordDao.getListByPage(uid, recordPageReq);

      // 将查询到的报告记录转换为前端需要的格式
      List<RecordResp> collect =
          page.getRecords().stream()
              .map(
                  generateRecord -> {
                    RecordResp resp = new RecordResp();
                    BeanUtils.copyProperties(generateRecord, resp);

                    // 如果作者ID在映射表中存在，则使用真实姓名，否则默认为"Unknown"
                    String realName =
                        ObjectUtils.defaultIfNull(map.get(generateRecord.getAuthorId()), "Unknown");
                    resp.setAuthor(realName);
                    return resp;
                  })
              .collect(Collectors.toList());

      // 构建并返回分页响应信息
      return PageBaseResp.init(page, collect);
    } catch (Exception e) {
      // 查询报告记录出错时，抛出运行时异常
      throw new RuntimeException("Error fetching report records.", e);
    }
  }

  /**
   * 删除报告记录
   *
   * @param recordIds
   * @return
   */
  @Override
  public boolean remove(List<Integer> recordIds) {
    // 验证输入是否有效
    if (recordIds == null || recordIds.isEmpty()) {
      throw new IllegalArgumentException("报告ID列表不能为空");
    }
    // 校验一下
    Set<Integer> authorIds =
        generateRecordDao.getByIds(recordIds).stream()
            .map(GenerateRecord::getAuthorId)
            .collect(Collectors.toSet());
    // 如果报告的作者不是当前用户，或者报告的作者数量不为1（即多个报告ID对应多个作者），则抛出业务异常
    if (authorIds.size() != 1 || !authorIds.contains(RequestHolder.get().getUid()))
      throw new BusinessException("不可删除他人的报告");
    // 删除报告记录
    return generateRecordDao.removeByIds(recordIds);
  }
}

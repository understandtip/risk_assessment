package com.yushang.risk.assessment.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yushang.risk.assessment.domain.entity.BsCategoryLatitude;
import com.yushang.risk.assessment.domain.entity.BsRiskLatitude;
import com.yushang.risk.assessment.mapper.BsCategoryLatitudeMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 分类纬度表关系 服务实现类
 *
 * @author zlp
 * @since 2024-03-05
 */
@Service
public class BsCategoryLatitudeDao
    extends ServiceImpl<BsCategoryLatitudeMapper, BsCategoryLatitude> {
  @Resource private BsRiskLatitudeDao bsRiskLatitudeDao;
  /**
   * 根据分类id查询分类下的纬度信息
   *
   * @param categoryId
   * @return
   */
  public List<BsRiskLatitude> getByLatitude(Integer categoryId) {
    LambdaQueryWrapper<BsCategoryLatitude> wrapper = new LambdaQueryWrapper<>();
    wrapper.eq(BsCategoryLatitude::getCategoryId, categoryId);
    List<Integer> latitudeIds =
        this.list(wrapper).stream()
            .map(BsCategoryLatitude::getLatitudeId)
            .collect(Collectors.toList());
    return bsRiskLatitudeDao.listByIds(latitudeIds);
  }
}

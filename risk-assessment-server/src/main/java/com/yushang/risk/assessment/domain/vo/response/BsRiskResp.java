package com.yushang.risk.assessment.domain.vo.response;

import com.yushang.risk.assessment.domain.entity.BsCategoryLatitude;
import com.yushang.risk.assessment.domain.entity.Category;
import com.yushang.risk.assessment.domain.entity.Risk;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author：zlp @Package：com.yushang.risk.assessment.domain.vo.response @Project：risk_assessment
 *
 * @name：BsRiskResp @Date：2024/3/5 10:35 @Filename：BsRiskResp
 */
@Data
public class BsRiskResp {
  private List<CategoryLatitude> categoryLatitudeList;

  @Data
  public static class CategoryLatitude {
    private String latitudeName;
    private List<RiskCategory> riskCategoryList;
  }

  @Data
  public static class RiskCategory {
    private String riskCategoryName;
    private List<Risk> riskList;
  }

  @Data
  public static class Risk {
    private Integer id;
    /** 标题 */
    @ApiModelProperty(value = "标题", notes = "")
    private String title;
    /** 定义 */
    @ApiModelProperty(value = "定义", notes = "")
    private String definition;

    private List<Risk> childRiskList;
  }
}

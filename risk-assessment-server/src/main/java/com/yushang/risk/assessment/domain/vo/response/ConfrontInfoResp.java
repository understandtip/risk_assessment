package com.yushang.risk.assessment.domain.vo.response;

import com.yushang.risk.assessment.domain.entity.CConfrontWay;
import com.yushang.risk.assessment.domain.entity.CElement;
import com.yushang.risk.assessment.domain.entity.CElementType;
import com.yushang.risk.assessment.domain.entity.CElementTypeMethod;
import lombok.Data;

import java.util.List;

/**
 * @Author：zlp @Package：com.yushang.risk.assessment.domain.vo.response @Project：risk_assessment
 *
 * @name：ConfrontInfoResp @Date：2024/2/23 15:08 @Filename：ConfrontInfoResp
 */
@Data
public class ConfrontInfoResp {
  private CElement cElement;
  private List<ElementTypeResp> elementTypeRespList;

  @Data
  static class ElementTypeResp {
    private CElementType cElementType;
    private List<ElementTypeMethodResp> elementTypeMethodResp;
  }

  @Data
  static class ElementTypeMethodResp {
    private CElementTypeMethod cElementTypeMethod;
    private List<CConfrontWay> cConfrontWayList;
  }
}

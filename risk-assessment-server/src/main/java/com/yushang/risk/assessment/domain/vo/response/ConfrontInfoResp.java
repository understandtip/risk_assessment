package com.yushang.risk.assessment.domain.vo.response;

import com.baomidou.mybatisplus.annotation.TableLogic;
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
  public static class ElementTypeResp {
    private CElementType cElementType;
    private List<ElementTypeMethodResp> elementTypeMethodResp;
  }

  @Data
  public static class ElementTypeMethodResp {
    private CElementTypeMethod cElementTypeMethod;
    private List<CConfrontWay> cConfrontWayList;
    private List<ElementTypeMethodResp> enHanceTypeMethodList;
  }
}

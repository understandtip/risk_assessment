package com.yushang.risk.assessment.domain.vo.response;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yushang.risk.assessment.domain.entity.CycleMark;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author：zlp @Package：com.yushang.risk.assessment.domain.vo.response @Project：risk_assessment
 *
 * @name：CycleResp @Date：2023/12/19 9:01 @Filename：CycleResp
 */
@Data
@ApiModel(value = "周期返回对象", description = "")
public class CycleResp implements Serializable, Cloneable {
  /** id */
  @ApiModelProperty(notes = "id")
  @TableId
  private Integer id;
  /** 周期名称 */
  @ApiModelProperty(notes = "周期名称")
  private String name;
  /** 周期标志 */
  @ApiModelProperty(notes = "周期标志")
  private List<String> cycleMarkList;
}

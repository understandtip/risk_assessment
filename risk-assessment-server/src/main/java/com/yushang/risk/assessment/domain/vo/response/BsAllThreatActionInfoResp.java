package com.yushang.risk.assessment.domain.vo.response;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.List;

/**
 * @Author：zlp @Package：com.yushang.risk.assessment.domain.vo.response @Project：risk_assessment
 *
 * @name：BsAllThreatActionInfoResp @Date：2024/3/20 14:51 @Filename：BsAllThreatActionInfoResp
 */
@Data
public class BsAllThreatActionInfoResp {
  /** 主键id */
  private Integer id;

  /** 标题 */
  @TableField("title")
  private String title;

  /** 描述 */
  @TableField("description")
  private String description;

  /** 参考 */
  @TableField("refer")
  private String refer;

  private List<ToolBuild> toolBuildList;
  private List<ToolUse> toolUseList;
  private List<RiskBuild> riskBuildList;
  private List<BsAllThreatActionInfoResp> childAttackList;

  @Data
  public static class ToolUse {
    private Integer id;
    private String title;
  }

  @Data
  public static class ToolBuild {
    private Integer id;
    private String title;
  }

  @Data
  public static class RiskBuild {
    private Integer id;
    private String title;
  }
}

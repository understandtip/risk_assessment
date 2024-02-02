package com.yushang.risk.admin.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @Author：zlp @Package：com.yushang.risk.admin.domain.enums @Project：risk_assessment
 *
 * @name：ApplyStateEnum @Date：2024/2/1 15:04 @Filename：ApplyStateEnum
 */
@Getter
@AllArgsConstructor
public enum ApplyStateEnum {
  //
  APPROVE(1, ""),
  WAIT(0, ""),
  REFUSE(-1, "");
  private final Integer state;
  private final String desc;

  /**
   * 判断状态码是否在枚举对象集合中
   *
   * @return
   */
  public static boolean isInside(Integer state) {
    AtomicBoolean flag = new AtomicBoolean(false);
    Arrays.stream(ApplyStateEnum.values())
        .forEach(
            e -> {
              if (e.getState().equals(state)) {
                flag.set(true);
              }
            });
    return flag.get();
  }
}

package com.yushang.risk.assessment.service.handler;

import com.yushang.risk.assessment.service.handler.OptLogHandlerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @Author：zlp @Package：com.yushang.risk.assessment.service @Project：risk_assessment
 *
 * @name：OptLogService @Date：2024/1/8 14:47 @Filename：OptLogService
 */
@Service
public abstract class AbstractOptLogHandler {
  protected static final String LOGIN_FILE_PATH = "/data/logs/login.log";
  protected static final String EXIT_FILE_PATH = "/data/logs/exit.log";

  @PostConstruct
  public void init() {
    OptLogHandlerFactory.of(this.getCode(), this);
  }

  /**
   * 记录日志
   *
   * @param flag true:操作成功 false:操作失败
   */
  public abstract void log(boolean flag);

  /**
   * 获取code
   *
   * @return
   */
  protected abstract Integer getCode();

  /**
   * 写入日志到本地文件中
   *
   * @param info
   */
  protected void writeToFile(String filePath, String info) {
    try {
      File file = new File(filePath);
      FileOutputStream fos;
      if (!file.exists()) {
        // 如果文件不存在，就创建该文件
        file.createNewFile();
        // 首次写入获取
        fos = new FileOutputStream(file);
      } else {
        // 如果文件已存在，那么就在文件末尾追加写入
        // 这里构造方法多了一个参数true,表示在文件末尾追加写入
        fos = new FileOutputStream(file, true);
      }
      // 指定以UTF-8格式写入文件
      OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
      osw.write(info);
      osw.write("\r\n");

      osw.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}

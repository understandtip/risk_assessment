package com.yushang.risk.assessment.controller;

import cn.hutool.core.io.FileUtil;
import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.plugin.table.LoopRowTableRenderPolicy;
import com.yushang.risk.common.domain.vo.ApiResult;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 * @Author：zlp @Package：com.yushang.risk @Project：risk_assessment
 *
 * @name：MyTestController @Date：2023/12/21 9:14 @Filename：MyTestController
 */
@RestController
public class MyTestController {
  @GetMapping("/test")
  public ResponseEntity<byte[]> download(HttpServletResponse response) {
    try {
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

      // 设置响应内容类型
      response.setContentType("application/octet-stream");
      response.addHeader(
          "Content-Disposition",
          "attachment; filename=" + URLEncoder.encode(FileUtil.getName("目标文档.docx"), "UTF-8"));

      // 构建要渲染的数据
      HashMap<String, Object> finalMap = new HashMap<>();
      finalMap.put("version", "周亮平");

      // 渲染数据并将内容写入到输出流
      XWPFTemplate.compile("C:\\Users\\zlp\\Desktop\\公司材料\\数据安全检查模板.docx")
          .render(finalMap)
          .writeAndClose(outputStream);

      // 将输出流中的字节内容转换为字节数组
      byte[] content = outputStream.toByteArray();

      // 设置响应头信息
      HttpHeaders headers = new HttpHeaders();
      headers.setContentLength(content.length);

      return new ResponseEntity<>(content, headers, HttpStatus.OK);
    } catch (IOException e) {
      e.printStackTrace();
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}

package com.yushang.risk.assessment.controller;

import cn.hutool.core.io.FileUtil;
import com.yushang.risk.assessment.domain.vo.request.GenerateReportReq;
import com.yushang.risk.assessment.service.RiskService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * @Author：zlp @Package：com.yushang.risk @Project：risk_assessment
 *
 * @name：MyTestController @Date：2023/12/21 9:14 @Filename：MyTestController
 */
@RestController
@RequestMapping("/test")
public class MyTestController {
  @Resource private RiskService riskService;

  @PostMapping("/download")
  public ResponseEntity<byte[]> download(HttpServletResponse response) throws Exception {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    // 设置响应内容类型
    response.setContentType("application/octet-stream");
    response.addHeader(
        "Content-Disposition",
        "attachment; filename=" + URLEncoder.encode(FileUtil.getName("目标文档.docx"), "UTF-8"));

    GenerateReportReq req = new GenerateReportReq();
    req.setProjectId(0);
    req.setRiskList(new ArrayList<>());
    riskService.generateReport(req, outputStream);
    // 将输出流中的字节内容转换为字节数组
    byte[] content = outputStream.toByteArray();

    // 设置响应头信息
    HttpHeaders headers = new HttpHeaders();
    headers.setContentLength(content.length);
    return new ResponseEntity<>(content, headers, HttpStatus.OK);
  }

  @PostMapping
  public String redisCache(String uid) {
    return "Hello World";
  }
}

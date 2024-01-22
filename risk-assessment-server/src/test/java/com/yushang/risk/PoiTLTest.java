package com.yushang.risk;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.data.Pictures;
import com.deepoove.poi.plugin.table.LoopRowTableRenderPolicy;
import com.deepoove.poi.policy.TableRenderPolicy;
import com.deepoove.poi.xwpf.NiceXWPFDocument;
import com.microsoft.schemas.vml.CTShape;
import fr.opensagres.poi.xwpf.converter.pdf.PdfConverter;
import fr.opensagres.poi.xwpf.converter.pdf.PdfOptions;
import net.sf.saxon.trans.SymbolicName;
import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.*;
import org.ddr.poi.html.HtmlRenderPolicy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;
import org.springframework.core.io.ClassPathResource;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author：zlp @Package：com.yushang.risk.admin.controller @Project：risk_assessment
 *
 * @name：PoiTLTest @Date：2024/1/17 14:05 @Filename：PoiTLTest
 */
public class PoiTLTest {
  @Test
  void test01() throws IOException {
    Map<String, Object> map = new HashMap<>();
    map.put("portName", "羽觞安全报告");
    map.put("name", "张三");
    map.put("phone", "1888888888");
    map.put("email", "1234567891@qq.com");
    map.put(
        "img1", Pictures.ofLocal("C:\\Users\\zlp\\Desktop\\公司材料\\pdf\\a1.jpeg").fitSize().create());
    map.put(
        "img2", Pictures.ofLocal("C:\\Users\\zlp\\Desktop\\公司材料\\pdf\\a2.jpeg").fitSize().create());
    map.put(
        "img3", Pictures.ofLocal("C:\\Users\\zlp\\Desktop\\公司材料\\pdf\\a3.jpeg").fitSize().create());
    map.put(
        "img4", Pictures.ofLocal("C:\\Users\\zlp\\Desktop\\公司材料\\pdf\\a4.jpeg").fitSize().create());
    map.put(
        "img5", Pictures.ofLocal("C:\\Users\\zlp\\Desktop\\公司材料\\pdf\\b1.jpeg").fitSize().create());
    map.put(
        "img6", Pictures.ofLocal("C:\\Users\\zlp\\Desktop\\公司材料\\pdf\\b2.jpeg").fitSize().create());
    map.put(
        "img7", Pictures.ofLocal("C:\\Users\\zlp\\Desktop\\公司材料\\pdf\\b3.jpeg").fitSize().create());
    List<Map<String, Object>> list = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      Map<String, Object> map1 = new HashMap<>();
      map1.put("name", "漏洞" + i);
      map1.put("theory", "漏洞原理" + i);
      map1.put("harm", "漏洞危害" + i);
      list.add(map1);
    }
    map.put("tab1", list);
    List<Map<String, Object>> list2 = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      Map<String, Object> map1 = new HashMap<>();
      map1.put("function", "渗透功能" + i);
      map1.put("unit", "单位" + i);
      map1.put("unit_price", "单价" + i);
      map1.put("number", "数量" + i);
      list2.add(map1);
    }
    map.put("tab2", list2);

    // 渲染数据
    Configure configure =
        Configure.builder()
            .bind("tab1", new LoopRowTableRenderPolicy())
            .bind("tab2", new LoopRowTableRenderPolicy())
            .build();
    XWPFTemplate compile = XWPFTemplate.compile("D:\\a.docx", configure);
    compile.render(map).writeToFile("D:\\poi.docx");
    // setWordWaterMark(compile.getXWPFDocument(), "南昌羽觞信息技术有限公司", "#FF0000");

    FileInputStream fileInputStream = new FileInputStream("D:\\poi.docx");
    XWPFDocument xwpfDocument = new XWPFDocument(fileInputStream);
    PdfOptions pdfOptions = PdfOptions.create();
    FileOutputStream fileOutputStream = new FileOutputStream("D:\\poi.pdf");
    PdfConverter.getInstance().convert(xwpfDocument, fileOutputStream, pdfOptions);
    fileInputStream.close();
    fileOutputStream.close();
  }

  @Test
  void test02() throws Exception {
    FileInputStream fileInputStream = new FileInputStream("D:\\b.docx");
    XWPFDocument xwpfDocument = new XWPFDocument(fileInputStream);
    PdfOptions pdfOptions = PdfOptions.create();
    FileOutputStream fileOutputStream = new FileOutputStream("D:\\b.pdf");
    PdfConverter.getInstance().convert(xwpfDocument, fileOutputStream, pdfOptions);
    fileInputStream.close();
    fileOutputStream.close();
  }

  @Test
  void test03() throws IOException {
    ClassPathResource resource = new ClassPathResource("template/安全服务漏洞报告模板.docx");
    XWPFTemplate compile = XWPFTemplate.compile(resource.getInputStream());
    compile
        .render(new HashMap<>())
        .writeAndClose(new FileOutputStream("src/main/resources/tem/123456.docx"));
    // compile.render(new HashMap<>()).writeToFile("d:\\1705629154054.docx");  //可以
  }

  @Test
  void test04() {
    System.out.println(
        "new File(\"tem/a.html\").delete() = "
            + new File("src/main/resources/tem/1705648533607.docx").delete());
  }

  @Test
  void test05() {
    String s = "234/23r/df/v/we/tem/sd";
    System.out.println("s.substring(s.lastIndexOf(\"/\")) = " + s.substring(s.lastIndexOf("tem/")));
  }

  @Test
  void test06() {
    System.out.println(
        "new File(\"\").delete() = "
            + new File("src/main/resources/tem/1705648464028.docx").delete());
  }
}

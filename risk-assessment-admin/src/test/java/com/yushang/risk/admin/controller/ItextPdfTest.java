package com.yushang.risk.admin.controller;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.List;

/**
 * @Author：zlp @Package：com.yushang.risk.admin.controller @Project：risk_assessment
 *
 * @name：PdfTest @Date：2024/1/17 9:58 @Filename：PdfTest
 */
public class ItextPdfTest {
  // 定义全局的字体静态变量
  private static Font titlefont;
  private static Font headfont;
  private static Font keyfont;
  private static Font textfont;
  // 最大宽度
  private static int maxWidth = 520;
  // 静态代码块
  static {
    try {
      // 不同字体（这里定义为同一种字体：包含不同字号、不同style）
      BaseFont bfChinese =
          BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
      titlefont = new Font(bfChinese, 16, Font.BOLD);
      headfont = new Font(bfChinese, 14, Font.BOLD);
      keyfont = new Font(bfChinese, 10, Font.BOLD);
      textfont = new Font(bfChinese, 10, Font.NORMAL);

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  void test01() {
    try {
      // 1.新建document对象
      Document document = new Document(PageSize.A4); // 建立一个Document对象

      // 2.建立一个书写器(Writer)与document对象关联
      File file = new File("D:\\PDFDemo.pdf");
      file.createNewFile();
      PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
      writer.setPageEvent(new Watermark("yushang")); // 水印
      writer.setPageEvent(new MyHeaderFooter()); // 页眉/页脚

      // 3.打开文档
      document.open();
      document.addTitle("羽觞安全漏洞报告"); // 标题
      document.addAuthor("羽觞信息技术有限公司"); // 作者
      document.addSubject("安全漏洞报告"); // 主题
      document.addKeywords("信息安全漏洞"); // 关键字
      document.addCreator("羽觞信息技术有限公司"); // 创建者

      // 4.向文档中添加内容
      this.generatePDF(document);

      // 5.关闭文档
      document.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  // 生成PDF文件
  public void generatePDF(Document document) throws Exception {

    // 段落
    Paragraph paragraph = new Paragraph("羽觞安全漏洞报告", titlefont);
    paragraph.setAlignment(1); // 设置文字居中 0靠左   1，居中     2，靠右
    paragraph.setIndentationLeft(12); // 设置左缩进
    paragraph.setIndentationRight(12); // 设置右缩进
    paragraph.setFirstLineIndent(24); // 设置首行缩进
    paragraph.setLeading(20f); // 行间距
    paragraph.setSpacingBefore(5f); // 设置段落上空白
    paragraph.setSpacingAfter(10f); // 设置段落下空白

    List<String[]> data =
        Arrays.asList(
            new String[] {"姓名：张三", "手机：18888888888"}, new String[] {"邮箱：1234567891@qq.com", ""});

    // 创建表格
    PdfPTable table = new PdfPTable(2);
    table.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
    // 遍历数据并添加到表格
    for (String[] rowData : data) {
      table.addCell(createCell(rowData[0], textfont));
      table.addCell(createCell(rowData[1], textfont));
    }

    // 直线
    Paragraph p1 = new Paragraph();
    p1.add(new Chunk(new LineSeparator()));

    // 添加图片
    Image image = Image.getInstance("C:\\Users\\zlp\\Desktop\\公司材料\\pdf\\a.jpeg");
    image.setAlignment(Image.ALIGN_CENTER);
    image.scalePercent(40); // 依照比例缩放

    // 两个table

    // 图片

    // 添加图片
    Image image1 = Image.getInstance("C:\\Users\\zlp\\Desktop\\公司材料\\pdf\\b.jpeg");
    image1.setAlignment(Image.ALIGN_CENTER);
    image1.scalePercent(40); // 依照比例缩放

    document.add(paragraph);
    document.add(table);
    document.add(p1);
    document.add(image);

    document.newPage(); // 手动触发分页
    // document.add(table);
    // document.add(table);
    document.add(image1);
  }

  public PdfPCell createCell(String value, Font font) {
    PdfPCell cell = new PdfPCell();
    cell.setVerticalAlignment(Element.ALIGN_LEFT);
    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    cell.setPhrase(new Phrase(value, font));
    cell.setBorder(0);
    return cell;
  }
}

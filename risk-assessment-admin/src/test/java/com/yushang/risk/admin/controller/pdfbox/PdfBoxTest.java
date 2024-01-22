package com.yushang.risk.admin.controller.pdfbox;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * @Author：zlp @Package：com.yushang.risk.admin.controller.pdfbox @Project：risk_assessment
 *
 * @name：PdfBoxTest @Date：2024/1/17 15:37 @Filename：PdfBoxTest
 */
public class PdfBoxTest {
  @Test
  void test01() throws Exception {
    try (PDDocument doc = new PDDocument()) {
      // 添加页面
      PDPage page1 = new PDPage();
      doc.addPage(page1);

      String imgFileName = "C:\\Users\\zlp\\Desktop\\公司材料\\pdf\\a1.jpeg";
      PDImageXObject pdImage = PDImageXObject.createFromFile(imgFileName, doc);

      // 设置表格参数
      float margin = 50;
      float yStart = page1.getMediaBox().getHeight() - margin;
      float tableWidth = page1.getMediaBox().getWidth() - 2 * margin;
      float yPosition = yStart - 10;
      float tableHeight = 100; // 表格高度
      float marginBotton = 50;

      // 设置字体大小
      int fontSize = 12;
      try (PDPageContentStream cont1 = new PDPageContentStream(doc, page1)) {
        // 设置字体和字号
        PDType1Font font = PDType1Font.HELVETICA_BOLD;
        // File file = new File("font/NotoSansCJKsc-Regular.otf");

        /*         PDType0Font font =
        PDType0Font.load(
            doc, new ClassPathResource("font/NotoSansCJKsc-Regular.otf").getInputStream()); */

        // 设置标题内容
        String titleText = "YUSHANG BUG REPORT";
        cont1.setFont(font, 20);
        cont1.beginText();
        cont1.newLineAtOffset(
            (page1.getMediaBox().getWidth()) / 2 - 120, yPosition); // 设置标题的位置（X，Y）
        cont1.showText(titleText);
        cont1.endText();
        // 绘制表格线
        drawTableOutline(cont1, yStart + 50, tableWidth, tableHeight, margin);

        // 添加表头
        yPosition -= fontSize;

        // 添加数据行
        for (int i = 0; i < 2; i++) {
          yPosition -= fontSize;
          drawText(cont1, margin + 10, yPosition, "Row " + (i + 1) + ", Cell 1", fontSize);
          drawText(cont1, margin + 400, yPosition, "Row " + (i + 1) + ", Cell 2", fontSize);
        }
        cont1.drawImage(pdImage, 50, 430, page1.getMediaBox().getWidth() - 100, 250);
        cont1.drawImage(
            PDImageXObject.createFromFile("C:\\Users\\zlp\\Desktop\\公司材料\\pdf\\a2.jpeg", doc),
            50,
            200,
            page1.getMediaBox().getWidth() - 100,
            250);
        // 添加页面
        PDPage page2 = new PDPage();
        doc.addPage(page2);
        try (PDPageContentStream cont2 = new PDPageContentStream(doc, page2)) {
          cont2.drawImage(
              PDImageXObject.createFromFile("C:\\Users\\zlp\\Desktop\\公司材料\\pdf\\a3.jpeg", doc),
              50,
              100,
              page1.getMediaBox().getWidth() - 100,
              250);
        }
      }
      doc.save("d:/box.pdf");
    }
  }

  private static void drawTableOutline(
      PDPageContentStream contentStream,
      float yStart,
      float tableWidth,
      float tableHeight,
      float margin)
      throws IOException {
    contentStream.setLineWidth(1.5f);
    contentStream.moveTo(margin, yStart);
    // contentStream.lineTo(margin + tableWidth, yStart);
    contentStream.moveTo(margin, yStart - tableHeight);
    contentStream.lineTo(margin + tableWidth, yStart - tableHeight);
    contentStream.stroke();
  }

  private static void drawText(
      PDPageContentStream contentStream, float x, float y, String text, int fontSize)
      throws IOException {
    contentStream.beginText();
    contentStream.newLineAtOffset(x, y);
    contentStream.setFont(PDType1Font.HELVETICA_BOLD, fontSize);
    contentStream.showText(text);
    contentStream.endText();
  }
}

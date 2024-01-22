package com.yushang.risk.admin.controller.flying;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.tool.xml.Pipeline;
import com.itextpdf.tool.xml.XMLWorker;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.itextpdf.tool.xml.html.CssAppliersImpl;
import com.itextpdf.tool.xml.html.Tags;
import com.itextpdf.tool.xml.net.FileRetrieve;
import com.itextpdf.tool.xml.net.ReadingProcessor;
import com.itextpdf.tool.xml.pipeline.css.CSSResolver;
import com.itextpdf.tool.xml.pipeline.css.CssResolverPipeline;
import com.itextpdf.tool.xml.pipeline.end.PdfWriterPipeline;
import com.itextpdf.tool.xml.pipeline.html.AbstractImageProvider;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipelineContext;
import com.itextpdf.tool.xml.pipeline.html.ImageProvider;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.xhtmlrenderer.pdf.ITextRenderer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author：zlp @Package：com.yushang.risk.admin.controller.flying @Project：risk_assessment
 *
 * @name：BaseUtil @Date：2024/1/17 11:19 @Filename：BaseUtil
 */
@Slf4j
public class BaseUtil {

  /**
   * @描述：文件转byte[] @返回：byte[] @作者：zhongjy @时间：2019年7月15日 下午10:19:18
   */
  public static byte[] file2byte(File file) {
    FileInputStream fileInputStream = null;
    byte[] bFile = null;
    try {
      bFile = new byte[(int) file.length()];
      fileInputStream = new FileInputStream(file);
      fileInputStream.read(bFile);
    } catch (Exception e) {
      log.error("", e);
    } finally {
      if (fileInputStream != null) {
        try {
          fileInputStream.close();
        } catch (Exception e) {
          log.error("", e);
        }
      }
    }
    return bFile;
  }
}

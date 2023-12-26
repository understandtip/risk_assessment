package com.yushang.risk;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.data.*;
import com.deepoove.poi.data.style.ParagraphStyle;
import com.deepoove.poi.policy.DocumentRenderPolicy;
import com.deepoove.poi.policy.ListRenderPolicy;
import org.apache.xmlgraphics.util.ClasspathResource;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author：zlp @Package：com.yushang.risk @Project：risk_assessment
 *
 * @name：PoiTest @Date：2023/12/20 9:34 @Filename：PoiTest
 */
public class PoiTest {
  @Test
  void test02() throws IOException {
    Map<String, Object> finalMap = new HashMap<>();
    List<Map<String, Object>> items = new ArrayList<>();
    for (int i = 0; i < 5; i++) {

      // 一个区块
      HashMap<String, Object> data = new HashMap<>();
      ParagraphRenderData paragraphRenderData =
          Paragraphs.of(Texts.of("标题一" + i).create()).create();
      paragraphRenderData.setParagraphStyle(ParagraphStyle.builder().withStyleId("1").build());
      DocumentRenderData document = Documents.of().addParagraph(paragraphRenderData).create();
      data.put("title", document);

      ParagraphRenderData paragraphRenderData1 =
          Paragraphs.of(Texts.of("我是标题二" + i).create()).create();
      paragraphRenderData1.setParagraphStyle(ParagraphStyle.builder().withStyleId("2").build());
      DocumentRenderData document1 = Documents.of().addParagraph(paragraphRenderData1).create();
      data.put("title1", document1);

      data.put("name", "zlp");
      items.add(data);
    }
    finalMap.put("titles", items);
    Configure config =
        Configure.builder()
            .bind("pictures", new ListRenderPolicy())
            .bind("title1", new DocumentRenderPolicy())
            .build();
    XWPFTemplate.compile("D:\\poi-tl\\qu\\titleTest.docx", config)
        .render(finalMap)
        .writeToFile("D:\\poi-tl\\qu\\titleTarget.docx");
  }

  @Test
  void test01() throws IOException {
    HashMap<String, Object> finalMap = new HashMap<>();
    List<Object> var = new ArrayList<>();
    // for (int i = 0; i < 4; i++) {
    //   HashMap<String, Object> map = new HashMap<>();
    //   map.put("riskId1", "R00" + i);
    //   map.put("title1", "越权获取超限的交换数据" + i);
    //   map.put("desc1", "有些数据交换是和第三方开展的。如果对数据交换授权不严格，则有可能导致获取到非授权数据范围、非授权字段，以及大于预期数据量级的数据等。" + i);
    //   map.put("option1", "完全符合");
    //   map.put("score1", "3");
    //   map.put("adv1", "建议" + i);
    //   var.add(map);
    // }
    finalMap.put("image", Pictures.ofUrl("http://43.139.167.19:9000/yushang/1.png").create());
    XWPFTemplate.compile("C:\\Users\\zlp\\Desktop\\公司材料\\a.docx")
        .render(finalMap)
        .writeToFile("C:\\Users\\zlp\\Desktop\\公司材料\\target.docx");
  }

  @Test
  void test03() throws IOException {
    File file = new File("D:\\a.docx");
    XWPFTemplate.compile(file)
        .render(null)
        .writeToFile("C:\\Users\\zlp\\Desktop\\公司材料\\target.docx");
  }
}

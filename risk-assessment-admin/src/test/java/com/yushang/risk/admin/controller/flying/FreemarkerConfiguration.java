package com.yushang.risk.admin.controller.flying;

import freemarker.template.Configuration;

/**
 * @描述：html报表模板配置 @作者：zhongjy @时间：2019年7月15日 下午12:25:59
 */
public class FreemarkerConfiguration {

  private static Configuration config = null;

  static {
    config = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
    config.setClassForTemplateLoading(FreemarkerConfiguration.class, "/report/");
  }

  public static Configuration getConfiguation() {
    return config;
  }
}

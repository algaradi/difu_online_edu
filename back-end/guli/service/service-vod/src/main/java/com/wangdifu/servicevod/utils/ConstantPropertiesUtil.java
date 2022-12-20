package com.wangdifu.servicevod.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Describe:
 * @Author: wangdifu
 * @Date: 2022-11-28 , 0028 15:38
 * @Version: 1.0
 */
@Component
public class ConstantPropertiesUtil implements InitializingBean {

  @Value("${aliyun.oss.file.keyid}")
  private String keyId;

  @Value("${aliyun.oss.file.keysecret}")
  private String keySecret;

  public static String ACCESS_KEY_ID;
  public static String ACCESS_KEY_SECRET;

  @Override
  public void afterPropertiesSet() throws Exception {
    ACCESS_KEY_ID = keyId;
    ACCESS_KEY_SECRET = keySecret;
  }

}

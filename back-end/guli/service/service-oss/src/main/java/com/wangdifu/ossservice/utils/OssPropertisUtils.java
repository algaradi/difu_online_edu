package com.wangdifu.ossservice.utils;

import javafx.fxml.Initializable;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Describe: 读取配置文件的oss数据值--实现spring的InitializingBean进行数据初始后的方法来
 * @Author: wangdifu
 * @Date: 2022-11-7 , 0007 18:59
 * @Version: 1.0
 */
@Component
public class OssPropertisUtils implements InitializingBean {
  @Value("${aliyun.oss.file.endpoint}")
  private String endpoint;
  @Value("${aliyun.oss.file.keyid}")
  private String keyId;
  @Value("${aliyun.oss.file.keysecret}")
  private String keySecret;
  @Value("${aliyun.oss.file.bucketname}")
  private String bucketName;

  // 定义staitc参数，获取时方便
  public static String END_POINT;
  public static String ACCESS_KEY_ID;
  public static String ACCESS_KEY_SECRET;
  public static String BUCKET_NAME;
  @Override
  public void afterPropertiesSet() throws Exception {
    END_POINT = endpoint;
    ACCESS_KEY_ID = keyId;
    ACCESS_KEY_SECRET = keySecret;
    BUCKET_NAME = bucketName;
  }
}

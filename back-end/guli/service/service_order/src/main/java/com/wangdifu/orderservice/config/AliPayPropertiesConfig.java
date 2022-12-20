package com.wangdifu.orderservice.config;

import com.alipay.api.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import javax.annotation.Resource;

import static com.alipay.api.AlipayConstants.SIGN_TYPE;

/**
 * @Describe:
 * @Author: wangdifu
 * @Date: 2022-12-11 , 0011 4:59
 * @Version: 1.0
 */
@Configuration
@PropertySource("classpath:Alipay_sandbox.properties")
public class AliPayPropertiesConfig {

  @Resource
  private Environment config;


  @Bean
 public AlipayClient alipayClient() throws AlipayApiException {

    AlipayConfig alipayConfig = new AlipayConfig();
    //设置网关地址
    alipayConfig.setServerUrl(config.getProperty("alipay.gatewayUrl"));
  //设置应用ID
    alipayConfig.setAppId(config.getProperty("alipay.appId"));
  //设置应用私钥
    alipayConfig.setPrivateKey(config.getProperty("alipay.privateKey"));
  //设置请求格式，固定值json
    alipayConfig.setFormat(AlipayConstants.FORMAT_JSON);
  //设置字符集
    alipayConfig.setCharset(AlipayConstants.CHARSET_UTF8);
  //设置签名类型
    alipayConfig.setSignType(AlipayConstants.SIGN_TYPE_RSA2);
  //设置支付宝公钥
    alipayConfig.setAlipayPublicKey(config.getProperty("alipay.publicKey"));
  //实例化客户端
    AlipayClient alipayClient = new DefaultAlipayClient(alipayConfig);

    return alipayClient;


  }

}

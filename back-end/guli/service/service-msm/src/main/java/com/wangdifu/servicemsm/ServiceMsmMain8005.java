package com.wangdifu.servicemsm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Describe:
 * @Author: wangdifu
 * @Date: 2022-12-2 , 0002 2:02
 * @Version: 1.0
 */
@ComponentScan({"com.wangdifu"})
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)//取消数据源自动配置
public class ServiceMsmMain8005 {
  public static void main(String[] args) {
    SpringApplication.run(ServiceMsmMain8005.class, args);
  }
}


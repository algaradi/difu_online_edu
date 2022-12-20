package com.wangdifu.servicecms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Describe:
 * @Author: wangdifu
 * @Date: 2022-11-30 , 0030 23:06
 * @Version: 1.0
 */
@SpringBootApplication
@ComponentScan({"com.wangdifu"}) //指定扫描位置
@MapperScan("com.wangdifu.servicecms.mapper")
@EnableDiscoveryClient
public class CmsApplication8004 {
  public static void main(String[] args) {
    SpringApplication.run(CmsApplication8004.class, args);
  }
}

package com.wangdifu.eduservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Describe:
 * @Author: wangdifu
 * @Date: 2022-10-29 , 0029 18:41
 * @Version: 1.0
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.wangdifu"})
@EnableDiscoveryClient//nacos 注册
@EnableFeignClients // feign服务调用
public class EduServiceMain8001 {
  public static void main(String[] args) {
    SpringApplication.run(EduServiceMain8001.class,args);
  }
}

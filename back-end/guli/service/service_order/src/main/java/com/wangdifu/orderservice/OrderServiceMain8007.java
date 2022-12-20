package com.wangdifu.orderservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Describe:
 * @Author: wangdifu
 * @Date: 2022-12-10 , 0010 20:39
 * @Version: 1.0
 */
@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication
@ComponentScan("com.wangdifu")
@MapperScan("com.wangdifu.orderservice.mapper")
public class OrderServiceMain8007 {
  public static void main(String[] args) {
    SpringApplication.run(OrderServiceMain8007.class,args);
  }
}

package com.wangdifu.serviceucenter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Describe:
 * @Author: wangdifu
 * @Date: 2022-12-2 , 0002 18:04
 * @Version: 1.0
 */
@ComponentScan(basePackages = {"com.wangdifu"})
@SpringBootApplication//取消数据源自动配置
@MapperScan("com.wangdifu.serviceucenter.mapper")
@EnableFeignClients // feign服务调用
@EnableDiscoveryClient // nacos 注册中心
public class ServiceUCenterMain8006 {
  public static void main(String[] args) {
    SpringApplication.run(ServiceUCenterMain8006.class, args);
  }
}

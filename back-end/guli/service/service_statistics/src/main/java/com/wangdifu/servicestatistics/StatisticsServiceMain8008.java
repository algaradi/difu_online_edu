package com.wangdifu.servicestatistics;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @Describe:
 * @Author: wangdifu
 * @Date: 2022-12-14 , 0014 7:20
 * @Version: 1.0
 */
@SpringBootApplication
@ComponentScan("com.wangdifu")
@MapperScan("com.wangdifu.servicestatistics.mapper")
@EnableDiscoveryClient
@EnableFeignClients
@EnableScheduling
public class StatisticsServiceMain8008 {
  public static void main(String[] args) {
    SpringApplication.run(StatisticsServiceMain8008.class,args);
  }
}

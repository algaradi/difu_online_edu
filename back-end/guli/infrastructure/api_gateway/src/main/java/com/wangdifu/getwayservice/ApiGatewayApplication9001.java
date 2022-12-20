package com.wangdifu.getwayservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Describe:
 * @Author: wangdifu
 * @Date: 2022-12-4 , 0004 1:13
 * @Version: 1.0
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ApiGatewayApplication9001 {
  public static void main(String[] args) {
    SpringApplication.run(ApiGatewayApplication9001.class, args);
  }
}

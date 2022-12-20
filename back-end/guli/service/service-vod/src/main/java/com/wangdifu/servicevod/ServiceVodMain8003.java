package com.wangdifu.servicevod;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Describe:
 * @Author: wangdifu
 * @Date: 2022-11-28 , 0028 15:30
 * @Version: 1.0
 */

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@ComponentScan(basePackages={"com.wangdifu"})
@EnableDiscoveryClient
public class ServiceVodMain8003 {
  public static void main(String[] args) {
    SpringApplication.run(ServiceVodMain8003.class,args);
  }
}

package com.wangdifu.ossservice;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
/**
 * @Describe:
 * @Author: wangdifu
 * @Date: 2022-11-7 , 0007 16:48
 * @Version: 1.0
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
// exclude = DataSourceAutoConfiguration.class 因为用不到数据库加上后避免启动时因为没有数据库配置而报错
@ComponentScan(basePackages = {"com.wangdifu"})
public class MainOssService8002 {
  public static void main(String[] args) {
    SpringApplication.run(MainOssService8002.class,args);
    System.out.println("++++++++++");

  }
}

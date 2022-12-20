package com.wangdifu.orderservice;

import lombok.extern.slf4j.Slf4j;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

import javax.annotation.Resource;

/**
 * @Describe:
 * @Author: wangdifu
 * @Date: 2022-12-11 , 0011 5:02
 * @Version: 1.0
 */
@SpringBootTest
@Slf4j
public class TestAlipayPropertiesConfig {

  @Resource
  private Environment environment;
  @Test
  public void test(){
  log.info(environment.getProperty("notifyUrl"));
  }
}

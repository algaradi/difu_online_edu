package com.wangdifu.eduservice.config;

import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.injector.LogicSqlInjector;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Describe:
 * @Author: wangdifu
 * @Date: 2022-10-29 , 0029 18:39
 * @Version: 1.0
 */
@Configuration
@MapperScan("com.wangdifu.eduservice.mapper")
public class EduServerConfig {

  /**
   * Bean mybatisplus逻辑删除
   * @return
   */
  @Bean
  public ISqlInjector iSqlInjector(){
    return  new LogicSqlInjector();
  }

  @Bean
  public PaginationInterceptor paginationInterceptor(){
    return new PaginationInterceptor();
  }
}

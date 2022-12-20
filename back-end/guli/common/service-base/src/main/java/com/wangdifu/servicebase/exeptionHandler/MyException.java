package com.wangdifu.servicebase.exeptionHandler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Describe: 自定义异常处理
 * @Author: wangdifu
 * @Date: 2022-11-2 , 0002 18:01
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyException extends RuntimeException {

  private Integer code;
  private String msg;

}

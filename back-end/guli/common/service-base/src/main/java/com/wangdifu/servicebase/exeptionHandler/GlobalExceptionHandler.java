package com.wangdifu.servicebase.exeptionHandler;

import com.wangdifu.commonutils.ExceptionUtil;
import com.wangdifu.commonutils.R;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Describe: 全局统一错误配置类
 * @Author: wangdifu
 * @Date: 2022-11-1 , 0001 17:46
 * @Version: 1.0
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

  /**
   * 全局统一 Exception 类型的错误配置。。。
   * @param e
   * @return
   */
  @ExceptionHandler(Exception.class) // 指定错误类型
  @ResponseBody //  由于该类不是在controller层 所以指定返回类型 json 通过ResponseBody
  public R globalException(Exception  e){
    e.printStackTrace();
    log.error(ExceptionUtil.getMessage(e));
     return R.error().message("全局错误提示。。。").code(500);
  }

  /**
   * 特定统一 Exception 类型的错误配置。。。
   * @param e
   * @return
   */
  @ExceptionHandler(NotFoundException.class) // 指定错误类型
  @ResponseBody //  由于该类不是在controller层 所以指定返回类型 json 通过ResponseBody
  public R globalException(NotFoundException  e){
    e.printStackTrace();
    log.error(ExceptionUtil.getMessage(e));
    return R.error().message("找不到资源异常--特定异常").code(404);
  }

  /**
   * 自定义统一 Exception 类型的错误配置。。。
   * @param e
   * @return
   */
  @ExceptionHandler(MyException.class) // 指定错误类型
  @ResponseBody //  由于该类不是在controller层 所以指定返回类型 json 通过ResponseBody
  public R globalException(MyException  e){
    e.printStackTrace();
    log.error(ExceptionUtil.getMessage(e));
    return R.error().message(e.getMsg()).code(e.getCode());
  }


}

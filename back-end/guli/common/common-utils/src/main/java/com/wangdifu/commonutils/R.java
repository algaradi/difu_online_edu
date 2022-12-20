package com.wangdifu.commonutils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @Describe:
 * @Author: wangdifu
 * @Date: 2022-11-1 , 0001 9:42
 * @Version: 1.0
 */
@Data
public class R  {


  private Boolean success;
  private Integer code;
  private String message;
  private Map<String , Object> data = new HashMap<>();


  private R(){}

  public static R ok(){
    R r = new R();
    r.setCode(ResultCode.SUCCESS);
    r.setMessage("成功");
    r.setSuccess(true);
    return  r;
  }

  public static R error(){
    R r = new R();
    r.setSuccess(false);
    r.setMessage("失败");
    r.setCode(ResultCode.ERROR);
   return r;
  }

  public R success(Boolean success){
    this.setSuccess(success);
    return this;
  }

  public R message(String message){
    this.setMessage(message);
    return this;
  }

  public R code(Integer code){
    this.setCode(code);
    return this;
  }

  public R data(String key , Object value){
    this.data.put(key,value);
    return this;
  }

  public R data(Map<String , Object> map){
    this.setData(map);
    return this;
  }

}

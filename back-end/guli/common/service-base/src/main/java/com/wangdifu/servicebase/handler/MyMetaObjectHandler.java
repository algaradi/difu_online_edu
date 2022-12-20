package com.wangdifu.servicebase.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import javax.xml.crypto.Data;
import java.util.Date;

/**
 * @Describe:
 * @Author: wangdifu
 * @Date: 2022-11-1 , 0001 17:05
 * @Version: 1.0
 */
@Component
public class MyMetaObjectHandler  implements MetaObjectHandler  {
  @Override
  public void insertFill(MetaObject metaObject) {
    this.setFieldValByName("gmtCreate" , new Date() , metaObject);
    this.setFieldValByName("gmtModified" , new Date() , metaObject);  }

  @Override
  public void updateFill(MetaObject metaObject) {
    this.setFieldValByName("gmtModified" , new Date() , metaObject);
  }
}

package com.wangdifu.eduservice.client.impl;

import com.wangdifu.eduservice.client.OrderClient;
import org.springframework.stereotype.Component;

/**
 * @Describe:
 * @Author: wangdifu
 * @Date: 2022-12-13 , 0013 2:05
 * @Version: 1.0
 */
@Component
public class OrderClientFallBack implements OrderClient {
  @Override
  public boolean isBuyCourse(String memberid, String courseId) {
    return false;
  }
}

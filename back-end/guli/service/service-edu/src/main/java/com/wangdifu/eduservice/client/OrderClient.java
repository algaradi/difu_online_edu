package com.wangdifu.eduservice.client;

import com.wangdifu.eduservice.client.impl.OrderClientFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Describe:
 * @Author: wangdifu
 * @Date: 2022-12-13 , 0013 1:43
 * @Version: 1.0
 */
@Component
@FeignClient(name = "service-order" , fallback = OrderClientFallBack.class)
public interface OrderClient {

  @GetMapping("/orderservice/order/isBuyCourse/{memberid}/{id}")
  public boolean isBuyCourse(@PathVariable("memberid") String memberid, @PathVariable("id") String courseId);

}

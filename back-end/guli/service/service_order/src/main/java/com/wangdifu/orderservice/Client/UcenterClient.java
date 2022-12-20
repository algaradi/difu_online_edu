package com.wangdifu.orderservice.Client;

import com.wangdifu.commonutils.ordervo.UcenterMemberOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @Describe:
 * @Author: wangdifu
 * @Date: 2022-12-11 , 0011 4:03
 * @Version: 1.0
 */
@Component
@FeignClient(name = "service-ucenter")
public interface UcenterClient {

  @PostMapping("/serviceucenter/ucenter-member/getUserOrderInfo/{id}")
  public UcenterMemberOrder getUserOrderInfo(@PathVariable("id") String id);
}

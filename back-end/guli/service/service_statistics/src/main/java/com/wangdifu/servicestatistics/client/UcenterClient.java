package com.wangdifu.servicestatistics.client;

import com.wangdifu.commonutils.R;
import com.wangdifu.servicestatistics.client.fallback.UcenterClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Describe:
 * @Author: wangdifu
 * @Date: 2022-12-14 , 0014 7:27
 * @Version: 1.0
 */
@Component
@FeignClient(name = "service-ucenter",fallback = UcenterClientFallback.class)
public interface UcenterClient {
  @GetMapping(value = "/serviceucenter/ucenter-member/countregister/{day}")
  public R registerCount(@PathVariable("day") String day);

}

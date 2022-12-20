package com.wangdifu.servicestatistics.client.fallback;

import com.wangdifu.commonutils.R;
import com.wangdifu.servicestatistics.client.UcenterClient;
import org.springframework.stereotype.Component;

/**
 * @Describe:
 * @Author: wangdifu
 * @Date: 2022-12-14 , 0014 7:28
 * @Version: 1.0
 */
@Component
public class UcenterClientFallback implements UcenterClient {
  @Override
  public R registerCount(String day) {
    return R.error().message("调用ucenter统计注册模块失败");
  }
}

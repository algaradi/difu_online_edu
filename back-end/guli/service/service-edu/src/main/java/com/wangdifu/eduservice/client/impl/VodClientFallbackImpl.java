package com.wangdifu.eduservice.client.impl;

import com.wangdifu.commonutils.R;
import com.wangdifu.eduservice.client.VodClient;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Describe:
 * @Author: wangdifu
 * @Date: 2022-11-30 , 0030 20:08
 * @Version: 1.0
 */
@Component
public class VodClientFallbackImpl implements VodClient {
  @Override
  public R removeVideo(String videoId) {
    return R.error().message("删除视频出错");
  }

  @Override
  public R deleteBatch(List<String> listIds) {
    return R.error().message("删除多个视频出错");
  }
}

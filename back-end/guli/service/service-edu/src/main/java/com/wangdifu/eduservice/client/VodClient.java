package com.wangdifu.eduservice.client;

import com.wangdifu.commonutils.R;
import com.wangdifu.eduservice.client.impl.VodClientFallbackImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Describe:
 * @Author: wangdifu
 * @Date: 2022-11-29 , 0029 18:37
 * @Version: 1.0
 */
@FeignClient(name = "service-vod", fallback = VodClientFallbackImpl.class)
@Component
public interface VodClient {

  /**
   * 根据id删除阿里云中的视频
   */
  @DeleteMapping("eduvod/vedio/deleteAliyunVideo/{videoId}")
  public R removeVideo(@PathVariable("videoId") String videoId);


  /**
   *  删除阿里云中多个视频
   */
  @DeleteMapping("eduvod/vedio/deleteBatch")
  public R deleteBatch(@RequestParam("listIds") List<String> listIds);

}

package com.wangdifu.servicevod.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Describe:
 * @Author: wangdifu
 * @Date: 2022-11-28 , 0028 15:44
 * @Version: 1.0
 */
public interface VideoService {
  String uploadAliyunVideo(MultipartFile file);

  // 根据id删除阿里云的视频
  void removeVideo(String videoId);

  void deleteBatch(List<String>  listIds);
}

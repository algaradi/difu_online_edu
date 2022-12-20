package com.wangdifu.ossservice.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @Describe:
 * @Author: wangdifu
 * @Date: 2022-11-7 , 0007 19:07
 * @Version: 1.0
 */
public interface OssService {
  String uploateAvatarFile(MultipartFile file);
  String uploateCoverFile(MultipartFile file);
}

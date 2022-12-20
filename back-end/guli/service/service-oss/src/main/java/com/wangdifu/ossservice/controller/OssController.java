package com.wangdifu.ossservice.controller;

import com.wangdifu.commonutils.R;
import com.wangdifu.ossservice.service.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Describe:
 * @Author: wangdifu
 * @Date: 2022-11-7 , 0007 19:08
 * @Version: 1.0
 */
@RestController
@CrossOrigin
@RequestMapping("/eduOss/fileoss")
public class OssController {

  @Autowired
  private OssService ossService;


  //上传头像方法
  @PostMapping("/avatarUpload")
  public R uplodeAvatarFile(MultipartFile file){
    String avatarUrl = ossService.uploateAvatarFile(file);
    return R.ok().data("avatarUrl",avatarUrl);
  }
  //上传头像方法
  @PostMapping("/coverUpload")
  public R uplodeCoverFile(MultipartFile file){
    String coverUrl = ossService.uploateCoverFile(file);
    return R.ok().data("coverUrl",coverUrl);
  }

}

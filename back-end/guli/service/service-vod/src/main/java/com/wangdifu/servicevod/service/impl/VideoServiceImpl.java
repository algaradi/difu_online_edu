package com.wangdifu.servicevod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;
import com.wangdifu.servicebase.exeptionHandler.MyException;
import com.wangdifu.servicevod.service.VideoService;
import com.wangdifu.servicevod.utils.AliyunVodSDKUtils;
import com.wangdifu.servicevod.utils.ConstantPropertiesUtil;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

/**
 * @Describe:
 * @Author: wangdifu
 * @Date: 2022-11-28 , 0028 15:48
 * @Version: 1.0
 */
@Service
@Slf4j
public class VideoServiceImpl implements VideoService {

  /**
   * 上传视频里到阿里云 返回视频id
   * @param file
   * @return
   */
  @Override
  public String uploadAliyunVideo(MultipartFile file) {

    try {
      InputStream inputStream = file.getInputStream();
      String originalFilename = file.getOriginalFilename();
      String title = originalFilename.substring(0, originalFilename.lastIndexOf("."));

      UploadStreamRequest request = new UploadStreamRequest(
              ConstantPropertiesUtil.ACCESS_KEY_ID,
              ConstantPropertiesUtil.ACCESS_KEY_SECRET,
              title, originalFilename, inputStream);
      /* 存储区域（可选）*/
      request.setStorageLocation("outin-a96e17bf6ef911edb90a00163e1c8dba.oss-cn-shanghai.aliyuncs.com");
        //手动设置服务接入点
      request.setApiRegionId("cn-shanghai");

      UploadVideoImpl uploader = new UploadVideoImpl();
      UploadStreamResponse response = uploader.uploadStream(request);

      //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。
      // 其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
      String videoId = response.getVideoId();
      if (!response.isSuccess()) {
        String errorMessage = "阿里云上传错误：" + "code：" + response.getCode() + ", message：" + response.getMessage();
        log.warn(errorMessage);
        if(StringUtils.isEmpty(videoId)){
          throw new MyException(20001, errorMessage);
        }
      }

      return videoId;
    } catch (IOException e) {
      throw new MyException(20001, "guli vod 服务上传失败");
    }





  }

  // 根据id删除阿里云的视频
  @Override
  public void removeVideo(String videoId) {
    try{
      DefaultAcsClient client = AliyunVodSDKUtils.initVodClient(
              ConstantPropertiesUtil.ACCESS_KEY_ID,
              ConstantPropertiesUtil.ACCESS_KEY_SECRET);

      DeleteVideoRequest request = new DeleteVideoRequest();

      request.setVideoIds(videoId);

      DeleteVideoResponse response = client.getAcsResponse(request);

      System.out.print("RequestId = " + response.getRequestId() + "\n");

    }catch (ClientException e){
      throw new MyException(20001, "视频删除失败");
    }
  }

  /**
   *  删除阿里云中多个视频
   */
  @Override
  public void deleteBatch(List<String> listIds) {
    try{
      DefaultAcsClient client = AliyunVodSDKUtils.initVodClient(
              ConstantPropertiesUtil.ACCESS_KEY_ID,
              ConstantPropertiesUtil.ACCESS_KEY_SECRET);

      DeleteVideoRequest request = new DeleteVideoRequest();

      System.out.println("==============B==============");
      for (String str :
              listIds) {
        System.out.println("=========== str listIds >>>>");
        System.out.println(str);
      }
      //  阿里云删除多个视频 需要传递 1,2,3,4 这样的字符穿形式，因此先把list集合转换为这个形式
      String videoIds = org.apache.commons.lang.StringUtils.join(listIds, ",");
      System.out.println(videoIds);
      request.setVideoIds(videoIds);

      DeleteVideoResponse response = client.getAcsResponse(request);

      System.out.print("RequestId = " + response.getRequestId() + "\n");

    }catch (ClientException e){
      throw new MyException(20001, "视频删除失败");
    }
  }
}

package com.wangdifu.servicevod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.wangdifu.commonutils.R;
import com.wangdifu.servicebase.exeptionHandler.MyException;
import com.wangdifu.servicevod.service.VideoService;
import com.wangdifu.servicevod.utils.AliyunVodSDKUtils;
import com.wangdifu.servicevod.utils.ConstantPropertiesUtil;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Describe:
 * @Author: wangdifu
 * @Date: 2022-11-28 , 0028 15:43
 * @Version: 1.0
 */
@RestController
@CrossOrigin
@RequestMapping("eduvod/vedio")
public class VideoAdminController {

  @Autowired
  private VideoService videoService;

  /**
   * 上传视频到阿里云返回视频的id
   */
  @PostMapping("uploadAliyunVideo")
  public R uploadAliyunVideo(MultipartFile file){
    String videoId = videoService.uploadAliyunVideo(file);
    return R.ok().data("videoId",videoId);
  }

  /**
   * 根据id删除阿里云中的视频
   */
  @DeleteMapping("deleteAliyunVideo/{videoId}")
  public R removeVideo(@PathVariable("videoId") String videoId){
    videoService.removeVideo(videoId);
    return R.ok().message("视频删除成功");
  }

  /**
   *  删除阿里云中多个视频
   */
  @DeleteMapping("deleteBatch")
  public R deleteBatch(@RequestParam("listIds") List<String>  listIds){
    videoService.deleteBatch(listIds);
    return R.ok();
  }


  @GetMapping("getPlayAuth/{id}")
  public  R getPlayAuth(@PathVariable String id){
    try{

      // 创建初始化客户对象
      DefaultAcsClient client = AliyunVodSDKUtils.initVodClient(ConstantPropertiesUtil.ACCESS_KEY_ID, ConstantPropertiesUtil.ACCESS_KEY_SECRET);

      // 创建获取凭证request和response对象
      GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
      // 向request设置视频id
      request.setVideoId(id);
      // 调用方法得到凭证
      GetVideoPlayAuthResponse response = client.getAcsResponse(request);
      String playAuth = response.getPlayAuth();

       return  R.ok().data("playAuth",playAuth);
    }catch (Exception e){
      throw new MyException(20001,"获取视频凭证失败");
    }
  }



}

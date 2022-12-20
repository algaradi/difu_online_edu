package com.wangdifu.eduservice.controller;


import com.wangdifu.commonutils.R;
import com.wangdifu.eduservice.client.VodClient;
import com.wangdifu.eduservice.entity.EduVideo;
import com.wangdifu.eduservice.service.EduVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author wangdifu
 * @since 2022-11-09
 */
@RestController
@RequestMapping("/eduservice/video")
@CrossOrigin
public class EduVideoController {

  @Autowired
  private EduVideoService videoService;


  @Autowired
  private VodClient vodClient; // 微服务接口

  /**
   * 添加小节信息
   */
  @PostMapping("addVideo")
  public R addVideo(@RequestBody EduVideo eduVideo){
    videoService.save(eduVideo);
    return R.ok();
  }

  /**
   * 删除小节 ----》 调用 service-vod 根据视频id 删除阿里云的中的视频
   */
  @DeleteMapping("{chapterId}")
  public R deleteVideo(@PathVariable String chapterId){
    // 根据小节id获取小节再获取阿里云中的视频id
    EduVideo videoChapter = videoService.getById(chapterId);
    String videoSourceId = videoChapter.getVideoSourceId();
    if(!StringUtils.isEmpty(videoSourceId)){
      vodClient.removeVideo(videoSourceId);
    }

    // 删除小节
    boolean b = videoService.removeById(chapterId);
    if(b){
      return R.ok();
    }else {
      return R.error();
    }
  }

  /**
   * 修改小节
   */
  @PostMapping("updateVideo")
  public R updateVideo(@RequestBody EduVideo eduVideo){
    boolean b = videoService.updateById(eduVideo);
      return R.ok();
  }

  /**
   * 根据id获取小节信息
   */
  @GetMapping("getVideo/{chapterId}")
  public R getVideo(@PathVariable String chapterId){
    EduVideo video = videoService.getById(chapterId);
    return R.ok().data("video",video);
  }

}


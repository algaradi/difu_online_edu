package com.wangdifu.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wangdifu.eduservice.client.VodClient;
import com.wangdifu.eduservice.entity.EduVideo;
import com.wangdifu.eduservice.mapper.EduVideoMapper;
import com.wangdifu.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author wangdifu
 * @since 2022-11-09
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {


  @Autowired
  private VodClient vodClient; // 微服务调用接口

  @Override
  public boolean removeByCourseId(String courseId) {
    // 根据课程id 获取所有小节的（视频）的id
    QueryWrapper<EduVideo>  eduVideoQueryWrapper = new QueryWrapper<>();
    eduVideoQueryWrapper.eq("course_id", courseId);
    eduVideoQueryWrapper.select("video_source_id");
    baseMapper.selectList(eduVideoQueryWrapper);
    List<EduVideo> eduVideoList = baseMapper.selectList(eduVideoQueryWrapper);

    // 遍历eduVideoList 得到所有的视频id--》List<String> video_source_id
    List<String> videosIdList = new ArrayList<>();
    for (int i = 0; i < eduVideoList.size(); i++) {
      EduVideo video = eduVideoList.get(i);

      // 获取对象中的视频id值，避免空指针问题
      //String videoSourceId = video.getVideoSourceId();
      //如果不为空就需要添加到videosIdList集合中
      if(!StringUtils.isEmpty(video)){

        System.out.println("=================== video>>>>"+video);
        if(!StringUtils.isEmpty(video.getVideoSourceId())){
          videosIdList.add(video.getVideoSourceId());
        }

      }
    }

    // videosIdList不为空时，就调用微服务远程删除阿里云中的视频
    if(videosIdList.size()>0){
      for (String s :
              videosIdList) {
        System.out.println("-------------------Video Deletet====> "+s);
      }
      vodClient.deleteBatch(videosIdList);
    }


    QueryWrapper<EduVideo> queryWrapper = new QueryWrapper<>();
    queryWrapper.eq("course_id", courseId);
    Integer count = baseMapper.delete(queryWrapper);
    return null != count && count > 0;
  }

}

package com.wangdifu.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wangdifu.eduservice.entity.EduChapter;
import com.wangdifu.eduservice.entity.EduVideo;
import com.wangdifu.eduservice.entity.vo.chapter.ChapterVo;
import com.wangdifu.eduservice.entity.vo.chapter.VideoVo;
import com.wangdifu.eduservice.mapper.EduChapterMapper;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wangdifu.eduservice.service.EduChapterService;
import com.wangdifu.eduservice.service.EduVideoService;
import com.wangdifu.servicebase.exeptionHandler.MyException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author wangdifu
 * @since 2022-11-09
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

  @Autowired
  private EduVideoService eduVideoService;

  @Override
  public List<ChapterVo> getAllChapterList(String courseId) {

    // 1. 根据课程id 获取课程的所有的章节
    QueryWrapper<EduChapter> wrapperChapter = new QueryWrapper<>();
    wrapperChapter.eq("course_id",courseId);
    List<EduChapter> eduChapterList = baseMapper.selectList(wrapperChapter);

    // 2. 根据课程id 查询所有的课程小节
    QueryWrapper<EduVideo> wrapperVideo = new QueryWrapper<>();
    wrapperChapter.eq("course_id",courseId);
    List<EduVideo> eduVideolist = eduVideoService.list(wrapperVideo);

    //创建一个返回最终结果的ChapterVo集合
    List<ChapterVo> finallChapterVoList =  new ArrayList<>();
    //3. 遍历所有的课程章节
    for (int i = 0; i < eduChapterList.size(); i++) {
    EduChapter eduChapter = eduChapterList.get(i);
    ChapterVo chapterVo = new ChapterVo();
    BeanUtils.copyProperties(eduChapter,chapterVo);
    finallChapterVoList.add(chapterVo);

    // 创建一个封装VideoVo的集合
      List<VideoVo> videoVoList = new ArrayList<>();
    //4 遍历所有课程的小节
      for (int m = 0; m < eduVideolist.size(); m++) {
        EduVideo eduVideo = eduVideolist.get(m);
        VideoVo videoVo = new VideoVo();
        BeanUtils.copyProperties(eduVideo,videoVo);
        // 判断： 小节里面的ChapterID 和 章节的id
        if(chapterVo.getId().equals(eduVideo.getChapterId())){
          videoVoList.add(videoVo);
        }

      }

    chapterVo.setChildren(videoVoList);
    }

    return finallChapterVoList;
  }

  @Override
  public Boolean deleteChapter(String chapterId) {

    QueryWrapper<EduVideo>  wrapper = new QueryWrapper<>();
    wrapper.eq("chapter_id",chapterId);

    int count = eduVideoService.count(wrapper);
    if(count>0){
      throw  new MyException(20001,"该分章节下存在视频课程，请先删除视频课程");
    }else{
      int result = baseMapper.deleteById(chapterId);
      return result>0;
    }
  }

  @Override
  public boolean removeByCourseId(String courseId) {
    QueryWrapper<EduChapter> queryWrapper = new QueryWrapper<>();
    queryWrapper.eq("course_id", courseId);
    Integer count = baseMapper.delete(queryWrapper);
    return null != count && count > 0;
  }
}

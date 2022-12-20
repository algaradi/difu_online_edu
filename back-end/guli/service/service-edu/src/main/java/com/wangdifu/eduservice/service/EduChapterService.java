package com.wangdifu.eduservice.service;

import com.wangdifu.eduservice.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wangdifu.eduservice.entity.vo.chapter.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author wangdifu
 * @since 2022-11-09
 */
public interface EduChapterService extends IService<EduChapter> {

  List<ChapterVo> getAllChapterList(String courseId);

  Boolean deleteChapter(String chapterId);

  boolean removeByCourseId(String courseId);
}

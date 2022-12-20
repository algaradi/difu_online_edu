package com.wangdifu.eduservice.controller;


import com.wangdifu.commonutils.R;
import com.wangdifu.eduservice.entity.EduChapter;
import com.wangdifu.eduservice.entity.vo.chapter.ChapterVo;
import com.wangdifu.eduservice.service.EduChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author wangdifu
 * @since 2022-11-09
 */
@RestController
@RequestMapping("/eduservice/chapter")
@CrossOrigin
public class EduChapterController {

  @Autowired
  private EduChapterService eduChapterService;

  @GetMapping("/getAllChapter/{courseId}")
  public R getAllChapter(@PathVariable("courseId") String courseId){
    List<ChapterVo> list = eduChapterService.getAllChapterList(courseId);

    return R.ok().data("chapters",list);
  }


  /**
   * 添加章节
   */
  @PostMapping("addChapter")
  public R addChapter(@RequestBody EduChapter eduChapter){
    eduChapterService.save(eduChapter);
    return R.ok().message("添加章节成功");
  }

  /**
   * 添加章节
   */
  @PostMapping("updateChapter")
  public R updateChapter(@RequestBody EduChapter eduChapter){
    eduChapterService.updateById(eduChapter);
    return R.ok().message("修改章节成功");
  }

  /**
   * 获取章节byid
   */
  @GetMapping("getChapter/{chapterId}")
  public R getChapter(@PathVariable("chapterId") String chapterId){
    EduChapter chapter = eduChapterService.getById(chapterId);
    return R.ok().data("item", chapter);
  }

  /**
   * 删除章节
   */
  @DeleteMapping("deleteChapter/{chapterId}")
  public R deleteChapter(@PathVariable("chapterId") String chapterId){
    Boolean chapter = eduChapterService.deleteChapter(chapterId);
    if(chapter){
      return R.ok();
    }else {
      return R.error();
    }
  }


}


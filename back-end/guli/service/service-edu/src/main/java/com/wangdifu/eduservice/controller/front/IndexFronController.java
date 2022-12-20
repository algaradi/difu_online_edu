package com.wangdifu.eduservice.controller.front;

import com.wangdifu.commonutils.R;
import com.wangdifu.eduservice.entity.EduCourse;
import com.wangdifu.eduservice.entity.EduTeacher;
import com.wangdifu.eduservice.service.EduCourseService;
import com.wangdifu.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Describe:  前台控制器 ， 热门老师，热门课程
 * @Author: wangdifu
 * @Date: 2022-11-30 , 0030 23:55
 * @Version: 1.0
 */
@RestController
@RequestMapping("/eduservice/indexFront")
@CrossOrigin
public class IndexFronController {

  @Autowired
  private EduCourseService eduCourseService;
  @Autowired
  private EduTeacherService eduTeacherService;


  /**
   * 获取热门信息 ， 访问量多前8条课程 以及4讲师信息
   * @return
   */
  @Cacheable(key = "'indexInfo'",value = "hotInfoList")
  @GetMapping("getHotInfo")
  public R getHotCourse(){
    // 获取访问量前8条课程
    List<EduCourse> courseList = eduCourseService.selectHotCourse();

    //4个讲师信息
    List<EduTeacher> teacherList = eduTeacherService.selectHotTeacher();
    return R.ok().data("hotCourse",courseList).data("hotTeacher",teacherList);
  }




}

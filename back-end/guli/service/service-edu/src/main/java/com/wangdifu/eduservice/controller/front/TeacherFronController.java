package com.wangdifu.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wangdifu.commonutils.R;
import com.wangdifu.eduservice.entity.EduCourse;
import com.wangdifu.eduservice.entity.EduTeacher;
import com.wangdifu.eduservice.service.EduCourseService;
import com.wangdifu.eduservice.service.EduTeacherService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Describe:
 * @Author: wangdifu
 * @Date: 2022-12-4 , 0004 15:36
 * @Version: 1.0
 */
@RestController
@RequestMapping("/eduservice/teacherFront")
@CrossOrigin
public class TeacherFronController {

  @Autowired
  private EduTeacherService teacherService;


  @Autowired
  private EduCourseService courseService;
  /***
   * 前台讲师分页查询
   * @param page
   * @param limit
   * @return
   */
  @ApiOperation(value = "分页讲师列表")
  @GetMapping(value = "getTeacherList/{page}/{limit}")
  public R pageList(
          @ApiParam(name = "page", value = "当前页码", required = true)
          @PathVariable Long page,

          @ApiParam(name = "limit", value = "每页记录数", required = true)
          @PathVariable Long limit){

    Page<EduTeacher> pageParam = new Page<EduTeacher>(page, limit);

    Map<String, Object> map = teacherService.pageListWeb(pageParam);

    return  R.ok().data(map);
  }


  /**
   * 讲师前台详情页面， 包括讲师的基本信息以及讲师所讲的课程
   * @param teacherId
   * @return
   */
  @GetMapping("getTeacherInfo/{id}")
  public R  getTeacherInfo(@PathVariable("id") String teacherId){
    // 获取讲师的信息
    EduTeacher teacherInfo = teacherService.getById(teacherId);

    // 获取讲师所讲的课程列表
    QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();
    queryWrapper.eq("teacher_id",teacherId);
    List<EduCourse> teacherCourseList = courseService.list(queryWrapper);
    return R.ok().data("teacherInfo",teacherInfo).data("teacherCourseList",teacherCourseList);
  }

}

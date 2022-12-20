package com.wangdifu.eduservice.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wangdifu.commonutils.R;
import com.wangdifu.eduservice.entity.EduCourse;
import com.wangdifu.eduservice.entity.vo.courssevo.CourseInfoVo;
import com.wangdifu.eduservice.entity.vo.courssevo.CoursePublishVo;
import com.wangdifu.eduservice.entity.vo.courssevo.CourseQuery;
import com.wangdifu.eduservice.service.EduCourseService;
import io.swagger.annotations.ApiParam;
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
@RequestMapping("/eduservice/course")
@CrossOrigin
public class EduCourseController {

  @Autowired
  private EduCourseService eduCourseService;

  /**
   * 添加课程信息
   * @param courseInfoVo
   * @return 返回课程id
   */
  @PostMapping("/addCourseInfo")
  public R  addCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
   String id =  eduCourseService.saveCoourseInfo(courseInfoVo);
    return R.ok().data("courseId",id);
  }
  /***
   * 根据课程id查询课程的信息
   */
  @GetMapping("/getCourseIno/{courseId}")
  public R getCourseInfoBycourseId(@PathVariable("courseId") String courseId){
    CourseInfoVo courseInfoVo = eduCourseService.getCourseInfo(courseId);
    return R.ok().data("courseInfo",courseInfoVo);
  }

  /**
   * 修改课程信息
   */
  @PostMapping("/updateCourseInfo")
  public R updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
    eduCourseService.updateCourseInfo(courseInfoVo);
    return R.ok();
  }

  /**
   * 查询课程最终的信息
   */

  @GetMapping("getCoursePublish/{id}")
  public R getCoursePublishVo(@PathVariable String id){
    CoursePublishVo coursePublishVo = eduCourseService.getCoursePublishVo(id);
    return R.ok().data("coursePublish",coursePublishVo);
  }

  /**
   *  提交发布
   */
  @PutMapping("publishCourse/{courseId}")
  public R publishCourse(@PathVariable String courseId){
    Boolean flag = eduCourseService.publishCourse(courseId);
    return R.ok();
  }

  /**
   * 查询功能
   */
  @GetMapping("{page}/{limit}")
  public R pageQuery(
          @ApiParam(name = "page", value = "当前页码", required = true)
          @PathVariable Long page,

          @ApiParam(name = "limit", value = "每页记录数", required = true)
          @PathVariable Long limit,

          @ApiParam(name = "courseQuery", value = "查询对象", required = false)
                  CourseQuery courseQuery){

    Page<EduCourse> pageParam = new Page<>(page, limit);

    eduCourseService.pageQuery(pageParam, courseQuery);
    List<EduCourse> records = pageParam.getRecords();

    long total = pageParam.getTotal();

    return  R.ok().data("total", total).data("rows", records);
  }

  /**
   * 删除课程信息
   * TODD 视频删除
   */
  @DeleteMapping("{id}")
  public R removeById(
          @ApiParam(name = "id", value = "课程ID", required = true)
          @PathVariable String id){

    boolean result = eduCourseService.removeCourseById(id);
    if(result){
      return R.ok();
    }else{
      return R.error().message("删除失败");
    }
  }
}


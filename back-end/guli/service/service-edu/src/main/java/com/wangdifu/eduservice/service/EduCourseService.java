package com.wangdifu.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wangdifu.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wangdifu.eduservice.entity.vo.courssevo.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author wangdifu
 * @since 2022-11-09
 */
public interface EduCourseService extends IService<EduCourse> {

  String saveCoourseInfo(CourseInfoVo courseInfoVo);

  CourseInfoVo getCourseInfo(String courseId);

  void updateCourseInfo(CourseInfoVo courseInfoVo);

  CoursePublishVo getCoursePublishVo(String id);

  Boolean publishCourse(String courseId);
  void pageQuery(Page<EduCourse> pageParam, CourseQuery courseQuery);

  boolean removeCourseById(String id);

  List<EduCourse> selectHotCourse();

//  前台课程条件分页查询
  Map<String, Object> frontPageListWeb(Page<EduCourse> pageParam, FrontCourseQueryVo courseQuery);




  /**
   * 获取课程信息
   * @param id
   * @return
   */
  FrontCourseSingleVo selectInfoWebById(String courseId);

  /**
   * 更新课程浏览数
   * @param id
   */
  void updatePageViewCount(String id);
}

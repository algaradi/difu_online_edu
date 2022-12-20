package com.wangdifu.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wangdifu.eduservice.entity.EduCourse;
import com.wangdifu.eduservice.entity.EduCourseDescription;
import com.wangdifu.eduservice.entity.EduVideo;
import com.wangdifu.eduservice.entity.vo.courssevo.*;
import com.wangdifu.eduservice.mapper.EduCourseMapper;
import com.wangdifu.eduservice.service.EduChapterService;
import com.wangdifu.eduservice.service.EduCourseDescriptionService;
import com.wangdifu.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wangdifu.eduservice.service.EduVideoService;
import com.wangdifu.servicebase.exeptionHandler.MyException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author wangdifu
 * @since 2022-11-09
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

  @Autowired
  private EduCourseDescriptionService eduCourseDescriptionService;
  @Autowired
  private EduVideoService videoService;
  @Autowired
  private EduChapterService chapterService;

  /**
   * 添加课程信息
   * 需要添加到两个表中 课程表和课程简介表
   * @param courseInfoVo
   * @return
   */
  @Override
  public String saveCoourseInfo(CourseInfoVo courseInfoVo) {
    // 1. 添加到课程表中
    EduCourse eduCourse = new EduCourse();
    BeanUtils.copyProperties(courseInfoVo,eduCourse);
    int insert = baseMapper.insert(eduCourse);
    //判断是否添加成功
    if(insert <= 0){
      throw new MyException(20001,"课程信息添加失败");
    }

    // 添加成功后就获取当前刚添加的 课程ID ，后面用于绑定 课程表和课程简介表
    String id = eduCourse.getId();

    // 2. 添加到课程简介表中
    EduCourseDescription eduCourseDescription = new EduCourseDescription();
    eduCourseDescription.setDescription(courseInfoVo.getDescription());
    eduCourseDescription.setId(id);
    eduCourseDescriptionService.save(eduCourseDescription);

    return id;
  }

  @Override
  public CourseInfoVo getCourseInfo(String courseId) {

    //  修改和封装course表
    CourseInfoVo courseInfoVo = new CourseInfoVo();
    System.out.println("++++++++++"+courseId);
    EduCourse eduCourse = baseMapper.selectById(courseId);
    System.out.println("==========="+eduCourse.getId());
    BeanUtils.copyProperties(eduCourse,courseInfoVo);
    // 修改和封装courseDescribtion表
    EduCourseDescription courseDescription = eduCourseDescriptionService.getById(courseId);
    courseInfoVo.setDescription(courseDescription.getDescription());

    return courseInfoVo;
  }

  @Override
  public void updateCourseInfo(CourseInfoVo courseInfoVo) {
    // 1. 修改course表
    EduCourse eduCourse = new EduCourse();
    BeanUtils.copyProperties(courseInfoVo,eduCourse);
    int i = baseMapper.updateById(eduCourse);
    if(i <= 0){
      throw new MyException(20001,"课程信息修改失败");
    }
    // 2。 修改CourseDescribtion 表
    EduCourseDescription courseDescription = new EduCourseDescription();
    courseDescription.setDescription(courseInfoVo.getDescription());
    courseDescription.setId(courseInfoVo.getId());
    boolean b = eduCourseDescriptionService.updateById(courseDescription);
    if(!b){
      throw new MyException(20001,"课程信息简介添加失败");
    }
  }

  @Override
  public CoursePublishVo getCoursePublishVo(String id) {
    CoursePublishVo coursePublishVo = baseMapper.selectCoursePublishVoById(id);
    return coursePublishVo;
  }

  @Override
  public Boolean publishCourse(String courseId) {
   EduCourse eduCourse = new EduCourse();
    eduCourse.setId(courseId);
    eduCourse.setStatus("Noraml");
    Integer count = baseMapper.updateById(eduCourse);
    return null != count && count > 0;

  }

  @Override
  public void pageQuery(Page<EduCourse> pageParam, CourseQuery courseQuery) {
    QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();
    queryWrapper.orderByDesc("gmt_create");

    if (courseQuery == null){
      baseMapper.selectPage(pageParam, queryWrapper);
      return;
    }

    String title = courseQuery.getTitle();
    String teacherId = courseQuery.getTeacherId();
    String subjectParentId = courseQuery.getSubjectParentId();
    String subjectId = courseQuery.getSubjectId();
    String status = courseQuery.getStatus();

    if (!StringUtils.isEmpty(title)) {
      queryWrapper.like("title", title);
    }

    if (!StringUtils.isEmpty(teacherId) ) {
      queryWrapper.eq("teacher_id", teacherId);
    }

    if (!StringUtils.isEmpty(subjectParentId)) {
      queryWrapper.ge("subject_parent_id", subjectParentId);
    }

    if (!StringUtils.isEmpty(subjectId)) {
      queryWrapper.ge("subject_id", subjectId);
    }

    if (!StringUtils.isEmpty(status)) {
      queryWrapper.ge("status", status);
    }

    baseMapper.selectPage(pageParam, queryWrapper);
  }

  /**
   * 根据课程id  删除课程 包括课程章节，小节视频
   * @param id
   * @return
   */
  @Override
  public boolean removeCourseById(String id) {

    //根据id删除所有视频
    videoService.removeByCourseId(id);

    //根据id删除所有章节
    chapterService.removeByCourseId(id);

    // TODO 删除封面

    Integer result = baseMapper.deleteById(id);
    return null != result && result > 0;
  }

  /**
   * 根据访问量降级排序 获取前8个课程作为热门课程
   * @return
   */
  @Override
  public List<EduCourse> selectHotCourse() {

    QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();
    queryWrapper.orderByDesc("view_count");
    queryWrapper.last("limit 8");
    List<EduCourse> eduCourses = baseMapper.selectList(queryWrapper);
    return eduCourses;
  }

  @Override
  public Map<String, Object> frontPageListWeb(Page<EduCourse> pageParam, FrontCourseQueryVo courseQuery) {
    QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();
    // 一级分类
    if (!StringUtils.isEmpty(courseQuery.getSubjectParentId())) {
      queryWrapper.eq("subject_parent_id", courseQuery.getSubjectParentId());
    }

//    二级分类
    if (!StringUtils.isEmpty(courseQuery.getSubjectId())) {
      queryWrapper.eq("subject_id", courseQuery.getSubjectId());
    }
// 购买量
    if (!StringUtils.isEmpty(courseQuery.getBuyCountSort())) {
      queryWrapper.orderByDesc("buy_count");
    }
//新课程
    if (!StringUtils.isEmpty(courseQuery.getGmtCreateSort())) {
      queryWrapper.orderByDesc("gmt_create");
    }
// 价格
    if (!StringUtils.isEmpty(courseQuery.getPriceSort())) {
      queryWrapper.orderByDesc("price");
    }

    baseMapper.selectPage(pageParam, queryWrapper);

    List<EduCourse> records = pageParam.getRecords();
    long current = pageParam.getCurrent();
    long pages = pageParam.getPages();
    long size = pageParam.getSize();
    long total = pageParam.getTotal();
    boolean hasNext = pageParam.hasNext();
    boolean hasPrevious = pageParam.hasPrevious();

    Map<String, Object> map = new HashMap<String, Object>();
    map.put("items", records);
    map.put("current", current);
    map.put("pages", pages);
    map.put("size", size);
    map.put("total", total);
    map.put("hasNext", hasNext);
    map.put("hasPrevious", hasPrevious);

    return map;
  }



  @Override
  public FrontCourseSingleVo selectInfoWebById(String id) {
    this.updatePageViewCount(id);
    FrontCourseSingleVo frontCourseSingleVo = baseMapper.selectInfoWebById(id);
    return frontCourseSingleVo;
  }

  @Override
  public void updatePageViewCount(String id) {
    EduCourse course = baseMapper.selectById(id);
    course.setViewCount((course.getViewCount() + 1L));
    baseMapper.updateById(course);
  }


}

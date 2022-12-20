package com.wangdifu.eduservice.mapper;

import com.wangdifu.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wangdifu.eduservice.entity.vo.courssevo.CoursePublishVo;
import com.wangdifu.eduservice.entity.vo.courssevo.FrontCourseSingleVo;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author wangdifu
 * @since 2022-11-09
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {

  CoursePublishVo selectCoursePublishVoById(String id);

  FrontCourseSingleVo selectInfoWebById(String courseId);


}

package com.wangdifu.eduservice.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wangdifu.commonutils.JwtUtils;
import com.wangdifu.commonutils.R;
import com.wangdifu.commonutils.ordervo.FrontCourseSingleVoOrder;
import com.wangdifu.eduservice.client.OrderClient;
import com.wangdifu.eduservice.entity.EduCourse;
import com.wangdifu.eduservice.entity.vo.chapter.ChapterVo;
import com.wangdifu.eduservice.entity.vo.courssevo.CourseInfoVo;
import com.wangdifu.eduservice.entity.vo.courssevo.FrontCourseQueryVo;
import com.wangdifu.eduservice.entity.vo.courssevo.FrontCourseSingleVo;
import com.wangdifu.eduservice.service.EduChapterService;
import com.wangdifu.eduservice.service.EduCourseService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @Describe:
 * @Author: wangdifu
 * @Date: 2022-12-4 , 0004 18:57
 * @Version: 1.0
 */
@RestController
@RequestMapping("/eduservice/courseFront")
@CrossOrigin
public class CourseFrontController {

  @Autowired
  private EduCourseService courseService;

  @Autowired
  private EduChapterService chapterService;

  @Autowired
  private OrderClient orderClient;

  /**
   * 前台课程条件分页查询
   * @param page
   * @param limit
   * @param courseQuery
   * @return
   */
  @ApiOperation(value = "分页课程列表")
  @PostMapping(value = "getCourseInfoList/{page}/{limit}")
  public R pageList(
          @PathVariable Long page,
          @PathVariable Long limit,
          @RequestBody(required = false) FrontCourseQueryVo courseQuery) {
    Page<EduCourse> pageParam = new Page<EduCourse>(page, limit);
    Map<String, Object> map = courseService.frontPageListWeb(pageParam, courseQuery);
    return R.ok().data(map);
  }


  /**
   * 更具课程id 获取课程信息， 课程详情信息
   */
  @GetMapping("getCourseAllInfo/{id}")
  public R getById(
          @ApiParam(name = "courseId", value = "课程ID", required = true)
          @PathVariable("id") String courseId, HttpServletRequest request){

    //查询课程信息和讲师信息
    FrontCourseSingleVo courseWebVo = courseService.selectInfoWebById(courseId);

    //查询当前课程的章节信息
    List<ChapterVo> allChapterList = chapterService.getAllChapterList(courseId);

    //远程调用，判断课程是否被购买
    boolean isBuy = orderClient.isBuyCourse(JwtUtils.getMemberIdByJwtToken(request), courseId);

    return R.ok().data("course", courseWebVo).data("chapterVoList", allChapterList).data("isBuy",isBuy);
  }


  /**
   * 根据课程id获取课程信息 ----》 实现订单功能
   * @param courseId
   * @return
   */
  @PostMapping("getCourseInfoOrder/{courseId}")
  public FrontCourseSingleVoOrder getCourseInfoOrder(@PathVariable("courseId") String courseId){
    FrontCourseSingleVo courseInfo = courseService.selectInfoWebById(courseId);
    System.out.println("课程复制前======》"+courseInfo);
    // 复制对象到CourseInfoVoOrder
    FrontCourseSingleVoOrder courseInfoVoOrder = new FrontCourseSingleVoOrder();
    BeanUtils.copyProperties(courseInfo,courseInfoVoOrder);
    System.out.println("课程复制后======》"+courseInfoVoOrder);
    return courseInfoVoOrder;

  }


}

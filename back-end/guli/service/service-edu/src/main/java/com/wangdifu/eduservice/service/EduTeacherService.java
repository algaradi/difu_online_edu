package com.wangdifu.eduservice.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wangdifu.eduservice.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wangdifu.eduservice.entity.vo.TeacherQuery;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author wangdifu
 * @since 2022-10-29
 */
public interface EduTeacherService extends IService<EduTeacher> {

  /**
   * teacher组合条件查询方法
   * @param page
   * @param queryTeacher
   * @return
   */
  void pageQuery(Page<EduTeacher> page , TeacherQuery queryTeacher);

  List<EduTeacher> selectHotTeacher();


  //前台讲师分页查询
  Map<String, Object> pageListWeb(Page<EduTeacher> pageParam);
}

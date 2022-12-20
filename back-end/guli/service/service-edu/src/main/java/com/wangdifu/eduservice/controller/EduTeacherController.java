package com.wangdifu.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wangdifu.commonutils.R;
import com.wangdifu.eduservice.entity.EduTeacher;
import com.wangdifu.eduservice.entity.vo.TeacherQuery;
import com.wangdifu.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author wangdifu
 * @since 2022-10-29
 */
@RestController
@RequestMapping("/eduservice/teacher")
@CrossOrigin
//@CrossOrigin(allowCredentials = "true",origins = "http://localhost:9528") // 解决跨域问题
public class EduTeacherController {

  @Autowired
  private EduTeacherService eduTeacherService;


  @GetMapping("/findAll")
  public R findAllTeacher(){

    List<EduTeacher> list = eduTeacherService.list(null);
    return R.ok().data("items",list);
  }

  @DeleteMapping("/{id}")
  public R deleteById(@PathVariable("id") String id ){
    boolean b = eduTeacherService.removeById(id);
    if(!b){
      return R.error();
    }
    return R.ok();
  }

  @GetMapping("/pageTeacher/{current}/{size}")
  public R pageTeacher(@PathVariable("current") Long current,
                       @PathVariable("size") Long size){

    Page<EduTeacher> pageTeacher = new Page<>(current,size);
    IPage<EduTeacher> page = eduTeacherService.page(pageTeacher, null);
    long total = page.getTotal();
    List<EduTeacher> records = page.getRecords();
    return R.ok().data("total",total).data("records",records);
  }


  @PostMapping("/pageQuery/{current}/{size}")
  public R pageQuery(@PathVariable("current") Long current,
                     @PathVariable("size") Long size ,
                     @RequestBody(required = false) TeacherQuery teacherQuery){
    Page<EduTeacher> pageTeacher = new Page<>(current,size);
    eduTeacherService.pageQuery(pageTeacher,teacherQuery);

    System.out.println("==================================="+teacherQuery);

    long total = pageTeacher.getTotal();
    List<EduTeacher> records = pageTeacher.getRecords();
    return R.ok().data("total",total).data("records",records);
  }

  @PostMapping("/addTeacher")
  public R  addTeacher( @RequestBody EduTeacher teacher){
  boolean save = eduTeacherService.save(teacher);
  if(!save) {
    return  R.error();
  }
  return R.ok();
  }

  @GetMapping("/getTeacher/{id}")
  public R getTeacherById( @PathVariable("id") String id){
    EduTeacher teacher = eduTeacherService.getById(id);
    return R.ok().data("teacher",teacher);
  }

  @PostMapping("/updateTeacher")
  public R updateTeacher( @RequestBody  EduTeacher eduTeacher){
    boolean b = eduTeacherService.updateById(eduTeacher);
    if(!b){
      return R.error();
    }
    return R.ok();
  }



}


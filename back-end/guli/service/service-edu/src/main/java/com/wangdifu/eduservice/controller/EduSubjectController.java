package com.wangdifu.eduservice.controller;


import com.wangdifu.commonutils.R;
import com.wangdifu.eduservice.entity.vo.subject.OneLeveSubject;
import com.wangdifu.eduservice.service.EduSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author wangdifu
 * @since 2022-11-08
 */
@RestController
@RequestMapping("/eduservice/subject")
@CrossOrigin
public class EduSubjectController {

  @Autowired
  private EduSubjectService eduSubjectService;

  @PostMapping("/saveExcelFile")
  public R saveSubjectbyExcelFile(MultipartFile file){
    //1 获取上传的excel文件 MultipartFile
    //返回错误提示信息
    Boolean flag = eduSubjectService.importSubjectData(file, eduSubjectService);
    //判断返回集合是否为成功
    if(flag){
      return R.ok().message("课程分类信息文件添加成功");
    }else{
      return R.error().message("课程分类信息文件添加失败");
    }
  }

 //  树形结构的形式获取课程分类信息
  @GetMapping("/getAllSubjectTree")
  public R  getAllSubjectTree(){
    // 按照指定格式获取所有的课程信息（树形）
    /**
     *    [
     *      一级：{
     *           id：
     *           title：
     *           childern：[
     *              id:
     *              title:
     *           ]
     *         }
     *    ]
     */
    List<OneLeveSubject> list = eduSubjectService.getAllSubjectTree();
    return R.ok().data("list",list);
  }

}


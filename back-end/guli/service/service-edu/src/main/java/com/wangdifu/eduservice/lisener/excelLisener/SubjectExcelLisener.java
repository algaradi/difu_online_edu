package com.wangdifu.eduservice.lisener.excelLisener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wangdifu.eduservice.entity.EduSubject;
import com.wangdifu.eduservice.entity.excel.ExcelSubjectData;
import com.wangdifu.eduservice.service.EduSubjectService;
import com.wangdifu.servicebase.exeptionHandler.MyException;

/**
 * @Describe:
 * @Author: wangdifu
 * @Date: 2022-11-8 , 0008 15:26
 * @Version: 1.0
 */
public class SubjectExcelLisener extends AnalysisEventListener<ExcelSubjectData> {
  // 由于该监听器是不能叫给spring容器来管理，因此无法直接使用spring注解来注入 service接口调用数据
  // 解决该问题我们可以通过构造方法传递service中要调用的接口

  private EduSubjectService eduSubjectService;
  public SubjectExcelLisener() { }
  public SubjectExcelLisener(EduSubjectService eduSubjectService) {
    this.eduSubjectService = eduSubjectService;
  }

  // EasyExcel 一行一行读取excel文件中的信息
  @Override
  public void invoke(ExcelSubjectData excelSubjectData, AnalysisContext analysisContext) {
    //判断文件是否为空
    if(excelSubjectData == null){
      throw new MyException(20001,"文件数据为空，添加失败");
    }

    // 判断和添加一级分类
    EduSubject existOneLevelSubject = this.existOneLevelSubject(this.eduSubjectService, excelSubjectData.getOneLevelSubject());
    if(existOneLevelSubject==null){
      existOneLevelSubject = new EduSubject();
      existOneLevelSubject.setTitle(excelSubjectData.getOneLevelSubject());
      existOneLevelSubject.setParentId("0");
      this.eduSubjectService.save(existOneLevelSubject);
    }

    //获取上一级的Id ，执行这个说明一级存在或者已经加上 那么可直接获取一级的id传递给二级的parentid
   String parentId = existOneLevelSubject.getId();
    // 判断和添加二级分类
    EduSubject existTwoLevelSubject = this.existTwoLevelSubject(this.eduSubjectService, excelSubjectData.getTwoLevelSubject(), parentId);
    if(existTwoLevelSubject==null){
      existTwoLevelSubject = new EduSubject();
      existTwoLevelSubject.setTitle(excelSubjectData.getTwoLevelSubject());
      existTwoLevelSubject.setParentId(parentId);
      this.eduSubjectService.save(existTwoLevelSubject);
    }

  }

  // 判断一级分类是否存在
  private EduSubject existOneLevelSubject(EduSubjectService eduSubjectService, String typeName){
    QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
    wrapper.eq("title",typeName)
            .eq("parent_id","0");
    return eduSubjectService.getOne(wrapper);
  }

  // 判断二级分类是否存在--同一级下不能出现两种相同的二级
  private EduSubject existTwoLevelSubject(EduSubjectService eduSubjectService, String typName,String parentId){
    QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
    wrapper.eq("title",typName)
            .eq("parent_id",parentId);
    return  eduSubjectService.getOne(wrapper);
  }

  @Override
  public void doAfterAllAnalysed(AnalysisContext analysisContext) {

  }
}

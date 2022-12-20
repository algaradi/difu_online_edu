package com.wangdifu.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sun.org.apache.xpath.internal.operations.Bool;
import com.wangdifu.eduservice.entity.EduSubject;
import com.wangdifu.eduservice.entity.excel.ExcelSubjectData;
import com.wangdifu.eduservice.entity.vo.subject.OneLeveSubject;
import com.wangdifu.eduservice.entity.vo.subject.TwoLevelSubject;
import com.wangdifu.eduservice.lisener.excelLisener.SubjectExcelLisener;
import com.wangdifu.eduservice.mapper.EduSubjectMapper;
import com.wangdifu.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wangdifu.servicebase.exeptionHandler.MyException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author wangdifu
 * @since 2022-11-08
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

  @Override
  public Boolean importSubjectData(MultipartFile file, EduSubjectService eduSubjectService) {

    Boolean flag = false;
    try {
      // 1. 获取文件流
      InputStream inputStream = file.getInputStream();
      // 用EasyExcel 读取文件
      EasyExcel.read(inputStream, ExcelSubjectData.class,new SubjectExcelLisener(eduSubjectService)).sheet().doRead();
      flag = true;
      return true;
    } catch (IOException e) {
      flag = false;
       throw new MyException(20001,"文件读取失败");
    }finally {
      return flag;
    }

  }


  /**
   * 按照指定格式获取课程分类信息（树形）
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
  @Override
  public List<OneLeveSubject> getAllSubjectTree() {
    /**
     * 思路：
     *  1. 获取一级分类信息例表 eduOneSubjects
     *  2. 获取二级分类信息例表 eduTwoSubjects
     *  3. 封装一级分类到指定的封装类 OneLevelSubject 然后到 ---[list]
     *  4. 封装二级分离到指定的封装类 TwoLevelSubject
     *  5. 添加TwoLevelSubject到OneLevelSubject ---[list] 的children集合中
     *  6. 返回OneLevelSubject---[list]
     */
    // 1. 获取一级分类信息例表
    QueryWrapper<EduSubject> wrapper1 = new QueryWrapper<>();
    wrapper1.eq("parent_id","0");
    List<EduSubject> eduOneSubjects = baseMapper.selectList(wrapper1);

    // 2. 获取二级分类信息例表
    QueryWrapper<EduSubject> wrapper2 = new QueryWrapper<>();
    wrapper1.ne("parent_id","0");
    List<EduSubject> eduTwoSubjects = baseMapper.selectList(wrapper2);

    //声明一个OneLevelSubject集合 ，添加数据，返回
    List<OneLeveSubject> list = new ArrayList<>();


    // 嵌套循环
    for (int i = 0; i < eduOneSubjects.size(); i++) {
      // 3. 封装一级分类到指定的封装类 OneLevelSubject 然后到 ---[list]
      OneLeveSubject oneLeveSubject = new OneLeveSubject();
      BeanUtils.copyProperties(eduOneSubjects.get(i),oneLeveSubject);
      list.add(oneLeveSubject);

      // 定义一个二级的集合，添加所有一级下的二级信息，然后再添加到list
       List<TwoLevelSubject> twoLevelSubjects = new ArrayList<>();
      // 第二循环
      for (int j = 0; j < eduTwoSubjects.size(); j++) {
        //判断如果当前的二级分类属于当前的一级分类就可以添加，不属于就继续循环到结束
        // 思路： 看当前的二级分类的id是否
        if(oneLeveSubject.getId().equals(eduTwoSubjects.get(j).getParentId())){
          TwoLevelSubject twoLevelSubject = new TwoLevelSubject();
          BeanUtils.copyProperties(eduTwoSubjects.get(j),twoLevelSubject);
          twoLevelSubjects.add(twoLevelSubject);
        }
      }
      oneLeveSubject.setChildren(twoLevelSubjects);
    }


    return list;
  }
}

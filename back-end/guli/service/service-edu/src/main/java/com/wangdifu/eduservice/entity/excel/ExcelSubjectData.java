package com.wangdifu.eduservice.entity.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @Describe: 实现EasyExcel 读excel文件对应的实体类
 * @Author: wangdifu
 * @Date: 2022-11-8 , 0008 15:13
 * @Version: 1.0
 */
@Data
public class ExcelSubjectData {

   @ExcelProperty(index = 0)
  private String oneLevelSubject;
   @ExcelProperty(index = 1)
  private String twoLevelSubject;

}

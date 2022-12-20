package com.wangdifu.eduservice.entity.vo.subject;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Describe: 课程分类 一级
 * @Author: wangdifu
 * @Date: 2022-11-9 , 0009 0:21
 * @Version: 1.0
 */
@Data
public class OneLeveSubject {
  private String id;
  private String title;
  List<TwoLevelSubject> children = new ArrayList<>();
}

package com.wangdifu.eduservice.entity.vo.courssevo;

import lombok.Data;

/**
 * @Describe:
 * @Author: wangdifu
 * @Date: 2022-11-25 , 0025 15:49
 * @Version: 1.0
 */
@Data
public class CoursePublishVo {
  private String id;
  private String title;
  private String cover;
  private Integer lessonNum;
  private String subjectLevelOne;
  private String subjectLevelTwo;
  private String teacherName;
  private String price;//只用于显示
}

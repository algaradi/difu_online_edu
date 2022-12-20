package com.wangdifu.eduservice.entity.vo.courssevo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Describe: 封装前台课程信息
 * @Author: wangdifu
 * @Date: 2022-12-4 , 0004 18:55
 * @Version: 1.0
 */
@Data
public class FrontCourseQueryVo {

  @ApiModelProperty(value = "课程名称")
  private String title;

  @ApiModelProperty(value = "讲师id")
  private String teacherId;

  @ApiModelProperty(value = "一级类别id")
  private String subjectParentId;

  @ApiModelProperty(value = "二级类别id")
  private String subjectId;

  @ApiModelProperty(value = "销量排序")
  private String buyCountSort;

  @ApiModelProperty(value = "最新时间排序")
  private String gmtCreateSort;

  @ApiModelProperty(value = "价格排序")
  private String priceSort;

}

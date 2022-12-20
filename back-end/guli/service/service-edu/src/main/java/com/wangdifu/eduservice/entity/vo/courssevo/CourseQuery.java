package com.wangdifu.eduservice.entity.vo.courssevo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Describe:
 * @Author: wangdifu
 * @Date: 2022-11-26 , 0026 15:08
 * @Version: 1.0
 */
@Data
public class CourseQuery {
  @ApiModelProperty(value = "课程名称")
  private String title;

  @ApiModelProperty(value = "讲师id")
  private String teacherId;

  @ApiModelProperty(value = "一级类别id")
  private String subjectParentId;

  @ApiModelProperty(value = "二级类别id")
  private String subjectId;

  private String status;
}

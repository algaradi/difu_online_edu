package com.wangdifu.eduservice.entity.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @Describe:
 * @Author: wangdifu
 * @Date: 2022-11-1 , 0001 13:01
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TeacherQuery implements Serializable {
  private static final long serialVersionUID = 1L;
  private String name;
  private Integer level;
  private String gmtCreate;
  private String gmtModified;

}

package com.wangdifu.eduservice.entity.vo.chapter;

import lombok.Data;

/**
 * @Describe:
 * @Author: wangdifu
 * @Date: 2022-11-10 , 0010 20:12
 * @Version: 1.0
 */
@Data
public class VideoVo {
  private String id;
  private String title;
  private String videoSourceId;//视频id
  private Integer isFree;

}

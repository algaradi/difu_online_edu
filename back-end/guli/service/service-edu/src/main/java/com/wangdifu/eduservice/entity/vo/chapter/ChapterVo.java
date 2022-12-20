package com.wangdifu.eduservice.entity.vo.chapter;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Describe:
 * @Author: wangdifu
 * @Date: 2022-11-10 , 0010 20:11
 * @Version: 1.0
 */
@Data
public class ChapterVo {
  private String id;
  private String title;

  List<VideoVo> children = new ArrayList<>();
}

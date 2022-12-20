package com.wangdifu.eduservice.service;

import com.wangdifu.eduservice.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author wangdifu
 * @since 2022-11-09
 */
public interface EduVideoService extends IService<EduVideo> {
  boolean removeByCourseId(String courseId);

}

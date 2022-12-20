package com.wangdifu.orderservice.Client;

import com.wangdifu.commonutils.ordervo.FrontCourseSingleVoOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @Describe:
 * @Author: wangdifu
 * @Date: 2022-12-11 , 0011 4:02
 * @Version: 1.0
 */
@Component
@FeignClient(name = "servicr-edu")
public interface EduClient {

  @PostMapping("/eduservice/courseFront/getCourseInfoOrder/{courseId}")
  public FrontCourseSingleVoOrder getCourseInfoOrder(@PathVariable("courseId") String courseId);
}

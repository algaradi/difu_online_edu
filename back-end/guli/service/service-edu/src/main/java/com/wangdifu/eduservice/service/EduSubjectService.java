package com.wangdifu.eduservice.service;

import com.sun.org.apache.xpath.internal.operations.Bool;
import com.wangdifu.eduservice.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wangdifu.eduservice.entity.vo.subject.OneLeveSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author wangdifu
 * @since 2022-11-08
 */
public interface EduSubjectService extends IService<EduSubject> {

  Boolean importSubjectData(MultipartFile file, EduSubjectService eduSubjectService);

  List<OneLeveSubject> getAllSubjectTree();
}

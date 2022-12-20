package com.wangdifu.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wangdifu.eduservice.entity.EduTeacher;
import com.wangdifu.eduservice.entity.vo.TeacherQuery;
import com.wangdifu.eduservice.mapper.EduTeacherMapper;
import com.wangdifu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author wangdifu
 * @since 2022-10-29
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {

  @Override
  public void pageQuery(Page<EduTeacher> page, TeacherQuery queryTeacher) {

    QueryWrapper<EduTeacher> queryWrapper = new QueryWrapper<>();
    queryWrapper.orderByAsc("sort");
    if(queryTeacher == null){
      baseMapper.selectPage(page,queryWrapper);
      return ;
    }

    String name = queryTeacher.getName();
    Integer level = queryTeacher.getLevel();
    String  gmtCreate = queryTeacher.getGmtCreate();
    String  gmtModified = queryTeacher.getGmtModified();

    if(!StringUtils.isEmpty(name)){
     queryWrapper.like("name",name);
    }
    if(!StringUtils.isEmpty(level)){
    queryWrapper.like("level",level);
    }
    if(!StringUtils.isEmpty(gmtCreate)){
     queryWrapper.ge("gmt_create",gmtCreate);
    }
    if(!StringUtils.isEmpty(gmtModified)){
    queryWrapper.le("gmt_create",gmtModified);
    }

    queryWrapper.orderByAsc("gmt_modified");

   baseMapper.selectPage(page,queryWrapper);
  }

  /**
   * 根据id排序获取前四个讲师 作为前台热门老师展示
   * @return
   */
  @Override
  public List<EduTeacher> selectHotTeacher() {

    QueryWrapper<EduTeacher> queryWrapper = new QueryWrapper<>();
    queryWrapper.orderByDesc("id");
    queryWrapper.last("limit 4");
    List<EduTeacher> eduTeachers = baseMapper.selectList(queryWrapper);
    return eduTeachers;
  }

  // 前台讲师分页查询
  @Override
  public Map<String, Object> pageListWeb(Page<EduTeacher> pageParam) {
    QueryWrapper<EduTeacher> queryWrapper = new QueryWrapper<>();
    queryWrapper.orderByAsc("sort");

    baseMapper.selectPage(pageParam, queryWrapper);

    List<EduTeacher> records = pageParam.getRecords();
    long current = pageParam.getCurrent();
    long pages = pageParam.getPages();
    long size = pageParam.getSize();
    long total = pageParam.getTotal();
    boolean hasNext = pageParam.hasNext();
    boolean hasPrevious = pageParam.hasPrevious();

    Map<String, Object> map = new HashMap<String, Object>();
    map.put("items", records);
    map.put("current", current);
    map.put("pages", pages);
    map.put("size", size);
    map.put("total", total);
    map.put("hasNext", hasNext);
    map.put("hasPrevious", hasPrevious);

    return map;
  }
}

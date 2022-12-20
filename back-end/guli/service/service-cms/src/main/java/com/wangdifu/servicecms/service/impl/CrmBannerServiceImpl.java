package com.wangdifu.servicecms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wangdifu.servicecms.entity.CrmBanner;
import com.wangdifu.servicecms.mapper.CrmBannerMapper;
import com.wangdifu.servicecms.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author wangdifu
 * @since 2022-11-30
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {

  /**
   * 根据id排序 获取前两个banner
   * @return
   */
  @Cacheable(key="'banner'",value = "selectList")
  @Override
  public List<CrmBanner> selectList() {
    QueryWrapper<CrmBanner> queryWrapper = new QueryWrapper<>();
    queryWrapper.orderByDesc("id");
    queryWrapper.last("limit 2");
    List<CrmBanner> crmBanners = baseMapper.selectList(queryWrapper);
    return crmBanners;
  }
}

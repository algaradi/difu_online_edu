package com.wangdifu.servicecms.controller;


import com.wangdifu.commonutils.R;
import com.wangdifu.servicecms.entity.CrmBanner;
import com.wangdifu.servicecms.service.CrmBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author wangdifu
 * @since 2022-11-30
 */
@RestController
@RequestMapping("/servicecms/bannerClient")
@CrossOrigin
public class BannerClientController {
  @Autowired
  private CrmBannerService crmBannerService;



  @GetMapping("getAllBanner")
  public R getAllBanner(){
    List<CrmBanner> list = crmBannerService.selectList();
    return R.ok().data("bannerList",list);
  }

}


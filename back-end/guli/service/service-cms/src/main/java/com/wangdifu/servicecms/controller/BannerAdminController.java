package com.wangdifu.servicecms.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wangdifu.commonutils.R;
import com.wangdifu.servicecms.entity.CrmBanner;
import com.wangdifu.servicecms.service.CrmBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 首页banner表 后台控制器
 * </p>
 *
 * @author wangdifu
 * @since 2022-11-30
 */
@RestController
@RequestMapping("/servicecms/bannerAdmin")
@CrossOrigin
public class BannerAdminController {

  @Autowired
  private CrmBannerService crmBannerService;

  /**
   * 分页查询banner 幻灯片
   * @param page
   * @param limit
   * @return
   */
  @GetMapping("pageList/{current}/{size}")
  public R pageList(@PathVariable("current") Long current , @PathVariable("size") Long size){

    Page<CrmBanner>  page = new Page<>(current,size);
    IPage<CrmBanner> bannerPage = crmBannerService.page(page, null);
    long total = bannerPage.getTotal();
    List<CrmBanner> records = bannerPage.getRecords();
    return R.ok().data("total",total).data("records",records);
  }

  /**
   * 根据id获取banner
   * @param bannerId
   * @return
   */
  @GetMapping("{bannerId}")
  public R getBannerById(@PathVariable("bannerId") String bannerId){
    CrmBanner banner = crmBannerService.getById(bannerId);
    return R.ok().data("item",banner);
  }

  @CacheEvict(value = "selectList", allEntries=true)
  @DeleteMapping("{bannerId}")
  public R deleteBanner(@PathVariable("bannerId") String bannerId){
    boolean falg = crmBannerService.removeById(bannerId);
    if(!falg){
      return R.error().message("删除失败");
    }
    return R.ok().message("删除成功");
  }

  @CacheEvict(value = "selectList", allEntries=true)
  @PutMapping("update")
  public R updateBanner(@RequestBody CrmBanner banner){
    boolean update = crmBannerService.update(banner, null);
    if(!update){
      return R.error().message("更新失败");
    }
    return R.ok().message("更新成功");
  }

  @CacheEvict(value = "selectList", allEntries=true)
  @PostMapping("add")
  public R addBanner(@RequestBody CrmBanner banner){
    boolean save = crmBannerService.save(banner);
    if(!save){
      return R.error().message("添加失败");
    }
    return R.ok().message("添加成功");
  }


}


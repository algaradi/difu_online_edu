package com.wangdifu.servicecms.service;

import com.wangdifu.servicecms.entity.CrmBanner;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author wangdifu
 * @since 2022-11-30
 */
public interface CrmBannerService extends IService<CrmBanner> {

  List<CrmBanner> selectList();
}

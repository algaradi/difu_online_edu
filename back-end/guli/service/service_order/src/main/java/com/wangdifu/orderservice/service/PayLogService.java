package com.wangdifu.orderservice.service;

import com.wangdifu.orderservice.entity.PayLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 支付日志表 服务类
 * </p>
 *
 * @author wangdifu
 * @since 2022-12-10
 */
public interface PayLogService extends IService<PayLog> {

  String getAliPayPage(String orderNo);

  String queryAliPayNotify(Map<String,String> params);

  public void updateOrderStatus(Map<String, String> map);
}

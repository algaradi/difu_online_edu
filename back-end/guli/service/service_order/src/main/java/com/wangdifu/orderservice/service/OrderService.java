package com.wangdifu.orderservice.service;

import com.wangdifu.orderservice.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author wangdifu
 * @since 2022-12-10
 */
public interface OrderService extends IService<Order> {

  String createOrder(String courseId, String memberIdByJwtToken);

}

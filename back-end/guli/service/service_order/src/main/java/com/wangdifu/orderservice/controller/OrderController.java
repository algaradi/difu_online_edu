package com.wangdifu.orderservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wangdifu.commonutils.JwtUtils;
import com.wangdifu.commonutils.R;
import com.wangdifu.orderservice.entity.Order;
import com.wangdifu.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author wangdifu
 * @since 2022-12-10
 */
@RestController
@RequestMapping("/orderservice/order")
@CrossOrigin
public class OrderController {
  @Autowired
  private OrderService orderService;
  //根据课程id和用户id创建订单，返回订单id
  @PostMapping("createOrder/{courseId}")
  public R save(@PathVariable String courseId, HttpServletRequest request) {
    String orderId = orderService.createOrder(courseId, JwtUtils.getMemberIdByJwtToken(request));
    return R.ok().data("orderId", orderId);
  }


  @GetMapping("getOrderInfo/{orderId}")
  public R getOrderInfo(@PathVariable String orderId) {
    QueryWrapper<Order> wrapper = new QueryWrapper<>();
    wrapper.eq("order_no",orderId);
    Order order = orderService.getOne(wrapper);
    return R.ok().data("item", order);
  }

  @GetMapping("isBuyCourse/{memberid}/{id}")
  public boolean isBuyCourse(@PathVariable String memberid,
                             @PathVariable String id) {
    //订单状态是1表示支付成功
    int count = orderService.count(new QueryWrapper<Order>().eq("member_id", memberid).eq("course_id", id).eq("status", 1));
    if(count>0) {
      return true;
    } else {
      return false;
    }
  }
}


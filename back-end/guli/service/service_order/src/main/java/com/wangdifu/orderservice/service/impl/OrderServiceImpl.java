package com.wangdifu.orderservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wangdifu.commonutils.ordervo.FrontCourseSingleVoOrder;
import com.wangdifu.commonutils.ordervo.UcenterMemberOrder;
import com.wangdifu.commonutils.utils.OrderNoUtil;
import com.wangdifu.orderservice.Client.EduClient;
import com.wangdifu.orderservice.Client.UcenterClient;
import com.wangdifu.orderservice.entity.Order;
import com.wangdifu.orderservice.mapper.OrderMapper;
import com.wangdifu.orderservice.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author wangdifu
 * @since 2022-12-10
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

  @Autowired
  private  EduClient eduClient;

  @Autowired
  private UcenterClient ucenterClient;


  @Override
  public String createOrder(String courseId, String memberId) {
    System.out.println("订单课程信息courseId=================》"+courseId);

    //  通过远程调用 获取用信息
    UcenterMemberOrder userOrderInfo = ucenterClient.getUserOrderInfo(memberId);
    // 通过远程调用 获取课程信息
    FrontCourseSingleVoOrder courseInfoOrder = eduClient.getCourseInfoOrder(courseId);
    System.out.println("订单课程信息=================》"+courseInfoOrder.getTeacherName());

    // 创建订单对象 Order 往里面设置值然后加入到数据库
    Order order = new Order();

    QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
    queryWrapper.eq("member_id",memberId).eq("course_id",courseId);
    Integer count = baseMapper.selectCount(queryWrapper);
    if(count==0){
      order.setOrderNo(OrderNoUtil.getOrderNo());
      order.setCourseId(courseInfoOrder.getId());
      order.setCourseTitle(courseInfoOrder.getTitle());
      order.setCourseCover(courseInfoOrder.getCover());
      order.setTeacherName(courseInfoOrder.getTeacherName());
      order.setTotalFee(courseInfoOrder.getPrice());
      order.setMemberId(memberId);
      order.setMobile(userOrderInfo.getMobile());
      order.setNickname(userOrderInfo.getNickname());
      order.setStatus(0); // 支付状态
      order.setPayType(2); // 支付类型 ： 微信1 ， 支付宝2
      baseMapper.insert(order);
    }else{
      order = baseMapper.selectOne(queryWrapper);
    }
    return order.getOrderNo();
  }
}

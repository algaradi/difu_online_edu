package com.wangdifu.orderservice.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConstants;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wangdifu.orderservice.entity.Order;
import com.wangdifu.orderservice.entity.PayLog;
import com.wangdifu.orderservice.mapper.PayLogMapper;
import com.wangdifu.orderservice.service.OrderService;
import com.wangdifu.orderservice.service.PayLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wangdifu.servicebase.exeptionHandler.MyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * <p>
 * 支付日志表 服务实现类
 * </p>
 *
 * @author wangdifu
 * @since 2022-12-10
 */
@Service
public class PayLogServiceImpl extends ServiceImpl<PayLogMapper, PayLog> implements PayLogService {

  @Autowired
  private AlipayClient alipayClient;

  @Autowired
  private OrderService orderService;


  @Autowired
  private Environment config;
  @Override
  public String getAliPayPage(String orderNo) {

    QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
    queryWrapper.eq("order_no",orderNo);
    Order order = orderService.getOne(queryWrapper);

    if(order == null){
      throw new MyException(20001,"订单不存在");
    }


    AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
    request.setNotifyUrl(config.getProperty("alipay.notifyUrl"));
    String returnUrl = "http://localhost:3000/course/"+order.getCourseId();
    request.setReturnUrl(returnUrl);
    JSONObject bizContent = new JSONObject();
    bizContent.put("out_trade_no", order.getOrderNo());
    bizContent.put("total_amount", order.getTotalFee());
    bizContent.put("subject", order.getCourseTitle());
    bizContent.put("product_code", "FAST_INSTANT_TRADE_PAY");
      //bizContent.put("time_expire", "2022-08-01 22:00:00");

      //// 商品明细信息，按需传入
      //JSONArray goodsDetail = new JSONArray();
      //JSONObject goods1 = new JSONObject();
      //goods1.put("goods_id", "goodsNo1");
      //goods1.put("goods_name", "子商品1");
      //goods1.put("quantity", 1);
      //goods1.put("price", 0.01);
      //goodsDetail.add(goods1);
      //bizContent.put("goods_detail", goodsDetail);

      //// 扩展信息，按需传入
      //JSONObject extendParams = new JSONObject();
      //extendParams.put("sys_service_provider_id", "2088511833207846");
      //bizContent.put("extend_params", extendParams);

    request.setBizContent(bizContent.toString());
    AlipayTradePagePayResponse response = null;
    try {
      response = alipayClient.pageExecute(request);
    } catch (AlipayApiException e) {
      e.printStackTrace();
    }
    if(response.isSuccess()){
      System.out.println("调用成功");
    } else {
      System.out.println("调用失败");
    }

    return response.getBody();
  }


  /**
   *支付宝异步通知
   * @return
   */
  @Override
  public String queryAliPayNotify(Map<String,String> params) {

    System.out.println("======================================================params==================\n"+params);

    String result = "failur";
    //将异步通知中收到的所有参数都存放到map中

    //调用SDK验证签名
    boolean signVerified = false;
    try {
      signVerified = AlipaySignature.rsaCheckV1(params, config.getProperty("alipay.publicKey"), AlipayConstants.CHARSET_UTF8, AlipayConstants.SIGN_TYPE_RSA2);
      if(!signVerified){
        // 验签失败则记录异常日志，并在response中返回failure.
        System.out.println("验签失败则记录异常日志，并在response中返回failure.");
        return result;
      }
      // 1. 商家需要验证该通知数据中的 out_trade_no 是否为商家系统中创建的订单号。
      String out_tarde_no = params.get("out_trade_no");
      Order order = orderService.getOne(new QueryWrapper<Order>().eq("order_no", out_tarde_no));
      if(order == null){
        System.out.println("1. 商家需要验证该通知数据中的 out_trade_no 是否为商家系统中创建的订单号。");
        return result;
      }

      // 2. 判断 total_amount 是否确实为该订单的实际金额（即商家订单创建时的金额）
      String total_amount = params.get("invoice_amount");
      BigDecimal totalFee = order.getTotalFee();
      String s = String.valueOf(totalFee);
      if(!total_amount.equals(s)){
        System.out.println(" 2. 判断 total_amount 是否确实为该订单的实际金额（即商家订单创建时的金额）");
        return result;
      }

      //3. 校验通知中的 seller_id
      String seller_id = params.get("seller_id");
      if(!seller_id.equals(config.getProperty("alipay.seller-id"))){
        System.out.println("3. 校验通知中的 seller_id");
        return result;
      }

      //4. 验证 app_id 是否为该商家本身。
      String app_id = params.get("app_id");
      if(!app_id.equals(config.getProperty("alipay.appId"))){
        System.out.println("4. 验证 app_id 是否为该商家本身。");
        return result;
      }

      //5. 验证支付状态只有交易通知状态为 TRADE_SUCCESS 或 TRADE_FINISHED 时，支付宝才会认定为买家付款成功。
      String trade_status = params.get("trade_status");
      if(!"TRADE_SUCCESS".equals(trade_status)){
        System.out.println("5. 验证支付状态只有交易通知状态为 TRADE_SUCCESS 或 TRADE_FINISHED 时，支付宝才会认定为买家付款成功。");
        return result;
      }

      // 验签成功后，按照支付结果异步通知中的描述，对支付结果中的业务内容进行二次校验，校验成功后在response中返回success并继续商户自身业务处理，校验失败返回failure
      // 处理业务方法，更新订单状态以及保存支付日志
      this.updateOrderStatus(params);
      result = "success";
      System.out.println("===================================处理业务后==============》支付状态"+result);
      return result;
    } catch (AlipayApiException e) {
      e.printStackTrace();
    }
    return result;
  }


  /**
   * 处理订单业务更新订单状态以及记录支付日志
   * @param map
   */
  @Override
  public void updateOrderStatus(Map<String, String> map) {
    //获取订单id
    String orderNo = map.get("out_trade_no");
    //根据订单id查询订单信息
    QueryWrapper<Order> wrapper = new QueryWrapper<>();
    wrapper.eq("order_no",orderNo);
    Order order = orderService.getOne(wrapper);

    if(order.getStatus().intValue() == 1) return;
    order.setStatus(1);
    orderService.updateById(order);

    //记录支付日志
    PayLog payLog=new PayLog();
    payLog.setOrderNo(order.getOrderNo());//支付订单号
    payLog.setPayTime(new Date());
    payLog.setPayType(2);//支付类型
    payLog.setTotalFee(order.getTotalFee());//总金额(分)
    payLog.setTradeState(map.get("trade_status"));//支付状态
    payLog.setTransactionId(map.get("trade_no"));
    payLog.setAttr(JSONObject.toJSONString(map));
    baseMapper.insert(payLog);//插入到支付日志表
  }


}

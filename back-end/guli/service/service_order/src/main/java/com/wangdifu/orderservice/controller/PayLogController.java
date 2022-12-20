package com.wangdifu.orderservice.controller;


import com.alipay.api.internal.util.AlipaySignature;
import com.wangdifu.commonutils.R;
import com.wangdifu.orderservice.entity.PayLog;
import com.wangdifu.orderservice.service.PayLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author wangdifu
 * @since 2022-12-10
 */
@RestController
@RequestMapping("/orderservice/paylog")
@CrossOrigin
public class PayLogController {


  @Autowired
  private PayLogService aliPayService;



  @PostMapping("alipay/pay/{orderNo}")
  public R getAliPayPage(@PathVariable String orderNo){
    String aliPayPage = aliPayService.getAliPayPage(orderNo);
    return R.ok().data("alipay",aliPayPage);
  }

  @PostMapping("/alipay/notify")
  public String aliPayNotify(@RequestParam Map<String,String> params){

   String result = aliPayService.queryAliPayNotify(params);
   return result;
  }

}


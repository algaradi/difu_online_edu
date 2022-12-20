package com.wangdifu.servicemsm.controller;

import com.wangdifu.commonutils.R;
import com.wangdifu.servicemsm.service.MsmService;
import com.wangdifu.servicemsm.utils.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Describe:
 * @Author: wangdifu
 * @Date: 2022-12-2 , 0002 2:04
 * @Version: 1.0
 */
@RestController
@RequestMapping("servicemsm/msm")
@CrossOrigin
public class MsmApiController {

  @Autowired
  private MsmService msmService;

  @Autowired
  private RedisTemplate<String,String>  redisTemplate;

  /**
   * 发送短信验证码 ， 保存到redis，设置有效时间，从redis获取
   * @param phone
   * @return
   */
  @GetMapping("sendCode/{phone}")
  public R sendCode(@PathVariable("phone")  String phone){
   // 1. 首先从redis中获取验证码
    String code = redisTemplate.opsForValue().get(phone);
    if(!StringUtils.isEmpty(code)){
      return R.ok();
    }

    // 2. 发送验证
    // 生成验证码随机值
     code = RandomUtil.getFourBitRandom();
    Map<String,Object> param = new HashMap<>();
    param.put("code",code);
    boolean isSend = msmService.sendCode(param,phone);
    if(!isSend){
      return R.error().message("短信发送失败");
    }
   // 保存到redis中，设置有效时间，5分钟内有效
    redisTemplate.opsForValue().set(phone, code,5, TimeUnit.MINUTES);
    return R.ok();
  }


}

package com.wangdifu.serviceucenter.controller;


import com.wangdifu.commonutils.JwtUtils;
import com.wangdifu.commonutils.R;
import com.wangdifu.commonutils.ordervo.UcenterMemberOrder;
import com.wangdifu.serviceucenter.entity.UcenterMember;
import com.wangdifu.serviceucenter.entity.vo.LoginVo;
import com.wangdifu.serviceucenter.entity.vo.RegisterVo;
import com.wangdifu.serviceucenter.service.UcenterMemberService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author wangdifu
 * @since 2022-12-02
 */
@RestController
@RequestMapping("/serviceucenter/ucenter-member")
@CrossOrigin
public class UcenterMemberController {

  @Autowired
  private UcenterMemberService memberService;

  @ApiOperation(value = "会员登录")
  @PostMapping("login")
  public R login(@RequestBody LoginVo loginVo) {
    String token = memberService.login(loginVo);
    return R.ok().data("token", token);
  }

  @ApiOperation(value = "会员注册")
  @PostMapping("register")
  public R register(@RequestBody RegisterVo registerVo){
    memberService.register(registerVo);
    return R.ok();
  }


  /**
   * 更具token值获取id，获取用户信息--返回保存到头信息
   * @param request
   * @return
   */
  @ApiOperation(value = "根据token获取登录信息")
  @GetMapping("auth/getLoginInfo")
  public R getLoginInfo(HttpServletRequest request){
    String memberId = JwtUtils.getMemberIdByJwtToken(request);
    UcenterMember user = memberService.getById(memberId);
    return R.ok().data("userInfo",user);

  }


  /**
   * 更具用户id 获取用户信息  ----》 实现订单模块
   * @param id
   * @return
   */
  @PostMapping("getUserOrderInfo/{id}")
  public UcenterMemberOrder getUserOrderInfo(@PathVariable("id") String id){
    UcenterMember member = memberService.getById(id);
    // 复制对象到UcenterMemberOrder
    UcenterMemberOrder ucenterMemberOrder = new UcenterMemberOrder();
    BeanUtils.copyProperties(member,ucenterMemberOrder);
    return ucenterMemberOrder;
  }

  @GetMapping(value = "countregister/{day}")
  public R registerCount(@PathVariable String day){
    Integer count = memberService.countRegisterByDay(day);
    return R.ok().data("countRegister", count);
  }


}


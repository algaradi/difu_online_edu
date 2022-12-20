package com.wangdifu.eduservice.controller;

import com.wangdifu.commonutils.R;
import org.springframework.web.bind.annotation.*;

/**
 * @Describe:
 * @Author: wangdifu
 * @Date: 2022-11-5 , 0005 16:19
 * @Version: 1.0
 */
@RestController
@RequestMapping("/eduservice/user/")
@CrossOrigin // 解决跨域问题
public class EduLoginController {


  //login
  @PostMapping("/login")
  public R login(){
    System.out.println("login----->");
    return R.ok().data("token","admin");
  }
  //info
  @GetMapping("/info")
  public R info(){
    return R.ok().data("roles","[admin]").data("name","name").data("avatar","http://e.hiphotos.baidu.com/image/pic/item/a1ec08fa513d2697e542494057fbb2fb4316d81e.jpg");
  }

}

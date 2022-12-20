package com.wangdifu.serviceucenter.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Describe:
 * @Author: wangdifu
 * @Date: 2022-12-2 , 0002 18:07
 * @Version: 1.0
 */
@Data
@ApiModel(value="注册对象", description="注册对象")
public class RegisterVo {

  @ApiModelProperty(value = "昵称")
  private String nickname;

  @ApiModelProperty(value = "手机号")
  private String mobile;

  @ApiModelProperty(value = "密码")
  private String password;

  @ApiModelProperty(value = "验证码")
  private String code;
}
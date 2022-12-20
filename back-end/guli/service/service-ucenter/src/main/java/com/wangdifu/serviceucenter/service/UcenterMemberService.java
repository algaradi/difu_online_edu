package com.wangdifu.serviceucenter.service;

import com.wangdifu.serviceucenter.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wangdifu.serviceucenter.entity.vo.LoginVo;
import com.wangdifu.serviceucenter.entity.vo.RegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author wangdifu
 * @since 2022-12-02
 */
public interface UcenterMemberService extends IService<UcenterMember> {

  void register(RegisterVo registerVo);

  String login(LoginVo loginVo);

  Integer countRegisterByDay(String day);
}

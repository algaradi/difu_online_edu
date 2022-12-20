package com.wangdifu.serviceucenter.mapper;

import com.wangdifu.serviceucenter.entity.UcenterMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author wangdifu
 * @since 2022-12-02
 */
public interface UcenterMemberMapper extends BaseMapper<UcenterMember> {

  Integer selectRegisterCountByDay(String day);
}

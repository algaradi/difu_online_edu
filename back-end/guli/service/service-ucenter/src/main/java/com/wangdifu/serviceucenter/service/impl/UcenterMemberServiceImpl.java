package com.wangdifu.serviceucenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wangdifu.commonutils.JwtUtils;
import com.wangdifu.commonutils.utils.MD5;
import com.wangdifu.servicebase.exeptionHandler.MyException;
import com.wangdifu.serviceucenter.entity.UcenterMember;
import com.wangdifu.serviceucenter.entity.vo.LoginVo;
import com.wangdifu.serviceucenter.entity.vo.RegisterVo;
import com.wangdifu.serviceucenter.mapper.UcenterMemberMapper;
import com.wangdifu.serviceucenter.service.UcenterMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import sun.misc.UCDecoder;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author wangdifu
 * @since 2022-12-02
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {


  @Autowired
  private RedisTemplate<String,String> redisTemplate;

  /**
   *  注册方法
   * @param registerVo
   */
  @Override
  public void register(RegisterVo registerVo) {
    String code = registerVo.getCode();
    String mobile = registerVo.getMobile();
    String nickname = registerVo.getNickname();
    String password = registerVo.getPassword();

    // 判断其中信息是否有空
    if(StringUtils.isEmpty(code) || StringUtils.isEmpty(password) || StringUtils.isEmpty(mobile) || StringUtils.isEmpty(nickname) ){
      throw new MyException(20001,"注册信息不能为空！");
    }

    // 判断验证码
    // 获取redis中的code
    String redisCode = redisTemplate.opsForValue().get(mobile);
    if(!code.equals(redisCode)){
      throw  new MyException(20001,"手机验证码错误");
    }


    // 判断手机号是否已经存在
    QueryWrapper<UcenterMember>  queryWrapper = new QueryWrapper<>();
    queryWrapper.eq("mobile",mobile);
    Integer count = baseMapper.selectCount(queryWrapper);
    if(count > 0){
      throw new MyException(20001,"该账号（手机号）已存在！");
    }

    // 把信息保存到数据库中
    UcenterMember member = new UcenterMember();
    member.setNickname(nickname);
    member.setMobile(mobile);
    member.setPassword(MD5.encrypt(password));
    member.setIsDisabled(false);
    member.setAvatar("https://edu-wangdifu-guili.oss-cn-chengdu.aliyuncs.com/avatar/2022/11/07/07ab042b028d4bfa91d0ab3b9b1140d2file.png");

    baseMapper.insert(member);
  }


  /**
   * 登录方法
   * @param loginVo
   * @return
   */
  @Override
  public String login(LoginVo loginVo) {
    String mobile = loginVo.getMobile();
    String password = loginVo.getPassword();

    //校验参数
    if(StringUtils.isEmpty(mobile) ||
            StringUtils.isEmpty(password) ||
            StringUtils.isEmpty(mobile)) {
      throw new MyException(20001,"登录信息不能为空！");
    }

    // 查询该账号是否存在
    UcenterMember member = baseMapper.selectOne(new QueryWrapper<UcenterMember>().eq("mobile", mobile));
    if(null == member) {
      throw new MyException(20001,"该账号不存在，请进行注册后再登录！");
    }

    //校验密码
    if(!MD5.encrypt(password).equals(member.getPassword())) {
      throw new MyException(20001,"您输入的密码有误，请用正确的密码登录！");
    }

    //校验是否被禁用
    if(member.getIsDisabled()) {
      throw new MyException(20001,"该用户已被禁用");
    }

    //使用JWT生成token字符串
    String token = JwtUtils.getJwtToken(member.getId(), member.getNickname());
    return token;
  }

  @Override
  public Integer countRegisterByDay(String day) {
    return baseMapper.selectRegisterCountByDay(day);
  }
}

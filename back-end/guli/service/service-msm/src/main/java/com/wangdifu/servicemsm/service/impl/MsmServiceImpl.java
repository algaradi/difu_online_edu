package com.wangdifu.servicemsm.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.wangdifu.commonutils.R;
import com.wangdifu.servicemsm.service.MsmService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * @Describe:
 * @Author: wangdifu
 * @Date: 2022-12-2 , 0002 2:05
 * @Version: 1.0
 */
@Service
public class MsmServiceImpl implements MsmService {

  @Override
  public boolean sendCode(Map<String, Object> param, String phone) {
    if(StringUtils.isEmpty(phone)) return false;

    DefaultProfile profile =
            DefaultProfile.getProfile("default", "LTAI5tLN796AQBdK3zUVv4ZY", "Uh4iI6vTJq79eMnH0qtctOkWqiWmff");
    IAcsClient client = new DefaultAcsClient(profile);

    CommonRequest request = new CommonRequest();
    //request.setProtocol(ProtocolType.HTTPS);
    request.setMethod(MethodType.POST);
    request.setDomain("dysmsapi.aliyuncs.com");
    request.setVersion("2017-05-25");
    request.setAction("SendSms");

    request.putQueryParameter("PhoneNumbers", phone); // 手机号
    request.putQueryParameter("SignName", "我的迪夫在线教育网站"); // 签名
    request.putQueryParameter("TemplateCode", "SMS_262410224"); // 模板code
    request.putQueryParameter("TemplateParam", JSONObject.toJSONString(param));

    try {
      CommonResponse response = client.getCommonResponse(request);
      System.out.println(response.getData());
      return response.getHttpResponse().isSuccess();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }
}

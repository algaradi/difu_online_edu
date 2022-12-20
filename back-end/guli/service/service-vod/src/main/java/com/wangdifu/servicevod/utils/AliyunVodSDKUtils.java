package com.wangdifu.servicevod.utils;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;

/**
 * @Describe:
 * @Author: wangdifu
 * @Date: 2022-11-29 , 0029 15:44
 * @Version: 1.0
 */
public class AliyunVodSDKUtils {
    public static DefaultAcsClient initVodClient(String accessKeyId, String accessKeySecret) throws ClientException {
      String regionId = "cn-shanghai";  // 点播服务接入区域
      DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
      DefaultAcsClient client = new DefaultAcsClient(profile);
      return client;
    }
  }


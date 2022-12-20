package com.wangdifu.servicemsm.service;

import java.util.Map;

/**
 * @Describe:
 * @Author: wangdifu
 * @Date: 2022-12-2 , 0002 2:06
 * @Version: 1.0
 */

public interface MsmService {

  public boolean sendCode(Map<String, Object> param, String phone);
}

package com.wangdifu.servicestatistics.scheduling;

import com.wangdifu.servicestatistics.service.StatisticsDailyService;
import com.wangdifu.servicestatistics.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Date;

/**
 * @Describe:
 * @Author: wangdifu
 * @Date: 2022-12-14 , 0014 10:32
 * @Version: 1.0
 */
@Component
public class SchedualTask {

  @Autowired
  private StatisticsDailyService dailyService;


  /**
   * 分析模块生成数据功能---》自动定时生成
   * 定时任务，每一新天的开始生成上一天的数据
   * 每天凌晨1点执行定时--->把前一天的数据生成
   */
  @Scheduled(cron = "0 0 1 * * ?")
  public void task2() {
    //获取上一天的日期
    String day = DateUtil.formatDate(DateUtil.addDays(new Date(), -1));
    dailyService.createStatisticsByDay(day);

  }
}

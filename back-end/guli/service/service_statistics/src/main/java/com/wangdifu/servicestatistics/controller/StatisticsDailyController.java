package com.wangdifu.servicestatistics.controller;


import com.wangdifu.commonutils.R;
import com.wangdifu.servicestatistics.service.StatisticsDailyService;
import org.ehcache.core.spi.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author wangdifu
 * @since 2022-12-14
 */
@RestController
@RequestMapping("/servicestatistics/statistics-daily")
@CrossOrigin
public class StatisticsDailyController {
  @Autowired
  private StatisticsDailyService statisticsDailyService;

  @PostMapping("{day}")
  public R createStatisticsByDate(@PathVariable String day) {
    statisticsDailyService.createStatisticsByDay(day);
    return R.ok();
  }


  @GetMapping("show-chart/{begin}/{end}/{type}")
  public R showChart(@PathVariable String begin,@PathVariable String end,@PathVariable String type){
    Map<String, Object> map = statisticsDailyService.getChartData(begin, end, type);
    return R.ok().data(map);
  }


}


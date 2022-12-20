package com.wangdifu.servicestatistics.service;

import com.wangdifu.servicestatistics.entity.StatisticsDaily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author wangdifu
 * @since 2022-12-14
 */
public interface StatisticsDailyService extends IService<StatisticsDaily> {
 void createStatisticsByDay(String day);

  Map<String, Object> getChartData(String begin, String end, String type);
}

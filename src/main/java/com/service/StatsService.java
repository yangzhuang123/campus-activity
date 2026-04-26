package com.service;

import java.util.Map;

/**
 * 数据统计服务
 */
public interface StatsService {

    // 获取系统统计数据
    Map<String, Object> getSystemStats();

    // 获取活动统计数据
    Map<String, Object> getActivityStats();

    // 获取用户统计数据
    Map<String, Object> getUserStats();

}

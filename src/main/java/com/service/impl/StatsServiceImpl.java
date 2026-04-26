package com.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.entity.ShetuanhuodongEntity;
import com.entity.UserEntity;
import com.service.StatsService;
import com.service.ShetuanhuodongService;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 数据统计服务实现
 */
@Service("statsService")
public class StatsServiceImpl implements StatsService {

    @Autowired
    private ShetuanhuodongService shetuanhuodongService;

    @Autowired
    private UserService userService;

    @Override
    public Map<String, Object> getSystemStats() {
        Map<String, Object> stats = new HashMap<>();

        // 活动总数
        int activityCount = shetuanhuodongService.selectCount(new EntityWrapper<ShetuanhuodongEntity>().eq("is_deleted", 0));
        stats.put("activityCount", activityCount);

        // 用户总数
        int userCount = userService.selectCount(null);
        stats.put("userCount", userCount);

        // 今日新增活动数
        // TODO: 实现今日新增活动统计

        // 今日新增用户数
        // TODO: 实现今日新增用户统计

        return stats;
    }

    @Override
    public Map<String, Object> getActivityStats() {
        Map<String, Object> stats = new HashMap<>();

        // 活动总数
        int totalCount = shetuanhuodongService.selectCount(new EntityWrapper<ShetuanhuodongEntity>().eq("is_deleted", 0));
        stats.put("totalCount", totalCount);

        // 已关闭报名的活动数
        int closedCount = shetuanhuodongService.selectCount(new EntityWrapper<ShetuanhuodongEntity>().eq("is_deleted", 0).eq("huodongzhuangtai", "已关闭"));
        stats.put("closedCount", closedCount);

        // 未开始的活动数
        // TODO: 实现未开始活动统计

        // 进行中的活动数
        // TODO: 实现进行中活动统计

        // 已结束的活动数
        // TODO: 实现已结束活动统计

        return stats;
    }

    @Override
    public Map<String, Object> getUserStats() {
        Map<String, Object> stats = new HashMap<>();

        // 用户总数
        int totalCount = userService.selectCount(null);
        stats.put("totalCount", totalCount);

        // 按角色统计用户数
        // TODO: 实现按角色统计用户数

        return stats;
    }

}

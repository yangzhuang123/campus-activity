package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.service.StatsService;
import com.utils.R;

/**
 * 数据统计
 */
@RequestMapping("/stats")
@RestController
public class StatsController {
	
	@Autowired
	private StatsService statsService;

	/**
	 * 获取系统统计数据
	 */
    @RequestMapping("/system")
    public R getSystemStats(){
        return R.ok().put("data", statsService.getSystemStats());
    }

    /**
     * 获取活动统计数据
     */
    @RequestMapping("/activity")
    public R getActivityStats(){
        return R.ok().put("data", statsService.getActivityStats());
    }

    /**
     * 获取用户统计数据
     */
    @RequestMapping("/user")
    public R getUserStats(){
        return R.ok().put("data", statsService.getUserStats());
    }

}

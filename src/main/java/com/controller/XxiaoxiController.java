package com.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.entity.XxiaoxiEntity;
import com.service.XxiaoxiService;
import com.utils.PageUtils;
import com.utils.R;

/**
 * 消息通知控制器
 */
@RestController
@RequestMapping("/xiaoxi")
public class XxiaoxiController {
	@Autowired
	private XxiaoxiService xxiaoxiService;

	/**
	 * 消息列表
	 */
	@RequestMapping("/list")
	public R list(@RequestParam Map<String, Object> params) {
		PageUtils page = xxiaoxiService.queryPageByCondition(params);
		return R.ok().put("data", page);
	}

	/**
	 * 标记为已读
	 */
	@RequestMapping("/read/{id}")
	public R read(@PathVariable("id") Integer id) {
		boolean result = xxiaoxiService.markAsRead(id);
		return result ? R.ok() : R.error();
	}

	/**
	 * 标记所有为已读
	 */
	@RequestMapping("/readAll")
	public R readAll(@RequestParam("yonghu") String yonghu, @RequestParam("yonghutable") String yonghutable) {
		boolean result = xxiaoxiService.markAllAsRead(yonghu, yonghutable);
		return result ? R.ok() : R.error();
	}

	/**
	 * 未读消息数量
	 */
	@RequestMapping("/unreadCount")
	public R unreadCount(@RequestParam("yonghu") String yonghu, @RequestParam("yonghutable") String yonghutable) {
		Integer count = xxiaoxiService.getUnreadCount(yonghu, yonghutable);
		return R.ok().put("count", count);
	}

	/**
	 * 发送消息
	 */
	@RequestMapping("/send")
	public R send(@RequestBody XxiaoxiEntity message) {
		boolean result = xxiaoxiService.sendMessage(message);
		return result ? R.ok() : R.error();
	}
}

package com.service;

import com.baomidou.mybatisplus.service.IService;
import com.entity.XxiaoxiEntity;
import com.entity.vo.XxiaoxiVO;
import com.entity.view.XxiaoxiView;
import com.utils.PageUtils;
import java.util.Map;

/**
 * 消息通知服务接口
 */
public interface XxiaoxiService extends IService<XxiaoxiEntity> {

	/**
	 * 分页查询消息通知
	 */
	PageUtils queryPage(Map<String, Object> params);

	/**
	 * 获取消息通知列表
	 */
	PageUtils queryPageByCondition(Map<String, Object> params);

	/**
	 * 根据接收人查询未读消息数量
	 */
	Integer getUnreadCount(String yonghu, String yonghutable);

	/**
	 * 根据接收人查询消息列表
	 */
	PageUtils getMessageList(String yonghu, String yonghutable, int page, int limit);

	/**
	 * 标记消息为已读
	 */
	boolean markAsRead(Integer id);

	/**
	 * 标记所有消息为已读
	 */
	boolean markAllAsRead(String yonghu, String yonghutable);

	/**
	 * 发送消息
	 */
	boolean sendMessage(XxiaoxiEntity message);
}

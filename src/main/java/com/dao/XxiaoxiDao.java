package com.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.entity.XxiaoxiEntity;
import com.entity.view.XxiaoxiView;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;

/**
 * 消息通知数据访问接口
 */
@Repository
public interface XxiaoxiDao extends BaseMapper<XxiaoxiEntity> {

	/**
	 * 获取消息通知列表
	 */
	List<XxiaoxiView> selectListView(Map<String, Object> params);

	/**
	 * 根据接收人查询未读消息数量
	 */
	Integer selectUnreadCount(@Param("yonghu") String yonghu, @Param("yonghutable") String yonghutable);

	/**
	 * 根据接收人查询消息列表
	 */
	List<XxiaoxiView> selectByYonghu(@Param("yonghu") String yonghu, @Param("yonghutable") String yonghutable);
}

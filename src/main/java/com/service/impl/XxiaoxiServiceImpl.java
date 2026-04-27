package com.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.List;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.utils.PageUtils;
import com.utils.Query;

import com.dao.XxiaoxiDao;
import com.entity.XxiaoxiEntity;
import com.entity.vo.XxiaoxiVO;
import com.entity.view.XxiaoxiView;
import com.service.XxiaoxiService;

/**
 * 消息通知服务实现类
 */
@Service("xxiaoxiService")
public class XxiaoxiServiceImpl extends ServiceImpl<XxiaoxiDao, XxiaoxiEntity> implements XxiaoxiService {

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		Page<XxiaoxiEntity> page = this.selectPage(
				new Query<XxiaoxiEntity>(params).getPage(),
				new EntityWrapper<XxiaoxiEntity>()
		);
		return new PageUtils(page);
	}

	@Override
	public PageUtils queryPageByCondition(Map<String, Object> params) {
		Page<XxiaoxiView> page = new Query<XxiaoxiView>(params).getPage();
		page.setRecords(baseMapper.selectListView(params));
		return new PageUtils(page);
	}

	@Override
	public Integer getUnreadCount(String yonghu, String yonghutable) {
		return baseMapper.selectUnreadCount(yonghu, yonghutable);
	}

	@Override
	public PageUtils getMessageList(String yonghu, String yonghutable, int page, int limit) {
		Map<String, Object> params = new java.util.HashMap<>();
		params.put("page", page);
		params.put("limit", limit);
		params.put("yonghu", yonghu);
		params.put("yonghutable", yonghutable);
		return queryPageByCondition(params);
	}

	@Override
	public boolean markAsRead(Integer id) {
		XxiaoxiEntity entity = this.selectById(id);
		if (entity != null) {
			entity.setYuedu(1);
			return this.updateById(entity);
		}
		return false;
	}

	@Override
	public boolean markAllAsRead(String yonghu, String yonghutable) {
		EntityWrapper<XxiaoxiEntity> wrapper = new EntityWrapper<>();
		wrapper.eq("yonghu", yonghu).eq("yonghutable", yonghutable).eq("yuedu", 0);
		XxiaoxiEntity entity = new XxiaoxiEntity();
		entity.setYuedu(1);
		return this.update(entity, wrapper);
	}

	@Override
	public boolean sendMessage(XxiaoxiEntity message) {
		return this.insert(message);
	}
}

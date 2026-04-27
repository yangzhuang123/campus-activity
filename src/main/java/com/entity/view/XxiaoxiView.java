package com.entity.view;

import com.entity.XxiaoxiEntity;
import com.baomidou.mybatisplus.annotations.TableName;
import org.apache.commons.beanutils.BeanUtils;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;

/**
 * 消息通知视图
 */
@TableName("xiaoxi")
public class XxiaoxiView extends XxiaoxiEntity {

	// 接收人名称
	private String yonghuName;

	public XxiaoxiView() {
	}

	public XxiaoxiView(XxiaoxiEntity xiaoxiEntity) {
		try {
			BeanUtils.copyProperties(this, xiaoxiEntity);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	public String getYonghuName() {
		return yonghuName;
	}

	public void setYonghuName(String yonghuName) {
		this.yonghuName = yonghuName;
	}
}

package com.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import java.io.Serializable;
import java.util.Date;

/**
 * 消息通知
 */
@TableName("xiaoxi")
public class XxiaoxiEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId(type = IdType.AUTO)
	private Integer id;

	/**
	 * 接收人id
	 */
	private String yonghu;

	/**
	 * 接收人表
	 */
	private String yonghutable;

	/**
	 * 消息类型
	 */
	private String xiaoxileixing;

	/**
	 * 消息标题
	 */
	private String xiaoxibiaoti;

	/**
	 * 消息内容
	 */
	private String xiaoxineirong;

	/**
	 * 发布时间
	 */
	private Date fabushijian;

	/**
	 * 触发时间
	 */
	private Date chushishijian;

	/**
	 * 已读状态
	 */
	private Integer yuedu;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getYonghu() {
		return yonghu;
	}

	public void setYonghu(String yonghu) {
		this.yonghu = yonghu;
	}

	public String getYonghutable() {
		return yonghutable;
	}

	public void setYonghutable(String yonghutable) {
		this.yonghutable = yonghutable;
	}

	public String getXiaoxileixing() {
		return xiaoxileixing;
	}

	public void setXiaoxileixing(String xiaoxileixing) {
		this.xiaoxileixing = xiaoxileixing;
	}

	public String getXiaoxibiaoti() {
		return xiaoxibiaoti;
	}

	public void setXiaoxibiaoti(String xiaoxibiaoti) {
		this.xiaoxibiaoti = xiaoxibiaoti;
	}

	public String getXiaoxineirong() {
		return xiaoxineirong;
	}

	public void setXiaoxineirong(String xiaoxineirong) {
		this.xiaoxineirong = xiaoxineirong;
	}

	public Date getFabushijian() {
		return fabushijian;
	}

	public void setFabushijian(Date fabushijian) {
		this.fabushijian = fabushijian;
	}

	public Date getChushishijian() {
		return chushishijian;
	}

	public void setChushishijian(Date chushishijian) {
		this.chushishijian = chushishijian;
	}

	public Integer getYuedu() {
		return yuedu;
	}

	public void setYuedu(Integer yuedu) {
		this.yuedu = yuedu;
	}
}

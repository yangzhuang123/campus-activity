package com.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.lang.reflect.InvocationTargetException;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.beanutils.BeanUtils;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.IdType;


@TableName("shetuanhuodong")
public class ShetuanhuodongEntity<T> implements Serializable {
	private static final long serialVersionUID = 1L;


	public ShetuanhuodongEntity() {

	}

	public ShetuanhuodongEntity(T t) {
		try {
			BeanUtils.copyProperties(this, t);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	@TableId
	private Long id;

	private String biaoti;

	private String shetuanmingcheng;

	private String huodongtupian;

	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
	@DateTimeFormat
	private Date kaishishijian;

	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
	@DateTimeFormat
	private Date jieshushijian;

	private Integer huodongrenshu;

	private String huodongdidian;

	private String zhanghao;

	private String huodongxiangqing;

	private String sfsh;

	private String shhf;

	private String huodongzhuangtai;

	private String isPublish;

	private String baomingzhuangtai;

	private Integer isDeleted;

	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat
	private Date addtime;

	public Date getAddtime() {
		return addtime;
	}
	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setBiaoti(String biaoti) {
		this.biaoti = biaoti;
	}

	public String getBiaoti() {
		return biaoti;
	}

	public void setShetuanmingcheng(String shetuanmingcheng) {
		this.shetuanmingcheng = shetuanmingcheng;
	}

	public String getShetuanmingcheng() {
		return shetuanmingcheng;
	}

	public void setHuodongtupian(String huodongtupian) {
		this.huodongtupian = huodongtupian;
	}

	public String getHuodongtupian() {
		return huodongtupian;
	}

	public void setKaishishijian(Date kaishishijian) {
		this.kaishishijian = kaishishijian;
	}

	public Date getKaishishijian() {
		return kaishishijian;
	}

	public void setJieshushijian(Date jieshushijian) {
		this.jieshushijian = jieshushijian;
	}

	public Date getJieshushijian() {
		return jieshushijian;
	}

	public void setHuodongrenshu(Integer huodongrenshu) {
		this.huodongrenshu = huodongrenshu;
	}

	public Integer getHuodongrenshu() {
		return huodongrenshu;
	}

	public void setHuodongdidian(String huodongdidian) {
		this.huodongdidian = huodongdidian;
	}

	public String getHuodongdidian() {
		return huodongdidian;
	}

	public void setZhanghao(String zhanghao) {
		this.zhanghao = zhanghao;
	}

	public String getZhanghao() {
		return zhanghao;
	}

	public void setHuodongxiangqing(String huodongxiangqing) {
		this.huodongxiangqing = huodongxiangqing;
	}

	public String getHuodongxiangqing() {
		return huodongxiangqing;
	}

	public void setSfsh(String sfsh) {
		this.sfsh = sfsh;
	}

	public String getSfsh() {
		return sfsh;
	}

	public void setShhf(String shhf) {
		this.shhf = shhf;
	}

	public String getShhf() {
		return shhf;
	}

	public void setHuodongzhuangtai(String huodongzhuangtai) {
		this.huodongzhuangtai = huodongzhuangtai;
	}

	public String getHuodongzhuangtai() {
		return huodongzhuangtai;
	}

	public void setIsPublish(String isPublish) {
		this.isPublish = isPublish;
	}

	public String getIsPublish() {
		return isPublish;
	}

	public void setBaomingzhuangtai(String baomingzhuangtai) {
		this.baomingzhuangtai = baomingzhuangtai;
	}

	public String getBaomingzhuangtai() {
		return baomingzhuangtai;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Integer getIsDeleted() {
		return isDeleted;
	}

}

package com.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

/** 
 * 用户
 */
@TableName("users")
public class UserEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@TableId(type = IdType.AUTO)
	private Long id;
	
	/**
	 * 用户账号
	 */
	private String username;
	
	/**
	 * 密码
	 */
	private String password;
	
	/**
	 * 用户类型
	 */
	private String role;
	
	/**
	 * 邮箱
	 */
	private String email;
	
	/**
	 * 手机号
	 */
	private String phone;
	
	/**
	 * 昵称
	 */
	private String nickname;
	
	/**
	 * 头像
	 */
	private String avatar;
	
	/**
	 * 性别
	 */
	private String gender;
	
	/**
	 * 生日
	 */
	private Date birthday;
	
	/**
	 * 个人简介
	 */
	private String introduction;
	
	/**
	 * 通知设置
	 */
	private String notificationSettings;
	
	private Date addtime;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getNotificationSettings() {
		return notificationSettings;
	}

	public void setNotificationSettings(String notificationSettings) {
		this.notificationSettings = notificationSettings;
	}

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

}

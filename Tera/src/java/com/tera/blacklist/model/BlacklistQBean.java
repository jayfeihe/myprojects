package com.tera.blacklist.model;

import java.util.Date;

public class BlacklistQBean {
	
	private String name; //姓名
	private String idNo; //证件号码
	private String appId; //申请号
	private String mobile; //手机
	private String qq; //QQ
	private String wechat; //微信号
	private String addProvince; //地址-省
	private String addCity; //地址-市
	private String addCounty; //地址-区县
	private String address; //地址
	private String company; //单位名称
	private String state; // 状态--1：有效，0：无效
	private Date createTimeMin; //创建日期
	private Date createTimeMax; //创建日期
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIdNo() {
		return idNo;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public String getWechat() {
		return wechat;
	}
	public void setWechat(String wechat) {
		this.wechat = wechat;
	}
	public String getAddProvince() {
		return addProvince;
	}
	public void setAddProvince(String addProvince) {
		this.addProvince = addProvince;
	}
	public String getAddCity() {
		return addCity;
	}
	public void setAddCity(String addCity) {
		this.addCity = addCity;
	}
	public String getAddCounty() {
		return addCounty;
	}
	public void setAddCounty(String addCounty) {
		this.addCounty = addCounty;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Date getCreateTimeMin() {
		return createTimeMin;
	}
	public void setCreateTimeMin(Date createTimeMin) {
		this.createTimeMin = createTimeMin;
	}
	public Date getCreateTimeMax() {
		return createTimeMax;
	}
	public void setCreateTimeMax(Date createTimeMax) {
		this.createTimeMax = createTimeMax;
	}
}

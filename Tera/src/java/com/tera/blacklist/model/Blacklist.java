package com.tera.blacklist.model;

import com.tera.util.DateUtils;

/**
 * 
 * 黑名单表实体类
 * <b>功能：</b>BlacklistDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2015-10-08 11:24:44<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public class Blacklist {

	//属性部分
	private int id; //ID
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
	private String platform; //逾期平台
	private int defaultDays; //逾期天数
	private int defaultNum; //逾期笔数
	private double defaultAmount; //逾期金额
	private String state; //状态--1：有效，0：无效
	private String remark; //备注
	private String operator; //操作员
	private java.sql.Timestamp createTime; //创建日期
	private String createTimeStr; //创建日期
	private java.sql.Timestamp updateTime; //修改日期
	private String updateTimeStr; //修改日期
	
	private String allAddress; // 全地址

	//getter部分
	public int getId () {
		return this.id;
	}
	public String getName () {
		return this.name;
	}
	public String getIdNo () {
		return this.idNo;
	}
	public String getAppId () {
		return this.appId;
	}
	public String getMobile () {
		return this.mobile;
	}
	public String getQq () {
		return this.qq;
	}
	public String getWechat () {
		return this.wechat;
	}
	public String getAddProvince () {
		return this.addProvince;
	}
	public String getAddCity () {
		return this.addCity;
	}
	public String getAddCounty () {
		return this.addCounty;
	}
	public String getAddress () {
		return this.address;
	}
	public String getCompany () {
		return this.company;
	}
	public String getPlatform () {
		return this.platform;
	}
	public int getDefaultDays () {
		return this.defaultDays;
	}
	public int getDefaultNum () {
		return this.defaultNum;
	}
	public double getDefaultAmount () {
		return this.defaultAmount;
	}
	public String getState () {
		return this.state;
	}
	public String getRemark () {
		return this.remark;
	}
	public String getOperator () {
		return this.operator;
	}
	public java.sql.Timestamp getCreateTime () {
		return this.createTime;
	}
	//getter部分,Timestamp类型的修改获取String的方法
	public String getCreateTimeStr () {
		return DateUtils.formatTime(this.createTime);
	}
	public java.sql.Timestamp getUpdateTime () {
		return this.updateTime;
	}
	//getter部分,Timestamp类型的修改获取String的方法
	public String getUpdateTimeStr () {
		return DateUtils.formatTime(this.updateTime);
	}

	//setter部分
	public void setId (int id) {
		this.id=id;
	}
	public void setName (String name) {
		this.name=name;
	}
	public void setIdNo (String idNo) {
		this.idNo=idNo;
	}
	public void setAppId (String appId) {
		this.appId=appId;
	}
	public void setMobile (String mobile) {
		this.mobile=mobile;
	}
	public void setQq (String qq) {
		this.qq=qq;
	}
	public void setWechat (String wechat) {
		this.wechat=wechat;
	}
	public void setAddProvince (String addProvince) {
		this.addProvince=addProvince;
	}
	public void setAddCity (String addCity) {
		this.addCity=addCity;
	}
	public void setAddCounty (String addCounty) {
		this.addCounty=addCounty;
	}
	public void setAddress (String address) {
		this.address=address;
	}
	public void setCompany (String company) {
		this.company=company;
	}
	public void setPlatform (String platform) {
		this.platform=platform;
	}
	public void setDefaultDays (int defaultDays) {
		this.defaultDays=defaultDays;
	}
	public void setDefaultNum (int defaultNum) {
		this.defaultNum=defaultNum;
	}
	public void setDefaultAmount (double defaultAmount) {
		this.defaultAmount=defaultAmount;
	}
	public void setState (String state) {
		this.state=state;
	}
	public void setRemark (String remark) {
		this.remark=remark;
	}
	public void setOperator (String operator) {
		this.operator=operator;
	}
	public void setCreateTime (java.sql.Timestamp createTime) {
		this.createTime=createTime;
	}
	public void setCreateTimeStr (String createTimeStr) {
		this.createTimeStr=createTimeStr;
	}
	public void setUpdateTime (java.sql.Timestamp updateTime) {
		this.updateTime=updateTime;
	}
	public void setUpdateTimeStr (String updateTimeStr) {
		this.updateTimeStr=updateTimeStr;
	}
	public String getAllAddress() {
		StringBuilder addBuild = new StringBuilder();
		addBuild.append(addProvince);
		addBuild.append(addCity);
		addBuild.append(addCounty);
		addBuild.append(address);
		allAddress = addBuild.toString();
		return allAddress;
	}
}


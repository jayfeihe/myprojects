package com.tera.feature.asset.model;

import com.tera.util.DateUtils;

/**
 * 
 * 押品检查信息记录
实体类
 * <b>功能：</b>CollateralCheckDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-01-12 10:11:56<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public class CollateralCheck {

	//属性部分
	private int id; //ID
	private int collateralId; //抵押物ID
	private String state; //检查情况
	private String remark; //检查说明
	private java.sql.Timestamp checkTime; //检查时间
	private String checkTimeStr; //检查时间
	private String checkUid; //检查操作人

	//getter部分
	public int getId () {
		return this.id;
	}
	public int getCollateralId () {
		return this.collateralId;
	}
	public String getState () {
		return this.state;
	}
	public String getRemark () {
		return this.remark;
	}
	public java.sql.Timestamp getCheckTime () {
		return this.checkTime;
	}
	//getter部分,Timestamp类型的修改获取String的方法
	public String getCheckTimeStr () {
		return DateUtils.formatTime(this.checkTime);
	}
	public String getCheckUid () {
		return this.checkUid;
	}

	//setter部分
	public void setId (int id) {
		this.id=id;
	}
	public void setCollateralId (int collateralId) {
		this.collateralId=collateralId;
	}
	public void setState (String state) {
		this.state=state;
	}
	public void setRemark (String remark) {
		this.remark=remark;
	}
	public void setCheckTime (java.sql.Timestamp checkTime) {
		this.checkTime=checkTime;
	}
	public void setCheckTimeStr (String checkTimeStr) {
		this.checkTimeStr=checkTimeStr;
	}
	public void setCheckUid (String checkUid) {
		this.checkUid=checkUid;
	}

}


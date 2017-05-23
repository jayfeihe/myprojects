package com.tera.batch.model;

import com.tera.util.DateUtils;

/**
 * 
 * 每月分公司成交量统计实体类
 * <b>功能：</b>ReportDealDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-02-15 10:21:34<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public class ReportDeal {

	//属性部分
	private int id; //
	private String orgId; //分公司
	private String type; //(新增、续贷)
	private String mon; //年月
	private int count; //笔数
	private double amt; //金额
	private java.sql.Timestamp createTime; //创建时间
	private String createTimeStr; //创建时间

	//getter部分
	public int getId () {
		return this.id;
	}
	public String getOrgId () {
		return this.orgId;
	}
	public String getType () {
		return this.type;
	}
	public String getMon () {
		return this.mon;
	}
	public int getCount () {
		return this.count;
	}
	public double getAmt () {
		return this.amt;
	}
	public java.sql.Timestamp getCreateTime () {
		return this.createTime;
	}
	//getter部分,Timestamp类型的修改获取String的方法
	public String getCreateTimeStr () {
		return DateUtils.formatTime(this.createTime);
	}

	//setter部分
	public void setId (int id) {
		this.id=id;
	}
	public void setOrgId (String orgId) {
		this.orgId=orgId;
	}
	public void setType (String type) {
		this.type=type;
	}
	public void setMon (String mon) {
		this.mon=mon;
	}
	public void setCount (int count) {
		this.count=count;
	}
	public void setAmt (double amt) {
		this.amt=amt;
	}
	public void setCreateTime (java.sql.Timestamp createTime) {
		this.createTime=createTime;
	}
	public void setCreateTimeStr (String createTimeStr) {
		this.createTimeStr=createTimeStr;
	}

}


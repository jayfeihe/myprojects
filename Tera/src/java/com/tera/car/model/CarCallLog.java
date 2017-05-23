package com.tera.car.model;

import com.tera.util.DateUtils;

/**
 * 
 * 信用贷款面审调查通话记录实体类
 * <b>功能：</b>CarCallLogDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-09-04 11:55:35<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public class CarCallLog {

	//属性部分
	private int id; //ID
	private int interviewingId; //面审ID
	private String appId; //申请ID
	private java.sql.Timestamp callDate; //拨打时间
	private String callDateStr; //拨打时间
	private String onRelation; //接听关系
	private String onState; //接听状态
	private String remarks; //备注
	private String operator; //操作员
	private String orgId; //所属机构
	private java.sql.Timestamp createTime; //创建日期
	private String createTimeStr; //创建日期
	private java.sql.Timestamp updateTime; //修改日期
	private String updateTimeStr; //修改日期
	private String state; //状态

	//getter部分
	public int getId () {
		return this.id;
	}
	public int getInterviewingId () {
		return this.interviewingId;
	}
	public String getAppId () {
		return this.appId;
	}
	public java.sql.Timestamp getCallDate () {
		return this.callDate;
	}
	//getter部分,Timestamp类型的修改获取String的方法
	public String getCallDateStr () {
		return DateUtils.formatTime(this.callDate);
	}
	public String getOnRelation () {
		return this.onRelation;
	}
	public String getOnState () {
		return this.onState;
	}
	public String getRemarks () {
		return this.remarks;
	}
	public String getOperator () {
		return this.operator;
	}
	public String getOrgId () {
		return this.orgId;
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
	public String getState () {
		return this.state;
	}

	//setter部分
	public void setId (int id) {
		this.id=id;
	}
	public void setInterviewingId (int interviewingId) {
		this.interviewingId=interviewingId;
	}
	public void setAppId (String appId) {
		this.appId=appId;
	}
	public void setCallDate (java.sql.Timestamp callDate) {
		this.callDate=callDate;
	}
	public void setCallDateStr (String callDateStr) {
		this.callDateStr=callDateStr;
	}
	public void setOnRelation (String onRelation) {
		this.onRelation=onRelation;
	}
	public void setOnState (String onState) {
		this.onState=onState;
	}
	public void setRemarks (String remarks) {
		this.remarks=remarks;
	}
	public void setOperator (String operator) {
		this.operator=operator;
	}
	public void setOrgId (String orgId) {
		this.orgId=orgId;
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
	public void setState (String state) {
		this.state=state;
	}

}


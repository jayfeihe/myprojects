package com.tera.car.model;

import com.tera.util.DateUtils;

/**
 * 
 * 信用贷款申请查重信息详细表实体类
 * <b>功能：</b>CarRepeatDetailDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-09-29 16:03:00<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public class CarRepeatDetail {

	//属性部分
	private int id; //ID
	private String appId; //申请ID
	private String repeatAppId; //重复申请ID
	private String type; //类型
	private String value; //值
	private String comment; //备注
	private String state; //状态
	private String operator; //操作员
	private String orgId; //所属机构
	private java.sql.Timestamp createTime; //创建日期
	private String createTimeStr; //创建日期
	private java.sql.Timestamp updateTime; //修改日期
	private String updateTimeStr; //修改日期

	private int number;//重复次数
	private String currentBpmState;// 当前流程状态
	
	
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	//getter部分
	public int getId () {
		return this.id;
	}
	public String getAppId () {
		return this.appId;
	}
	public String getRepeatAppId () {
		return this.repeatAppId;
	}
	public String getType () {
		return this.type;
	}
	public String getValue () {
		return this.value;
	}
	public String getComment () {
		return this.comment;
	}
	public String getState () {
		return this.state;
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

	//setter部分
	public void setId (int id) {
		this.id=id;
	}
	public void setAppId (String appId) {
		this.appId=appId;
	}
	public void setRepeatAppId (String repeatAppId) {
		this.repeatAppId=repeatAppId;
	}
	public void setType (String type) {
		this.type=type;
	}
	public void setValue (String value) {
		this.value=value;
	}
	public void setComment (String comment) {
		this.comment=comment;
	}
	public void setState (String state) {
		this.state=state;
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
	public String getCurrentBpmState() {
		return currentBpmState;
	}
	public void setCurrentBpmState(String currentBpmState) {
		this.currentBpmState = currentBpmState;
	}
}


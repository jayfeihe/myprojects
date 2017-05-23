package com.tera.renhang.model;

import com.tera.util.DateUtils;

/**
 * 
 * 信用贷款人行报告查询明细实体类
 * <b>功能：</b>RhQueryDetailDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2015-01-14 16:07:46<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public class RhQueryDetail {

	//属性部分
	private int id; //ID
	private String appId; //申请ID
	private int queryId; //查询ID
	private java.util.Date queryDate; //查询日期
	private String queryDateStr; //查询日期
	private String queryOperator; //查询操作员
	private String queryReason; //查询原因
	private String remarks; //备注
	private String state; //状态
	private String operator; //操作员
	private String orgId; //所属机构
	private java.sql.Timestamp createTime; //创建日期
	private String createTimeStr; //创建日期
	private java.sql.Timestamp updateTime; //修改日期
	private String updateTimeStr; //修改日期

	//getter部分
	public int getId () {
		return this.id;
	}
	public String getAppId () {
		return this.appId;
	}
	public int getQueryId () {
		return this.queryId;
	}
	public java.util.Date getQueryDate () {
		return this.queryDate;
	}
	//getter部分,Date类型的修改获取String的方法
	public String getQueryDateStr () {
		return DateUtils.formatDate(this.queryDate);
	}
	public String getQueryOperator () {
		return this.queryOperator;
	}
	public String getQueryReason () {
		return this.queryReason;
	}
	public String getRemarks () {
		return this.remarks;
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
	public void setQueryId (int queryId) {
		this.queryId=queryId;
	}
	public void setQueryDate (java.util.Date queryDate) {
		this.queryDate=queryDate;
	}
	public void setQueryDateStr (String queryDateStr) {
		this.queryDateStr=queryDateStr;
	}
	public void setQueryOperator (String queryOperator) {
		this.queryOperator=queryOperator;
	}
	public void setQueryReason (String queryReason) {
		this.queryReason=queryReason;
	}
	public void setRemarks (String remarks) {
		this.remarks=remarks;
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

}


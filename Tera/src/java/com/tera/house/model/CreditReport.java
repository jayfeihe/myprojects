package com.tera.house.model;

import com.tera.util.DateUtils;

/**
 * 
 * 信用贷款申请人行信息实体类
 * <b>功能：</b>CreditReportDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-09-15 17:34:44<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public class CreditReport {

	//属性部分
	private int id; //ID
	private String appId; //申请ID
	private String type; //类型
	private int creditCardException; //信用卡近12个月异常
	private int querySixCount; //6个月查询次数
	private int maxDefault; //最大账龄（月）
	private double amount; //最高额度/总额
	private double defaultAmount; //逾期总额
	private int defaultCount; //逾期的 笔数/数量
	private int defaultMaxCount; //最大逾期次数
	private int defaultNinetyCount; //90天以上逾期次数
	private int expireLoan; //一个月内到期的贷款笔数
	private int expireHousing; //个人住房贷款笔数
	private String state; //状态
	private String operator; //操作员
	private String orgId; //所属机构
	private java.sql.Timestamp createTime; //创建日期
	private String createTimeStr; //创建日期
	private java.sql.Timestamp updateTime; //修改日期
	private String updateTimeStr; //修改日期
	private double usedAmount; //已用额度

	//getter部分
	public int getId () {
		return this.id;
	}
	public String getAppId () {
		return this.appId;
	}
	public String getType () {
		return this.type;
	}
	public int getCreditCardException () {
		return this.creditCardException;
	}
	public int getQuerySixCount () {
		return this.querySixCount;
	}
	public int getMaxDefault () {
		return this.maxDefault;
	}
	public double getAmount () {
		return this.amount;
	}
	public double getDefaultAmount () {
		return this.defaultAmount;
	}
	public int getDefaultCount () {
		return this.defaultCount;
	}
	public int getDefaultMaxCount () {
		return this.defaultMaxCount;
	}
	public int getDefaultNinetyCount () {
		return this.defaultNinetyCount;
	}
	public int getExpireLoan () {
		return this.expireLoan;
	}
	public int getExpireHousing () {
		return this.expireHousing;
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
	public double getUsedAmount () {
		return this.usedAmount;
	}

	//setter部分
	public void setId (int id) {
		this.id=id;
	}
	public void setAppId (String appId) {
		this.appId=appId;
	}
	public void setType (String type) {
		this.type=type;
	}
	public void setCreditCardException (int creditCardException) {
		this.creditCardException=creditCardException;
	}
	public void setQuerySixCount (int querySixCount) {
		this.querySixCount=querySixCount;
	}
	public void setMaxDefault (int maxDefault) {
		this.maxDefault=maxDefault;
	}
	public void setAmount (double amount) {
		this.amount=amount;
	}
	public void setDefaultAmount (double defaultAmount) {
		this.defaultAmount=defaultAmount;
	}
	public void setDefaultCount (int defaultCount) {
		this.defaultCount=defaultCount;
	}
	public void setDefaultMaxCount (int defaultMaxCount) {
		this.defaultMaxCount=defaultMaxCount;
	}
	public void setDefaultNinetyCount (int defaultNinetyCount) {
		this.defaultNinetyCount=defaultNinetyCount;
	}
	public void setExpireLoan (int expireLoan) {
		this.expireLoan=expireLoan;
	}
	public void setExpireHousing (int expireHousing) {
		this.expireHousing=expireHousing;
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
	public void setUsedAmount (double usedAmount) {
		this.usedAmount=usedAmount;
	}

}


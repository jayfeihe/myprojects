package com.tera.customer.model;

import com.tera.util.DateUtils;

/**
 * 
 * <br>
 * <b>功能：</b>CustomerExtDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-06-05 18:36:15<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public class CustomerExt {

	//属性部分
	private int customerId; //客户ID
	private String interests; //兴趣爱好
	private String activities; //参加活动
	private String realEstate; //置业情况
	private String investmentLevel; //投资了解程度
	private String investmentSource; //投资资金来源
	private String investmentExp; //投资经验
	private String investmentProduct; //投资产品
	private String investmentIncome; //投资收益
	private String followType; //关注方式
	private String followPoint; //关注特点
	private String preferenceGoal; //偏好-目标
	private String preferencePeriod; //偏好-期限
	private String preferenceDecision; //偏好-投资决策
	private String preferenceRisk; //偏好-风险偏好
	private String preferenceProduct; //偏好-投资产品
	private String preferenceAmount; //偏好-追加金额
	private String operator; //操作员
	private String orgId; //所属机构
	private java.sql.Timestamp createTime; //创建日期
	private String createTimeStr; //创建日期
	private java.sql.Timestamp updateTime; //修改日期
	private String updateTimeStr; //修改日期

	//getter部分
	public int getCustomerId () {
		return this.customerId;
	}
	public String getInterests () {
		return this.interests;
	}
	public String getActivities () {
		return this.activities;
	}
	public String getRealEstate () {
		return this.realEstate;
	}
	public String getInvestmentLevel () {
		return this.investmentLevel;
	}
	public String getInvestmentSource () {
		return this.investmentSource;
	}
	public String getInvestmentExp () {
		return this.investmentExp;
	}
	public String getInvestmentProduct () {
		return this.investmentProduct;
	}
	public String getInvestmentIncome () {
		return this.investmentIncome;
	}
	public String getFollowType () {
		return this.followType;
	}
	public String getFollowPoint () {
		return this.followPoint;
	}
	public String getPreferenceGoal () {
		return this.preferenceGoal;
	}
	public String getPreferencePeriod () {
		return this.preferencePeriod;
	}
	public String getPreferenceDecision () {
		return this.preferenceDecision;
	}
	public String getPreferenceRisk () {
		return this.preferenceRisk;
	}
	public String getPreferenceProduct () {
		return this.preferenceProduct;
	}
	public String getPreferenceAmount () {
		return this.preferenceAmount;
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
	public void setCustomerId (int customerId) {
		this.customerId=customerId;
	}
	public void setInterests (String interests) {
		this.interests=interests;
	}
	public void setActivities (String activities) {
		this.activities=activities;
	}
	public void setRealEstate (String realEstate) {
		this.realEstate=realEstate;
	}
	public void setInvestmentLevel (String investmentLevel) {
		this.investmentLevel=investmentLevel;
	}
	public void setInvestmentSource (String investmentSource) {
		this.investmentSource=investmentSource;
	}
	public void setInvestmentExp (String investmentExp) {
		this.investmentExp=investmentExp;
	}
	public void setInvestmentProduct (String investmentProduct) {
		this.investmentProduct=investmentProduct;
	}
	public void setInvestmentIncome (String investmentIncome) {
		this.investmentIncome=investmentIncome;
	}
	public void setFollowType (String followType) {
		this.followType=followType;
	}
	public void setFollowPoint (String followPoint) {
		this.followPoint=followPoint;
	}
	public void setPreferenceGoal (String preferenceGoal) {
		this.preferenceGoal=preferenceGoal;
	}
	public void setPreferencePeriod (String preferencePeriod) {
		this.preferencePeriod=preferencePeriod;
	}
	public void setPreferenceDecision (String preferenceDecision) {
		this.preferenceDecision=preferenceDecision;
	}
	public void setPreferenceRisk (String preferenceRisk) {
		this.preferenceRisk=preferenceRisk;
	}
	public void setPreferenceProduct (String preferenceProduct) {
		this.preferenceProduct=preferenceProduct;
	}
	public void setPreferenceAmount (String preferenceAmount) {
		this.preferenceAmount=preferenceAmount;
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


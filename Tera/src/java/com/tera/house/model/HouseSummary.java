package com.tera.house.model;

import com.tera.util.DateUtils;

/**
 * 
 * 信用贷款申请影像摘要实体类
 * <b>功能：</b>houseSummaryDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-09-04 11:45:01<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public class HouseSummary {

	//属性部分
	private int id; //ID
	private String appId; //申请ID
	private String validteDivorceFlag; //有效性_离婚证
	private String validteDivorceRemark; //有效性_离婚证备注
	private String validteIdnoFlag; //有效性_身份证
	private String validteIdnoRemark; //有效性_身份证备注
	private String validteHouseFlag; //有效性_房产证
	private String validteHouseRemark; //有效性_房产证备注
	private String validteDebitFlag; //有效性_常用借记卡流水
	private String validteDebitRemark; //有效性_常用借记卡流水备注
	private String validteWageFlag; //有效性_工资卡流水
	private String validteWageRemark; //有效性_工资卡流水备注
	private int creditCard; //是否有信用卡
	private int isLoan; //否有贷款
	private int queryCount; //近12个月贷后管理查询次数
	private String socialComName; //社保单位名称
	private String socialCode; //社保卡号
	private String socialAmount; //社保月基纳金额
	private java.util.Date socialDate; //社保最近基纳时间
	private String socialDateStr; //社保最近基纳时间
	private int socialMonths; //社保已基纳时间
	private String publicComName; //公积金单位名称
	private String publicAmount; //公积金月基纳金额
	private java.util.Date publicDate; //公积金最近基纳时间
	private String publicDateStr; //公积金最近基纳时间
	private int publicMonths; //公积金已基纳时间
	private String state; //状态
	private String remarks; //备注
	private String wageFlowRemarks; //工资流水备注
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
	public String getValidteDivorceFlag () {
		return this.validteDivorceFlag;
	}
	public String getValidteDivorceRemark () {
		return this.validteDivorceRemark;
	}
	public String getValidteIdnoFlag () {
		return this.validteIdnoFlag;
	}
	public String getValidteIdnoRemark () {
		return this.validteIdnoRemark;
	}
	public String getValidteHouseFlag () {
		return this.validteHouseFlag;
	}
	public String getValidteHouseRemark () {
		return this.validteHouseRemark;
	}
	public String getValidteDebitFlag () {
		return this.validteDebitFlag;
	}
	public String getValidteDebitRemark () {
		return this.validteDebitRemark;
	}
	public String getValidteWageFlag () {
		return this.validteWageFlag;
	}
	public String getValidteWageRemark () {
		return this.validteWageRemark;
	}
	public int getCreditCard () {
		return this.creditCard;
	}
	public int getIsLoan () {
		return this.isLoan;
	}
	public int getQueryCount () {
		return this.queryCount;
	}
	public String getSocialComName () {
		return this.socialComName;
	}
	public String getSocialCode () {
		return this.socialCode;
	}
	public String getSocialAmount () {
		return this.socialAmount;
	}
	public java.util.Date getSocialDate () {
		return this.socialDate;
	}
	//getter部分,Date类型的修改获取String的方法
	public String getSocialDateStr () {
		return DateUtils.formatDate(this.socialDate);
	}
	public int getSocialMonths () {
		return this.socialMonths;
	}
	public String getPublicComName () {
		return this.publicComName;
	}
	public String getPublicAmount () {
		return this.publicAmount;
	}
	public java.util.Date getPublicDate () {
		return this.publicDate;
	}
	//getter部分,Date类型的修改获取String的方法
	public String getPublicDateStr () {
		return DateUtils.formatDate(this.publicDate);
	}
	public int getPublicMonths () {
		return this.publicMonths;
	}
	public String getState () {
		return this.state;
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

	//setter部分
	public void setId (int id) {
		this.id=id;
	}
	public void setAppId (String appId) {
		this.appId=appId;
	}
	public void setValidteDivorceFlag (String validteDivorceFlag) {
		this.validteDivorceFlag=validteDivorceFlag;
	}
	public void setValidteDivorceRemark (String validteDivorceRemark) {
		this.validteDivorceRemark=validteDivorceRemark;
	}
	public void setValidteIdnoFlag (String validteIdnoFlag) {
		this.validteIdnoFlag=validteIdnoFlag;
	}
	public void setValidteIdnoRemark (String validteIdnoRemark) {
		this.validteIdnoRemark=validteIdnoRemark;
	}
	public void setValidteHouseFlag (String validteHouseFlag) {
		this.validteHouseFlag=validteHouseFlag;
	}
	public void setValidteHouseRemark (String validteHouseRemark) {
		this.validteHouseRemark=validteHouseRemark;
	}
	public void setValidteDebitFlag (String validteDebitFlag) {
		this.validteDebitFlag=validteDebitFlag;
	}
	public void setValidteDebitRemark (String validteDebitRemark) {
		this.validteDebitRemark=validteDebitRemark;
	}
	public void setValidteWageFlag (String validteWageFlag) {
		this.validteWageFlag=validteWageFlag;
	}
	public void setValidteWageRemark (String validteWageRemark) {
		this.validteWageRemark=validteWageRemark;
	}
	public void setCreditCard (int creditCard) {
		this.creditCard=creditCard;
	}
	public void setIsLoan (int isLoan) {
		this.isLoan=isLoan;
	}
	public void setQueryCount (int queryCount) {
		this.queryCount=queryCount;
	}
	public void setSocialComName (String socialComName) {
		this.socialComName=socialComName;
	}
	public void setSocialCode (String socialCode) {
		this.socialCode=socialCode;
	}
	public void setSocialAmount (String socialAmount) {
		this.socialAmount=socialAmount;
	}
	public void setSocialDate (java.util.Date socialDate) {
		this.socialDate=socialDate;
	}
	public void setSocialDateStr (String socialDateStr) {
		this.socialDateStr=socialDateStr;
	}
	public void setSocialMonths (int socialMonths) {
		this.socialMonths=socialMonths;
	}
	public void setPublicComName (String publicComName) {
		this.publicComName=publicComName;
	}
	public void setPublicAmount (String publicAmount) {
		this.publicAmount=publicAmount;
	}
	public void setPublicDate (java.util.Date publicDate) {
		this.publicDate=publicDate;
	}
	public void setPublicDateStr (String publicDateStr) {
		this.publicDateStr=publicDateStr;
	}
	public void setPublicMonths (int publicMonths) {
		this.publicMonths=publicMonths;
	}
	public void setState (String state) {
		this.state=state;
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
	public String getWageFlowRemarks() {
		return wageFlowRemarks;
	}
	public void setWageFlowRemarks(String wageFlowRemarks) {
		this.wageFlowRemarks = wageFlowRemarks;
	}

}


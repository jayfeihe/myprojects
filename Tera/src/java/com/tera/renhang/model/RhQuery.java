package com.tera.renhang.model;

import com.tera.util.DateUtils;

/**
 * 
 * 信用贷款人行报告查询汇总实体类
 * <b>功能：</b>RhQueryDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2015-01-14 16:07:22<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public class RhQuery {

	//属性部分
	private int id; //ID
	private String appId; //申请ID
	private int query1mLoanCom; //最近1月机构查询贷款审批
	private int query1mCreditCom; //最近1月机构查询信用卡审批
	private int query1mLoan; //最近1月查询贷款审批
	private int query1mCredit; //最近1月查询信用卡审批
	private int query2yLoanAfter; //最近2年查询贷后管理
	private int query2ySecureVerify; //最近2年查询担保资格审查
	private int query2yMerchantVerify; //最近2年查询特约商户实名审查
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
	public int getQuery1mLoanCom () {
		return this.query1mLoanCom;
	}
	public int getQuery1mCreditCom () {
		return this.query1mCreditCom;
	}
	public int getQuery1mLoan () {
		return this.query1mLoan;
	}
	public int getQuery1mCredit () {
		return this.query1mCredit;
	}
	public int getQuery2yLoanAfter () {
		return this.query2yLoanAfter;
	}
	public int getQuery2ySecureVerify () {
		return this.query2ySecureVerify;
	}
	public int getQuery2yMerchantVerify () {
		return this.query2yMerchantVerify;
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
	public void setQuery1mLoanCom (int query1mLoanCom) {
		this.query1mLoanCom=query1mLoanCom;
	}
	public void setQuery1mCreditCom (int query1mCreditCom) {
		this.query1mCreditCom=query1mCreditCom;
	}
	public void setQuery1mLoan (int query1mLoan) {
		this.query1mLoan=query1mLoan;
	}
	public void setQuery1mCredit (int query1mCredit) {
		this.query1mCredit=query1mCredit;
	}
	public void setQuery2yLoanAfter (int query2yLoanAfter) {
		this.query2yLoanAfter=query2yLoanAfter;
	}
	public void setQuery2ySecureVerify (int query2ySecureVerify) {
		this.query2ySecureVerify=query2ySecureVerify;
	}
	public void setQuery2yMerchantVerify (int query2yMerchantVerify) {
		this.query2yMerchantVerify=query2yMerchantVerify;
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


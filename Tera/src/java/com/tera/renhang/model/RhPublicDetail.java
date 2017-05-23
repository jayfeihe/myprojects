package com.tera.renhang.model;

import com.tera.util.DateUtils;

/**
 * 
 * 信用贷款人行报告公共信息明细实体类
 * <b>功能：</b>RhPublicDetailDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2015-01-14 16:07:03<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public class RhPublicDetail {

	//属性部分
	private int id; //ID
	private String appId; //申请ID
	private String fundPlace; //公积金参缴地
	private java.util.Date fundStartDate; //公积金参缴日期
	private String fundStartDateStr; //公积金参缴日期
	private java.util.Date fundFirstDate; //公积金初缴月份
	private String fundFirstDateStr; //公积金初缴月份
	private java.util.Date fundPaidDate; //公积金缴至月份
	private String fundPaidDateStr; //公积金缴至月份
	private String fundState; //公积金缴费状态
	private double fundAmount; //公积金月缴存额
	private double fundPersonPercent; //公积金个人缴存比例
	private double fundComPercent; //公积金单位缴存比例
	private String fundCompany; //公积金缴费单位
	private java.util.Date fundUpdateDate; //公积金信息更新日期
	private String fundUpdateDateStr; //公积金信息更新日期
	private String pensionPlace; //养老保险参保地
	private java.util.Date pensionStartDate; //养老保险参保日期
	private String pensionStartDateStr; //养老保险参保日期
	private int pensionPayMonth; //养老保险缴费月数
	private java.util.Date pensionWorkDate; //养老保险参加工作月份
	private String pensionWorkDateStr; //养老保险参加工作月份
	private String pensionState; //养老保险缴费状态
	private double pensionBaseAmount; //养老保险个人缴费基数
	private double pensionPayAmount; //养老保险本月缴费金额
	private java.util.Date pensionUpdateDate; //养老保险信息更新日期
	private String pensionUpdateDateStr; //养老保险信息更新日期
	private String pensionCompany; //养老保险缴费单位
	private String pensionInterruptReason; //养老保险中断终止原因
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
	public String getFundPlace () {
		return this.fundPlace;
	}
	public java.util.Date getFundStartDate () {
		return this.fundStartDate;
	}
	//getter部分,Date类型的修改获取String的方法
	public String getFundStartDateStr () {
		return DateUtils.formatDate(this.fundStartDate);
	}
	public java.util.Date getFundFirstDate () {
		return this.fundFirstDate;
	}
	//getter部分,Date类型的修改获取String的方法
	public String getFundFirstDateStr () {
		return DateUtils.formatDate(this.fundFirstDate);
	}
	public java.util.Date getFundPaidDate () {
		return this.fundPaidDate;
	}
	//getter部分,Date类型的修改获取String的方法
	public String getFundPaidDateStr () {
		return DateUtils.formatDate(this.fundPaidDate);
	}
	public String getFundState () {
		return this.fundState;
	}
	public double getFundAmount () {
		return this.fundAmount;
	}
	public double getFundPersonPercent () {
		return this.fundPersonPercent;
	}
	public double getFundComPercent () {
		return this.fundComPercent;
	}
	public String getFundCompany () {
		return this.fundCompany;
	}
	public java.util.Date getFundUpdateDate () {
		return this.fundUpdateDate;
	}
	//getter部分,Date类型的修改获取String的方法
	public String getFundUpdateDateStr () {
		return DateUtils.formatDate(this.fundUpdateDate);
	}
	public String getPensionPlace () {
		return this.pensionPlace;
	}
	public java.util.Date getPensionStartDate () {
		return this.pensionStartDate;
	}
	//getter部分,Date类型的修改获取String的方法
	public String getPensionStartDateStr () {
		return DateUtils.formatDate(this.pensionStartDate);
	}
	public int getPensionPayMonth () {
		return this.pensionPayMonth;
	}
	public java.util.Date getPensionWorkDate () {
		return this.pensionWorkDate;
	}
	//getter部分,Date类型的修改获取String的方法
	public String getPensionWorkDateStr () {
		return DateUtils.formatDate(this.pensionWorkDate);
	}
	public String getPensionState () {
		return this.pensionState;
	}
	public double getPensionBaseAmount () {
		return this.pensionBaseAmount;
	}
	public double getPensionPayAmount () {
		return this.pensionPayAmount;
	}
	public java.util.Date getPensionUpdateDate () {
		return this.pensionUpdateDate;
	}
	//getter部分,Date类型的修改获取String的方法
	public String getPensionUpdateDateStr () {
		return DateUtils.formatDate(this.pensionUpdateDate);
	}
	public String getPensionCompany () {
		return this.pensionCompany;
	}
	public String getPensionInterruptReason () {
		return this.pensionInterruptReason;
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
	public void setFundPlace (String fundPlace) {
		this.fundPlace=fundPlace;
	}
	public void setFundStartDate (java.util.Date fundStartDate) {
		this.fundStartDate=fundStartDate;
	}
	public void setFundStartDateStr (String fundStartDateStr) {
		this.fundStartDateStr=fundStartDateStr;
	}
	public void setFundFirstDate (java.util.Date fundFirstDate) {
		this.fundFirstDate=fundFirstDate;
	}
	public void setFundFirstDateStr (String fundFirstDateStr) {
		this.fundFirstDateStr=fundFirstDateStr;
	}
	public void setFundPaidDate (java.util.Date fundPaidDate) {
		this.fundPaidDate=fundPaidDate;
	}
	public void setFundPaidDateStr (String fundPaidDateStr) {
		this.fundPaidDateStr=fundPaidDateStr;
	}
	public void setFundState (String fundState) {
		this.fundState=fundState;
	}
	public void setFundAmount (double fundAmount) {
		this.fundAmount=fundAmount;
	}
	public void setFundPersonPercent (double fundPersonPercent) {
		this.fundPersonPercent=fundPersonPercent;
	}
	public void setFundComPercent (double fundComPercent) {
		this.fundComPercent=fundComPercent;
	}
	public void setFundCompany (String fundCompany) {
		this.fundCompany=fundCompany;
	}
	public void setFundUpdateDate (java.util.Date fundUpdateDate) {
		this.fundUpdateDate=fundUpdateDate;
	}
	public void setFundUpdateDateStr (String fundUpdateDateStr) {
		this.fundUpdateDateStr=fundUpdateDateStr;
	}
	public void setPensionPlace (String pensionPlace) {
		this.pensionPlace=pensionPlace;
	}
	public void setPensionStartDate (java.util.Date pensionStartDate) {
		this.pensionStartDate=pensionStartDate;
	}
	public void setPensionStartDateStr (String pensionStartDateStr) {
		this.pensionStartDateStr=pensionStartDateStr;
	}
	public void setPensionPayMonth (int pensionPayMonth) {
		this.pensionPayMonth=pensionPayMonth;
	}
	public void setPensionWorkDate (java.util.Date pensionWorkDate) {
		this.pensionWorkDate=pensionWorkDate;
	}
	public void setPensionWorkDateStr (String pensionWorkDateStr) {
		this.pensionWorkDateStr=pensionWorkDateStr;
	}
	public void setPensionState (String pensionState) {
		this.pensionState=pensionState;
	}
	public void setPensionBaseAmount (double pensionBaseAmount) {
		this.pensionBaseAmount=pensionBaseAmount;
	}
	public void setPensionPayAmount (double pensionPayAmount) {
		this.pensionPayAmount=pensionPayAmount;
	}
	public void setPensionUpdateDate (java.util.Date pensionUpdateDate) {
		this.pensionUpdateDate=pensionUpdateDate;
	}
	public void setPensionUpdateDateStr (String pensionUpdateDateStr) {
		this.pensionUpdateDateStr=pensionUpdateDateStr;
	}
	public void setPensionCompany (String pensionCompany) {
		this.pensionCompany=pensionCompany;
	}
	public void setPensionInterruptReason (String pensionInterruptReason) {
		this.pensionInterruptReason=pensionInterruptReason;
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


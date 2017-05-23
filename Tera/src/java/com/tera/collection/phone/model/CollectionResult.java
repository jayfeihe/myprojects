package com.tera.collection.phone.model;

import com.tera.util.DateUtils;

/**
 * 
 * 催收人员绩效表实体类
 * <b>功能：</b>CollectionResultDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2015-06-08 14:40:25<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public class CollectionResult {

	//属性部分
	private int id; //id
	private int distributionId; //催收分配表ID
	private String collectionUid; //催收人
	private String contractNo; //合同编号
	private String idType; //客户证件类型
	private String idNo; //证件号码
	private String customerName; //客户姓名
	private String customerTel; //联系方式
	private double contractAmount; //合同额
	private String product; //产品
	private int lateAge; //账龄
	private int lateDays; //逾期天数
	private java.util.Date repaymentDate; //还款日
	private String repaymentDateStr; //还款日
	private String loanPlatform; //放款平台
	private String orgName; //营业部
	private double penalty; //罚息总额
	private double defaultFee; //违约金总额
	private double delay; //滞纳金总额
	private double sreviceFee; //服务费
	private double principal; //本金总额
	private double interest; //利息总额
	private double reduce; //减免总额
	private double amountAll; //应还总额
	private int periodAll; //总期数
	private int periodCur; //当前期数
	private int periodLateHis; //历史逾期期数
	private java.util.Date actualRepaymentDate; //还款日期
	private String actualRepaymentDateStr; //还款日期
	private String collectionWay; //催收渠道
	private java.lang.Object remark; //备注
	private String createUid; //创建人
	private java.sql.Timestamp createTime; //创建时间
	private String createTimeStr; //创建时间
	private String updateUid; //修改人
	private java.sql.Timestamp updateTime; //修改时间
	private String updateTimeStr; //修改时间

	//getter部分
	public int getId () {
		return this.id;
	}
	public int getDistributionId () {
		return this.distributionId;
	}
	public String getCollectionUid () {
		return this.collectionUid;
	}
	public String getContractNo () {
		return this.contractNo;
	}
	public String getIdType () {
		return this.idType;
	}
	public String getIdNo () {
		return this.idNo;
	}
	public String getCustomerName () {
		return this.customerName;
	}
	public String getCustomerTel () {
		return this.customerTel;
	}
	public double getContractAmount () {
		return this.contractAmount;
	}
	public String getProduct () {
		return this.product;
	}
	public int getLateAge () {
		return this.lateAge;
	}
	public int getLateDays () {
		return this.lateDays;
	}
	public java.util.Date getRepaymentDate () {
		return this.repaymentDate;
	}
	//getter部分,Timestamp类型的修改获取String的方法
	public String getRepaymentDateStr () {
		return DateUtils.formatTime(this.repaymentDate);
	}
	public String getLoanPlatform () {
		return this.loanPlatform;
	}
	public String getOrgName () {
		return this.orgName;
	}
	public double getPenalty () {
		return this.penalty;
	}
	public double getDefaultFee () {
		return this.defaultFee;
	}
	public double getDelay () {
		return this.delay;
	}
	public double getSreviceFee () {
		return this.sreviceFee;
	}
	public double getPrincipal () {
		return this.principal;
	}
	public double getInterest () {
		return this.interest;
	}
	public double getReduce () {
		return this.reduce;
	}
	public double getAmountAll () {
		return this.amountAll;
	}
	public int getPeriodAll () {
		return this.periodAll;
	}
	public int getPeriodCur () {
		return this.periodCur;
	}
	public int getPeriodLateHis () {
		return this.periodLateHis;
	}
	public java.util.Date getActualRepaymentDate () {
		return this.actualRepaymentDate;
	}
	//getter部分,Date类型的修改获取String的方法
	public String getActualRepaymentDateStr () {
		return DateUtils.formatDate(this.actualRepaymentDate);
	}
	public String getCollectionWay () {
		return this.collectionWay;
	}
	public java.lang.Object getRemark () {
		return this.remark;
	}
	public String getCreateUid () {
		return this.createUid;
	}
	public java.sql.Timestamp getCreateTime () {
		return this.createTime;
	}
	//getter部分,Timestamp类型的修改获取String的方法
	public String getCreateTimeStr () {
		return DateUtils.formatTime(this.createTime);
	}
	public String getUpdateUid () {
		return this.updateUid;
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
	public void setDistributionId (int distributionId) {
		this.distributionId=distributionId;
	}
	public void setCollectionUid (String collectionUid) {
		this.collectionUid=collectionUid;
	}
	public void setContractNo (String contractNo) {
		this.contractNo=contractNo;
	}
	public void setIdType (String idType) {
		this.idType=idType;
	}
	public void setIdNo (String idNo) {
		this.idNo=idNo;
	}
	public void setCustomerName (String customerName) {
		this.customerName=customerName;
	}
	public void setCustomerTel (String customerTel) {
		this.customerTel=customerTel;
	}
	public void setContractAmount (double contractAmount) {
		this.contractAmount=contractAmount;
	}
	public void setProduct (String product) {
		this.product=product;
	}
	public void setLateAge (int lateAge) {
		this.lateAge=lateAge;
	}
	public void setLateDays (int lateDays) {
		this.lateDays=lateDays;
	}
	public void setRepaymentDate (java.util.Date repaymentDate) {
		this.repaymentDate=repaymentDate;
	}
	public void setRepaymentDateStr (String repaymentDateStr) {
		this.repaymentDateStr=repaymentDateStr;
	}
	public void setLoanPlatform (String loanPlatform) {
		this.loanPlatform=loanPlatform;
	}
	public void setOrgName (String orgName) {
		this.orgName=orgName;
	}
	public void setPenalty (double penalty) {
		this.penalty=penalty;
	}
	public void setDefaultFee (double defaultFee) {
		this.defaultFee=defaultFee;
	}
	public void setDelay (double delay) {
		this.delay=delay;
	}
	public void setSreviceFee (double sreviceFee) {
		this.sreviceFee=sreviceFee;
	}
	public void setPrincipal (double principal) {
		this.principal=principal;
	}
	public void setInterest (double interest) {
		this.interest=interest;
	}
	public void setReduce (double reduce) {
		this.reduce=reduce;
	}
	public void setAmountAll (double amountAll) {
		this.amountAll=amountAll;
	}
	public void setPeriodAll (int periodAll) {
		this.periodAll=periodAll;
	}
	public void setPeriodCur (int periodCur) {
		this.periodCur=periodCur;
	}
	public void setPeriodLateHis (int periodLateHis) {
		this.periodLateHis=periodLateHis;
	}
	public void setActualRepaymentDate (java.util.Date actualRepaymentDate) {
		this.actualRepaymentDate=actualRepaymentDate;
	}
	public void setActualRepaymentDateStr (String actualRepaymentDateStr) {
		this.actualRepaymentDateStr=actualRepaymentDateStr;
	}
	public void setCollectionWay (String collectionWay) {
		this.collectionWay=collectionWay;
	}
	public void setRemark (java.lang.Object remark) {
		this.remark=remark;
	}
	public void setCreateUid (String createUid) {
		this.createUid=createUid;
	}
	public void setCreateTime (java.sql.Timestamp createTime) {
		this.createTime=createTime;
	}
	public void setCreateTimeStr (String createTimeStr) {
		this.createTimeStr=createTimeStr;
	}
	public void setUpdateUid (String updateUid) {
		this.updateUid=updateUid;
	}
	public void setUpdateTime (java.sql.Timestamp updateTime) {
		this.updateTime=updateTime;
	}
	public void setUpdateTimeStr (String updateTimeStr) {
		this.updateTimeStr=updateTimeStr;
	}

}


package com.tera.collection.outsourcing.model;

import com.tera.util.DateUtils;

public class CollectionOutsourcing {
	//属性部分
	private String id; //id
	private String contractNo; //合同编号
	private String idNo; //证件号码
	private String customerName; //客户姓名
	private String customerTel; //联系方式
	private double contractAmount; //合同额
	private String product; //产品
	private int lateAge; //账龄
	private int lateDays; //逾期天数
	private String loanPlatform; //放款平台
	private java.util.Date repaymentDate;//还款日
	private String repaymentDateStr;
	private String orgId; //营业部
	private String orgName;//营业部名称
	private double penalty; //罚息总额
	private double defaultFee; //违约金总额
	private double delay; //滞纳金总额
	private double sreviceFee; //服务费
	private double principal; //本金总额
	private double interest; //利息总额
	private double reduce; //减免总额
	private double monthAmountAll; //月还总额为本金和利息总和，不包括已还的
	private double amountAll; //应还总额
	private int periodAll; //总期数
	private int periodCur; //当前期数
	private int periodLateHis; //历史逾期期数
	private int periodFinish; //已还期数
	private java.util.Date followTime; //跟进时间
	private String followTimeStr; //跟进时间
	private String collectionUidCur;//当前处理人
	private String state;//申请表 状态 
	private String checkUid;//复核人
	private String checkName;//复核人
	private String checkResult;
	private java.util.Date checkTime;
	private String checkTimeStr;
	private String checkText;
	private String applyUid;
	private String applyName;//申请人 
	private java.util.Date applyTime;
	private String applyTimeStr;
	private String applyText;
	private String collectionWay;
	
	
	public CollectionOutsourcing() {
		// TODO Auto-generated constructor stub
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getContractNo() {
		return contractNo;
	}
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	public String getIdNo() {
		return idNo;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCustomerTel() {
		return customerTel;
	}
	public void setCustomerTel(String customerTel) {
		this.customerTel = customerTel;
	}
	public double getContractAmount() {
		return contractAmount;
	}
	public void setContractAmount(double contractAmount) {
		this.contractAmount = contractAmount;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public int getLateAge() {
		return lateAge;
	}
	public void setLateAge(int lateAge) {
		this.lateAge = lateAge;
	}
	public int getLateDays() {
		return lateDays;
	}
	public void setLateDays(int lateDays) {
		this.lateDays = lateDays;
	}
	public java.util.Date getRepaymentDate() {
		return repaymentDate;
	}
	public void setRepaymentDate(java.util.Date repaymentDate) {
		this.repaymentDate = repaymentDate;
	}
	public String getRepaymentDateStr() {
		return repaymentDateStr;
	}
	public void setRepaymentDateStr(String repaymentDateStr) {
		this.repaymentDateStr = repaymentDateStr;
	}
	public String getLoanPlatform() {
		return loanPlatform;
	}
	public void setLoanPlatform(String loanPlatform) {
		this.loanPlatform = loanPlatform;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public double getPenalty() {
		return penalty;
	}
	public void setPenalty(double penalty) {
		this.penalty = penalty;
	}
	public double getDefaultFee() {
		return defaultFee;
	}
	public void setDefaultFee(double defaultFee) {
		this.defaultFee = defaultFee;
	}
	public double getDelay() {
		return delay;
	}
	public void setDelay(double delay) {
		this.delay = delay;
	}
	public double getSreviceFee() {
		return sreviceFee;
	}
	public void setSreviceFee(double sreviceFee) {
		this.sreviceFee = sreviceFee;
	}
	public double getPrincipal() {
		return principal;
	}
	public void setPrincipal(double principal) {
		this.principal = principal;
	}
	public double getInterest() {
		return interest;
	}
	public void setInterest(double interest) {
		this.interest = interest;
	}
	public double getReduce() {
		return reduce;
	}
	public void setReduce(double reduce) {
		this.reduce = reduce;
	}
	public double getMonthAmountAll() {
		return monthAmountAll;
	}
	public void setMonthAmountAll(double monthAmountAll) {
		this.monthAmountAll = monthAmountAll;
	}
	public double getAmountAll() {
		return amountAll;
	}
	public void setAmountAll(double amountAll) {
		this.amountAll = amountAll;
	}
	public int getPeriodAll() {
		return periodAll;
	}
	public void setPeriodAll(int periodAll) {
		this.periodAll = periodAll;
	}
	public int getPeriodCur() {
		return periodCur;
	}
	public void setPeriodCur(int periodCur) {
		this.periodCur = periodCur;
	}
	public int getPeriodLateHis() {
		return periodLateHis;
	}
	public void setPeriodLateHis(int periodLateHis) {
		this.periodLateHis = periodLateHis;
	}
	public int getPeriodFinish() {
		return periodFinish;
	}
	public void setPeriodFinish(int periodFinish) {
		this.periodFinish = periodFinish;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public java.util.Date getFollowTime() {
		return followTime;
	}
	public void setFollowTime(java.util.Date followTime) {
		this.followTime = followTime;
	}
	public String getFollowTimeStr() {
		return DateUtils.formatTime(this.followTime);
	}
	public void setFollowTimeStr(String followTimeStr) {
		this.followTimeStr = followTimeStr;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getCollectionUidCur() {
		return collectionUidCur;
	}
	public void setCollectionUidCur(String collectionUidCur) {
		this.collectionUidCur = collectionUidCur;
	}
	public String getCheckUid() {
		return checkUid;
	}
	public void setCheckUid(String checkUid) {
		this.checkUid = checkUid;
	}
	public String getCheckName() {
		return checkName;
	}
	public void setCheckName(String checkName) {
		this.checkName = checkName;
	}
	public String getCheckResult() {
		return checkResult;
	}
	public void setCheckResult(String checkResult) {
		this.checkResult = checkResult;
	}
	public java.util.Date getCheckTime() {
		return checkTime;
	}
	public void setCheckTime(java.util.Date checkTime) {
		this.checkTime = checkTime;
	}
	public String getCheckText() {
		return checkText;
	}
	public void setCheckText(String checkText) {
		this.checkText = checkText;
	}
	public String getApplyUid() {
		return applyUid;
	}
	public void setApplyUid(String applyUid) {
		this.applyUid = applyUid;
	}
	public String getApplyName() {
		return applyName;
	}
	public void setApplyName(String applyName) {
		this.applyName = applyName;
	}
	public java.util.Date getApplyTime() {
		return applyTime;
	}
	public void setApplyTime(java.util.Date applyTime) {
		this.applyTime = applyTime;
	}
	public String getApplyText() {
		return applyText;
	}
	public void setApplyText(String applyText) {
		this.applyText = applyText;
	}
	public String getCollectionWay() {
		return collectionWay;
	}
	public void setCollectionWay(String collectionWay) {
		this.collectionWay = collectionWay;
	}
	public String getCheckTimeStr() {
		return DateUtils.formatTime(this.checkTime);
	}
	public void setCheckTimeStr(String checkTimeStr) {
		this.checkTimeStr = checkTimeStr;
	}
	public String getApplyTimeStr() {
		return DateUtils.formatTime(this.applyTime);
	}
	public void setApplyTimeStr(String applyTimeStr) {
		this.applyTimeStr = applyTimeStr;
	}
	
	
}

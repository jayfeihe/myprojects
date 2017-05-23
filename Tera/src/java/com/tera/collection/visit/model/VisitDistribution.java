package com.tera.collection.visit.model;

import com.tera.util.DateUtils;

public class VisitDistribution {
	//属性部分
	private int id; //id
	private String contractNo;		 		//合同编号
	private String customerName; 	 		//客户姓名
	private String idNo;					//身份证号
	private String product; 		 		//产品
	private double contractAmount; 	 		//合同额
	private double monthAmountAll; 			//月还总额为本金和利息总和，不包括已还的
	private int lateAge; 			 		//账龄
	private int lateDays;           		//逾期天数
	private String orgId;            		//营业部
	private String orgName;          		//营业部name	
	private String answerState;      		//接听状态
	private String phoneSummary;     		//电催摘要
	private String state;            		//状态标识
	private java.sql.Timestamp followTime;  //跟进时间
	private String followTimeStr;           //跟进时间
	private java.sql.Timestamp orderTime;   //预约时间
	private String orderTimeStr;            //预约时间
	private String loanPlatform;			//放款平台
	private String distributionState;		//分配状态
	
	private String roleId;					//登陆用户的角色id
	private String channelName;				//放款平台名称
	private String keyValue;				//催收摘要中文值
	private String handleState;				//处理状态
	
	public String getHandleState() {
		return handleState;
	}
	public void setHandleState(String handleState) {
		this.handleState = handleState;
	}
	public String getKeyValue() {
		return keyValue;
	}
	public void setKeyValue(String keyValue) {
		this.keyValue = keyValue;
	}
	public double getMonthAmountAll() {
		return monthAmountAll;
	}
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	public void setMonthAmountAll(double monthAmountAll) {
		this.monthAmountAll = monthAmountAll;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getDistributionState() {
		return distributionState;
	}
	public void setDistributionState(String distributionState) {
		this.distributionState = distributionState;
	}
	public String getLoanPlatform() {
		return loanPlatform;
	}
	public void setLoanPlatform(String loanPlatform) {
		this.loanPlatform = loanPlatform;
	}
	public String getIdNo() {
		return idNo;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getContractNo() {
		return contractNo;
	}
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public double getContractAmount() {
		return contractAmount;
	}
	public void setContractAmount(double contractAmount) {
		this.contractAmount = contractAmount;
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
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getAnswerState() {
		return answerState;
	}
	public void setAnswerState(String answerState) {
		this.answerState = answerState;
	}
	public String getPhoneSummary() {
		return phoneSummary;
	}
	public void setPhoneSummary(String phoneSummary) {
		this.phoneSummary = phoneSummary;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public java.sql.Timestamp getFollowTime() {
		return followTime;
	}
	public void setFollowTime(java.sql.Timestamp followTime) {
		this.followTime = followTime;
	}
	public String getFollowTimeStr() {
		return DateUtils.formatTime(this.followTime);
	}
	public void setFollowTimeStr(String followTimeStr) {
		this.followTimeStr = followTimeStr;
	}
	public java.sql.Timestamp getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(java.sql.Timestamp orderTime) {
		this.orderTime = orderTime;
	}
	public String getOrderTimeStr() {
		return DateUtils.formatTime(this.orderTime);
	}
	public void setOrderTimeStr(String orderTimeStr) {
		this.orderTimeStr = orderTimeStr;
	}
	
}

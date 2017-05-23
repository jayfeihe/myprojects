package com.tera.lend.model;

import com.tera.util.DateUtils;

/**
 * 
 * 财富端申请表实体类
 * <b>功能：</b>LendAppDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2015-01-30 14:19:25<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public class LendApp {

	//属性部分
	private int id; //ID
	private String appId; //申请ID
	private String contractNo; //合同编号
	private String customerNo; //客户ID
	private String customerType; //类型-个人/机构
	private String customerLever; //客户等级
	private String name; //姓名
	private String mobile; //手机号
	private String phone; //电话
	private String idType; //证件类型
	private String idNo; //证件号码
	private String cfoName; //财务负责人姓名
	private String product; //产品
	private java.util.Date serviceEndDate; //服务截至日期
	private String serviceEndDateStr; //服务截至日期
	private String appType; //申请类型
	private double amount; //出借金额
	private String lendAccName; //出借账户名
	private String lendAccBank; //出借开户银行
	private String lendAccount; //出借银行账号
	private String repayAccName; //回款账户名
	private String bankProvince; //开户行省
	private String bankCity; //开户行市
	private String bankCounty; //开户行区
	private String bankName; //开户行名称
	private String bankCode; //开户行代码
	private String repayAccBank; //支行名称
	private String repayAccount; //银行卡号
	private String bankMobile; //银行卡 绑定手机
	private String matchType; //撮合类型
	private String customerManager; //客户经理
	private String staffNo; //营销人员
	private String operator; //操作员
	private String orgId; //所属机构
	private java.sql.Timestamp createTime; //创建日期
	private String createTimeStr; //创建日期
	private java.sql.Timestamp updateTime; //修改日期
	private String updateTimeStr; //修改日期
	private String state; //状态

	private String address; //地址，巨亚红添加
	private String period;//期限，巨亚红添加
	private String interestRate;//利率，巨亚红添加
	private String taskState; //流程待处理，处理中，处理完

	//getter部分
	public int getId () {
		return this.id;
	}
	public String getAppId () {
		return this.appId;
	}
	public String getContractNo () {
		return this.contractNo;
	}
	public String getCustomerNo () {
		return this.customerNo;
	}
	public String getCustomerType () {
		return this.customerType;
	}
	public String getCustomerLever () {
		return this.customerLever;
	}
	public String getName () {
		return this.name;
	}
	public String getMobile () {
		return this.mobile;
	}
	public String getPhone () {
		return this.phone;
	}
	public String getIdType () {
		return this.idType;
	}
	public String getIdNo () {
		return this.idNo;
	}
	public String getCfoName () {
		return this.cfoName;
	}
	public String getProduct () {
		return this.product;
	}
	public java.util.Date getServiceEndDate () {
		return this.serviceEndDate;
	}
	//getter部分,Date类型的修改获取String的方法
	public String getServiceEndDateStr () {
		return DateUtils.formatDate(this.serviceEndDate);
	}
	public String getAppType () {
		return this.appType;
	}
	public double getAmount () {
		return this.amount;
	}
	public String getLendAccName () {
		return this.lendAccName;
	}
	public String getLendAccBank () {
		return this.lendAccBank;
	}
	public String getLendAccount () {
		return this.lendAccount;
	}
	public String getRepayAccName () {
		return this.repayAccName;
	}
	public String getBankProvince () {
		return this.bankProvince;
	}
	public String getBankCity () {
		return this.bankCity;
	}
	public String getBankCounty () {
		return this.bankCounty;
	}
	public String getBankName () {
		return this.bankName;
	}
	public String getBankCode () {
		return this.bankCode;
	}
	public String getRepayAccBank () {
		return this.repayAccBank;
	}
	public String getRepayAccount () {
		return this.repayAccount;
	}
	public String getBankMobile () {
		return this.bankMobile;
	}
	public String getMatchType () {
		return this.matchType;
	}
	public String getCustomerManager () {
		return this.customerManager;
	}
	public String getStaffNo () {
		return this.staffNo;
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
	public String getState () {
		return this.state;
	}

	//setter部分
	public void setId (int id) {
		this.id=id;
	}
	public void setAppId (String appId) {
		this.appId=appId;
	}
	public void setContractNo (String contractNo) {
		this.contractNo=contractNo;
	}
	public void setCustomerNo (String customerNo) {
		this.customerNo=customerNo;
	}
	public void setCustomerType (String customerType) {
		this.customerType=customerType;
	}
	public void setCustomerLever (String customerLever) {
		this.customerLever=customerLever;
	}
	public void setName (String name) {
		this.name=name;
	}
	public void setMobile (String mobile) {
		this.mobile=mobile;
	}
	public void setPhone (String phone) {
		this.phone=phone;
	}
	public void setIdType (String idType) {
		this.idType=idType;
	}
	public void setIdNo (String idNo) {
		this.idNo=idNo;
	}
	public void setCfoName (String cfoName) {
		this.cfoName=cfoName;
	}
	public void setProduct (String product) {
		this.product=product;
	}
	public void setServiceEndDate (java.util.Date serviceEndDate) {
		this.serviceEndDate=serviceEndDate;
	}
	public void setServiceEndDateStr (String serviceEndDateStr) {
		this.serviceEndDateStr=serviceEndDateStr;
	}
	public void setAppType (String appType) {
		this.appType=appType;
	}
	public void setAmount (double amount) {
		this.amount=amount;
	}
	public void setLendAccName (String lendAccName) {
		this.lendAccName=lendAccName;
	}
	public void setLendAccBank (String lendAccBank) {
		this.lendAccBank=lendAccBank;
	}
	public void setLendAccount (String lendAccount) {
		this.lendAccount=lendAccount;
	}
	public void setRepayAccName (String repayAccName) {
		this.repayAccName=repayAccName;
	}
	public void setBankProvince (String bankProvince) {
		this.bankProvince=bankProvince;
	}
	public void setBankCity (String bankCity) {
		this.bankCity=bankCity;
	}
	public void setBankCounty (String bankCounty) {
		this.bankCounty=bankCounty;
	}
	public void setBankName (String bankName) {
		this.bankName=bankName;
	}
	public void setBankCode (String bankCode) {
		this.bankCode=bankCode;
	}
	public void setRepayAccBank (String repayAccBank) {
		this.repayAccBank=repayAccBank;
	}
	public void setRepayAccount (String repayAccount) {
		this.repayAccount=repayAccount;
	}
	public void setBankMobile (String bankMobile) {
		this.bankMobile=bankMobile;
	}
	public void setMatchType (String matchType) {
		this.matchType=matchType;
	}
	public void setCustomerManager (String customerManager) {
		this.customerManager=customerManager;
	}
	public void setStaffNo (String staffNo) {
		this.staffNo=staffNo;
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
	public void setState (String state) {
		this.state=state;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	public String getInterestRate() {
		return interestRate;
	}
	public void setInterestRate(String interestRate) {
		this.interestRate = interestRate;
	}
	public String getTaskState() {
		return taskState;
	}
	public void setTaskState(String taskState) {
		this.taskState = taskState;
	}

}


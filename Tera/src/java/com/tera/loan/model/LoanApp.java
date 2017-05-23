package com.tera.loan.model;

import com.tera.util.DateUtils;

/**
 * 
 * <br>
 * <b>功能：</b>LoanAppDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-06-18 18:30:15<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public class LoanApp {

	//属性部分
	private int id; //ID
	private String appId; //申请ID
	private String appType; //申请类型-个人/机构
	private String appChannel; //申请渠道
	private String customerNo; //客户ID
	private String mainFlag; //主借款人标识
	private String type; //类型-抵押/信用
	private int consultId; //咨询ID
	private String customerType; //类型-个人/机构
	private String name; //姓名/机构全称
	private String mobile; //手机号
	private String phone; //电话
	private String idType; //证件类型
	private String idNo; //证件号码
	private String marriage; //婚姻
	private String addProvince; //通讯地址-省
	private String addCity; //通讯地址-市
	private String addCounty; //通讯地址-区县
	private String address; //通讯地址
	private String industry1; //行业代码1
	private String industry2; //行业代码2
	private String industry3; //行业代码3
	private double amount; //借款金额
	private String useage; //用途
	private String detailUseage; //详细用途
	private String product; //产品
	private int period; //期限 
	private double sreviceFeeRate; //服务费率
	private double interestRate; //借款利率
	private String lendAccName; //收款账户名
	private String lendAccBank; //收款开户银行
	private String lendAccount; //收款银行账号
	private String repayAccName; //还款账户名
	private String repayAccBank; //还款开户银行
	private String repayAccount; //还款银行账号
	private java.util.Date regDate; //注册时间
	private String regDateStr; //注册时间
	private double regAmount; //注册资本
	private double acctualAmount; //实缴资本
	private String orgCodeNo; //组织机构代码证
	private java.util.Date orgCodeExpiryDate; //组织机构代码证有效期
	private String orgCodeExpiryDateStr; //组织机构代码证有效期
	private String taxRegNo; //税务登记证
	private String baiscAccountBank; //基本账户开户银行
	private String baiscAccount; //基本账户账号
	private String bizzScope; //经营范围
	private String companyType; //经营实体属性
	private String mainProduct; //主要销售产品
	private double lastYearTurnover; //上一年度营业额
	private double last3mTurnover; //近三个月营业额
	private String cooperateBankCompany; //以往主要合作银行或公司
	private String dailyClearBank; //日常结算银行
	private double loanBalance; //贷款融资余额
	private String financeCompany; //融资公司名称
	private String financeBank; //融资银行名称
	private double nearly3mEleBill1; //近三个月电费单1月
	private double nearly3mEleBill2; //近三个月电费单2月
	private double nearly3mEleBill3; //近三个月电费单3月
	private String matchType; //撮合类型
	private String customerManager; //客户经理
	private String sales; //销售人员	
	private String operator; //操作员
	private String orgId; //所属机构
	private java.sql.Timestamp createTime; //创建日期
	private String createTimeStr; //创建日期
	private java.sql.Timestamp updateTime; //修改日期
	private String updateTimeStr; //修改日期
	private String state; //状态 0 无效，1正常，2是拒件，3 复核退回
	private String taskState;		//查询流程列表的 时候 获取 当前的 流程
	private String taskProcesser;	//流程查询的时候 得到申请的 待处理人
	

	public String getTaskState() {
		return taskState;
	}
	public void setTaskState(String taskState) {
		this.taskState = taskState;
	}
	public String getTaskProcesser() {
		return taskProcesser;
	}
	public void setTaskProcesser(String taskProcesser) {
		this.taskProcesser = taskProcesser;
	}
	//getter部分
	public int getId () {
		return this.id;
	}
	public String getAppId () {
		return this.appId;
	}
	public String getAppType () {
		return this.appType;
	}
	public String getAppChannel () {
		return this.appChannel;
	}
	public String getCustomerNo () {
		return this.customerNo;
	}
	public String getMainFlag () {
		return this.mainFlag;
	}
	public String getType () {
		return this.type;
	}
	public int getConsultId () {
		return this.consultId;
	}
	public String getCustomerType () {
		return this.customerType;
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
	public String getMarriage () {
		return this.marriage;
	}
	public String getAddProvince () {
		return this.addProvince;
	}
	public String getAddCity () {
		return this.addCity;
	}
	public String getAddCounty () {
		return this.addCounty;
	}
	public String getAddress () {
		return this.address;
	}
	public String getIndustry1 () {
		return this.industry1;
	}
	public String getIndustry2 () {
		return this.industry2;
	}
	public String getIndustry3 () {
		return this.industry3;
	}
	public double getAmount () {
		return this.amount;
	}
	public String getUseage () {
		return this.useage;
	}
	public String getDetailUseage () {
		return this.detailUseage;
	}
	public String getProduct () {
		return this.product;
	}
	public int getPeriod() {
		return period;
	}
	public void setPeriod(int period) {
		this.period = period;
	}
	public double getSreviceFeeRate () {
		return this.sreviceFeeRate;
	}
	public double getInterestRate () {
		return this.interestRate;
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
	public String getRepayAccBank () {
		return this.repayAccBank;
	}
	public String getRepayAccount () {
		return this.repayAccount;
	}
	public java.util.Date getRegDate () {
		return this.regDate;
	}
	//getter部分,Date类型的修改获取String的方法
	public String getRegDateStr () {
		return DateUtils.formatDate(this.regDate);
	}
	public double getRegAmount () {
		return this.regAmount;
	}
	public double getAcctualAmount () {
		return this.acctualAmount;
	}
	public String getOrgCodeNo () {
		return this.orgCodeNo;
	}
	public java.util.Date getOrgCodeExpiryDate () {
		return this.orgCodeExpiryDate;
	}
	//getter部分,Date类型的修改获取String的方法
	public String getOrgCodeExpiryDateStr () {
		return DateUtils.formatDate(this.orgCodeExpiryDate);
	}
	public String getTaxRegNo () {
		return this.taxRegNo;
	}
	public String getBaiscAccountBank () {
		return this.baiscAccountBank;
	}
	public String getBaiscAccount () {
		return this.baiscAccount;
	}
	public String getBizzScope () {
		return this.bizzScope;
	}
	public String getCompanyType () {
		return this.companyType;
	}
	public String getMainProduct () {
		return this.mainProduct;
	}
	public double getLastYearTurnover () {
		return this.lastYearTurnover;
	}
	public double getLast3mTurnover () {
		return this.last3mTurnover;
	}
	public String getCooperateBankCompany () {
		return this.cooperateBankCompany;
	}
	public String getDailyClearBank () {
		return this.dailyClearBank;
	}
	public double getLoanBalance () {
		return this.loanBalance;
	}
	public String getFinanceCompany () {
		return this.financeCompany;
	}
	public String getFinanceBank () {
		return this.financeBank;
	}
	public double getNearly3mEleBill1 () {
		return this.nearly3mEleBill1;
	}
	public double getNearly3mEleBill2 () {
		return this.nearly3mEleBill2;
	}
	public double getNearly3mEleBill3 () {
		return this.nearly3mEleBill3;
	}
	public String getMatchType () {
		return this.matchType;
	}
	public String getCustomerManager () {
		return this.customerManager;
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
	public void setAppType (String appType) {
		this.appType=appType;
	}
	public void setAppChannel (String appChannel) {
		this.appChannel=appChannel;
	}
	public void setCustomerNo (String customerNo) {
		this.customerNo=customerNo;
	}
	public void setMainFlag (String mainFlag) {
		this.mainFlag=mainFlag;
	}
	public void setType (String type) {
		this.type=type;
	}
	public void setConsultId (int consultId) {
		this.consultId=consultId;
	}
	public void setCustomerType (String customerType) {
		this.customerType=customerType;
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
	public void setMarriage (String marriage) {
		this.marriage=marriage;
	}
	public void setAddProvince (String addProvince) {
		this.addProvince=addProvince;
	}
	public void setAddCity (String addCity) {
		this.addCity=addCity;
	}
	public void setAddCounty (String addCounty) {
		this.addCounty=addCounty;
	}
	public void setAddress (String address) {
		this.address=address;
	}
	public void setIndustry1 (String industry1) {
		this.industry1=industry1;
	}
	public void setIndustry2 (String industry2) {
		this.industry2=industry2;
	}
	public void setIndustry3 (String industry3) {
		this.industry3=industry3;
	}
	public void setAmount (double amount) {
		this.amount=amount;
	}
	public void setUseage (String useage) {
		this.useage=useage;
	}
	public void setDetailUseage (String detailUseage) {
		this.detailUseage=detailUseage;
	}
	public void setProduct (String product) {
		this.product=product;
	}
	public void setSreviceFeeRate (double sreviceFeeRate) {
		this.sreviceFeeRate=sreviceFeeRate;
	}
	public void setInterestRate (double interestRate) {
		this.interestRate=interestRate;
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
	public void setRepayAccBank (String repayAccBank) {
		this.repayAccBank=repayAccBank;
	}
	public void setRepayAccount (String repayAccount) {
		this.repayAccount=repayAccount;
	}
	public void setRegDate (java.util.Date regDate) {
		this.regDate=regDate;
	}
	public void setRegDateStr (String regDateStr) {
		this.regDateStr=regDateStr;
	}
	public void setRegAmount (double regAmount) {
		this.regAmount=regAmount;
	}
	public void setAcctualAmount (double acctualAmount) {
		this.acctualAmount=acctualAmount;
	}
	public void setOrgCodeNo (String orgCodeNo) {
		this.orgCodeNo=orgCodeNo;
	}
	public void setOrgCodeExpiryDate (java.util.Date orgCodeExpiryDate) {
		this.orgCodeExpiryDate=orgCodeExpiryDate;
	}
	public void setOrgCodeExpiryDateStr (String orgCodeExpiryDateStr) {
		this.orgCodeExpiryDateStr=orgCodeExpiryDateStr;
	}
	public void setTaxRegNo (String taxRegNo) {
		this.taxRegNo=taxRegNo;
	}
	public void setBaiscAccountBank (String baiscAccountBank) {
		this.baiscAccountBank=baiscAccountBank;
	}
	public void setBaiscAccount (String baiscAccount) {
		this.baiscAccount=baiscAccount;
	}
	public void setBizzScope (String bizzScope) {
		this.bizzScope=bizzScope;
	}
	public void setCompanyType (String companyType) {
		this.companyType=companyType;
	}
	public void setMainProduct (String mainProduct) {
		this.mainProduct=mainProduct;
	}
	public void setLastYearTurnover (double lastYearTurnover) {
		this.lastYearTurnover=lastYearTurnover;
	}
	public void setLast3mTurnover (double last3mTurnover) {
		this.last3mTurnover=last3mTurnover;
	}
	public void setCooperateBankCompany (String cooperateBankCompany) {
		this.cooperateBankCompany=cooperateBankCompany;
	}
	public void setDailyClearBank (String dailyClearBank) {
		this.dailyClearBank=dailyClearBank;
	}
	public void setLoanBalance (double loanBalance) {
		this.loanBalance=loanBalance;
	}
	public void setFinanceCompany (String financeCompany) {
		this.financeCompany=financeCompany;
	}
	public void setFinanceBank (String financeBank) {
		this.financeBank=financeBank;
	}
	public void setNearly3mEleBill1 (double nearly3mEleBill1) {
		this.nearly3mEleBill1=nearly3mEleBill1;
	}
	public void setNearly3mEleBill2 (double nearly3mEleBill2) {
		this.nearly3mEleBill2=nearly3mEleBill2;
	}
	public void setNearly3mEleBill3 (double nearly3mEleBill3) {
		this.nearly3mEleBill3=nearly3mEleBill3;
	}
	public void setMatchType (String matchType) {
		this.matchType=matchType;
	}
	public void setCustomerManager (String customerManager) {
		this.customerManager=customerManager;
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
	public String getSales() {
		return sales;
	}
	public void setSales(String sales) {
		this.sales = sales;
	}

}


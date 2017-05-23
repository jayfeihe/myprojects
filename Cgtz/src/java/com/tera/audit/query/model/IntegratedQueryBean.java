package com.tera.audit.query.model;

import com.tera.audit.loan.model.LoanBase;
import com.tera.util.DateUtils;

public class IntegratedQueryBean {

	private String id; // 申请主键
	private String loanId; // 申请编号
	private String name; // 姓名
	private String idNo; // 证件号
	private double loanAmt; // 借款金额
	private String product; // 产品
	private String productName; // 产品名称
	private String isRenew; // 是否续贷
	private String renewNum; // 续贷次数
	private String isEnough; // 是否足额

	private java.sql.Timestamp inputTime; // 录入时间
	private java.sql.Timestamp branchAuditTime; // 分公司初审时间
	private java.sql.Timestamp riskFirstTime; // 风控初审时间
	private java.sql.Timestamp riskCheckTime; // 风控复核时间
	private java.sql.Timestamp meetFirstTime; // 评审会初审时间
	private java.sql.Timestamp meetCheckTime; // 评审会复核时间
	        
	private java.sql.Timestamp lawTime; // 法务初审时间
	private java.sql.Timestamp cashTime; // 出纳核帐时间
	private java.sql.Timestamp acctTime; // 财务核帐时间
	private java.sql.Timestamp loanTime; // 放款时间
	
	private String inputTimeStr; // 录入时间展示
	private String branchAuditTimeStr; // 分公司初审时间展示
	private String riskFirstTimeStr; // 风控初审时间展示
	private String riskCheckTimeStr; // 风控复核时间展示
	private String meetFirstTimeStr; // 评审会初审时间展示
	private String meetCheckTimeStr; // 评审会复核时间展示
	 
	private String lawTimeStr; // 法务处理时间展示
	private String cashTimeStr; // 出纳核帐时间展示
	private String acctTimeStr; // 财务核帐时间展示
	private String loanTimeStr; // 放款时间展示
	 
	private String inputTimeMin;
	private String branchAuditTimeMin;
	private String riskFirstTimeMin;
	private String riskCheckTimeMin;
	private String meetFirstTimeMin;
	private String meetCheckTimeMin;
	private String lawTimeMin;
	private String cashTimeMin;
	private String acctTimeMin;
	private String loanTimeMin;
	 
	private String inputTimeMax;
	private String branchAuditTimeMax;
	private String riskFirstTimeMax;
	private String riskCheckTimeMax;
	private String meetFirstTimeMax;
	private String meetCheckTimeMax;
	private String lawTimeMax;
	private String cashTimeMax;
	private String acctTimeMax;
	private String loanTimeMax;
	 
	private String salesman; // 业务员
	private String branchAuditUser; // 分公司初审人员
	private String riskFirstAuditUser; // 风控初审人员
	private String riskCheckUser; // 风控复核人员
	private String meetFirstAuditUser; // 评审会初审人员
	private String meetCheckUser; // 评审会复核人员
	 
	private String lawInsideUser; // 法务内勤人员
	private String lawFirstUser; // 法务初审人员
	private String lawReviewUser; // 法务复核人员
	private String cashUser; // 出纳核帐人员
	private String acctUser; // 财务核帐人员
	private String loanUser; // 放款人员
	
	private String processerName; // 当前待处理人
	 
	private String orgId; // 机构id
	private String orgName; // 机构名称
	private String bpmState; // 节点状态
	private String appState; // 申请状态
	
	private String state1st; //一级状态
	private String contractId; // 合同号
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLoanId() {
		return loanId;
	}
	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIdNo() {
		return idNo;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	public double getLoanAmt() {
		return loanAmt;
	}
	public void setLoanAmt(double loanAmt) {
		this.loanAmt = loanAmt;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getIsRenew() {
		return isRenew;
	}
	public void setIsRenew(String isRenew) {
		this.isRenew = isRenew;
	}
	public String getRenewNum() {
		return renewNum;
	}
	public void setRenewNum(String renewNum) {
		this.renewNum = renewNum;
	}
	public String getIsEnough() {
		return isEnough;
	}
	public void setIsEnough(String isEnough) {
		this.isEnough = isEnough;
	}
	public java.sql.Timestamp getInputTime() {
		return inputTime;
	}
	public void setInputTime(java.sql.Timestamp inputTime) {
		this.inputTime = inputTime;
	}
	public java.sql.Timestamp getBranchAuditTime() {
		return branchAuditTime;
	}
	public void setBranchAuditTime(java.sql.Timestamp branchAuditTime) {
		this.branchAuditTime = branchAuditTime;
	}
	public java.sql.Timestamp getRiskFirstTime() {
		return riskFirstTime;
	}
	public void setRiskFirstTime(java.sql.Timestamp riskFirstTime) {
		this.riskFirstTime = riskFirstTime;
	}
	public java.sql.Timestamp getRiskCheckTime() {
		return riskCheckTime;
	}
	public void setRiskCheckTime(java.sql.Timestamp riskCheckTime) {
		this.riskCheckTime = riskCheckTime;
	}
	public java.sql.Timestamp getMeetFirstTime() {
		return meetFirstTime;
	}
	public void setMeetFirstTime(java.sql.Timestamp meetFirstTime) {
		this.meetFirstTime = meetFirstTime;
	}
	public java.sql.Timestamp getMeetCheckTime() {
		return meetCheckTime;
	}
	public void setMeetCheckTime(java.sql.Timestamp meetCheckTime) {
		this.meetCheckTime = meetCheckTime;
	}
	public java.sql.Timestamp getLawTime() {
		return lawTime;
	}
	public void setLawTime(java.sql.Timestamp lawTime) {
		this.lawTime = lawTime;
	}
	public java.sql.Timestamp getCashTime() {
		return cashTime;
	}
	public void setCashTime(java.sql.Timestamp cashTime) {
		this.cashTime = cashTime;
	}
	public java.sql.Timestamp getAcctTime() {
		return acctTime;
	}
	public void setAcctTime(java.sql.Timestamp acctTime) {
		this.acctTime = acctTime;
	}
	public java.sql.Timestamp getLoanTime() {
		return loanTime;
	}
	public void setLoanTime(java.sql.Timestamp loanTime) {
		this.loanTime = loanTime;
	}
	public String getInputTimeStr() {
		return DateUtils.formatTime(inputTime);
	}
	public void setInputTimeStr(String inputTimeStr) {
		this.inputTimeStr = inputTimeStr;
	}
	public String getBranchAuditTimeStr() {
		return DateUtils.formatTime(branchAuditTime);
	}
	public void setBranchAuditTimeStr(String branchAuditTimeStr) {
		this.branchAuditTimeStr = branchAuditTimeStr;
	}
	public String getRiskFirstTimeStr() {
		return DateUtils.formatTime(riskFirstTime);
	}
	public void setRiskFirstTimeStr(String riskFirstTimeStr) {
		this.riskFirstTimeStr = riskFirstTimeStr;
	}
	public String getRiskCheckTimeStr() {
		return DateUtils.formatTime(riskCheckTime);
	}
	public void setRiskCheckTimeStr(String riskCheckTimeStr) {
		this.riskCheckTimeStr = riskCheckTimeStr;
	}
	public String getMeetFirstTimeStr() {
		return DateUtils.formatTime(meetFirstTime);
	}
	public void setMeetFirstTimeStr(String meetFirstTimeStr) {
		this.meetFirstTimeStr = meetFirstTimeStr;
	}
	public String getMeetCheckTimeStr() {
		return DateUtils.formatTime(meetCheckTime);
	}
	public void setMeetCheckTimeStr(String meetCheckTimeStr) {
		this.meetCheckTimeStr = meetCheckTimeStr;
	}
	public String getLawTimeStr() {
		return DateUtils.formatTime(lawTime);
	}
	public void setLawTimeStr(String lawTimeStr) {
		this.lawTimeStr = lawTimeStr;
	}
	public String getCashTimeStr() {
		return DateUtils.formatTime(cashTime);
	}
	public void setCashTimeStr(String cashTimeStr) {
		this.cashTimeStr = cashTimeStr;
	}
	public String getAcctTimeStr() {
		return DateUtils.formatTime(acctTime);
	}
	public void setAcctTimeStr(String acctTimeStr) {
		this.acctTimeStr = acctTimeStr;
	}
	public String getLoanTimeStr() {
		return DateUtils.formatTime(loanTime);
	}
	public void setLoanTimeStr(String loanTimeStr) {
		this.loanTimeStr = loanTimeStr;
	}
	public String getInputTimeMin() {
		return inputTimeMin;
	}
	public void setInputTimeMin(String inputTimeMin) {
		this.inputTimeMin = inputTimeMin;
	}
	public String getBranchAuditTimeMin() {
		return branchAuditTimeMin;
	}
	public void setBranchAuditTimeMin(String branchAuditTimeMin) {
		this.branchAuditTimeMin = branchAuditTimeMin;
	}
	public String getRiskFirstTimeMin() {
		return riskFirstTimeMin;
	}
	public void setRiskFirstTimeMin(String riskFirstTimeMin) {
		this.riskFirstTimeMin = riskFirstTimeMin;
	}
	public String getRiskCheckTimeMin() {
		return riskCheckTimeMin;
	}
	public void setRiskCheckTimeMin(String riskCheckTimeMin) {
		this.riskCheckTimeMin = riskCheckTimeMin;
	}
	public String getMeetFirstTimeMin() {
		return meetFirstTimeMin;
	}
	public void setMeetFirstTimeMin(String meetFirstTimeMin) {
		this.meetFirstTimeMin = meetFirstTimeMin;
	}
	public String getMeetCheckTimeMin() {
		return meetCheckTimeMin;
	}
	public void setMeetCheckTimeMin(String meetCheckTimeMin) {
		this.meetCheckTimeMin = meetCheckTimeMin;
	}
	public String getLawTimeMin() {
		return lawTimeMin;
	}
	public void setLawTimeMin(String lawTimeMin) {
		this.lawTimeMin = lawTimeMin;
	}
	public String getCashTimeMin() {
		return cashTimeMin;
	}
	public void setCashTimeMin(String cashTimeMin) {
		this.cashTimeMin = cashTimeMin;
	}
	public String getAcctTimeMin() {
		return acctTimeMin;
	}
	public void setAcctTimeMin(String acctTimeMin) {
		this.acctTimeMin = acctTimeMin;
	}
	public String getLoanTimeMin() {
		return loanTimeMin;
	}
	public void setLoanTimeMin(String loanTimeMin) {
		this.loanTimeMin = loanTimeMin;
	}
	public String getInputTimeMax() {
		return inputTimeMax;
	}
	public void setInputTimeMax(String inputTimeMax) {
		this.inputTimeMax = inputTimeMax;
	}
	public String getBranchAuditTimeMax() {
		return branchAuditTimeMax;
	}
	public void setBranchAuditTimeMax(String branchAuditTimeMax) {
		this.branchAuditTimeMax = branchAuditTimeMax;
	}
	public String getRiskFirstTimeMax() {
		return riskFirstTimeMax;
	}
	public void setRiskFirstTimeMax(String riskFirstTimeMax) {
		this.riskFirstTimeMax = riskFirstTimeMax;
	}
	public String getRiskCheckTimeMax() {
		return riskCheckTimeMax;
	}
	public void setRiskCheckTimeMax(String riskCheckTimeMax) {
		this.riskCheckTimeMax = riskCheckTimeMax;
	}
	public String getMeetFirstTimeMax() {
		return meetFirstTimeMax;
	}
	public void setMeetFirstTimeMax(String meetFirstTimeMax) {
		this.meetFirstTimeMax = meetFirstTimeMax;
	}
	public String getMeetCheckTimeMax() {
		return meetCheckTimeMax;
	}
	public void setMeetCheckTimeMax(String meetCheckTimeMax) {
		this.meetCheckTimeMax = meetCheckTimeMax;
	}
	public String getLawTimeMax() {
		return lawTimeMax;
	}
	public void setLawTimeMax(String lawTimeMax) {
		this.lawTimeMax = lawTimeMax;
	}
	public String getCashTimeMax() {
		return cashTimeMax;
	}
	public void setCashTimeMax(String cashTimeMax) {
		this.cashTimeMax = cashTimeMax;
	}
	public String getAcctTimeMax() {
		return acctTimeMax;
	}
	public void setAcctTimeMax(String acctTimeMax) {
		this.acctTimeMax = acctTimeMax;
	}
	public String getLoanTimeMax() {
		return loanTimeMax;
	}
	public void setLoanTimeMax(String loanTimeMax) {
		this.loanTimeMax = loanTimeMax;
	}
	public String getSalesman() {
		return salesman;
	}
	public void setSalesman(String salesman) {
		this.salesman = salesman;
	}
	public String getBranchAuditUser() {
		return branchAuditUser;
	}
	public void setBranchAuditUser(String branchAuditUser) {
		this.branchAuditUser = branchAuditUser;
	}
	public String getRiskFirstAuditUser() {
		return riskFirstAuditUser;
	}
	public void setRiskFirstAuditUser(String riskFirstAuditUser) {
		this.riskFirstAuditUser = riskFirstAuditUser;
	}
	public String getRiskCheckUser() {
		return riskCheckUser;
	}
	public void setRiskCheckUser(String riskCheckUser) {
		this.riskCheckUser = riskCheckUser;
	}
	public String getMeetFirstAuditUser() {
		return meetFirstAuditUser;
	}
	public void setMeetFirstAuditUser(String meetFirstAuditUser) {
		this.meetFirstAuditUser = meetFirstAuditUser;
	}
	public String getMeetCheckUser() {
		return meetCheckUser;
	}
	public void setMeetCheckUser(String meetCheckUser) {
		this.meetCheckUser = meetCheckUser;
	}
	public String getLawInsideUser() {
		return lawInsideUser;
	}
	public void setLawInsideUser(String lawInsideUser) {
		this.lawInsideUser = lawInsideUser;
	}
	public String getLawFirstUser() {
		return lawFirstUser;
	}
	public void setLawFirstUser(String lawFirstUser) {
		this.lawFirstUser = lawFirstUser;
	}
	public String getLawReviewUser() {
		return lawReviewUser;
	}
	public void setLawReviewUser(String lawReviewUser) {
		this.lawReviewUser = lawReviewUser;
	}
	public String getCashUser() {
		return cashUser;
	}
	public void setCashUser(String cashUser) {
		this.cashUser = cashUser;
	}
	public String getAcctUser() {
		return acctUser;
	}
	public void setAcctUser(String acctUser) {
		this.acctUser = acctUser;
	}
	public String getLoanUser() {
		return loanUser;
	}
	public void setLoanUser(String loanUser) {
		this.loanUser = loanUser;
	}
	public String getProcesserName() {
		return processerName;
	}
	public void setProcesserName(String processerName) {
		this.processerName = processerName;
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
	public String getBpmState() {
		return bpmState;
	}
	public void setBpmState(String bpmState) {
		this.bpmState = bpmState;
	}
	public String getAppState() {
		LoanBase base = new LoanBase();
		base.setAppState(appState);
		return base.getAppState();	
	}
	public void setAppState(String appState) {
		this.appState = appState;
	}
	public String getState1st() {
		return state1st;
	}
	public void setState1st(String state1st) {
		this.state1st = state1st;
	}
	public String getContractId() {
		return contractId;
	}
	public void setContractId(String contractId) {
		this.contractId = contractId;
	}
}

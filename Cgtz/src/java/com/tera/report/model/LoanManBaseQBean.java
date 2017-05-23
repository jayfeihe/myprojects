package com.tera.report.model;

/**
 * 借款人基本情况表实体类
 * @author QYANZE
 *
 */
public class LoanManBaseQBean {

	private String onlineConId; 	// 线上编号 
	private String contractId;      // 线下编号 
	private String projectName;     // 项目名称   
	private java.util.Date onlineStartDate; // 上线时间 
	private java.util.Date startDate; 	// 开始时间 
	private java.util.Date endDate; 	// 结束时间 
	private double loanAmt; 			// 合同金额 
	private String lendUser;        // 债权人 
	private String name;            // 借款人 
	private double rate;   			// 线下利率 
	private double onlineRateOut; 	// 线上利率 	
	private double planCapital;  		// 应收本金 
	private double realCapital; 		// 实收本金 
	private String isRenew;  			// 项目类型 
	private double planInterest; 		// 应收利息 
	private double realInterest; 		// 实收利息 
	private double payOutInterest;      // 线上垫付利息
	private java.util.Date collectDate; // 收到时间
	private String num;					// 期数
	private String remark;              // 备注
	
	private String orgId;               // 机构
	private String orgName;             // 机构名称
	
	private String startDateMin; // 合同开始时间
	private String startDateMax; // 合同开始时间
	private String endDateMin; // 合同结束时间
	private String endDateMax; // 合同结束时间
	
	public String getOnlineConId() {
		return onlineConId;
	}
	public void setOnlineConId(String onlineConId) {
		this.onlineConId = onlineConId;
	}
	public String getContractId() {
		return contractId;
	}
	public void setContractId(String contractId) {
		this.contractId = contractId;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public java.util.Date getOnlineStartDate() {
		return onlineStartDate;
	}
	public void setOnlineStartDate(java.util.Date onlineStartDate) {
		this.onlineStartDate = onlineStartDate;
	}
	public java.util.Date getStartDate() {
		return startDate;
	}
	public void setStartDate(java.util.Date startDate) {
		this.startDate = startDate;
	}
	public java.util.Date getEndDate() {
		return endDate;
	}
	public void setEndDate(java.util.Date endDate) {
		this.endDate = endDate;
	}
	public double getLoanAmt() {
		return loanAmt;
	}
	public void setLoanAmt(double loanAmt) {
		this.loanAmt = loanAmt;
	}
	public String getLendUser() {
		return lendUser;
	}
	public void setLendUser(String lendUser) {
		this.lendUser = lendUser;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
	public double getOnlineRateOut() {
		return onlineRateOut;
	}
	public void setOnlineRateOut(double onlineRateOut) {
		this.onlineRateOut = onlineRateOut;
	}
	public double getPlanCapital() {
		return planCapital;
	}
	public void setPlanCapital(double planCapital) {
		this.planCapital = planCapital;
	}
	public double getRealCapital() {
		return realCapital;
	}
	public void setRealCapital(double realCapital) {
		this.realCapital = realCapital;
	}
	public String getIsRenew() {
		return isRenew;
	}
	public void setIsRenew(String isRenew) {
		this.isRenew = isRenew;
	}
	public double getPlanInterest() {
		return planInterest;
	}
	public void setPlanInterest(double planInterest) {
		this.planInterest = planInterest;
	}
	public double getRealInterest() {
		return realInterest;
	}
	public void setRealInterest(double realInterest) {
		this.realInterest = realInterest;
	}
	public double getPayOutInterest() {
		return payOutInterest;
	}
	public void setPayOutInterest(double payOutInterest) {
		this.payOutInterest = payOutInterest;
	}
	public java.util.Date getCollectDate() {
		return collectDate;
	}
	public void setCollectDate(java.util.Date collectDate) {
		this.collectDate = collectDate;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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
	public String getStartDateMin() {
		return startDateMin;
	}
	public void setStartDateMin(String startDateMin) {
		this.startDateMin = startDateMin;
	}
	public String getStartDateMax() {
		return startDateMax;
	}
	public void setStartDateMax(String startDateMax) {
		this.startDateMax = startDateMax;
	}
	public String getEndDateMin() {
		return endDateMin;
	}
	public void setEndDateMin(String endDateMin) {
		this.endDateMin = endDateMin;
	}
	public String getEndDateMax() {
		return endDateMax;
	}
	public void setEndDateMax(String endDateMax) {
		this.endDateMax = endDateMax;
	}
}

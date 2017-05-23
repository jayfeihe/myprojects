package com.tera.report.model;

/**
 * 项目明细表实体类
 * @author QYANZE
 *
 */
public class ProjectDetailQBean {

	private String contractId;     //  线下合同编号            
	private String onlineConId;    //  线上合同编号            
	private String projectName;    //  项目名称                
	private java.util.Date startDate;      //  合同开始时间    
	private java.util.Date endDate;        //  合同结束时间    
	private java.util.Date onlineStartDate;//  上线时间        
	private java.util.Date realEndDate;    //  合同实际结束时间
	private String contractDays;   //  合同天数，固定不变的    
	private String contractState;  //  合同状态                
	private double loanAmt;        //  线下借款金额            
	private double onlineAmt;      //  线上借款金额            
	private String loanUser;       //  借款人                  
	private String lendUser;       //  出借人                  
	private double loanRate;       //  线下借款利率            
	private double onlineRate;     //  线上利率                
	private String onlineRetway;   //  还款方式                
	private String getLoanWay;     //  融资方式   
	private String orgId; 		   //  分公司id
	private String orgName;        //  分公司名称                  
	private String loanType;       //  线下类别                
	private String onlineType;     //  线上类别    
	
	private String product;        // 产品（贷款类别）
	
	private String startDateMin;    // 合同开始时间
	private String startDateMax;    // 合同结束时间
	
	public String getContractId() {
		return contractId;
	}
	public void setContractId(String contractId) {
		this.contractId = contractId;
	}
	public String getOnlineConId() {
		return onlineConId;
	}
	public void setOnlineConId(String onlineConId) {
		this.onlineConId = onlineConId;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
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
	public java.util.Date getOnlineStartDate() {
		return onlineStartDate;
	}
	public void setOnlineStartDate(java.util.Date onlineStartDate) {
		this.onlineStartDate = onlineStartDate;
	}
	public java.util.Date getRealEndDate() {
		return realEndDate;
	}
	public void setRealEndDate(java.util.Date realEndDate) {
		this.realEndDate = realEndDate;
	}
	public String getContractDays() {
		return contractDays;
	}
	public void setContractDays(String contractDays) {
		this.contractDays = contractDays;
	}
	public String getContractState() {
		return contractState;
	}
	public void setContractState(String contractState) {
		this.contractState = contractState;
	}
	public double getLoanAmt() {
		return loanAmt;
	}
	public void setLoanAmt(double loanAmt) {
		this.loanAmt = loanAmt;
	}
	public double getOnlineAmt() {
		return onlineAmt;
	}
	public void setOnlineAmt(double onlineAmt) {
		this.onlineAmt = onlineAmt;
	}
	public String getLoanUser() {
		return loanUser;
	}
	public void setLoanUser(String loanUser) {
		this.loanUser = loanUser;
	}
	public String getLendUser() {
		return lendUser;
	}
	public void setLendUser(String lendUser) {
		this.lendUser = lendUser;
	}
	public double getLoanRate() {
		return loanRate;
	}
	public void setLoanRate(double loanRate) {
		this.loanRate = loanRate;
	}
	public double getOnlineRate() {
		return onlineRate;
	}
	public void setOnlineRate(double onlineRate) {
		this.onlineRate = onlineRate;
	}
	public String getOnlineRetway() {
		return onlineRetway;
	}
	public void setOnlineRetway(String onlineRetway) {
		this.onlineRetway = onlineRetway;
	}
	public String getGetLoanWay() {
		return getLoanWay;
	}
	public void setGetLoanWay(String getLoanWay) {
		this.getLoanWay = getLoanWay;
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
	public String getLoanType() {
		return loanType;
	}
	public void setLoanType(String loanType) {
		this.loanType = loanType;
	}
	public String getOnlineType() {
		return onlineType;
	}
	public void setOnlineType(String onlineType) {
		this.onlineType = onlineType;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
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
}

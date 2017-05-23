package com.tera.feature.projectDetail.model;

import java.util.Date;

import com.tera.util.DateUtils;

public class ProjectDetail {
	public static final String IS_END_Y="1";//有实际结束时间
	public static final String IS_END_N="0";//无实际结束时间
	//属性部分
	private int id;
	private String contractNo;
	private String projectId;
	private String projectName;
	private Date startDate;
	private String startDateStr;
	private Date endDate;
	private String endDateStr;
	private Date onlineDate;
	private String onlineDateStr;
	private Date realEndDate;
	private String realEndDateStr;
	private String isEnd;
	private int days;//天数
	private double loanAmt;
	private String lendName;
	private String lendId;
	private String lendNo;
	private String loanName;
	private String loanNo;
	private double loanRate;
	private double onlineRate;
	private String retWay;//还款方式
	private String type;//贷款类型
	private String org;//机构
	private String orgName;
	private String branchRemark;
	private String acctRemark;
	
	//时间区间
	private Date minStartDate;
	private Date maxStartDate;
	private Date minEndDate;
	private Date maxEndDate;
	private Date minOnlineDate;
	private Date maxOnlineDate;
	private Date minRealEndDate;
	private Date maxRealEndDate;
	
	//getter部分
	public int getId() {
		return id;
	}
	public String getContractNo() {
		return contractNo;
	}
	public String getProjectId() {
		return projectId;
	}
	public String getProjectName() {
		return projectName;
	}
	public Date getStartDate() {
		return startDate;
	}
	public String getStartDateStr() {
		return DateUtils.formatDate(startDate);
	}
	public Date getEndDate() {
		return endDate;
	}
	public String getEndDateStr() {
		return DateUtils.formatDate(endDate);
	}
	public Date getOnlineDate(){
		return onlineDate;
	}
	public String getOnlineDateStr(){
		return DateUtils.formatDate(onlineDate);
	}
	public Date getRealEndDate() {
		return realEndDate;
	}
	public String getRealEndDateStr() {
		return DateUtils.formatDate(realEndDate);
	}
	public String getIsEnd() {
		return isEnd;
	}
	public int getDays() {
		if(this.realEndDate!=null){
			days=DateUtils.getDayRange(startDate, realEndDate);
		}else{
			days=DateUtils.getDayRange(startDate, endDate);
		}
		return days;
	}
	public double getLoanAmt() {
		return loanAmt;
	}
	public String getLendName() {
		return lendName;
	}
	public String getLendId() {
		return lendId;
	}
	public String getLendNo() {
		return lendNo;
	}
	public String getLoanName() {
		return loanName;
	}
	public String getLoanNo() {
		return loanNo;
	}
	public double getLoanRate() {
		return loanRate;
	}
	public double getOnlineRate() {
		return onlineRate;
	}
	public String getRetWay() {
		return retWay;
	}
	public String getType() {
		return type;
	}
	public String getOrg() {
		return org;
	}
	public String getOrgName(){
		return orgName;
	}
	public String getBranchRemark() {
		return branchRemark;
	}
	public String getAcctRemark() {
		return acctRemark;
	}
	public Date getMinStartDate() {
		return minStartDate;
	}
	public Date getMaxStartDate() {
		return maxStartDate;
	}
	public Date getMinEndDate() {
		return minEndDate;
	}
	public Date getMaxEndDate() {
		return maxEndDate;
	}
	
	public Date getMinOnlineDate() {
		return minOnlineDate;
	}
	public Date getMaxOnlineDate() {
		return maxOnlineDate;
	}
	public Date getMinRealEndDate() {
		return minRealEndDate;
	}
	public Date getMaxRealEndDate() {
		return maxRealEndDate;
	}
	
	//setter部分
	public void setId(int id) {
		this.id = id;
	}
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public void setStartDateStr(String startDateStr) {
		this.startDateStr = startDateStr;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public void setEndDateStr(String endDateStr) {
		this.endDateStr = endDateStr;
	}
	public void setOnlineDate(Date onlineDate){
		this.onlineDate=onlineDate;
	}
	public void setOnlineDateStr(String onlineDateStr){
		this.onlineDateStr=onlineDateStr;
	}
	public void setRealEndDate(Date realEndDate) {
		this.realEndDate = realEndDate;
	}
	public void setRealEndDateStr(String realEndDateStr) {
		this.realEndDateStr = realEndDateStr;
	}
	public void setIsEnd(String isEnd) {
		this.isEnd = isEnd;
	}
	public void setDays(int days) {
		this.days = days;
	}
	public void setLoanAmt(double loanAmt) {
		this.loanAmt = loanAmt;
	}
	public void setLendName(String lendName) {
		this.lendName = lendName;
	}
	public void setLendId(String lendId) {
		this.lendId = lendId;
	}
	public void setLendNo(String lendNo) {
		this.lendNo = lendNo;
	}
	public void setLoanName(String loanName) {
		this.loanName = loanName;
	}
	public void setLoanNo(String loanNo) {
		this.loanNo = loanNo;
	}
	public void setLoanRate(double loanRate) {
		this.loanRate = loanRate;
	}
	public void setOnlineRate(double onlineRate) {
		this.onlineRate = onlineRate;
	}
	public void setRetWay(String retWay) {
		this.retWay = retWay;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setOrg(String org) {
		this.org = org;
	}
	public void setOrgName(String orgName){
		this.orgName=orgName;
	}
	public void setBranchRemark(String branchRemark) {
		this.branchRemark = branchRemark;
	}
	public void setAcctRemark(String acctRemark) {
		this.acctRemark = acctRemark;
	}
	public void setMinStartDate(Date minStartDate) {
		this.minStartDate = minStartDate;
	}
	public void setMaxStartDate(Date maxStartDate) {
		this.maxStartDate = maxStartDate;
	}
	public void setMinEndDate(Date minEndDate) {
		this.minEndDate = minEndDate;
	}
	public void setMaxEndDate(Date maxEndDate) {
		this.maxEndDate = maxEndDate;
	}
	
	public void setMinOnlineDate(Date minOnlineDate) {
		this.minOnlineDate = minOnlineDate;
	}
	public void setMaxOnlineDate(Date maxOnlineDate) {
		this.maxOnlineDate = maxOnlineDate;
	}
	public void setMinRealEndDate(Date minRealEndDate) {
		this.minRealEndDate = minRealEndDate;
	}
	public void setMaxRealEndDate(Date maxRealEndDate) {
		this.maxRealEndDate = maxRealEndDate;
	}
	
	
}

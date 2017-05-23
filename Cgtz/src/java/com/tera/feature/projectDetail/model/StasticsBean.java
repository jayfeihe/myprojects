package com.tera.feature.projectDetail.model;

import java.util.Date;

import com.tera.util.DateUtils;

public class StasticsBean {
	//属性部分
	private String org;//分公司
	private String orgName;//分公司名称
	private Date startDate;//合同开始日期
	private String startDateStr;
	private Date endDate;//合同结束日期
	private String endDateStr;
	private Date inputDate;//用于存量统计
	private String inputDateStr;
	private String mon;//年月字符串
	private Date minStartDate;
	private Date maxStartDate;
	private int counts;//成交笔数(合同数)
	private double loanAmt;//金额
	private String type;//产品
	
	//getter部分
	public String getOrg() {
		return org;
	}
	public String getOrgName() {
		return orgName;
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
	public Date getInputDate() {
		return inputDate;
	}
	
	public String getInputDateStr() {
		return DateUtils.formatDate(inputDate);
	}
	public String getMon(){
		return mon;
	}
	public Date getMinStartDate() {
		return minStartDate;
	}
	public Date getMaxStartDate() {
		return maxStartDate;
	}
	public int getCounts() {
		return counts;
	}
	public double getLoanAmt() {
		return loanAmt;
	}
	public String getType() {
		return type;
	}
	//setter部分
	public void setOrg(String org) {
		this.org = org;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
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
	public void setInputDate(Date inputDate) {
		this.inputDate = inputDate;
	}
	public void setInputDateStr(String inputDateStr) {
		this.inputDateStr = inputDateStr;
	}
	public void setMon(String mon){
		this.mon=mon;
	}
	public void setMinStartDate(Date minStartDate) {
		this.minStartDate = minStartDate;
	}
	public void setMaxStartDate(Date maxStartDate) {
		this.maxStartDate = maxStartDate;
	}
	public void setCounts(int counts) {
		this.counts = counts;
	}
	public void setLoanAmt(double loanAmt) {
		this.loanAmt = loanAmt;
	}
	public void setType(String type) {
		this.type = type;
	}		
}

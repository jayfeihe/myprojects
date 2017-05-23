package com.tera.report.model;

import java.util.Date;

import com.tera.util.DateUtils;
import com.tera.util.MathUtils;

public class ReportOverdue {
   //分公司逾期统计表
	private int id;
	private String orgId;
	private String orgName;
	private String type;
	private int dealNum;
	private int projNum;
	private double overdueAmt;
	private double prinRate;
	private double amt_14;
	private double rate_14;
	private double amt_30;
	private double rate_30;
	private double amt_90;
	private double rate_90;
	private double amt_180;
	private double rate_180;
	private double amtMore;
	private double rateMore;
	private double loanAmt;
	private Date createDate;
	private String createDateStr;
	//日期筛选
	private Date startDate;
	private String startDateStr;
	private Date endDate;
	private String endDateStr;
	//getter
	public int getId() {
		return id;
	}
	public String getOrgId() {
		return orgId;
	}
	
	public String getOrgName() {
		return orgName;
	}
	public String getType() {
		return type;
	}
	public int getDealNum() {
		return dealNum;
	}
	public int getProjNum() {
		return projNum;
	}
	public double getOverdueAmt() {
		return overdueAmt;
	}
	public double getPrinRate() {
		if(loanAmt==0){
			return 0;
		}
		return MathUtils.div(overdueAmt,loanAmt);
	}
	public double getAmt_14() {
		return amt_14;
	}
	public double getRate_14() {
		if(overdueAmt==0){
			return 0;
		}
		return MathUtils.div(amt_14,overdueAmt);
	}
	public double getAmt_30() {
		return amt_30;
	}
	public double getRate_30() {
		if(overdueAmt==0){
			return 0;
		}
		return MathUtils.div(amt_30,overdueAmt);
	}
	public double getAmt_90() {
		return amt_90;
	}
	public double getRate_90() {
		if(overdueAmt==0){
			return 0;
		}
		return MathUtils.div(amt_90,overdueAmt);
	}
	public double getAmt_180() {
		return amt_180;
	}
	public double getRate_180() {
		if(overdueAmt==0){
			return 0;
		}
		return MathUtils.div(amt_180,overdueAmt);
	}
	public double getAmtMore() {
		return amtMore;
	}
	public double getRateMore() {
		if(overdueAmt==0){
			return 0;
		}
		return MathUtils.div(amtMore,overdueAmt);
	}
	public double getLoanAmt() {
		return loanAmt;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public String getCreateDateStr(){
		return DateUtils.formatDate(createDate);
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
	//setter
	public void setId(int id) {
		this.id = id;
	}
	public void setOrgId(String org_id) {
		this.orgId = org_id;
	}
	
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setDealNum(int dealNum) {
		this.dealNum = dealNum;
	}
	public void setProjNum(int projNum) {
		this.projNum = projNum;
	}
	public void setOverdueAmt(double overdueAmt) {
		this.overdueAmt = overdueAmt;
	}
	public void setPrinRate(double prinRate) {
		this.prinRate = prinRate;
	}
	public void setAmt_14(double amt_14) {
		this.amt_14 = amt_14;
	}
	public void setRate_14(double rate_14) {
		this.rate_14 = rate_14;
	}
	public void setAmt_30(double amt_30) {
		this.amt_30 = amt_30;
	}
	public void setRate_30(double rate_30) {
		this.rate_30 = rate_30;
	}
	public void setAmt_90(double amt_90) {
		this.amt_90 = amt_90;
	}
	public void setRate_90(double rate_90) {
		this.rate_90 = rate_90;
	}
	public void setAmt_180(double amt_180) {
		this.amt_180 = amt_180;
	}
	public void setRate_180(double rate_180) {
		this.rate_180 = rate_180;
	}
	public void setAmtMore(double amtMore) {
		this.amtMore = amtMore;
	}
	public void setRateMore(double rateMore) {
		this.rateMore = rateMore;
	}
	public void setLoanAmt(double loanAmt) {
		this.loanAmt = loanAmt;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public void setCreateDateStr(String createDateStr){
		this.createDateStr=createDateStr;
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
	
	
}

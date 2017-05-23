package com.tera.report.model;

import java.util.Date;

import com.tera.util.DateUtils;

public class LawFee {
   //法律服务费统计
	//属性部分
		private String loanId; //申请Id
		private String contractId; //合同id
		private java.util.Date retDate; //还款日
		private String retDateStr; //合同开始日期(还款日)
		private int num; //期数(第1期)
		private String type; //类别(1利息2本金)
		private double planLawFee; //当月应收法律服务费	
		private double realLawFee; //当月实收法律服务费
		private String state;
		
		//时间
		private Date minRetDate;
		private Date maxRetDate;
		public String getLoanId() {
			return loanId;
		}
		public String getContractId() {
			return contractId;
		}
		public java.util.Date getRetDate() {
			return retDate;
		}
		public String getRetDateStr() {
			return DateUtils.formatDate(retDate);
		}
		public int getNum() {
			return num;
		}
		public String getType() {
			return type;
		}
		public double getPlanLawFee() {
			return planLawFee;
		}
		public double getRealLawFee() {
			return realLawFee;
		}
		public String getState(){
			return state;
		}
		
		public Date getMinRetDate() {
			return minRetDate;
		}
		public Date getMaxRetDate() {
			return maxRetDate;
		}
		public void setLoanId(String loanId) {
			this.loanId = loanId;
		}
		public void setContractId(String contractId) {
			this.contractId = contractId;
		}
		public void setRetDate(java.util.Date retDate) {
			this.retDate = retDate;
		}
		public void setRetDateStr(String retDateStr) {
			this.retDateStr = retDateStr;
		}
		public void setNum(int num) {
			this.num = num;
		}
		public void setType(String type) {
			this.type = type;
		}
		public void setPlanLawFee(double planLawFee) {
			this.planLawFee = planLawFee;
		}
		public void setRealLawFee(double realLawFee) {
			this.realLawFee = realLawFee;
		}
	    public void setState(String state){
	    	this.state=state;
	    }
		public void setMinRetDate(Date minRetDate) {
			this.minRetDate = minRetDate;
		}
		public void setMaxRetDate(Date maxRetDate) {
			this.maxRetDate = maxRetDate;
		}
	    
}

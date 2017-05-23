package com.tera.batch.model;

import com.tera.util.DateUtils;

/**
 * 
 * 分公司逾期统计表实体类
 * <b>功能：</b>ReportOverdueStatisticsDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-02-15 10:21:53<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public class ReportOverdueStatistics {

	//属性部分
	private int id; //
	private String orgId; //分公司
	private String type; //逾期类型（本金，利息）
	private int dealNum; //交易笔数
	private int projNum; //项目数
	private double overdueAmt; //逾期金额
	private double prinRate; //本金逾期率（逾期金额/当天存量）
           
	private double amt14; //0<X<=14天 金额
	private double rate14; //0<X<=14天比例 该区间金额/逾期金额
	private double amt30; //14<X<=30天 金额
	private double rate30; //14<X<=30天 比例(真实值*100)
	private double amt90; //30<X<=90天 金额
	private double rate90; //30<X<=90天 比例真实值*100
	private double amt180; //90<X<=180天 金额
	private double rate180; //90<X<=180天 比例真实值*100
	private double amtMore; //180<X天 金额
	private double rateMore; //180<X天 比例
	private double loanAmt; //当天存量
	private java.util.Date createDate; //创建日期
	private String createDateStr; //创建日期

	//getter部分
	public int getId () {
		return this.id;
	}
	public String getOrgId () {
		return this.orgId;
	}
	public String getType () {
		return this.type;
	}
	public int getDealNum () {
		return this.dealNum;
	}
	public int getProjNum () {
		return this.projNum;
	}
	public double getOverdueAmt () {
		return this.overdueAmt;
	}
	public double getPrinRate () {
		return this.prinRate;
	}
	public double getAmt14 () {
		return this.amt14;
	}
	public double getRate14 () {
		return this.rate14;
	}
	public double getAmt30 () {
		return this.amt30;
	}
	public double getRate30 () {
		return this.rate30;
	}
	public double getAmt90 () {
		return this.amt90;
	}
	public double getRate90 () {
		return this.rate90;
	}
	public double getAmt180 () {
		return this.amt180;
	}
	public double getRate180 () {
		return this.rate180;
	}
	public double getAmtMore () {
		return this.amtMore;
	}
	public double getRateMore () {
		return this.rateMore;
	}
	public double getLoanAmt () {
		return this.loanAmt;
	}
	public java.util.Date getCreateDate () {
		return this.createDate;
	}
	//getter部分,Date类型的修改获取String的方法
	public String getCreateDateStr () {
		return DateUtils.formatDate(this.createDate);
	}

	//setter部分
	public void setId (int id) {
		this.id=id;
	}
	public void setOrgId (String orgId) {
		this.orgId=orgId;
	}
	public void setType (String type) {
		this.type=type;
	}
	public void setDealNum (int dealNum) {
		this.dealNum=dealNum;
	}
	public void setProjNum (int projNum) {
		this.projNum=projNum;
	}
	public void setOverdueAmt (double overdueAmt) {
		this.overdueAmt=overdueAmt;
	}
	public void setPrinRate (double prinRate) {
		this.prinRate=prinRate;
	}
	public void setAmt14 (double amt14) {
		this.amt14=amt14;
	}
	public void setRate14 (double rate14) {
		this.rate14=rate14;
	}
	public void setAmt30 (double amt30) {
		this.amt30=amt30;
	}
	public void setRate30 (double rate30) {
		this.rate30=rate30;
	}
	public void setAmt90 (double amt90) {
		this.amt90=amt90;
	}
	public void setRate90 (double rate90) {
		this.rate90=rate90;
	}
	public void setAmt180 (double amt180) {
		this.amt180=amt180;
	}
	public void setRate180 (double rate180) {
		this.rate180=rate180;
	}
	public void setAmtMore (double amtMore) {
		this.amtMore=amtMore;
	}
	public void setRateMore (double rateMore) {
		this.rateMore=rateMore;
	}
	public void setLoanAmt (double loanAmt) {
		this.loanAmt=loanAmt;
	}
	public void setCreateDate (java.util.Date createDate) {
		this.createDate=createDate;
	}
	public void setCreateDateStr (String createDateStr) {
		this.createDateStr=createDateStr;
	}

}


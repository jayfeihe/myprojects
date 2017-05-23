package com.tera.collection.phone.model;

import com.tera.util.DateUtils;

/**
 * 
 * 逾期计算实体类
 * <b>功能：</b>CollectionBaseDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2015-06-08 14:36:46<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public class LateDaysCal {

	//属性部分
	private int id; //id
	private String contractNo; //合同编号
	private String isLate; //是否逾期
	private double amountAll; //应还总额
	private double reduce; //减免总额
	private int lateDays; //逾期天数
	private java.util.Date repaymentDate; //还款日
	private String repaymentDateStr; //还款日
	private String isFinish; //还款状态
	private double penalty; //当月应收罚息总额
	private double delay; //当月应收滞纳金总额
	private double sreviceFee; //当月应收服务费
	private double principal; //当月应收本金总额
	private double interest; //当月应收利息总额
	private double monthAmountAll; //月还总额为本金和利息总和，不包括已还的
	private double factpenalty; //当月实收罚息总额
	private double factdelay; //当月实收滞纳金总额
	private double factsreviceFee; //当月实收服务费
	private double factprincipal; //当月实收本金总额
	private double factinterest; //当月实收利息总额
	private double factmonthAmountAll; //月实还总额为本金和利息总和，不包括已还的
	//getter部分
	public int getId () {
		return this.id;
	}
	public String getContractNo () {
		return this.contractNo;
	}
	public int getLateDays () {
		return this.lateDays;
	}
	public java.util.Date getRepaymentDate () {
		return this.repaymentDate;
	}
	//getter部分,Date类型的修改获取String的方法
	public String getRepaymentDateStr () {
		return DateUtils.formatDate(this.repaymentDate);
	}
	public String getIsLate () {
		return this.isLate;
	}
	public String getIsFinish () {
		return this.isFinish;
	}
	public double getPenalty () {
		return this.penalty;
	}
	public double getDelay () {
		return this.delay;
	}
	public double getSreviceFee () {
		return this.sreviceFee;
	}
	public double getPrincipal () {
		return this.principal;
	}
	public double getInterest () {
		return this.interest;
	}
	public double getReduce () {
		return this.reduce;
	}
	public double getMonthAmountAll () {
		return this.monthAmountAll;
	}
	public double getAmountAll () {
		return this.amountAll;
	}

	public double getFactpenalty() {
		return factpenalty;
	}
	public double getFactdelay() {
		return factdelay;
	}
	public double getFactsreviceFee() {
		return factsreviceFee;
	}
	public double getFactprincipal() {
		return factprincipal;
	}
	public double getFactinterest() {
		return factinterest;
	}
	public double getFactmonthAmountAll() {
		return factmonthAmountAll;
	}
	//setter部分
	public void setId (int id) {
		this.id=id;
	}
	public void setContractNo (String contractNo) {
		this.contractNo=contractNo;
	}
	public void setLateDays (int lateDays) {
		this.lateDays=lateDays;
	}
	public void setRepaymentDate (java.util.Date repaymentDate) {
		this.repaymentDate=repaymentDate;
	}
	public void setRepaymentDateStr (String repaymentDateStr) {
		this.repaymentDateStr=repaymentDateStr;
	}
	public void setIsLate (String isLate) {
		this.isLate=isLate;
	}
	public void setIsFinish (String isFinish) {
		this.isFinish=isFinish;
	}
	public void setPenalty (double penalty) {
		this.penalty=penalty;
	}
	public void setDelay (double delay) {
		this.delay=delay;
	}
	public void setSreviceFee (double sreviceFee) {
		this.sreviceFee=sreviceFee;
	}
	public void setPrincipal (double principal) {
		this.principal=principal;
	}
	public void setInterest (double interest) {
		this.interest=interest;
	}
	public void setReduce (double reduce) {
		this.reduce=reduce;
	}
	public void setAmountAll (double amountAll) {
		this.amountAll=amountAll;
	}

	public void setMonthAmountAll (double monthAmountAll) {
		this.monthAmountAll=monthAmountAll;
	}
	public void setFactpenalty(double factpenalty) {
		this.factpenalty = factpenalty;
	}
	public void setFactdelay(double factdelay) {
		this.factdelay = factdelay;
	}
	public void setFactsreviceFee(double factsreviceFee) {
		this.factsreviceFee = factsreviceFee;
	}
	public void setFactprincipal(double factprincipal) {
		this.factprincipal = factprincipal;
	}
	public void setFactinterest(double factinterest) {
		this.factinterest = factinterest;
	}
	public void setFactmonthAmountAll(double factmonthAmountAll) {
		this.factmonthAmountAll = factmonthAmountAll;
	}
	
	
}


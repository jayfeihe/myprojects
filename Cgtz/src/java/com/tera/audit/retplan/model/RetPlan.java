package com.tera.audit.retplan.model;

import com.tera.util.DateUtils;

/**
 * 
 * 还款计划表实体类
 * <b>功能：</b>RetPlanDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-01-18 14:57:45<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public class RetPlan {

	//属性部分
	private int id; //ID
	private String loanId; //申请Id
	private String contractId; //合同id
	private java.util.Date retDate; //还款日
	private String retDateStr; //还款日
	private int num; //期数
	private String type; //类别(1利息2本金)
	private double planCapital; //当月应收本金
	private double planInterest; //当月应收利息
	private double planMemFee; //当月应收会员服务费
	private double planLawFee; //当月应收法律服务费
	private double planTranFee; //当月应收转贷费
	private double planMargin; //当月应收保证金
	private double planOtherFee; //当月应收其他费用
	private double realCapital; //当月实收本金
	private double realInterest; //当月实收利息
	private double realMemFee; //当月实收会员服务费
	private double realLawFee; //当月实收法律服务费
	private double realTranFee; //当月实收转贷费
	private double realMargin; //当月实收保证金
	private double realOtherFee; //当月实收其他费用
	private double defaultFee; //收罚息费用（只做记录）
	private double penaltyFee; //收滞纳金费用（只做记录）
	private double reduceFee; //减免费用
	private double remainAmt; //当期剩余合同金额
	private java.util.Date lastDate; //最后还款日期
	private String lastDateStr; //最后还款日期
	private String state; //状态（1未还2结清3提前还款）
	private java.sql.Timestamp createTime; //创建时间
	private String createTimeStr; //创建时间
	private java.sql.Timestamp updateTime; //修改时间
	private String updateTimeStr; //修改时间
	private double planAmt; //应收合计
	
	private double  planMgrFee;//应收管理服务费
	private double planCertFee;//应收他项权证费
	private double planEvalFee;//应收评估费
	private double planAgentFee;//应收中介费
	private double realMgrFee;//实收管理服务费
	private double realCertFee;//实收他项权证费
	private double realEvalFee;//实收评估费
	private double realAgentFee;//实收中介费
	private double storFee;//仓储费
	private double loanOtherFee;//贷后收取的其他费用
	
	

	//getter部分
	public int getId () {
		return this.id;
	}
	public String getLoanId () {
		return this.loanId;
	}
	public String getContractId () {
		return this.contractId;
	}
	public java.util.Date getRetDate () {
		return this.retDate;
	}
	//getter部分,Date类型的修改获取String的方法
	public String getRetDateStr () {
		return DateUtils.formatDate(this.retDate);
	}
	public int getNum () {
		return this.num;
	}
	public String getType () {
		return this.type;
	}
	public double getPlanCapital () {
		return this.planCapital;
	}
	public double getPlanInterest () {
		return this.planInterest;
	}
	public double getPlanMemFee () {
		return this.planMemFee;
	}
	public double getPlanLawFee () {
		return this.planLawFee;
	}
	public double getPlanTranFee () {
		return this.planTranFee;
	}
	public double getPlanMargin () {
		return this.planMargin;
	}
	public double getPlanOtherFee () {
		return this.planOtherFee;
	}
	public double getRealCapital () {
		return this.realCapital;
	}
	public double getRealInterest () {
		return this.realInterest;
	}
	public double getRealMemFee () {
		return this.realMemFee;
	}
	public double getRealLawFee () {
		return this.realLawFee;
	}
	public double getRealTranFee () {
		return this.realTranFee;
	}
	public double getRealMargin () {
		return this.realMargin;
	}
	public double getRealOtherFee () {
		return this.realOtherFee;
	}
	public double getDefaultFee () {
		return this.defaultFee;
	}
	public double getPenaltyFee () {
		return this.penaltyFee;
	}
	public double getReduceFee () {
		return this.reduceFee;
	}
	public double getRemainAmt () {
		return this.remainAmt;
	}
	public java.util.Date getLastDate () {
		return this.lastDate;
	}
	//getter部分,Date类型的修改获取String的方法
	public String getLastDateStr () {
		return DateUtils.formatDate(this.lastDate);
	}
	public String getState () {
		return this.state;
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
	
	
	public double getPlanAmt() {
		return planAmt;
	}
	public void setPlanAmt(double planAmt) {
		this.planAmt = planAmt;
	}
	//setter部分
	public void setId (int id) {
		this.id=id;
	}
	public void setLoanId (String loanId) {
		this.loanId=loanId;
	}
	public void setContractId (String contractId) {
		this.contractId=contractId;
	}
	public void setRetDate (java.util.Date retDate) {
		this.retDate=retDate;
	}
	public void setRetDateStr (String retDateStr) {
		this.retDateStr=retDateStr;
	}
	public void setNum (int num) {
		this.num=num;
	}
	public void setType (String type) {
		this.type=type;
	}
	public void setPlanCapital (double planCapital) {
		this.planCapital=planCapital;
	}
	public void setPlanInterest (double planInterest) {
		this.planInterest=planInterest;
	}
	public void setPlanMemFee (double planMemFee) {
		this.planMemFee=planMemFee;
	}
	public void setPlanLawFee (double planLawFee) {
		this.planLawFee=planLawFee;
	}
	public void setPlanTranFee (double planTranFee) {
		this.planTranFee=planTranFee;
	}
	public void setPlanMargin (double planMargin) {
		this.planMargin=planMargin;
	}
	public void setPlanOtherFee (double planOtherFee) {
		this.planOtherFee=planOtherFee;
	}
	public void setRealCapital (double realCapital) {
		this.realCapital=realCapital;
	}
	public void setRealInterest (double realInterest) {
		this.realInterest=realInterest;
	}
	public void setRealMemFee (double realMemFee) {
		this.realMemFee=realMemFee;
	}
	public void setRealLawFee (double realLawFee) {
		this.realLawFee=realLawFee;
	}
	public void setRealTranFee (double realTranFee) {
		this.realTranFee=realTranFee;
	}
	public void setRealMargin (double realMargin) {
		this.realMargin=realMargin;
	}
	public void setRealOtherFee (double realOtherFee) {
		this.realOtherFee=realOtherFee;
	}
	public void setDefaultFee (double defaultFee) {
		this.defaultFee=defaultFee;
	}
	public void setPenaltyFee (double penaltyFee) {
		this.penaltyFee=penaltyFee;
	}
	public void setReduceFee (double reduceFee) {
		this.reduceFee=reduceFee;
	}
	public void setRemainAmt (double remainAmt) {
		this.remainAmt=remainAmt;
	}
	public void setLastDate (java.util.Date lastDate) {
		this.lastDate=lastDate;
	}
	public void setLastDateStr (String lastDateStr) {
		this.lastDateStr=lastDateStr;
	}
	public void setState (String state) {
		this.state=state;
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
	public double getPlanMgrFee() {
		return planMgrFee;
	}
	public void setPlanMgrFee(double planMgrFee) {
		this.planMgrFee = planMgrFee;
	}
	public double getPlanCertFee() {
		return planCertFee;
	}
	public void setPlanCertFee(double planCertFee) {
		this.planCertFee = planCertFee;
	}
	public double getPlanEvalFee() {
		return planEvalFee;
	}
	public void setPlanEvalFee(double planEvalFee) {
		this.planEvalFee = planEvalFee;
	}
	public double getPlanAgentFee() {
		return planAgentFee;
	}
	public void setPlanAgentFee(double planAgentFee) {
		this.planAgentFee = planAgentFee;
	}
	public double getRealMgrFee() {
		return realMgrFee;
	}
	public void setRealMgrFee(double realMgrFee) {
		this.realMgrFee = realMgrFee;
	}
	public double getRealCertFee() {
		return realCertFee;
	}
	public void setRealCertFee(double realCertFee) {
		this.realCertFee = realCertFee;
	}
	public double getRealEvalFee() {
		return realEvalFee;
	}
	public void setRealEvalFee(double realEvalFee) {
		this.realEvalFee = realEvalFee;
	}
	public double getRealAgentFee() {
		return realAgentFee;
	}
	public void setRealAgentFee(double realAgentFee) {
		this.realAgentFee = realAgentFee;
	}
	public double getStorFee() {
		return storFee;
	}
	public void setStorFee(double storFee) {
		this.storFee = storFee;
	}
	public double getLoanOtherFee() {
		return loanOtherFee;
	}
	public void setLoanOtherFee(double loanOtherFee) {
		this.loanOtherFee = loanOtherFee;
	}
	
	

}


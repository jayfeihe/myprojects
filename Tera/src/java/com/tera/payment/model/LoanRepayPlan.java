package com.tera.payment.model;

import com.tera.util.DateUtils;

/**
 * 
 * 借款还款计划表实体类
 * <b>功能：</b>LoanRepayPlanDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-10-11 14:06:42<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public class LoanRepayPlan {

	//属性部分
	private int id; //ID
	private String contractNo; //合同号
	private String repayMethod; //还款方式
	private java.util.Date repaymentDate; //还款日
	private String repaymentDateStr; //还款日
	private java.util.Date startDate; //开始日期
	private String startDateStr; //开始日期
	private java.util.Date endDate; //结束日期
	private String endDateStr; //结束日期
	private int periodNum; //期数
	private double sreviceFeeReceivable; //当月应收服务费
	private double sreviceFeeReceived; //当月实收服务费
	
	private double interestReceivable; //当月应收利息
	private double interestReceived; //当月实收利息
	
	private double principalReceivable; //当月应收本金
	private double principalReceived; //当月实收本金
	
	private double penaltyReceivable; //当月应收罚息
	private double penaltyReceived; //当月实收罚息
	
	private double defaultReceivable; //当月应收违约金
	private double defaultReceived; //当月实收违约金
	
	private double delayReceivable; //应收滞纳金
	private double delayReceived; //实收滞纳金
	
	private double restPrincipal; //剩余本金
	private double penaltyReduce; //罚息减免
	private double delayReduce; //滞纳金减免
	private java.util.Date payDate; //实际还款日期
	private String payDateStr; //实际还款日期
	private String defaultFlag; //违约记账标志 0,未记账，1,记账
	private String state; //状态 1没还，2还完，3不足额还款
	private String operator; //操作员
	private String orgId; //所属机构
	private java.sql.Timestamp createTime; //创建日期
	private String createTimeStr; //创建日期
	private java.sql.Timestamp updateTime; //修改日期
	private String updateTimeStr; //修改日期

	
	
	private double sreviceCurrent; //本次所收服务费
	private double interestCurrent; //本次所收利息
	private double principalCurrent; //本次所收本金
	private double penaltyCurrent; //本次所收罚息
	private double defaultCurrent; //本次所收违约金
	private double delayCurrent; //本次所收滞纳金
	
	
	//以下三个字段 为  程序使用字段 没有入库
	private double monthAmount; //月还款额
	private double repaySum; //还款总额
	private double serviceSum; //趸交服务费总额
	
	//还款管理合计用到的字段
	private double dyyshj; //当月应收合计
	private double dyshhj; //当月实收合计
	private double bqyhkhj; //本期应还款合计
	private int yqts; //逾期天数
	private String hkzt; //还款状态
	
	private String fyLoanState; //富有划款状态

	public double getSreviceCurrent() {
		return sreviceCurrent;
	}
	public void setSreviceCurrent(double sreviceCurrent) {
		this.sreviceCurrent = sreviceCurrent;
	}
	public double getInterestCurrent() {
		return interestCurrent;
	}
	public void setInterestCurrent(double interestCurrent) {
		this.interestCurrent = interestCurrent;
	}
	public double getPrincipalCurrent() {
		return principalCurrent;
	}
	public void setPrincipalCurrent(double principalCurrent) {
		this.principalCurrent = principalCurrent;
	}
	public double getPenaltyCurrent() {
		return penaltyCurrent;
	}
	public void setPenaltyCurrent(double penaltyCurrent) {
		this.penaltyCurrent = penaltyCurrent;
	}
	public double getDefaultCurrent() {
		return defaultCurrent;
	}
	public void setDefaultCurrent(double defaultCurrent) {
		this.defaultCurrent = defaultCurrent;
	}
	public double getDelayCurrent() {
		return delayCurrent;
	}
	public void setDelayCurrent(double delayCurrent) {
		this.delayCurrent = delayCurrent;
	}
	public double getMonthAmount() {
		return monthAmount;
	}
	public void setMonthAmount(double monthAmount) {
		this.monthAmount = monthAmount;
	}
	public double getRepaySum() {
		return repaySum;
	}
	public void setRepaySum(double repaySum) {
		this.repaySum = repaySum;
	}
	public double getServiceSum() {
		return serviceSum;
	}
	public void setServiceSum(double serviceSum) {
		this.serviceSum = serviceSum;
	}
	//getter部分
	public int getId () {
		return this.id;
	}
	public String getContractNo () {
		return this.contractNo;
	}
	public String getRepayMethod () {
		return this.repayMethod;
	}
	public java.util.Date getRepaymentDate () {
		return this.repaymentDate;
	}
	//getter部分,Date类型的修改获取String的方法
	public String getRepaymentDateStr () {
		return DateUtils.formatDate(this.repaymentDate);
	}
	public java.util.Date getStartDate () {
		return this.startDate;
	}
	//getter部分,Date类型的修改获取String的方法
	public String getStartDateStr () {
		return DateUtils.formatDate(this.startDate);
	}
	public java.util.Date getEndDate () {
		return this.endDate;
	}
	//getter部分,Date类型的修改获取String的方法
	public String getEndDateStr () {
		return DateUtils.formatDate(this.endDate);
	}
	public int getPeriodNum () {
		return this.periodNum;
	}
	public double getSreviceFeeReceivable () {
		return this.sreviceFeeReceivable;
	}
	public double getSreviceFeeReceived () {
		return this.sreviceFeeReceived;
	}
	public double getInterestReceivable () {
		return this.interestReceivable;
	}
	public double getInterestReceived () {
		return this.interestReceived;
	}
	public double getPrincipalReceivable () {
		return this.principalReceivable;
	}
	public double getPrincipalReceived () {
		return this.principalReceived;
	}
	public double getPenaltyReceivable () {
		return this.penaltyReceivable;
	}
	public double getPenaltyReceived () {
		return this.penaltyReceived;
	}
	public double getDefaultReceivable () {
		return this.defaultReceivable;
	}
	public double getDefaultReceived () {
		return this.defaultReceived;
	}
	public double getDelayReceivable () {
		return this.delayReceivable;
	}
	public double getDelayReceived () {
		return this.delayReceived;
	}
	public double getRestPrincipal () {
		return this.restPrincipal;
	}
	public double getPenaltyReduce () {
		return this.penaltyReduce;
	}
	public double getDelayReduce () {
		return this.delayReduce;
	}
	public java.util.Date getPayDate () {
		return this.payDate;
	}
	//getter部分,Date类型的修改获取String的方法
	public String getPayDateStr () {
		return DateUtils.formatDate(this.payDate);
	}
	public String getDefaultFlag () {
		return this.defaultFlag;
	}
	public String getState () {
		return this.state;
	}
	public String getOperator () {
		return this.operator;
	}
	public String getOrgId () {
		return this.orgId;
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

	//setter部分
	public void setId (int id) {
		this.id=id;
	}
	public void setContractNo (String contractNo) {
		this.contractNo=contractNo;
	}
	public void setRepayMethod (String repayMethod) {
		this.repayMethod=repayMethod;
	}
	public void setRepaymentDate (java.util.Date repaymentDate) {
		this.repaymentDate=repaymentDate;
	}
	public void setRepaymentDateStr (String repaymentDateStr) {
		this.repaymentDateStr=repaymentDateStr;
	}
	public void setStartDate (java.util.Date startDate) {
		this.startDate=startDate;
	}
	public void setStartDateStr (String startDateStr) {
		this.startDateStr=startDateStr;
	}
	public void setEndDate (java.util.Date endDate) {
		this.endDate=endDate;
	}
	public void setEndDateStr (String endDateStr) {
		this.endDateStr=endDateStr;
	}
	public void setPeriodNum (int periodNum) {
		this.periodNum=periodNum;
	}
	public void setSreviceFeeReceivable (double sreviceFeeReceivable) {
		this.sreviceFeeReceivable=sreviceFeeReceivable;
	}
	public void setSreviceFeeReceived (double sreviceFeeReceived) {
		this.sreviceFeeReceived=sreviceFeeReceived;
	}
	public void setInterestReceivable (double interestReceivable) {
		this.interestReceivable=interestReceivable;
	}
	public void setInterestReceived (double interestReceived) {
		this.interestReceived=interestReceived;
	}
	public void setPrincipalReceivable (double principalReceivable) {
		this.principalReceivable=principalReceivable;
	}
	public void setPrincipalReceived (double principalReceived) {
		this.principalReceived=principalReceived;
	}
	public void setPenaltyReceivable (double penaltyReceivable) {
		this.penaltyReceivable=penaltyReceivable;
	}
	public void setPenaltyReceived (double penaltyReceived) {
		this.penaltyReceived=penaltyReceived;
	}
	public void setDefaultReceivable (double defaultReceivable) {
		this.defaultReceivable=defaultReceivable;
	}
	public void setDefaultReceived (double defaultReceived) {
		this.defaultReceived=defaultReceived;
	}
	public void setDelayReceivable (double delayReceivable) {
		this.delayReceivable=delayReceivable;
	}
	public void setDelayReceived (double delayReceived) {
		this.delayReceived=delayReceived;
	}
	public void setRestPrincipal (double restPrincipal) {
		this.restPrincipal=restPrincipal;
	}
	public void setPenaltyReduce (double penaltyReduce) {
		this.penaltyReduce=penaltyReduce;
	}
	public void setDelayReduce (double delayReduce) {
		this.delayReduce=delayReduce;
	}
	public void setPayDate (java.util.Date payDate) {
		this.payDate=payDate;
	}
	public void setPayDateStr (String payDateStr) {
		this.payDateStr=payDateStr;
	}
	public void setDefaultFlag (String defaultFlag) {
		this.defaultFlag=defaultFlag;
	}
	public void setState (String state) {
		this.state=state;
	}
	public void setOperator (String operator) {
		this.operator=operator;
	}
	public void setOrgId (String orgId) {
		this.orgId=orgId;
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
	public double getDyyshj() {
		return dyyshj;
	}
	public void setDyyshj(double dyyshj) {
		this.dyyshj = dyyshj;
	}
	public double getDyshhj() {
		return dyshhj;
	}
	public void setDyshhj(double dyshhj) {
		this.dyshhj = dyshhj;
	}
	public int getYqts() {
		return yqts;
	}
	public void setYqts(int yqts) {
		this.yqts = yqts;
	}
	public String getHkzt() {
		return hkzt;
	}
	public void setHkzt(String hkzt) {
		this.hkzt = hkzt;
	}
	public double getBqyhkhj() {
		return bqyhkhj;
	}
	public void setBqyhkhj(double bqyhkhj) {
		this.bqyhkhj = bqyhkhj;
	}
	public String getFyLoanState() {
		return fyLoanState;
	}
	public void setFyLoanState(String fyLoanState) {
		this.fyLoanState = fyLoanState;
	}

}


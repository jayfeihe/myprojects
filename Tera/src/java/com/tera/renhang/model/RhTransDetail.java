package com.tera.renhang.model;

import java.util.List;

import com.tera.util.DateUtils;

/**
 * 
 * 信用贷款人行报告交易明细实体类
 * <b>功能：</b>RhTransDetailDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2015-01-14 16:06:05<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public class RhTransDetail {

	//属性部分
	private int id; //ID
	private String appId; //申请ID
	private String type; //类型
	private java.util.Date startDate; //发放时间
	private String startDateStr; //发放时间
	private String company; //发放机构
	private double amount; //发放金额
	private String loanType; //发放类型
	private String bizNo; //业务号码
	private String bizType; //业务类型
	private int period; //期数
	private String payMethod; //还款方式
	private java.util.Date endDate; //到期日
	private String endDateStr; //到期日
	private java.util.Date toDate; //截至日期
	private String toDateStr; //截至日期
	private java.util.Date clearDate; //结清日期
	private String clearDateStr; //结清日期
	private String transState; //交易状态
	private int twoYearsOverdue; //近两年逾期次数
	private int twoYearsDegree; //近两年逾期最高程度
	private double twoYearsLimit; //近两年逾期最高金额
	private String loanClass; //贷款五级分类
	private double loanRestAmount; //贷款本金余额
	private int loanRestPeriod; //贷款剩余还款期数
	private double loanPayAmount; //贷款本月应还款
	private java.util.Date loanPayDate; //贷款应还款日期
	private String loanPayDateStr; //贷款应还款日期
	private double loanPayReceived; //贷款本月实还款
	private java.util.Date loanLastPayDate; //贷款最近一次还款日期
	private String loanLastPayDateStr; //贷款最近一次还款日期
	private int loanDefaultNum; //贷款当前逾期期数
	private double loanDefaultAount; //贷款当前逾期金额
	private double loanDefault12mAount; //贷款逾期31-60未还本金
	private double loanDefault23mAount; //贷款逾期61-90未还本金
	private double loanDefault36mAount; //贷款逾期91-180未还本金
	private double loanDefault6mAount; //贷款逾期181以上未还本金
	private double creditTotalAmount; //贷记卡共享额度
	private double creditUseAmount; //贷记卡已用额度
	private double creditAvg6mAmount; //贷记卡6月均用额度
	private double creditMaxAmount; //贷记卡最大使用额度
	private double creditPayAmount; //贷记卡本月还款
	private java.util.Date creditBillDate; //贷记卡账单日
	private String creditBillDateStr; //贷记卡账单日
	private double creditPayReceived; //贷记卡本月实还
	private java.util.Date creditLastPayDate; //贷记卡最近还款日
	private String creditLastPayDateStr; //贷记卡最近还款日
	private int creditDefaultNum; //贷记卡当前逾期期数
	private double creditDefaultAount; //贷记卡当前逾期金额
	private double semiCreditTotalAmount; //准贷记卡共享额度
	private double semiCreditUseAmount; //准贷记卡透支余额
	private double semiCreditAvg6mAmount; //准贷记卡6月均透支额度
	private double semiCreditMaxAmount; //准贷记卡最大透支额度
	private java.util.Date semiCreditBillDate; //准贷记卡账单日
	private String semiCreditBillDateStr; //准贷记卡账单日
	private double semiCreditPayReceived; //准贷记卡本月实还款额
	private java.util.Date semiCreditLastPayDate; //准贷记卡最近一次还款日
	private String semiCreditLastPayDateStr; //准贷记卡最近一次还款日
	private double semiCreditDefault6mAount; //准贷记卡透支180天未付余额
	private String secureCompany; //担保贷款发放机构
	private double secureContractAmount; //担保贷款合同金额
	private java.util.Date secureStartDate; //担保贷款发放日期
	private String secureStartDateStr; //担保贷款发放日期
	private java.util.Date secureEndDate; //担保贷款到期日期
	private String secureEndDateStr; //担保贷款到期日期
	private double secureAmount; //担保金额
	private double secureRestAmount; //担保贷款本金余额
	private String secureClass; //担保贷款五级分类
	private java.util.Date secureBalanceDate; //担保贷款结算日期
	private String secureBalanceDateStr; //担保贷款结算日期
	private java.util.Date payStartDate; //还款记录开始日期
	private String payStartDateStr; //还款记录开始日期
	private java.util.Date payEndDate; //还款记录结束日期
	private String payEndDateStr; //还款记录结束日期
	private String n1; //N1
	private String n2; //N2
	private String n3; //N3
	private String n4; //N4
	private String n5; //N5
	private String n6; //N6
	private String n7; //N7
	private String n8; //N8
	private String n9; //N9
	private String n10; //N10
	private String n11; //N11
	private String n12; //N12
	private String n13; //N13
	private String n14; //N14
	private String n15; //N15
	private String n16; //N16
	private String n17; //N17
	private String n18; //N18
	private String n19; //N19
	private String n20; //N20
	private String n21; //N21
	private String n22; //N22
	private String n23; //N23
	private String n24; //N24
	private String specialTransClass; //特殊交易类型
	private java.util.Date transDate; //交易发生日期
	private String transDateStr; //交易发生日期
	private int changeMonth; //变更月数
	private double transAmount; //发生金额
	private String transDetail; //明细记录
	private String remarks; //备注
	private String state; //状态
	private String operator; //操作员
	private String orgId; //所属机构
	private java.sql.Timestamp createTime; //创建日期
	private String createTimeStr; //创建日期
	private java.sql.Timestamp updateTime; //修改日期
	private String updateTimeStr; //修改日期
	
	private List<RhTransDefault> rhTransDefaultList;//逾期透支记录集合

	//getter部分
	public int getId () {
		return this.id;
	}
	public String getAppId () {
		return this.appId;
	}
	public String getType () {
		return this.type;
	}
	public java.util.Date getStartDate () {
		return this.startDate;
	}
	//getter部分,Date类型的修改获取String的方法
	public String getStartDateStr () {
		return DateUtils.formatDate(this.startDate);
	}
	public String getCompany () {
		return this.company;
	}
	public double getAmount () {
		return this.amount;
	}
	public String getLoanType () {
		return this.loanType;
	}
	public String getBizNo () {
		return this.bizNo;
	}
	public String getBizType () {
		return this.bizType;
	}
	public int getPeriod () {
		return this.period;
	}
	public String getPayMethod () {
		return this.payMethod;
	}
	public java.util.Date getEndDate () {
		return this.endDate;
	}
	//getter部分,Date类型的修改获取String的方法
	public String getEndDateStr () {
		return DateUtils.formatDate(this.endDate);
	}
	public java.util.Date getToDate () {
		return this.toDate;
	}
	//getter部分,Date类型的修改获取String的方法
	public String getToDateStr () {
		return DateUtils.formatDate(this.toDate);
	}
	public java.util.Date getClearDate () {
		return this.clearDate;
	}
	//getter部分,Date类型的修改获取String的方法
	public String getClearDateStr () {
		return DateUtils.formatDate(this.clearDate);
	}
	public int getTwoYearsOverdue () {
		return this.twoYearsOverdue;
	}
	public int getTwoYearsDegree () {
		return this.twoYearsDegree;
	}
	public double getTwoYearsLimit () {
		return this.twoYearsLimit;
	}
	public String getTransState () {
		return this.transState;
	}
	public String getLoanClass () {
		return this.loanClass;
	}
	public double getLoanRestAmount () {
		return this.loanRestAmount;
	}
	public int getLoanRestPeriod () {
		return this.loanRestPeriod;
	}
	public double getLoanPayAmount () {
		return this.loanPayAmount;
	}
	public java.util.Date getLoanPayDate () {
		return this.loanPayDate;
	}
	//getter部分,Date类型的修改获取String的方法
	public String getLoanPayDateStr () {
		return DateUtils.formatDate(this.loanPayDate);
	}
	public double getLoanPayReceived () {
		return this.loanPayReceived;
	}
	public java.util.Date getLoanLastPayDate () {
		return this.loanLastPayDate;
	}
	//getter部分,Date类型的修改获取String的方法
	public String getLoanLastPayDateStr () {
		return DateUtils.formatDate(this.loanLastPayDate);
	}
	public int getLoanDefaultNum () {
		return this.loanDefaultNum;
	}
	public double getLoanDefaultAount () {
		return this.loanDefaultAount;
	}
	public double getLoanDefault12mAount () {
		return this.loanDefault12mAount;
	}
	public double getLoanDefault23mAount () {
		return this.loanDefault23mAount;
	}
	public double getLoanDefault36mAount () {
		return this.loanDefault36mAount;
	}
	public double getLoanDefault6mAount () {
		return this.loanDefault6mAount;
	}
	public double getCreditTotalAmount () {
		return this.creditTotalAmount;
	}
	public double getCreditUseAmount () {
		return this.creditUseAmount;
	}
	public double getCreditAvg6mAmount () {
		return this.creditAvg6mAmount;
	}
	public double getCreditMaxAmount () {
		return this.creditMaxAmount;
	}
	public double getCreditPayAmount () {
		return this.creditPayAmount;
	}
	public java.util.Date getCreditBillDate () {
		return this.creditBillDate;
	}
	//getter部分,Date类型的修改获取String的方法
	public String getCreditBillDateStr () {
		return DateUtils.formatDate(this.creditBillDate);
	}
	public double getCreditPayReceived () {
		return this.creditPayReceived;
	}
	public java.util.Date getCreditLastPayDate () {
		return this.creditLastPayDate;
	}
	//getter部分,Date类型的修改获取String的方法
	public String getCreditLastPayDateStr () {
		return DateUtils.formatDate(this.creditLastPayDate);
	}
	public int getCreditDefaultNum () {
		return this.creditDefaultNum;
	}
	public double getCreditDefaultAount () {
		return this.creditDefaultAount;
	}
	public double getSemiCreditTotalAmount () {
		return this.semiCreditTotalAmount;
	}
	public double getSemiCreditUseAmount () {
		return this.semiCreditUseAmount;
	}
	public double getSemiCreditAvg6mAmount () {
		return this.semiCreditAvg6mAmount;
	}
	public double getSemiCreditMaxAmount () {
		return this.semiCreditMaxAmount;
	}
	public java.util.Date getSemiCreditBillDate () {
		return this.semiCreditBillDate;
	}
	//getter部分,Date类型的修改获取String的方法
	public String getSemiCreditBillDateStr () {
		return DateUtils.formatDate(this.semiCreditBillDate);
	}
	public double getSemiCreditPayReceived () {
		return this.semiCreditPayReceived;
	}
	public java.util.Date getSemiCreditLastPayDate () {
		return this.semiCreditLastPayDate;
	}
	//getter部分,Date类型的修改获取String的方法
	public String getSemiCreditLastPayDateStr () {
		return DateUtils.formatDate(this.semiCreditLastPayDate);
	}
	public double getSemiCreditDefault6mAount () {
		return this.semiCreditDefault6mAount;
	}
	public String getSecureCompany () {
		return this.secureCompany;
	}
	public double getSecureContractAmount () {
		return this.secureContractAmount;
	}
	public java.util.Date getSecureStartDate () {
		return this.secureStartDate;
	}
	//getter部分,Date类型的修改获取String的方法
	public String getSecureStartDateStr () {
		return DateUtils.formatDate(this.secureStartDate);
	}
	public java.util.Date getSecureEndDate () {
		return this.secureEndDate;
	}
	//getter部分,Date类型的修改获取String的方法
	public String getSecureEndDateStr () {
		return DateUtils.formatDate(this.secureEndDate);
	}
	public double getSecureAmount () {
		return this.secureAmount;
	}
	public double getSecureRestAmount () {
		return this.secureRestAmount;
	}
	public String getSecureClass () {
		return this.secureClass;
	}
	public java.util.Date getSecureBalanceDate () {
		return this.secureBalanceDate;
	}
	//getter部分,Date类型的修改获取String的方法
	public String getSecureBalanceDateStr () {
		return DateUtils.formatDate(this.secureBalanceDate);
	}
	public java.util.Date getPayStartDate () {
		return this.payStartDate;
	}
	//getter部分,Date类型的修改获取String的方法
	public String getPayStartDateStr () {
		return DateUtils.formatDate(this.payStartDate);
	}
	public java.util.Date getPayEndDate () {
		return this.payEndDate;
	}
	//getter部分,Date类型的修改获取String的方法
	public String getPayEndDateStr () {
		return DateUtils.formatDate(this.payEndDate);
	}
	public String getN1 () {
		return this.n1;
	}
	public String getN2 () {
		return this.n2;
	}
	public String getN3 () {
		return this.n3;
	}
	public String getN4 () {
		return this.n4;
	}
	public String getN5 () {
		return this.n5;
	}
	public String getN6 () {
		return this.n6;
	}
	public String getN7 () {
		return this.n7;
	}
	public String getN8 () {
		return this.n8;
	}
	public String getN9 () {
		return this.n9;
	}
	public String getN10 () {
		return this.n10;
	}
	public String getN11 () {
		return this.n11;
	}
	public String getN12 () {
		return this.n12;
	}
	public String getN13 () {
		return this.n13;
	}
	public String getN14 () {
		return this.n14;
	}
	public String getN15 () {
		return this.n15;
	}
	public String getN16 () {
		return this.n16;
	}
	public String getN17 () {
		return this.n17;
	}
	public String getN18 () {
		return this.n18;
	}
	public String getN19 () {
		return this.n19;
	}
	public String getN20 () {
		return this.n20;
	}
	public String getN21 () {
		return this.n21;
	}
	public String getN22 () {
		return this.n22;
	}
	public String getN23 () {
		return this.n23;
	}
	public String getN24 () {
		return this.n24;
	}
	public String getSpecialTransClass () {
		return this.specialTransClass;
	}
	public java.util.Date getTransDate () {
		return this.transDate;
	}
	//getter部分,Date类型的修改获取String的方法
	public String getTransDateStr () {
		return DateUtils.formatDate(this.transDate);
	}
	public int getChangeMonth () {
		return this.changeMonth;
	}
	public double getTransAmount () {
		return this.transAmount;
	}
	public String getTransDetail () {
		return this.transDetail;
	}
	public String getRemarks () {
		return this.remarks;
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
	public void setAppId (String appId) {
		this.appId=appId;
	}
	public void setType (String type) {
		this.type=type;
	}
	public void setStartDate (java.util.Date startDate) {
		this.startDate=startDate;
	}
	public void setStartDateStr (String startDateStr) {
		this.startDateStr=startDateStr;
	}
	public void setCompany (String company) {
		this.company=company;
	}
	public void setAmount (double amount) {
		this.amount=amount;
	}
	public void setLoanType (String loanType) {
		this.loanType=loanType;
	}
	public void setBizNo (String bizNo) {
		this.bizNo=bizNo;
	}
	public void setBizType (String bizType) {
		this.bizType=bizType;
	}
	public void setPeriod (int period) {
		this.period=period;
	}
	public void setPayMethod (String payMethod) {
		this.payMethod=payMethod;
	}
	public void setEndDate (java.util.Date endDate) {
		this.endDate=endDate;
	}
	public void setEndDateStr (String endDateStr) {
		this.endDateStr=endDateStr;
	}
	public void setToDate (java.util.Date toDate) {
		this.toDate=toDate;
	}
	public void setToDateStr (String toDateStr) {
		this.toDateStr=toDateStr;
	}
	public void setClearDate (java.util.Date clearDate) {
		this.clearDate=clearDate;
	}
	public void setClearDateStr (String clearDateStr) {
		this.clearDateStr=clearDateStr;
	}
	public void setTwoYearsOverdue (int twoYearsOverdue) {
		this.twoYearsOverdue=twoYearsOverdue;
	}
	public void setTwoYearsDegree (int twoYearsDegree) {
		this.twoYearsDegree=twoYearsDegree;
	}
	public void setTwoYearsLimit (double twoYearsLimit) {
		this.twoYearsLimit=twoYearsLimit;
	}
	public void setTransState (String transState) {
		this.transState=transState;
	}
	public void setLoanClass (String loanClass) {
		this.loanClass=loanClass;
	}
	public void setLoanRestAmount (double loanRestAmount) {
		this.loanRestAmount=loanRestAmount;
	}
	public void setLoanRestPeriod (int loanRestPeriod) {
		this.loanRestPeriod=loanRestPeriod;
	}
	public void setLoanPayAmount (double loanPayAmount) {
		this.loanPayAmount=loanPayAmount;
	}
	public void setLoanPayDate (java.util.Date loanPayDate) {
		this.loanPayDate=loanPayDate;
	}
	public void setLoanPayDateStr (String loanPayDateStr) {
		this.loanPayDateStr=loanPayDateStr;
	}
	public void setLoanPayReceived (double loanPayReceived) {
		this.loanPayReceived=loanPayReceived;
	}
	public void setLoanLastPayDate (java.util.Date loanLastPayDate) {
		this.loanLastPayDate=loanLastPayDate;
	}
	public void setLoanLastPayDateStr (String loanLastPayDateStr) {
		this.loanLastPayDateStr=loanLastPayDateStr;
	}
	public void setLoanDefaultNum (int loanDefaultNum) {
		this.loanDefaultNum=loanDefaultNum;
	}
	public void setLoanDefaultAount (double loanDefaultAount) {
		this.loanDefaultAount=loanDefaultAount;
	}
	public void setLoanDefault12mAount (double loanDefault12mAount) {
		this.loanDefault12mAount=loanDefault12mAount;
	}
	public void setLoanDefault23mAount (double loanDefault23mAount) {
		this.loanDefault23mAount=loanDefault23mAount;
	}
	public void setLoanDefault36mAount (double loanDefault36mAount) {
		this.loanDefault36mAount=loanDefault36mAount;
	}
	public void setLoanDefault6mAount (double loanDefault6mAount) {
		this.loanDefault6mAount=loanDefault6mAount;
	}
	public void setCreditTotalAmount (double creditTotalAmount) {
		this.creditTotalAmount=creditTotalAmount;
	}
	public void setCreditUseAmount (double creditUseAmount) {
		this.creditUseAmount=creditUseAmount;
	}
	public void setCreditAvg6mAmount (double creditAvg6mAmount) {
		this.creditAvg6mAmount=creditAvg6mAmount;
	}
	public void setCreditMaxAmount (double creditMaxAmount) {
		this.creditMaxAmount=creditMaxAmount;
	}
	public void setCreditPayAmount (double creditPayAmount) {
		this.creditPayAmount=creditPayAmount;
	}
	public void setCreditBillDate (java.util.Date creditBillDate) {
		this.creditBillDate=creditBillDate;
	}
	public void setCreditBillDateStr (String creditBillDateStr) {
		this.creditBillDateStr=creditBillDateStr;
	}
	public void setCreditPayReceived (double creditPayReceived) {
		this.creditPayReceived=creditPayReceived;
	}
	public void setCreditLastPayDate (java.util.Date creditLastPayDate) {
		this.creditLastPayDate=creditLastPayDate;
	}
	public void setCreditLastPayDateStr (String creditLastPayDateStr) {
		this.creditLastPayDateStr=creditLastPayDateStr;
	}
	public void setCreditDefaultNum (int creditDefaultNum) {
		this.creditDefaultNum=creditDefaultNum;
	}
	public void setCreditDefaultAount (double creditDefaultAount) {
		this.creditDefaultAount=creditDefaultAount;
	}
	public void setSemiCreditTotalAmount (double semiCreditTotalAmount) {
		this.semiCreditTotalAmount=semiCreditTotalAmount;
	}
	public void setSemiCreditUseAmount (double semiCreditUseAmount) {
		this.semiCreditUseAmount=semiCreditUseAmount;
	}
	public void setSemiCreditAvg6mAmount (double semiCreditAvg6mAmount) {
		this.semiCreditAvg6mAmount=semiCreditAvg6mAmount;
	}
	public void setSemiCreditMaxAmount (double semiCreditMaxAmount) {
		this.semiCreditMaxAmount=semiCreditMaxAmount;
	}
	public void setSemiCreditBillDate (java.util.Date semiCreditBillDate) {
		this.semiCreditBillDate=semiCreditBillDate;
	}
	public void setSemiCreditBillDateStr (String semiCreditBillDateStr) {
		this.semiCreditBillDateStr=semiCreditBillDateStr;
	}
	public void setSemiCreditPayReceived (double semiCreditPayReceived) {
		this.semiCreditPayReceived=semiCreditPayReceived;
	}
	public void setSemiCreditLastPayDate (java.util.Date semiCreditLastPayDate) {
		this.semiCreditLastPayDate=semiCreditLastPayDate;
	}
	public void setSemiCreditLastPayDateStr (String semiCreditLastPayDateStr) {
		this.semiCreditLastPayDateStr=semiCreditLastPayDateStr;
	}
	public void setSemiCreditDefault6mAount (double semiCreditDefault6mAount) {
		this.semiCreditDefault6mAount=semiCreditDefault6mAount;
	}
	public void setSecureCompany (String secureCompany) {
		this.secureCompany=secureCompany;
	}
	public void setSecureContractAmount (double secureContractAmount) {
		this.secureContractAmount=secureContractAmount;
	}
	public void setSecureStartDate (java.util.Date secureStartDate) {
		this.secureStartDate=secureStartDate;
	}
	public void setSecureStartDateStr (String secureStartDateStr) {
		this.secureStartDateStr=secureStartDateStr;
	}
	public void setSecureEndDate (java.util.Date secureEndDate) {
		this.secureEndDate=secureEndDate;
	}
	public void setSecureEndDateStr (String secureEndDateStr) {
		this.secureEndDateStr=secureEndDateStr;
	}
	public void setSecureAmount (double secureAmount) {
		this.secureAmount=secureAmount;
	}
	public void setSecureRestAmount (double secureRestAmount) {
		this.secureRestAmount=secureRestAmount;
	}
	public void setSecureClass (String secureClass) {
		this.secureClass=secureClass;
	}
	public void setSecureBalanceDate (java.util.Date secureBalanceDate) {
		this.secureBalanceDate=secureBalanceDate;
	}
	public void setSecureBalanceDateStr (String secureBalanceDateStr) {
		this.secureBalanceDateStr=secureBalanceDateStr;
	}
	public void setPayStartDate (java.util.Date payStartDate) {
		this.payStartDate=payStartDate;
	}
	public void setPayStartDateStr (String payStartDateStr) {
		this.payStartDateStr=payStartDateStr;
	}
	public void setPayEndDate (java.util.Date payEndDate) {
		this.payEndDate=payEndDate;
	}
	public void setPayEndDateStr (String payEndDateStr) {
		this.payEndDateStr=payEndDateStr;
	}
	public void setN1 (String n1) {
		this.n1=n1;
	}
	public void setN2 (String n2) {
		this.n2=n2;
	}
	public void setN3 (String n3) {
		this.n3=n3;
	}
	public void setN4 (String n4) {
		this.n4=n4;
	}
	public void setN5 (String n5) {
		this.n5=n5;
	}
	public void setN6 (String n6) {
		this.n6=n6;
	}
	public void setN7 (String n7) {
		this.n7=n7;
	}
	public void setN8 (String n8) {
		this.n8=n8;
	}
	public void setN9 (String n9) {
		this.n9=n9;
	}
	public void setN10 (String n10) {
		this.n10=n10;
	}
	public void setN11 (String n11) {
		this.n11=n11;
	}
	public void setN12 (String n12) {
		this.n12=n12;
	}
	public void setN13 (String n13) {
		this.n13=n13;
	}
	public void setN14 (String n14) {
		this.n14=n14;
	}
	public void setN15 (String n15) {
		this.n15=n15;
	}
	public void setN16 (String n16) {
		this.n16=n16;
	}
	public void setN17 (String n17) {
		this.n17=n17;
	}
	public void setN18 (String n18) {
		this.n18=n18;
	}
	public void setN19 (String n19) {
		this.n19=n19;
	}
	public void setN20 (String n20) {
		this.n20=n20;
	}
	public void setN21 (String n21) {
		this.n21=n21;
	}
	public void setN22 (String n22) {
		this.n22=n22;
	}
	public void setN23 (String n23) {
		this.n23=n23;
	}
	public void setN24 (String n24) {
		this.n24=n24;
	}
	public void setSpecialTransClass (String specialTransClass) {
		this.specialTransClass=specialTransClass;
	}
	public void setTransDate (java.util.Date transDate) {
		this.transDate=transDate;
	}
	public void setTransDateStr (String transDateStr) {
		this.transDateStr=transDateStr;
	}
	public void setChangeMonth (int changeMonth) {
		this.changeMonth=changeMonth;
	}
	public void setTransAmount (double transAmount) {
		this.transAmount=transAmount;
	}
	public void setTransDetail (String transDetail) {
		this.transDetail=transDetail;
	}
	public void setRemarks (String remarks) {
		this.remarks=remarks;
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
	public List<RhTransDefault> getRhTransDefaultList() {
		return rhTransDefaultList;
	}
	public void setRhTransDefaultList(List<RhTransDefault> rhTransDefaultList) {
		this.rhTransDefaultList = rhTransDefaultList;
	}

}


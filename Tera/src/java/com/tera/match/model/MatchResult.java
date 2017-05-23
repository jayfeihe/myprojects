package com.tera.match.model;

import com.tera.util.DateUtils;

/**
 * 
 * <br>
 * <b>功能：</b>MatchResultDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-06-18 15:22:35<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public class MatchResult {

	//属性部分
	private int id; //ID
	private String matchType; //撮合类型
	private java.sql.Timestamp matchTime; //撮合时间
	private String matchTimeStr; //撮合时间
	private String lendType; //出借类型
	private String lendAppId; //出借申请号
	private double lendAmount; //出借金额
	private String lendProduct; //出借产品
	private double lendInterestRate; //出借利率
	private int lendPeriod; //出借期限
	private java.util.Date startDate; //开始日期
	private String startDateStr; //开始日期
	private java.util.Date endDate; //结束日期
	private String endDateStr; //结束日期
	private String investType; //投资类型-借款人/信托
	private String loanAppId; //贷款申请号
	private String loanType; //借款类型
	private double loanAmount; //借款金额
	private String loanProduct; //借款产品
	private double loanInterestRate; //借款利率
	private int loanPeriod; //借款期限
	private double startAmount; //起点金额
	private String companyName; //机构名称
	private String useage; //借款用途
	private String contractNo; //合同号
	private java.util.Date actualStartDate; //实际开始日期
	private String actualStartDateStr; //实际开始日期
	private java.util.Date actualEndDate; //实际结束日期
	private String actualEndDateStr; //实际结束日期
	private String state; //状态
	private String flag; //清除标记  默认1，可以清理 ；0 不可清理
	private String operator; //操作员
	private String orgId; //所属机构
	private java.sql.Timestamp createTime; //创建日期
	private String createTimeStr; //创建日期
	private java.sql.Timestamp updateTime; //修改日期
	private String updateTimeStr; //修改日期

	private String idNo; //出借人 身份证号
	private String name; //出借人 姓名
	
	/**
	 * 下载债权报告用到
	 */
	private String comDuty;//借款人职业情况
	private String loanStartDate;//还款起始日期
	private int surplusLoanPeriod;//剩余还款时间（月）


	//getter部分
	public int getId () {
		return this.id;
	}
	public String getMatchType () {
		return this.matchType;
	}
	public java.sql.Timestamp getMatchTime () {
		return this.matchTime;
	}
	//getter部分,Timestamp类型的修改获取String的方法
	public String getMatchTimeStr () {
		return DateUtils.formatTime(this.matchTime);
	}
	public String getLendType () {
		return this.lendType;
	}
	public String getLendAppId () {
		return this.lendAppId;
	}
	public double getLendAmount () {
		return this.lendAmount;
	}
	public String getLendProduct () {
		return this.lendProduct;
	}
	public double getLendInterestRate () {
		return this.lendInterestRate;
	}
	public int getLendPeriod () {
		return this.lendPeriod;
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
	public String getInvestType () {
		return this.investType;
	}
	public String getLoanAppId () {
		return this.loanAppId;
	}
	public String getLoanType () {
		return this.loanType;
	}
	public double getLoanAmount () {
		return this.loanAmount;
	}
	public String getLoanProduct () {
		return this.loanProduct;
	}
	public double getLoanInterestRate () {
		return this.loanInterestRate;
	}
	public int getLoanPeriod () {
		return this.loanPeriod;
	}
	public double getStartAmount () {
		return this.startAmount;
	}
	public String getCompanyName () {
		return this.companyName;
	}
	public String getUseage () {
		return this.useage;
	}
	public String getContractNo () {
		return this.contractNo;
	}
	public java.util.Date getActualStartDate () {
		return this.actualStartDate;
	}
	//getter部分,Date类型的修改获取String的方法
	public String getActualStartDateStr () {
		return DateUtils.formatDate(this.actualStartDate);
	}
	public java.util.Date getActualEndDate () {
		return this.actualEndDate;
	}
	//getter部分,Date类型的修改获取String的方法
	public String getActualEndDateStr () {
		return DateUtils.formatDate(this.actualEndDate);
	}
	public String getState () {
		return this.state;
	}
	public String getFlag () {
		return this.flag;
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
	public void setMatchType (String matchType) {
		this.matchType=matchType;
	}
	public void setMatchTime (java.sql.Timestamp matchTime) {
		this.matchTime=matchTime;
	}
	public void setMatchTimeStr (String matchTimeStr) {
		this.matchTimeStr=matchTimeStr;
	}
	public void setLendType (String lendType) {
		this.lendType=lendType;
	}
	public void setLendAppId (String lendAppId) {
		this.lendAppId=lendAppId;
	}
	public void setLendAmount (double lendAmount) {
		this.lendAmount=lendAmount;
	}
	public void setLendProduct (String lendProduct) {
		this.lendProduct=lendProduct;
	}
	public void setLendInterestRate (double lendInterestRate) {
		this.lendInterestRate=lendInterestRate;
	}
	public void setLendPeriod (int lendPeriod) {
		this.lendPeriod=lendPeriod;
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
	public void setInvestType (String investType) {
		this.investType=investType;
	}
	public void setLoanAppId (String loanAppId) {
		this.loanAppId=loanAppId;
	}
	public void setLoanType (String loanType) {
		this.loanType=loanType;
	}
	public void setLoanAmount (double loanAmount) {
		this.loanAmount=loanAmount;
	}
	public void setLoanProduct (String loanProduct) {
		this.loanProduct=loanProduct;
	}
	public void setLoanInterestRate (double loanInterestRate) {
		this.loanInterestRate=loanInterestRate;
	}
	public void setLoanPeriod (int loanPeriod) {
		this.loanPeriod=loanPeriod;
	}
	public void setStartAmount (double startAmount) {
		this.startAmount=startAmount;
	}
	public void setCompanyName (String companyName) {
		this.companyName=companyName;
	}
	public void setUseage (String useage) {
		this.useage=useage;
	}
	public void setContractNo (String contractNo) {
		this.contractNo=contractNo;
	}
	public void setActualStartDate (java.util.Date actualStartDate) {
		this.actualStartDate=actualStartDate;
	}
	public void setActualStartDateStr (String actualStartDateStr) {
		this.actualStartDateStr=actualStartDateStr;
	}
	public void setActualEndDate (java.util.Date actualEndDate) {
		this.actualEndDate=actualEndDate;
	}
	public void setActualEndDateStr (String actualEndDateStr) {
		this.actualEndDateStr=actualEndDateStr;
	}
	public void setState (String state) {
		this.state=state;
	}
	public void setFlag (String flag) {
		this.flag=flag;
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
	public String getIdNo() {
		return idNo;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getComDuty() {
		return comDuty;
	}
	public void setComDuty(String comDuty) {
		this.comDuty = comDuty;
	}
	public String getLoanStartDate() {
		return loanStartDate;
	}
	public void setLoanStartDate(String loanStartDate) {
		this.loanStartDate = loanStartDate;
	}
	public int getSurplusLoanPeriod() {
		return surplusLoanPeriod;
	}
	public void setSurplusLoanPeriod(int surplusLoanPeriod) {
		this.surplusLoanPeriod = surplusLoanPeriod;
	}

}


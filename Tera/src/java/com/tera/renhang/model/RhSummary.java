package com.tera.renhang.model;

import com.tera.util.DateUtils;

/**
 * 
 * 信用贷款人行报告信息概要实体类
 * <b>功能：</b>RhSummaryDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2015-01-14 16:05:39<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public class RhSummary {

	//属性部分
	private int id; //ID
	private String appId; //申请ID
	private int personHouseLoanNum; //个人住房贷款笔数
	private int personBizHouseLoanNum; //个人商用房(包括商住两用)贷款笔数
	private int otherLoanNum; //其他贷款笔数
	private java.util.Date firstLoanDate; //首笔贷款发放月份
	private String firstLoanDateStr; //首笔贷款发放月份
	private int creditNum; //贷记卡账户数
	private java.util.Date firstCreditDate; //首张贷记卡发卡月份
	private String firstCreditDateStr; //首张贷记卡发卡月份
	private int semiCreditNum; //准贷记卡账户数
	private java.util.Date semiCreditDate; //首张准贷记卡发卡月份
	private String semiCreditDateStr; //首张准贷记卡发卡月份
	private int declareNum; //本人声明数目
	private int objectionNum; //异议标注数目
	private int loanDefaultNum; //贷款逾期笔数
	private int loanDefaultMonthNum; //贷款逾期月份数
	private double loanMaxDefaultAmount; //贷款单月最高逾期总额
	private int loanMaxDefaultMonth; //贷款最长逾期月数
	private int creditDefaultNum; //贷记卡逾期账户数
	private int creditDefaultMonthNum; //贷记卡逾期月份数
	private double creditMaxDefaultAmount; //贷记卡单月最高逾期总额
	private int creditMaxDefaultMonth; //贷记卡最长逾期月数
	private int semiCreditDefaultNum; //准贷记卡逾期账户数
	private int semiCreditDefaultMonthNum; //准贷记卡逾期月份数
	private double semiCreditMaxDefaultAmount; //准贷记卡单月最高透支总额
	private int semiCreditMaxDefaultMonth; //准贷记卡最长透支月数
	private int loanLegalNum; //未结清贷款法人机构数
	private int loanComNum; //未结清贷款机构数
	private int loanNum; //未结清贷款笔数
	private double loanAmount; //未结清贷款合同总额
	private double loanRestAmount; //未结清贷款余额
	private double loanAvg6mAmount; //未结清贷款6月均还款
	private int creditLegalNum; //未销户贷记卡法人机构数
	private int creditComNum; //未销户贷记卡机构数
	private int creditAccountNum; //未销户贷记卡账户数
	private double creditTotalAmount; //未销户贷记卡授信总额
	private double creditMaxAmount; //未销户贷记卡单家最高额度
	private double creditMinAmount; //未销户贷记卡单家最低额度
	private double creditUseAmount; //未销户贷记卡已用额度
	private double creditAvg6mAmount; //未销户贷记卡6月均用额度
	private int semiCreditLegalNum; //未销户准贷记卡法人机构数
	private int semiCreditComNum; //未销户准贷记卡机构数
	private int semiCreditAccountNum; //未销户准贷记卡账户数
	private double semiCreditTotalAmount; //未销户准贷记卡授信总额
	private double semiCreditMaxAmount; //未销户准贷记卡单家最高额度
	private double semiCreditMinAmount; //未销户准贷记卡单家最低额度
	private double semiCreditUseAmount; //未销户准贷记卡透支额度
	private double semiCreditAvg6mAmount; //未销户准贷记卡6月均透支额度
	private int secureNum; //担保笔数
	private double secureAmount; //担保金额
	private double secureRestAmount; //担保本金余额
	private String remarks; //备注
	private String state; //状态
	private String operator; //操作员
	private String orgId; //所属机构
	private java.sql.Timestamp createTime; //创建日期
	private String createTimeStr; //创建日期
	private java.sql.Timestamp updateTime; //修改日期
	private String updateTimeStr; //修改日期

	//getter部分
	public int getId () {
		return this.id;
	}
	public String getAppId () {
		return this.appId;
	}
	public int getPersonHouseLoanNum() {
		return personHouseLoanNum;
	}
	public int getPersonBizHouseLoanNum() {
		return personBizHouseLoanNum;
	}
	public int getOtherLoanNum () {
		return this.otherLoanNum;
	}
	public java.util.Date getFirstLoanDate () {
		return this.firstLoanDate;
	}
	//getter部分,Date类型的修改获取String的方法
	public String getFirstLoanDateStr () {
		return DateUtils.formatDate(this.firstLoanDate);
	}
	public int getCreditNum () {
		return this.creditNum;
	}
	public java.util.Date getFirstCreditDate () {
		return this.firstCreditDate;
	}
	//getter部分,Date类型的修改获取String的方法
	public String getFirstCreditDateStr () {
		return DateUtils.formatDate(this.firstCreditDate);
	}
	public int getSemiCreditNum () {
		return this.semiCreditNum;
	}
	public java.util.Date getSemiCreditDate () {
		return this.semiCreditDate;
	}
	//getter部分,Date类型的修改获取String的方法
	public String getSemiCreditDateStr () {
		return DateUtils.formatDate(this.semiCreditDate);
	}
	public int getDeclareNum () {
		return this.declareNum;
	}
	public int getObjectionNum () {
		return this.objectionNum;
	}
	public int getLoanDefaultNum () {
		return this.loanDefaultNum;
	}
	public int getLoanDefaultMonthNum () {
		return this.loanDefaultMonthNum;
	}
	public double getLoanMaxDefaultAmount () {
		return this.loanMaxDefaultAmount;
	}
	public int getLoanMaxDefaultMonth () {
		return this.loanMaxDefaultMonth;
	}
	public int getCreditDefaultNum () {
		return this.creditDefaultNum;
	}
	public int getCreditDefaultMonthNum () {
		return this.creditDefaultMonthNum;
	}
	public double getCreditMaxDefaultAmount () {
		return this.creditMaxDefaultAmount;
	}
	public int getCreditMaxDefaultMonth () {
		return this.creditMaxDefaultMonth;
	}
	public int getSemiCreditDefaultNum () {
		return this.semiCreditDefaultNum;
	}
	public int getSemiCreditDefaultMonthNum () {
		return this.semiCreditDefaultMonthNum;
	}
	public double getSemiCreditMaxDefaultAmount () {
		return this.semiCreditMaxDefaultAmount;
	}
	public int getSemiCreditMaxDefaultMonth () {
		return this.semiCreditMaxDefaultMonth;
	}
	public int getLoanLegalNum () {
		return this.loanLegalNum;
	}
	public int getLoanComNum () {
		return this.loanComNum;
	}
	public int getLoanNum () {
		return this.loanNum;
	}
	public double getLoanAmount () {
		return this.loanAmount;
	}
	public double getLoanRestAmount () {
		return this.loanRestAmount;
	}
	public double getLoanAvg6mAmount () {
		return this.loanAvg6mAmount;
	}
	public int getCreditLegalNum () {
		return this.creditLegalNum;
	}
	public int getCreditComNum () {
		return this.creditComNum;
	}
	public int getCreditAccountNum () {
		return this.creditAccountNum;
	}
	public double getCreditTotalAmount () {
		return this.creditTotalAmount;
	}
	public double getCreditMaxAmount () {
		return this.creditMaxAmount;
	}
	public double getCreditMinAmount () {
		return this.creditMinAmount;
	}
	public double getCreditUseAmount () {
		return this.creditUseAmount;
	}
	public double getCreditAvg6mAmount () {
		return this.creditAvg6mAmount;
	}
	public int getSemiCreditLegalNum () {
		return this.semiCreditLegalNum;
	}
	public int getSemiCreditComNum () {
		return this.semiCreditComNum;
	}
	public int getSemiCreditAccountNum () {
		return this.semiCreditAccountNum;
	}
	public double getSemiCreditTotalAmount () {
		return this.semiCreditTotalAmount;
	}
	public double getSemiCreditMaxAmount () {
		return this.semiCreditMaxAmount;
	}
	public double getSemiCreditMinAmount () {
		return this.semiCreditMinAmount;
	}
	public double getSemiCreditUseAmount () {
		return this.semiCreditUseAmount;
	}
	public double getSemiCreditAvg6mAmount () {
		return this.semiCreditAvg6mAmount;
	}
	public int getSecureNum () {
		return this.secureNum;
	}
	public double getSecureAmount () {
		return this.secureAmount;
	}
	public double getSecureRestAmount () {
		return this.secureRestAmount;
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
	public void setPersonHouseLoanNum(int personHouseLoanNum) {
		this.personHouseLoanNum = personHouseLoanNum;
	}
	public void setPersonBizHouseLoanNum(int personBizHouseLoanNum) {
		this.personBizHouseLoanNum = personBizHouseLoanNum;
	}
	public void setOtherLoanNum (int otherLoanNum) {
		this.otherLoanNum=otherLoanNum;
	}
	public void setFirstLoanDate (java.util.Date firstLoanDate) {
		this.firstLoanDate=firstLoanDate;
	}
	public void setFirstLoanDateStr (String firstLoanDateStr) {
		this.firstLoanDateStr=firstLoanDateStr;
	}
	public void setCreditNum (int creditNum) {
		this.creditNum=creditNum;
	}
	public void setFirstCreditDate (java.util.Date firstCreditDate) {
		this.firstCreditDate=firstCreditDate;
	}
	public void setFirstCreditDateStr (String firstCreditDateStr) {
		this.firstCreditDateStr=firstCreditDateStr;
	}
	public void setSemiCreditNum (int semiCreditNum) {
		this.semiCreditNum=semiCreditNum;
	}
	public void setSemiCreditDate (java.util.Date semiCreditDate) {
		this.semiCreditDate=semiCreditDate;
	}
	public void setSemiCreditDateStr (String semiCreditDateStr) {
		this.semiCreditDateStr=semiCreditDateStr;
	}
	public void setDeclareNum (int declareNum) {
		this.declareNum=declareNum;
	}
	public void setObjectionNum (int objectionNum) {
		this.objectionNum=objectionNum;
	}
	public void setLoanDefaultNum (int loanDefaultNum) {
		this.loanDefaultNum=loanDefaultNum;
	}
	public void setLoanDefaultMonthNum (int loanDefaultMonthNum) {
		this.loanDefaultMonthNum=loanDefaultMonthNum;
	}
	public void setLoanMaxDefaultAmount (double loanMaxDefaultAmount) {
		this.loanMaxDefaultAmount=loanMaxDefaultAmount;
	}
	public void setLoanMaxDefaultMonth (int loanMaxDefaultMonth) {
		this.loanMaxDefaultMonth=loanMaxDefaultMonth;
	}
	public void setCreditDefaultNum (int creditDefaultNum) {
		this.creditDefaultNum=creditDefaultNum;
	}
	public void setCreditDefaultMonthNum (int creditDefaultMonthNum) {
		this.creditDefaultMonthNum=creditDefaultMonthNum;
	}
	public void setCreditMaxDefaultAmount (double creditMaxDefaultAmount) {
		this.creditMaxDefaultAmount=creditMaxDefaultAmount;
	}
	public void setCreditMaxDefaultMonth (int creditMaxDefaultMonth) {
		this.creditMaxDefaultMonth=creditMaxDefaultMonth;
	}
	public void setSemiCreditDefaultNum (int semiCreditDefaultNum) {
		this.semiCreditDefaultNum=semiCreditDefaultNum;
	}
	public void setSemiCreditDefaultMonthNum (int semiCreditDefaultMonthNum) {
		this.semiCreditDefaultMonthNum=semiCreditDefaultMonthNum;
	}
	public void setSemiCreditMaxDefaultAmount (double semiCreditMaxDefaultAmount) {
		this.semiCreditMaxDefaultAmount=semiCreditMaxDefaultAmount;
	}
	public void setSemiCreditMaxDefaultMonth (int semiCreditMaxDefaultMonth) {
		this.semiCreditMaxDefaultMonth=semiCreditMaxDefaultMonth;
	}
	public void setLoanLegalNum (int loanLegalNum) {
		this.loanLegalNum=loanLegalNum;
	}
	public void setLoanComNum (int loanComNum) {
		this.loanComNum=loanComNum;
	}
	public void setLoanNum (int loanNum) {
		this.loanNum=loanNum;
	}
	public void setLoanAmount (double loanAmount) {
		this.loanAmount=loanAmount;
	}
	public void setLoanRestAmount (double loanRestAmount) {
		this.loanRestAmount=loanRestAmount;
	}
	public void setLoanAvg6mAmount (double loanAvg6mAmount) {
		this.loanAvg6mAmount=loanAvg6mAmount;
	}
	public void setCreditLegalNum (int creditLegalNum) {
		this.creditLegalNum=creditLegalNum;
	}
	public void setCreditComNum (int creditComNum) {
		this.creditComNum=creditComNum;
	}
	public void setCreditAccountNum (int creditAccountNum) {
		this.creditAccountNum=creditAccountNum;
	}
	public void setCreditTotalAmount (double creditTotalAmount) {
		this.creditTotalAmount=creditTotalAmount;
	}
	public void setCreditMaxAmount (double creditMaxAmount) {
		this.creditMaxAmount=creditMaxAmount;
	}
	public void setCreditMinAmount (double creditMinAmount) {
		this.creditMinAmount=creditMinAmount;
	}
	public void setCreditUseAmount (double creditUseAmount) {
		this.creditUseAmount=creditUseAmount;
	}
	public void setCreditAvg6mAmount (double creditAvg6mAmount) {
		this.creditAvg6mAmount=creditAvg6mAmount;
	}
	public void setSemiCreditLegalNum (int semiCreditLegalNum) {
		this.semiCreditLegalNum=semiCreditLegalNum;
	}
	public void setSemiCreditComNum (int semiCreditComNum) {
		this.semiCreditComNum=semiCreditComNum;
	}
	public void setSemiCreditAccountNum (int semiCreditAccountNum) {
		this.semiCreditAccountNum=semiCreditAccountNum;
	}
	public void setSemiCreditTotalAmount (double semiCreditTotalAmount) {
		this.semiCreditTotalAmount=semiCreditTotalAmount;
	}
	public void setSemiCreditMaxAmount (double semiCreditMaxAmount) {
		this.semiCreditMaxAmount=semiCreditMaxAmount;
	}
	public void setSemiCreditMinAmount (double semiCreditMinAmount) {
		this.semiCreditMinAmount=semiCreditMinAmount;
	}
	public void setSemiCreditUseAmount (double semiCreditUseAmount) {
		this.semiCreditUseAmount=semiCreditUseAmount;
	}
	public void setSemiCreditAvg6mAmount (double semiCreditAvg6mAmount) {
		this.semiCreditAvg6mAmount=semiCreditAvg6mAmount;
	}
	public void setSecureNum (int secureNum) {
		this.secureNum=secureNum;
	}
	public void setSecureAmount (double secureAmount) {
		this.secureAmount=secureAmount;
	}
	public void setSecureRestAmount (double secureRestAmount) {
		this.secureRestAmount=secureRestAmount;
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

}


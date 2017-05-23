package com.tera.report.model;

import java.sql.Timestamp;
import java.util.Date;

import com.tera.util.DateUtils;

public class NetFunds {
    /*con.GET_LOAN_WAY,-- 融资类型   
conline.CONTRACT_ID ,-- 线下合同编号
conline.ONLINE_CON_ID ,-- 线上编号
conline.PROJECT_NAME ,-- 线上项目名称
base.NAME , -- 借款人名称
lend.NAME, -- 债权人
bill.CREATE_TIME ,-- 时间
bill.AMT, -- 放款金额
lend.ACCT_CODE ,-- 债权人卡号
lend.ACCT_BANK,-- 开户行
base.ACCT_CODE, -- 借款人卡号
base.ACCT_BRANCH,-- 借款人分行信息
bill.REMARK -- 说明
     * 
     * */
	private String getLoanWay;
	private String loanType;
	private String contractId;
	private String onlineConId;
	private String projectName;
	private String loanName;
	private String lendName;
	private Timestamp createTime;
	private String createTimeStr;
	private Date minCreateTime;
	private String minCreateTimeStr;
	private Date maxCreateTime;
	private String maxCreateTimeStr;
	private double amt;
	private String acctCode;
	private String acctBank;
	private String loanAcctCode;
	private String acctBranch;
	private String remark;
	//getter
	
	public String getGetLoanWay() {
		return getLoanWay;
	}
	public String getLoanType() {
		return loanType;
	}
	public String getContractId() {
		return contractId;
	}
	public String getOnlineConId() {
		return onlineConId;
	}
	public String getProjectName() {
		return projectName;
	}
	public String getLoanName() {
		return loanName;
	}
	public String getLendName() {
		return lendName;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public double getAmt() {
		return amt;
	}
	public String getAcctCode() {
		return acctCode;
	}
	public String getAcctBank() {
		return acctBank;
	}
	public String getLoanAcctCode() {
		return loanAcctCode;
	}
	public String getAcctBranch() {
		return acctBranch;
	}
	public String getRemark() {
		return remark;
	}
	
	
	public String getMinCreateTimeStr() {
		return DateUtils.formatDate(minCreateTime);
	}
	public String getMaxCreateTimeStr() {
		return DateUtils.formatDate(maxCreateTime);
	}
	public String getCreateTimeStr() {
		return DateUtils.formatTime(createTime);
	}
	public Date getMinCreateTime() {
		return minCreateTime;
	}
	public Date getMaxCreateTime() {
		return maxCreateTime;
	}
	
	//setter
	public void setGetLoanWay(String getLoanWay) {
		this.getLoanWay = getLoanWay;
	}
	public void setLoanType(String loanType) {
		this.loanType = loanType;
	}
	public void setContractId(String contractId) {
		this.contractId = contractId;
	}
	public void setOnlineConId(String onlineConId) {
		this.onlineConId = onlineConId;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public void setLoanName(String loanName) {
		this.loanName = loanName;
	}
	public void setLendName(String creditor) {
		this.lendName = creditor;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public void setAmt(double amt) {
		this.amt = amt;
	}
	public void setAcctCode(String acctCode) {
		this.acctCode = acctCode;
	}
	public void setAcctBank(String acctBank) {
		this.acctBank = acctBank;
	}
	public void setLoanAcctCode(String loanAcctCode) {
		this.loanAcctCode = loanAcctCode;
	}
	public void setAcctBranch(String acctBranch) {
		this.acctBranch = acctBranch;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}
	public void setMinCreateTimeStr(String minCreateTime) {
		this.minCreateTimeStr = minCreateTime;
	}
	public void setMaxCreateTimeStr(String maxCreateTime) {
		this.maxCreateTimeStr = maxCreateTime;
	}
	public void setMinCreateTime(Date minCreateTime) {
		this.minCreateTime = minCreateTime;
	}
	public void setMaxCreateTime(Date maxCreateTime) {
		this.maxCreateTime = maxCreateTime;
	}
	
	
}

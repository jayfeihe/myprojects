package com.tera.report.model;

import java.sql.Timestamp;
import java.util.Date;

import com.tera.util.DateUtils;

public class BillAcctFlow {
	/*acct.CONTRACT_ID,
	CONLI.ONLINE_CON_ID,
	CONLI.PROJECT_NAME,
	led. NAME lend_name,
	acct.proof,
	acct.num,
	acct.subject,
	base. NAME loan_name,
	acct.amt,
	ACCT.OPER_TIME,
  org.org_id,
  org.org_name
	 * */
//流水表
	private String contractId;//线下编号
	private String onlineConId;//线上编号
	private String projectName;//项目名称
	private String lendName;//债权人
	private String proof;//凭证号
	private String num;//期数
	private String subject;//类别
	private String loanName;//借款人
	private double amt;//金额
	private Timestamp operTime;//操作时间
	private String operName;//操作人
	private String operTimeStr;
	private Date minOperTime;
	private String minOperTimeStr;
	private Date maxOperTime;
	private String maxOperTimeStr;
	private String orgName;//分公司
	private String orgId;
	private String sub;//billbase中subject
	//t_ret_plan还款计划表
	private double realMargin;//保证金
	private double realLawFee;//法律服务费
	private double realMemFee;//会员费
	private double realTranFee;//转贷费用
	private double realOtherFee;//其他费用
	private String remark;//备注
	//getter部分
	public String getContractId() {
		return contractId;
	}
	public String getOnlineConId() {
		return onlineConId;
	}
	public String getProjectName() {
		return projectName;
	}
	public String getLendName() {
		return lendName;
	}
	public String getProof() {
		return proof;
	}
	public String getNum() {
		return num;
	}
	public String getSubject() {
		return subject;
	}
	public String getLoanName() {
		return loanName;
	}
	public double getAmt() {
		return amt;
	}
	public Timestamp getOperTime() {
		return operTime;
	}
	public String getOperName(){
		return operName;
	}
	public String getOperTimeStr() {
		return DateUtils.formatTime(operTime);
	}
	public Date getMinOperTime() {
		return minOperTime;
	}
	public Date getMaxOperTime() {
		return maxOperTime;
	}
	
	public String getMinOperTimeStr() {
		return DateUtils.formatDate(minOperTime);
	}
	public String getMaxOperTimeStr() {
		return DateUtils.formatDate(maxOperTime);
	}
	public String getOrgName() {
		return orgName;
	}
	
    public String getOrgId() {
		return orgId;
	}
    
	public String getSub() {
		return sub;
	}
	
	public double getRealMargin() {
		return realMargin;
	}
	public double getRealLawFee() {
		return realLawFee;
	}
	public double getRealMemFee() {
		return realMemFee;
	}
	public double getRealTranFee() {
		return realTranFee;
	}
	public double getRealOtherFee() {
		return realOtherFee;
	}
	public String getRemark(){
		return remark;
	}
	//setter部分
	public void setContractId(String contractId) {
		this.contractId = contractId;
	}
	public void setOnlineConId(String onlineConId) {
		this.onlineConId = onlineConId;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public void setLendName(String lendName) {
		this.lendName = lendName;
	}
	public void setProof(String proof) {
		this.proof = proof;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public void setLoanName(String loanName) {
		this.loanName = loanName;
	}
	public void setAmt(double amt) {
		this.amt = amt;
	}
	public void setOperTime(Timestamp operTime) {
		this.operTime = operTime;
	}
	public void setOperName(String operName){
		this.operName=operName;
	}
	public void setOperTimeStr(String operTimeStr) {
		this.operTimeStr = operTimeStr;
	}
	public void setMinOperTime(Date minOperTime) {
		this.minOperTime = minOperTime;
	}
	public void setMaxOperTime(Date maxOperTime) {
		this.maxOperTime = maxOperTime;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public void setSub(String billBaseSubject) {
		this.sub = billBaseSubject;
	}
	public void setMinOperTimeStr(String minOperTimeStr) {
		this.minOperTimeStr = minOperTimeStr;
	}
	public void setMaxOperTimeStr(String maxOperTimeStr) {
		this.maxOperTimeStr = maxOperTimeStr;
	}
	public void setRealMargin(double realMargin) {
		this.realMargin = realMargin;
	}
	public void setRealLawFee(double realLawFee) {
		this.realLawFee = realLawFee;
	}
	public void setRealMemFee(double realMemFee) {
		this.realMemFee = realMemFee;
	}
	public void setRealTranFee(double realTranFee) {
		this.realTranFee = realTranFee;
	}
	public void setRealOtherFee(double realOtherFee) {
		this.realOtherFee = realOtherFee;
	}
	public void setRemark(String remark){
		this.remark=remark;
	}
}

package com.tera.cooperation.dinxuan.model;


/**
 * 鼎轩确认放款管理实体类
 * 
 * @author QYANZE
 *
 */
public class DxConfirmLoanQBean {

	private int appId; // 申请id
	private String appNo; //申请编号
	private String contractNo; // 合同编号
	private String orgName; // 营业部
	private String product; // 产品
	private String name; // 客户姓名
	private String idNo; // 身份证
	private String bankName; // 开户行
	private String bankAccountName; // 账户名称
	private java.sql.Timestamp incomeTime; // 进件时间
	private java.util.Date signDate; // 合同签订日期
	private java.util.Date firstRepayDate; // 首次还款日
	private double contractAmount; // 合同金额
	private double monthRepayAmount; // 月还款额
	private String loanApplication;  // 借款用途
	
	private String decisionCode1; // 拒件码1
	private String decisionCode2; // 拒件码2
	
	private String confirmLoanDate; // 确认放款日期
	private String remark; // 操作备注
	
	private String inputTimeMin; // 开始进件时间
	private String inputTimeMax; // 结束进件时间
	
	public int getAppId() {
		return appId;
	}
	public void setAppId(int appId) {
		this.appId = appId;
	}
	public String getAppNo() {
		return appNo;
	}
	public void setAppNo(String appNo) {
		this.appNo = appNo;
	}
	public String getContractNo() {
		return contractNo;
	}
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIdNo() {
		return idNo;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBankAccountName() {
		return bankAccountName;
	}
	public void setBankAccountName(String bankAccountName) {
		this.bankAccountName = bankAccountName;
	}
	public java.sql.Timestamp getIncomeTime() {
		return incomeTime;
	}
	public void setIncomeTime(java.sql.Timestamp incomeTime) {
		this.incomeTime = incomeTime;
	}
	public java.util.Date getSignDate() {
		return signDate;
	}
	public void setSignDate(java.util.Date signDate) {
		this.signDate = signDate;
	}
	public java.util.Date getFirstRepayDate() {
		return firstRepayDate;
	}
	public void setFirstRepayDate(java.util.Date firstRepayDate) {
		this.firstRepayDate = firstRepayDate;
	}
	public double getContractAmount() {
		return contractAmount;
	}
	public void setContractAmount(double contractAmount) {
		this.contractAmount = contractAmount;
	}
	public double getMonthRepayAmount() {
		return monthRepayAmount;
	}
	public void setMonthRepayAmount(double monthRepayAmount) {
		this.monthRepayAmount = monthRepayAmount;
	}
	public String getLoanApplication() {
		return loanApplication;
	}
	public void setLoanApplication(String loanApplication) {
		this.loanApplication = loanApplication;
	}
	public String getDecisionCode1() {
		return decisionCode1;
	}
	public void setDecisionCode1(String decisionCode1) {
		this.decisionCode1 = decisionCode1;
	}
	public String getDecisionCode2() {
		return decisionCode2;
	}
	public void setDecisionCode2(String decisionCode2) {
		this.decisionCode2 = decisionCode2;
	}
	public String getConfirmLoanDate() {
		return confirmLoanDate;
	}
	public void setConfirmLoanDate(String confirmLoanDate) {
		this.confirmLoanDate = confirmLoanDate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getInputTimeMin() {
		return inputTimeMin;
	}
	public void setInputTimeMin(String inputTimeMin) {
		this.inputTimeMin = inputTimeMin;
	}
	public String getInputTimeMax() {
		return inputTimeMax;
	}
	public void setInputTimeMax(String inputTimeMax) {
		this.inputTimeMax = inputTimeMax;
	}
}

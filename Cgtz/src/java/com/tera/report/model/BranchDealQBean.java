package com.tera.report.model;

/**
 * 分公司成交量实体
 * @author QYANZE
 *
 */
public class BranchDealQBean {

	private String orgId; // 机构id
	private String orgName; // 机构名称
	private String product; // 产品
	private String productName; // 产品名称
	private int newNum; // 新增合计量
	private double newAmt; // 新增合计金额
	private int renewNum; // 续贷合计量
	private double renewAmt; // 续贷合计金额
	private int totalNum; // 总计合计量
	private double totalAmt; // 总计合计金额
	
	private int newTotalNum; // 新增合计笔数
	private double newTotalAmt; // 新增合计金额
	private int renewTotalNum; // 续贷合计笔数
	private double renewTotalAmt; // 续贷合计金额
	private int totalTotalNum; // 总计合计笔数
	private double totalTotalAmt; // 总计合计金额
	
	private String loanTimeMin; // 放款时间
	private String loanTimeMax; // 放款时间

	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
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
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public int getNewNum() {
		return newNum;
	}
	public void setNewNum(int newNum) {
		this.newNum = newNum;
	}
	public double getNewAmt() {
		return newAmt;
	}
	public void setNewAmt(double newAmt) {
		this.newAmt = newAmt;
	}
	public int getRenewNum() {
		return renewNum;
	}
	public void setRenewNum(int renewNum) {
		this.renewNum = renewNum;
	}
	public double getRenewAmt() {
		return renewAmt;
	}
	public void setRenewAmt(double renewAmt) {
		this.renewAmt = renewAmt;
	}
	public int getTotalNum() {
		return totalNum;
	}
	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}
	public double getTotalAmt() {
		return totalAmt;
	}
	public void setTotalAmt(double totalAmt) {
		this.totalAmt = totalAmt;
	}
	public int getNewTotalNum() {
		return newTotalNum;
	}
	public void setNewTotalNum(int newTotalNum) {
		this.newTotalNum = newTotalNum;
	}
	public double getNewTotalAmt() {
		return newTotalAmt;
	}
	public void setNewTotalAmt(double newTotalAmt) {
		this.newTotalAmt = newTotalAmt;
	}
	public int getRenewTotalNum() {
		return renewTotalNum;
	}
	public void setRenewTotalNum(int renewTotalNum) {
		this.renewTotalNum = renewTotalNum;
	}
	public double getRenewTotalAmt() {
		return renewTotalAmt;
	}
	public void setRenewTotalAmt(double renewTotalAmt) {
		this.renewTotalAmt = renewTotalAmt;
	}
	public int getTotalTotalNum() {
		return totalTotalNum;
	}
	public void setTotalTotalNum(int totalTotalNum) {
		this.totalTotalNum = totalTotalNum;
	}
	public double getTotalTotalAmt() {
		return totalTotalAmt;
	}
	public void setTotalTotalAmt(double totalTotalAmt) {
		this.totalTotalAmt = totalTotalAmt;
	}
	public String getLoanTimeMin() {
		return loanTimeMin;
	}
	public void setLoanTimeMin(String loanTimeMin) {
		this.loanTimeMin = loanTimeMin;
	}
	public String getLoanTimeMax() {
		return loanTimeMax;
	}
	public void setLoanTimeMax(String loanTimeMax) {
		this.loanTimeMax = loanTimeMax;
	}
}

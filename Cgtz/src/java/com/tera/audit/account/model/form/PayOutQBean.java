package com.tera.audit.account.model.form;

/** 垫付查询实体
 * @author QYANZE
 *
 */
public class PayOutQBean {

	private String id; // 申请主键id
	private String loanId; // 申请编号
	private String contractId;  // 线下合同id 
	private String onLineContractId;  // 线上合同id
	private String name;  // 姓名
	private double onLineAmt;  // 线上合同金额
	private String projectName; // 项目名称
	private String isRenew; // 是否续贷
	private String renewNum; // 续贷次数
	
	private String product;
	private String productName;
	private String idNo;
	
	public String getId() {
		return id;
	}
	public String getLoanId() {
		return loanId;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}
	public String getContractId() {
		return contractId;
	}
	public String getOnLineContractId() {
		return onLineContractId;
	}
	public String getName() {
		return name;
	}
	public double getOnLineAmt() {
		return onLineAmt;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setContractId(String contractId) {
		this.contractId = contractId;
	}
	public void setOnLineContractId(String onLineContractId) {
		this.onLineContractId = onLineContractId;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setOnLineAmt(double onLineAmt) {
		this.onLineAmt = onLineAmt;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getProduct() {
		return product;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getIdNo() {
		return idNo;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	public String getIsRenew() {
		return isRenew;
	}
	public void setIsRenew(String isRenew) {
		this.isRenew = isRenew;
	}
	public String getRenewNum() {
		return renewNum;
	}
	public void setRenewNum(String renewNum) {
		this.renewNum = renewNum;
	}
}

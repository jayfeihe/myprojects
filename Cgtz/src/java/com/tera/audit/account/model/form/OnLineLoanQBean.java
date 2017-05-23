package com.tera.audit.account.model.form;

/** 线上查询实体
 * @author QYANZE
 *
 */
public class OnLineLoanQBean {

	private String id; // 申请主键id
	private String loanId;  // 申请编号
	private String name ; // 姓名
	private String idNo; // 身份证
	private String product ; // 产品
 	private String productName ;  //  产品名称
	private String contractId ; // 线下合同编号
	private String onLineContractId ; // 线上合同编号
	private double onLineAmt ; // 线上合同金额
	private String onLineRetWay ; // 线上还款方式
	private String onLineState ; //  线上放款状态
	private String getLoanWay; // 合同融资方式
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLoanId() {
		return loanId;
	}
	public String getName() {
		return name;
	}
	public String getIdNo() {
		return idNo;
	}
	public String getProduct() {
		return product;
	}
	public String getProductName() {
		return productName;
	}
	public String getContractId() {
		return contractId;
	}
	public String getOnLineContractId() {
		return onLineContractId;
	}
	public double getOnLineAmt() {
		return onLineAmt;
	}
	public String getOnLineRetWay() {
		return onLineRetWay;
	}
	public String getOnLineState() {
		return onLineState;
	}
	public String getGetLoanWay() {
		return getLoanWay;
	}
	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public void setContractId(String contractId) {
		this.contractId = contractId;
	}
	public void setOnLineContractId(String onLineContractId) {
		this.onLineContractId = onLineContractId;
	}
	public void setOnLineAmt(double onLineAmt) {
		this.onLineAmt = onLineAmt;
	}
	public void setOnLineRetWay(String onLineRetWay) {
		this.onLineRetWay = onLineRetWay;
	}
	public void setOnLineState(String onLineState) {
		this.onLineState = onLineState;
	}
	public void setGetLoanWay(String getLoanWay) {
		this.getLoanWay = getLoanWay;
	}
}

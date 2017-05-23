package com.tera.collection.reduce.model.form;

import com.tera.collection.reduce.model.CollectionRemission;
import com.tera.contract.model.Contract;


/**
 * 减免列表查询实体
 * 
 * @author QYANZE
 *
 */
public class RemissionQBean {

	private String id; // 减免申请id
	private String contractNo; // 合同编号
	private String orgName; // 营业部
	private String name; // 客户姓名
	private String idNo; // 身份证
	private String contractAmount; // 合同金额
	private String remissionMount; // 减免金额
	private String firstRepayDate; // 首次还款日期
	private String accountAge; // 帐龄
	private String overdueDays; // 逾期天数
	private String product; // 产品
	private String collectionUid; // 催收人员
	private String currentState; // 当前状态
	private String processStatus; // 处理状态   0：待处理，1：已处理
	
	private String overdueDaysStart; // 开始逾期天数
	private String overdueDaysEnd; // 结束逾期天数
	
	
	private CollectionRemission collectionRemission;
	
	private Contract contract; 
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getContractAmount() {
		return contractAmount;
	}
	public void setContractAmount(String contractAmount) {
		this.contractAmount = contractAmount;
	}
	public String getRemissionMount() {
		return remissionMount;
	}
	public void setRemissionMount(String remissionMount) {
		this.remissionMount = remissionMount;
	}
	public String getFirstRepayDate() {
		return firstRepayDate;
	}
	public void setFirstRepayDate(String firstRepayDate) {
		this.firstRepayDate = firstRepayDate;
	}
	public String getAccountAge() {
		return accountAge;
	}
	public void setAccountAge(String accountAge) {
		this.accountAge = accountAge;
	}
	public String getOverdueDays() {
		return overdueDays;
	}
	public void setOverdueDays(String overdueDays) {
		this.overdueDays = overdueDays;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public String getCollectionUid() {
		return collectionUid;
	}
	public void setCollectionUid(String collectionUid) {
		this.collectionUid = collectionUid;
	}
	public String getCurrentState() {
		return currentState;
	}
	public void setCurrentState(String currentState) {
		this.currentState = currentState;
	}
	public String getProcessStatus() {
		return processStatus;
	}
	public void setProcessStatus(String processStatus) {
		this.processStatus = processStatus;
	}
	public CollectionRemission getCollectionRemission() {
		return collectionRemission;
	}
	public void setCollectionRemission(CollectionRemission collectionRemission) {
		this.collectionRemission = collectionRemission;
	}
	public Contract getContract() {
		return contract;
	}
	public void setContract(Contract contract) {
		this.contract = contract;
	}
	public String getOverdueDaysStart() {
		return overdueDaysStart;
	}
	public void setOverdueDaysStart(String overdueDaysStart) {
		this.overdueDaysStart = overdueDaysStart;
	}
	public String getOverdueDaysEnd() {
		return overdueDaysEnd;
	}
	public void setOverdueDaysEnd(String overdueDaysEnd) {
		this.overdueDaysEnd = overdueDaysEnd;
	}
}

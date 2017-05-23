package com.tera.collection.judicial.model.form;

public class CollectionQBean {
	private String contractNo; // 合同编号
	private String customerName; // 客户姓名
	private String idNo; // 身份证号

	private String collectionUidCur; // 当前处理人
	private String orgId; // 营业部
	private String appCode; // 申请编号
	private String distributionState;// 分配状态
	private String state;// 状态
	private String lateDaysMin;// 逾期天数Min
	private String lateDaysMax;// 逾期天数Max
	private String applyUid;//当前催收人
	
	private String checkUid;//复核人
	private String approvalUid;//审批人
	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public String getCollectionUidCur() {
		return collectionUidCur;
	}

	public void setCollectionUidCur(String collectionUidCur) {
		this.collectionUidCur = collectionUidCur;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getAppCode() {
		return appCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}


	public String getDistributionState() {
		return distributionState;
	}

	public void setDistributionState(String distributionState) {
		this.distributionState = distributionState;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getLateDaysMin() {
		return lateDaysMin;
	}

	public void setLateDaysMin(String lateDaysMin) {
		this.lateDaysMin = lateDaysMin;
	}

	public String getLateDaysMax() {
		return lateDaysMax;
	}

	public void setLateDaysMax(String lateDaysMax) {
		this.lateDaysMax = lateDaysMax;
	}

	public CollectionQBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getApplyUid() {
		return applyUid;
	}

	public void setApplyUid(String applyUid) {
		this.applyUid = applyUid;
	}

	public String getCheckUid() {
		return checkUid;
	}

	public void setCheckUid(String checkUid) {
		this.checkUid = checkUid;
	}

	public String getApprovalUid() {
		return approvalUid;
	}

	public void setApprovalUid(String approvalUid) {
		this.approvalUid = approvalUid;
	}

}

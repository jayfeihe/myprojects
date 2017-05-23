package com.tera.collection.visit.model;

public class VisitQbean {
	//落地催分单查询条件
	private String customerName;		//客户名称
	private String idNo;				//身份证号
	private String contractNo;			//合同号
	private String collectionUidCur;	//当前处理人
	private String orgId;				//营业部id
	private String distributionState;	//分配状态
	private String state;				//当前状态
	private double lateDaysMin;			//逾期天数最小值
	private double lateDaysMax;			//逾期天数最大值
	
	
	private String collectionWayNo;		//当前渠道				0 无 1 电催 2 落地催
	private String collectionUid;		//催收人id
	private String ishelp;				//是否协催                                      是:y;否:n; 默认为否;
	private String isCur;				//是否当前标识			是:y;否:n; 默认为否;
	private String applyState;			//司法/欺诈申请状态		申请状态:0  无申请或者退回     1  申请中      2  申请通过 
	
	//落地催信息列表查询条件（添加）
	private String product;				//产品
	private String phoneSummary;	//电催摘要
	private String loanPlatform;		//放款平台
	private String customerTel;			//联系方式
	private String handleState;			//处理状态
	
	
	//落地催分单统计查询（添加）
	private java.sql.Timestamp distributionDateMin;
	private java.sql.Timestamp distributionDateMax;
	
	 
	/*//落地催主管或落地催专员 标识         1 主管 2专员
	private String curRole;			//当前登陆角色
	private String curLoginId;		//当前登陆用户id
	
	
	public String getCurRole() {
		return curRole;
	}
	public void setCurRole(String curRole) {
		this.curRole = curRole;
	}
	public String getCurLoginId() {
		return curLoginId;
	}
	public void setCurLoginId(String curLoginId) {
		this.curLoginId = curLoginId;
	}*/
	public String getHandleState() {
		return handleState;
	}
	public void setHandleState(String handleState) {
		this.handleState = handleState;
	}
	
	
	 
	
	public java.sql.Timestamp getDistributionDateMin() {
		return distributionDateMin;
	}
	public void setDistributionDateMin(java.sql.Timestamp distributionDateMin) {
		this.distributionDateMin = distributionDateMin;
	}
	public java.sql.Timestamp getDistributionDateMax() {
		return distributionDateMax;
	}
	public void setDistributionDateMax(java.sql.Timestamp distributionDateMax) {
		this.distributionDateMax = distributionDateMax;
	}
	public String getLoanPlatform() {
		return loanPlatform;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public String getPhoneSummary() {
		return phoneSummary;
	}
	public void setPhoneSummary(String phoneSummary) {
		this.phoneSummary = phoneSummary;
	}
	public void setLoanPlatform(String loanPlatform) {
		this.loanPlatform = loanPlatform;
	}
	public String getCustomerTel() {
		return customerTel;
	}
	public void setCustomerTel(String customerTel) {
		this.customerTel = customerTel;
	}
	public String getCollectionUid() {
		return collectionUid;
	}
	public void setCollectionUid(String collectionUid) {
		this.collectionUid = collectionUid;
	}
	public String getCollectionWayNo() {
		return collectionWayNo;
	}
	public void setCollectionWayNo(String collectionWayNo) {
		this.collectionWayNo = collectionWayNo;
	}
	public String getIshelp() {
		return ishelp;
	}
	public void setIshelp(String ishelp) {
		this.ishelp = ishelp;
	}
	public String getIsCur() {
		return isCur;
	}
	public void setIsCur(String isCur) {
		this.isCur = isCur;
	}
	public String getApplyState() {
		return applyState;
	}
	public void setApplyState(String applyState) {
		this.applyState = applyState;
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
	public String getContractNo() {
		return contractNo;
	}
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
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
	public double getLateDaysMin() {
		return lateDaysMin;
	}
	public void setLateDaysMin(double lateDaysMin) {
		this.lateDaysMin = lateDaysMin;
	}
	public double getLateDaysMax() {
		return lateDaysMax;
	}
	public void setLateDaysMax(double lateDaysMax) {
		this.lateDaysMax = lateDaysMax;
	}
	
}

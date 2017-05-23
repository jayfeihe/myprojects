package com.tera.collection.judicial.model;

import com.tera.util.DateUtils;

/**
 * 司法欺诈信息实体类
 * @author Administrator
 *
 */
public class CollectionJudicial {
	//CollectionJudicial
	private String id; //id
	private String contractNo; //合同编号
	private String idNo; //证件号码
	private String customerName; //客户姓名
	private double contractAmount; //合同额
	private int lateAge; //账龄
	private int lateDays; //逾期天数
	private java.util.Date repaymentDate; //还款日
	private String repaymentDateStr; //还款日
	private String orgId; //营业部
	
	private String applyState; //申请状态:欺诈，司法共用   初始0  无申请或者退回  1  申请中 2  申请通过
	private String collectionUidCur; //当前处理人
	//新增字段
	private String orgName;//营业部名称
	//CollectionApplication
	private String distributionState; //分配状态
	private String state; //状态

	
	private String checkUid; //复核人
	private String checkName;//复核人姓名
	private String checkResult;//复核结论
	private java.util.Date checkTime;//复核时间
	private String checkTimeStr;//复核时间
	private String checkText;//复核意见
	
	private String approvalUid; //审批人
	private String approvalName; //审批人姓名
	private String approvalText;//审批意见
	private String approvalResult; //审批结果
	private java.util.Date approvalTime;//审批时间
	private String approvalTimeStr;//审批时间
	
	private String applyUid;//申请人
	private String applyName;//申请人姓名
	private java.util.Date applyTime;//申请时间
	private String applyTimeStr;//申请时间
	private String applyText;//申请内容
	private String departName;//组织名称
	private String collectionWay;//催收渠道
	
	private String logContent1;//bpm_log 备注1（复核、审批结论）
	private String logContent2;//bpm_log 备注2（复核、审批意见）
	
	
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
	public String getIdNo() {
		return idNo;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public double getContractAmount() {
		return contractAmount;
	}
	public void setContractAmount(double contractAmount) {
		this.contractAmount = contractAmount;
	}
	public int getLateAge() {
		return lateAge;
	}
	public void setLateAge(int lateAge) {
		this.lateAge = lateAge;
	}
	public int getLateDays() {
		return lateDays;
	}
	public void setLateDays(int lateDays) {
		this.lateDays = lateDays;
	}
	public java.util.Date getRepaymentDate() {
		return repaymentDate;
	}
	public void setRepaymentDate(java.util.Date repaymentDate) {
		this.repaymentDate = repaymentDate;
	}
	//getter部分,Date类型的修改获取String的方法
	public String getRepaymentDateStr () {
		return DateUtils.formatDate(this.repaymentDate);
	}
	public void setRepaymentDateStr (String repaymentDateStr) {
		this.repaymentDateStr=repaymentDateStr;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getApplyState() {
		return applyState;
	}
	public void setApplyState(String applyState) {
		this.applyState = applyState;
	}
	public String getCollectionUidCur() {
		return collectionUidCur;
	}
	public void setCollectionUidCur(String collectionUidCur) {
		this.collectionUidCur = collectionUidCur;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
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
	public String getApprovalResult() {
		return approvalResult;
	}
	public void setApprovalResult(String approvalResult) {
		this.approvalResult = approvalResult;
	}
	public String getCheckUid() {
		return checkUid;
	}
	public void setCheckUid(String checkUid) {
		this.checkUid = checkUid;
	}
	public String getCheckName() {
		return checkName;
	}
	public void setCheckName(String checkName) {
		this.checkName = checkName;
	}
	public String getApprovalUid() {
		return approvalUid;
	}
	public void setApprovalUid(String approvalUid) {
		this.approvalUid = approvalUid;
	}
	public String getApprovalName() {
		return approvalName;
	}
	public void setApprovalName(String approvalName) {
		this.approvalName = approvalName;
	}
	public String getCheckResult() {
		return checkResult;
	}
	public void setCheckResult(String checkResult) {
		this.checkResult = checkResult;
	}
	public java.util.Date getCheckTime() {
		return checkTime;
	}
	public void setCheckTime(java.util.Date checkTime) {
		this.checkTime = checkTime;
	}
	//getter部分,Date类型的修改获取String的方法
	public String getCheckTimeStr() {
		return DateUtils.formatDate(this.checkTime);
	}
	public void setCheckTimeStr(String checkTimeStr) {
		this.checkTimeStr = checkTimeStr;
	}
	public String getCheckText() {
		return checkText;
	}
	public void setCheckText(String checkText) {
		this.checkText = checkText;
	}
	public String getApprovalText() {
		return approvalText;
	}
	public void setApprovalText(String approvalText) {
		this.approvalText = approvalText;
	}
	public java.util.Date getApprovalTime() {
		return approvalTime;
	}
	public void setApprovalTime(java.util.Date approvalTime) {
		this.approvalTime = approvalTime;
	}
	//getter部分,Date类型的修改获取String的方法
	public String getApprovalTimeStr() {
		return DateUtils.formatDate(this.approvalTime);
	}
	public void setApprovalTimeStr(String approvalTimeStr) {
		this.approvalTimeStr = approvalTimeStr;
	}
	public String getApplyUid() {
		return applyUid;
	}
	public void setApplyUid(String applyUid) {
		this.applyUid = applyUid;
	}
	public String getApplyName() {
		return applyName;
	}
	public void setApplyName(String applyName) {
		this.applyName = applyName;
	}
	public java.util.Date getApplyTime() {
		return applyTime;
	}
	public void setApplyTime(java.util.Date applyTime) {
		this.applyTime = applyTime;
	}
	//getter部分,Date类型的修改获取String的方法
	public String getApplyTimeStr() {
		return DateUtils.formatDate(this.applyTime);
	}
	public void setApplyTimeStr(String applyTimeStr) {
		this.applyTimeStr = applyTimeStr;
	}
	public String getApplyText() {
		return applyText;
	}
	public void setApplyText(String applyText) {
		this.applyText = applyText;
	}
	public String getDepartName() {
		return departName;
	}
	public void setDepartName(String departName) {
		this.departName = departName;
	}
	public String getCollectionWay() {
		return collectionWay;
	}
	public void setCollectionWay(String collectionWay) {
		this.collectionWay = collectionWay;
	}
	public String getLogContent1() {
		return logContent1;
	}
	public void setLogContent1(String logContent1) {
		this.logContent1 = logContent1;
	}
	public String getLogContent2() {
		return logContent2;
	}
	public void setLogContent2(String logContent2) {
		this.logContent2 = logContent2;
	}
	
}

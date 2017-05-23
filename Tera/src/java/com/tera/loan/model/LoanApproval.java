package com.tera.loan.model;

import com.tera.util.DateUtils;

/**
 * 
 * <br>
 * <b>功能：</b>LoanApprovalDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-06-09 15:36:08<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public class LoanApproval {

	//属性部分
	private String appId; //申请ID
	private String selfCreditMark; //借款人信用正常标志
	private String selfCreditCardDvalue; //借款人贷记卡违约值
	private double selfCreditCardDamount; //借款人贷记卡金额
	private String selfCreditCardRemark; //借款人贷记卡备注
	private String selfLoanDvalue; //借款人贷款违约值
	private double selfLoanDamount; //借款人贷款金额
	private String selfLoanRemark; //借款人贷款备注
	private String spouseCreditMark; //配偶信用正常标志
	private String spouseCreditCardDvalue; //配偶贷记卡违约值
	private double spouseCreditCardDamount; //配偶贷记卡金额
	private String spouseCreditCardRemark; //配偶贷记卡备注
	private String spouseLoanDvalue; //配偶贷款违约值
	private double spouseLoanDamount; //配偶贷款金额
	private String spouseLoanRemark; //配偶贷款备注
	private String entLoanMark; //企业信用正常标志
	private String entLoanDvalue; //企业贷款违约值
	private double entLoanDamount; //企业贷款余额
	private java.util.Date entLoanExpiryDate; //企业贷款到期日
	private String entLoanExpiryDateStr; //企业贷款到期日
	private String entLoanRemark; //企业贷款备注
	private String courtCompany; //法院执行-公司
	private String courtCompanyRemark; //法院执行-公司备注
	private String courtLegal; //法院执行-公司法人
	private String courtLegalRemark; //法院执行-公司法人备注
	private String courtGuarantee; //法院执行-抵押担保人
	private String courtGuaranteeRemark; //法院执行-抵押担保人备注
	private String industryNetwork; //工商网
	private String industryNetworkRemark; //工商网备注
	private String orgNetwork; //组织结构网
	private String orgNetworkRemark; //组织结构网备注
	private String network114; //114查询备注
	private String network114Remark; //网查备注
	private String telLegal; //电话调查-法人
	private String telLegalRemark; //电话调查-法人备注
	private String telAgent; //电话调查-代理人
	private String telAgentRemark; //电话调查-代理人备注
	private String telCfo; //电话调查-财务主管
	private String telCfoRemark; //电话调查-财务主管备注
	private String telContract; //电话调查-购销合同
	private String telContractRemark; //电话调查-购销合同备注
	private double approvalAmount; //审批金额
	private int approvalPeriod; //审批期限
	private double approvalServiceRate; //审批服务费率
	private String thirdPartyContract; //需要第三方购销合同
	private String guaranteeCondition; //担保条件
	private String matchType; //撮合类型
	private String operator; //操作员
	private String orgId; //所属机构
	private java.sql.Timestamp createTime; //创建日期
	private String createTimeStr; //创建日期
	private java.sql.Timestamp updateTime; //修改日期
	private String updateTimeStr; //修改日期

	//getter部分
	public String getAppId () {
		return this.appId;
	}
	public String getSelfCreditMark () {
		return this.selfCreditMark;
	}
	public String getSelfCreditCardDvalue () {
		return this.selfCreditCardDvalue;
	}
	public double getSelfCreditCardDamount () {
		return this.selfCreditCardDamount;
	}
	public String getSelfCreditCardRemark () {
		return this.selfCreditCardRemark;
	}
	public String getSelfLoanDvalue () {
		return this.selfLoanDvalue;
	}
	public double getSelfLoanDamount () {
		return this.selfLoanDamount;
	}
	public String getSelfLoanRemark () {
		return this.selfLoanRemark;
	}
	public String getSpouseCreditMark () {
		return this.spouseCreditMark;
	}
	public String getSpouseCreditCardDvalue () {
		return this.spouseCreditCardDvalue;
	}
	public double getSpouseCreditCardDamount () {
		return this.spouseCreditCardDamount;
	}
	public String getSpouseCreditCardRemark () {
		return this.spouseCreditCardRemark;
	}
	public String getSpouseLoanDvalue () {
		return this.spouseLoanDvalue;
	}
	public double getSpouseLoanDamount () {
		return this.spouseLoanDamount;
	}
	public String getSpouseLoanRemark () {
		return this.spouseLoanRemark;
	}
	public String getEntLoanMark () {
		return this.entLoanMark;
	}
	public String getEntLoanDvalue () {
		return this.entLoanDvalue;
	}
	public double getEntLoanDamount () {
		return this.entLoanDamount;
	}
	public java.util.Date getEntLoanExpiryDate () {
		return this.entLoanExpiryDate;
	}
	//getter部分,Date类型的修改获取String的方法
	public String getEntLoanExpiryDateStr () {
		return DateUtils.formatDate(this.entLoanExpiryDate);
	}
	public String getEntLoanRemark () {
		return this.entLoanRemark;
	}
	public String getCourtCompany () {
		return this.courtCompany;
	}
	public String getCourtCompanyRemark () {
		return this.courtCompanyRemark;
	}
	public String getCourtLegal () {
		return this.courtLegal;
	}
	public String getCourtLegalRemark () {
		return this.courtLegalRemark;
	}
	public String getCourtGuarantee () {
		return this.courtGuarantee;
	}
	public String getCourtGuaranteeRemark () {
		return this.courtGuaranteeRemark;
	}
	public String getIndustryNetwork () {
		return this.industryNetwork;
	}
	public String getIndustryNetworkRemark () {
		return this.industryNetworkRemark;
	}
	public String getOrgNetwork () {
		return this.orgNetwork;
	}
	public String getOrgNetworkRemark () {
		return this.orgNetworkRemark;
	}
	public String getNetwork114 () {
		return this.network114;
	}
	public String getNetwork114Remark () {
		return this.network114Remark;
	}
	public String getTelLegal () {
		return this.telLegal;
	}
	public String getTelLegalRemark () {
		return this.telLegalRemark;
	}
	public String getTelAgent () {
		return this.telAgent;
	}
	public String getTelAgentRemark () {
		return this.telAgentRemark;
	}
	public String getTelCfo () {
		return this.telCfo;
	}
	public String getTelCfoRemark () {
		return this.telCfoRemark;
	}
	public String getTelContract () {
		return this.telContract;
	}
	public String getTelContractRemark () {
		return this.telContractRemark;
	}
	public double getApprovalAmount () {
		return this.approvalAmount;
	}
	public int getApprovalPeriod () {
		return this.approvalPeriod;
	}
	public double getApprovalServiceRate () {
		return this.approvalServiceRate;
	}
	public String getThirdPartyContract () {
		return this.thirdPartyContract;
	}
	public String getGuaranteeCondition () {
		return this.guaranteeCondition;
	}
	public String getMatchType () {
		return this.matchType;
	}
	public String getOperator () {
		return this.operator;
	}
	public String getOrgId () {
		return this.orgId;
	}
	public java.sql.Timestamp getCreateTime () {
		return this.createTime;
	}
	//getter部分,Timestamp类型的修改获取String的方法
	public String getCreateTimeStr () {
		return DateUtils.formatTime(this.createTime);
	}
	public java.sql.Timestamp getUpdateTime () {
		return this.updateTime;
	}
	//getter部分,Timestamp类型的修改获取String的方法
	public String getUpdateTimeStr () {
		return DateUtils.formatTime(this.updateTime);
	}

	//setter部分
	public void setAppId (String appId) {
		this.appId=appId;
	}
	public void setSelfCreditMark (String selfCreditMark) {
		this.selfCreditMark=selfCreditMark;
	}
	public void setSelfCreditCardDvalue (String selfCreditCardDvalue) {
		this.selfCreditCardDvalue=selfCreditCardDvalue;
	}
	public void setSelfCreditCardDamount (double selfCreditCardDamount) {
		this.selfCreditCardDamount=selfCreditCardDamount;
	}
	public void setSelfCreditCardRemark (String selfCreditCardRemark) {
		this.selfCreditCardRemark=selfCreditCardRemark;
	}
	public void setSelfLoanDvalue (String selfLoanDvalue) {
		this.selfLoanDvalue=selfLoanDvalue;
	}
	public void setSelfLoanDamount (double selfLoanDamount) {
		this.selfLoanDamount=selfLoanDamount;
	}
	public void setSelfLoanRemark (String selfLoanRemark) {
		this.selfLoanRemark=selfLoanRemark;
	}
	public void setSpouseCreditMark (String spouseCreditMark) {
		this.spouseCreditMark=spouseCreditMark;
	}
	public void setSpouseCreditCardDvalue (String spouseCreditCardDvalue) {
		this.spouseCreditCardDvalue=spouseCreditCardDvalue;
	}
	public void setSpouseCreditCardDamount (double spouseCreditCardDamount) {
		this.spouseCreditCardDamount=spouseCreditCardDamount;
	}
	public void setSpouseCreditCardRemark (String spouseCreditCardRemark) {
		this.spouseCreditCardRemark=spouseCreditCardRemark;
	}
	public void setSpouseLoanDvalue (String spouseLoanDvalue) {
		this.spouseLoanDvalue=spouseLoanDvalue;
	}
	public void setSpouseLoanDamount (double spouseLoanDamount) {
		this.spouseLoanDamount=spouseLoanDamount;
	}
	public void setSpouseLoanRemark (String spouseLoanRemark) {
		this.spouseLoanRemark=spouseLoanRemark;
	}
	public void setEntLoanMark (String entLoanMark) {
		this.entLoanMark=entLoanMark;
	}
	public void setEntLoanDvalue (String entLoanDvalue) {
		this.entLoanDvalue=entLoanDvalue;
	}
	public void setEntLoanDamount (double entLoanDamount) {
		this.entLoanDamount=entLoanDamount;
	}
	public void setEntLoanExpiryDate (java.util.Date entLoanExpiryDate) {
		this.entLoanExpiryDate=entLoanExpiryDate;
	}
	public void setEntLoanExpiryDateStr (String entLoanExpiryDateStr) {
		this.entLoanExpiryDateStr=entLoanExpiryDateStr;
	}
	public void setEntLoanRemark (String entLoanRemark) {
		this.entLoanRemark=entLoanRemark;
	}
	public void setCourtCompany (String courtCompany) {
		this.courtCompany=courtCompany;
	}
	public void setCourtCompanyRemark (String courtCompanyRemark) {
		this.courtCompanyRemark=courtCompanyRemark;
	}
	public void setCourtLegal (String courtLegal) {
		this.courtLegal=courtLegal;
	}
	public void setCourtLegalRemark (String courtLegalRemark) {
		this.courtLegalRemark=courtLegalRemark;
	}
	public void setCourtGuarantee (String courtGuarantee) {
		this.courtGuarantee=courtGuarantee;
	}
	public void setCourtGuaranteeRemark (String courtGuaranteeRemark) {
		this.courtGuaranteeRemark=courtGuaranteeRemark;
	}
	public void setIndustryNetwork (String industryNetwork) {
		this.industryNetwork=industryNetwork;
	}
	public void setIndustryNetworkRemark (String industryNetworkRemark) {
		this.industryNetworkRemark=industryNetworkRemark;
	}
	public void setOrgNetwork (String orgNetwork) {
		this.orgNetwork=orgNetwork;
	}
	public void setOrgNetworkRemark (String orgNetworkRemark) {
		this.orgNetworkRemark=orgNetworkRemark;
	}
	public void setNetwork114 (String network114) {
		this.network114=network114;
	}
	public void setNetwork114Remark (String network114Remark) {
		this.network114Remark=network114Remark;
	}
	public void setTelLegal (String telLegal) {
		this.telLegal=telLegal;
	}
	public void setTelLegalRemark (String telLegalRemark) {
		this.telLegalRemark=telLegalRemark;
	}
	public void setTelAgent (String telAgent) {
		this.telAgent=telAgent;
	}
	public void setTelAgentRemark (String telAgentRemark) {
		this.telAgentRemark=telAgentRemark;
	}
	public void setTelCfo (String telCfo) {
		this.telCfo=telCfo;
	}
	public void setTelCfoRemark (String telCfoRemark) {
		this.telCfoRemark=telCfoRemark;
	}
	public void setTelContract (String telContract) {
		this.telContract=telContract;
	}
	public void setTelContractRemark (String telContractRemark) {
		this.telContractRemark=telContractRemark;
	}
	public void setApprovalAmount (double approvalAmount) {
		this.approvalAmount=approvalAmount;
	}
	public void setApprovalPeriod (int approvalPeriod) {
		this.approvalPeriod=approvalPeriod;
	}
	public void setApprovalServiceRate (double approvalServiceRate) {
		this.approvalServiceRate=approvalServiceRate;
	}
	public void setThirdPartyContract (String thirdPartyContract) {
		this.thirdPartyContract=thirdPartyContract;
	}
	public void setGuaranteeCondition (String guaranteeCondition) {
		this.guaranteeCondition=guaranteeCondition;
	}
	public void setMatchType (String matchType) {
		this.matchType=matchType;
	}
	public void setOperator (String operator) {
		this.operator=operator;
	}
	public void setOrgId (String orgId) {
		this.orgId=orgId;
	}
	public void setCreateTime (java.sql.Timestamp createTime) {
		this.createTime=createTime;
	}
	public void setCreateTimeStr (String createTimeStr) {
		this.createTimeStr=createTimeStr;
	}
	public void setUpdateTime (java.sql.Timestamp updateTime) {
		this.updateTime=updateTime;
	}
	public void setUpdateTimeStr (String updateTimeStr) {
		this.updateTimeStr=updateTimeStr;
	}

}


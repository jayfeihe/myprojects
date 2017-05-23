package com.tera.collection.phone.model;

import com.tera.util.DateUtils;

/**
 * 
 * 催收信息基本表实体类
 * <b>功能：</b>CollectionBaseDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2015-06-08 14:36:46<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public class CollectionBaseInfo {

	//属性部分
	private int id; //id
	private String contractNo; //合同编号
	private String customerId; //客户id
	private String idType; //证件类型
	private String idNo; //证件号码
	private String customerName; //客户姓名
	private String customerTel; //联系方式
	private double contractAmount; //合同额
	private String product; //产品
	private int lateAge; //账龄
	private int lateDays; //逾期天数
	private java.util.Date repaymentDate; //还款日
	private String repaymentDateStr; //还款日
	private String loanPlatform; //放款平台
	private String orgId; //营业部
	private String isLate; //是否逾期
	private String isFinish; //是否结清
	private double penalty; //罚息总额
	private double defaultFee; //违约金总额
	private double delay; //滞纳金总额
	private double sreviceFee; //服务费
	private double principal; //本金总额
	private double interest; //利息总额
	private double reduce; //减免总额
	private double monthAmountAll; //月还总额为本金和利息总和，不包括已还的
	private double amountAll; //应还总额
	private int periodAll; //总期数
	private int periodCur; //当前期数
	private int periodLateHis; //历史逾期期数
	private int periodFinish; //已还期数
	private String answerState; //接听状态
	private String phoneSummary; //电催摘要
	private String state; //状态标识
	private java.sql.Timestamp followTime; //跟进时间
	private String followTimeStr; //跟进时间
	private java.sql.Timestamp orderTime; //预约时间
	private String orderTimeStr; //预约时间
	private String distributionState; //分配状态
            
	private String collectionWayCur; //当前渠道: 0 无 1 电催      2 落地催
	private String collectionUidCur; //当前处理人
	private String applyState; //申请状态:欺诈，司法共用   初始0  无申请或者退回  1  申请中 2  申请通过
        
	private String remark; //备注
	private String createUid; //创建人
	private java.sql.Timestamp createTime; //创建时间
	private String createTimeStr; //创建时间
	private String updateUid; //修改人
	private java.sql.Timestamp updateTime; //修改时间
	private String updateTimeStr; //修改时间
	private String gender;//性别 
	private String orgName; //营业部
	private String minLateDays; //逾期最小范围值
	private String maxLateDays; //逾期最大范围值
	
	private String bankName; //开户行名称
	private String bankCode; //开户行代码
	private String bankAccountName; //账户名
	private String bankAccount; //卡号
	private String name; //user姓名 （催收人员姓名）
	
	
	private String handleState; //处理标识
	private java.sql.Timestamp helpFollowTime; //协催跟进时间
	private String helpFollowTimeStr; //协催跟进时间
	private java.sql.Timestamp submitTime; //提交时间（电催，协催，落地催）
	private String submitTimeStr; //提交时间（电催，协催，落地催）
	private java.util.Date keepDate;//保留时间
	
	/*落地催所需字段*/
	private String collectionUid;		//当前分配表处理人id
	private String collectionUidName; 	//当前分配表处理人
	private String isCur;				//是否当前  Y N
	private String isHelp;				//是否协催	Y N
	
	private String appId;				//申请id   2015-07-01 zhangguo add
	private String lessSysTime;			//小于系统时间
	private String moreSysTimeUndo;		//大于系统时间未处理
	private String moreSysTimeDone;		//大于系统时间已处理
	private String finshReturn;			//已流转（非电催）
	private String contractId;			//合同表Id
	private String keyValue;			//电催摘要
	private String channelName;			//渠道名称
	private String departName;			//组别名称
	
	
	public String getLessSysTime() {
		return lessSysTime;
	}
	public void setLessSysTime(String lessSysTime) {
		this.lessSysTime = lessSysTime;
	}
	public String getMoreSysTimeUndo() {
		return moreSysTimeUndo;
	}
	public void setMoreSysTimeUndo(String moreSysTimeUndo) {
		this.moreSysTimeUndo = moreSysTimeUndo;
	}
	public String getMoreSysTimeDone() {
		return moreSysTimeDone;
	}
	public void setMoreSysTimeDone(String moreSysTimeDone) {
		this.moreSysTimeDone = moreSysTimeDone;
	}
	public String getFinshReturn() {
		return finshReturn;
	}
	public void setFinshReturn(String finshReturn) {
		this.finshReturn = finshReturn;
	}
	public String getDepartName() {
		return departName;
	}
	public void setDepartName(String departName) {
		this.departName = departName;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	//getter部分
	public int getId () {
		return this.id;
	}
	public String getContractNo () {
		return this.contractNo;
	}
	public String getCustomerId () {
		return this.customerId;
	}
	public String getIdType () {
		return this.idType;
	}
	public String getIdNo () {
		return this.idNo;
	}
	public String getCustomerName () {
		return this.customerName;
	}
	public String getCustomerTel () {
		return this.customerTel;
	}
	public double getContractAmount () {
		return this.contractAmount;
	}
	public String getProduct () {
		return this.product;
	}
	public int getLateAge () {
		return this.lateAge;
	}
	public int getLateDays () {
		return this.lateDays;
	}
	public java.util.Date getRepaymentDate () {
		return this.repaymentDate;
	}
	//getter部分,Date类型的修改获取String的方法
	public String getRepaymentDateStr () {
		return DateUtils.formatDate(this.repaymentDate);
	}
	public String getLoanPlatform () {
		return this.loanPlatform;
	}
	public String getOrgId () {
		return this.orgId;
	}
	public String getIsLate () {
		return this.isLate;
	}
	public String getIsFinish () {
		return this.isFinish;
	}
	public double getPenalty () {
		return this.penalty;
	}
	public double getDefaultFee () {
		return this.defaultFee;
	}
	public double getDelay () {
		return this.delay;
	}
	public double getSreviceFee () {
		return this.sreviceFee;
	}
	public double getPrincipal () {
		return this.principal;
	}
	public double getInterest () {
		return this.interest;
	}
	public double getReduce () {
		return this.reduce;
	}
	public double getMonthAmountAll () {
		return this.monthAmountAll;
	}
	public double getAmountAll () {
		return this.amountAll;
	}
	public int getPeriodAll () {
		return this.periodAll;
	}
	public int getPeriodCur () {
		return this.periodCur;
	}
	public int getPeriodLateHis () {
		return this.periodLateHis;
	}
	public int getPeriodFinish () {
		return this.periodFinish;
	}
	public String getAnswerState () {
		return this.answerState;
	}
	public String getPhoneSummary () {
		return this.phoneSummary;
	}
	public String getState () {
		return this.state;
	}
	public java.sql.Timestamp getFollowTime () {
		return this.followTime;
	}
	//getter部分,Timestamp类型的修改获取String的方法
	public String getFollowTimeStr () {
		return DateUtils.formatTime(this.followTime);
	}
	public java.sql.Timestamp getOrderTime () {
		return this.orderTime;
	}
	//getter部分,Timestamp类型的修改获取String的方法
	public String getOrderTimeStr () {
		return DateUtils.formatTime(this.orderTime);
	}
	public String getDistributionState () {
		return this.distributionState;
	}
	public String getCollectionWayCur () {
		return this.collectionWayCur;
	}
	public String getCollectionUidCur () {
		return this.collectionUidCur;
	}
	public String getApplyState () {
		return this.applyState;
	}
	public String getRemark () {
		return this.remark;
	}
	public String getCreateUid () {
		return this.createUid;
	}
	public java.sql.Timestamp getCreateTime () {
		return this.createTime;
	}
	//getter部分,Timestamp类型的修改获取String的方法
	public String getCreateTimeStr () {
		return DateUtils.formatTime(this.createTime);
	}
	public String getUpdateUid () {
		return this.updateUid;
	}
	public java.sql.Timestamp getUpdateTime () {
		return this.updateTime;
	}
	//getter部分,Timestamp类型的修改获取String的方法
	public String getUpdateTimeStr () {
		return DateUtils.formatTime(this.updateTime);
	}

	//setter部分
	public void setId (int id) {
		this.id=id;
	}
	public void setContractNo (String contractNo) {
		this.contractNo=contractNo;
	}
	public void setCustomerId (String customerId) {
		this.customerId=customerId;
	}
	public void setIdType (String idType) {
		this.idType=idType;
	}
	public void setIdNo (String idNo) {
		this.idNo=idNo;
	}
	public void setCustomerName (String customerName) {
		this.customerName=customerName;
	}
	public void setCustomerTel (String customerTel) {
		this.customerTel=customerTel;
	}
	public void setContractAmount (double contractAmount) {
		this.contractAmount=contractAmount;
	}
	public void setProduct (String product) {
		this.product=product;
	}
	public void setLateAge (int lateAge) {
		this.lateAge=lateAge;
	}
	public void setLateDays (int lateDays) {
		this.lateDays=lateDays;
	}
	public void setRepaymentDate (java.util.Date repaymentDate) {
		this.repaymentDate=repaymentDate;
	}
	public void setRepaymentDateStr (String repaymentDateStr) {
		this.repaymentDateStr=repaymentDateStr;
	}
	public void setLoanPlatform (String loanPlatform) {
		this.loanPlatform=loanPlatform;
	}
	public void setOrgId (String orgId) {
		this.orgId=orgId;
	}
	public void setIsLate (String isLate) {
		this.isLate=isLate;
	}
	public void setIsFinish (String isFinish) {
		this.isFinish=isFinish;
	}
	public void setPenalty (double penalty) {
		this.penalty=penalty;
	}
	public void setDefaultFee (double defaultFee) {
		this.defaultFee=defaultFee;
	}
	public void setDelay (double delay) {
		this.delay=delay;
	}
	public void setSreviceFee (double sreviceFee) {
		this.sreviceFee=sreviceFee;
	}
	public void setPrincipal (double principal) {
		this.principal=principal;
	}
	public void setInterest (double interest) {
		this.interest=interest;
	}
	public void setReduce (double reduce) {
		this.reduce=reduce;
	}
	public void setAmountAll (double amountAll) {
		this.amountAll=amountAll;
	}
	public void setPeriodAll (int periodAll) {
		this.periodAll=periodAll;
	}
	public void setPeriodCur (int periodCur) {
		this.periodCur=periodCur;
	}
	public void setPeriodLateHis (int periodLateHis) {
		this.periodLateHis=periodLateHis;
	}
	public void setPeriodFinish (int periodFinish) {
		this.periodFinish=periodFinish;
	}
	public void setAnswerState (String answerState) {
		this.answerState=answerState;
	}
	public void setPhoneSummary (String phoneSummary) {
		this.phoneSummary=phoneSummary;
	}
	public void setState (String state) {
		this.state=state;
	}
	public void setFollowTime (java.sql.Timestamp followTime) {
		this.followTime=followTime;
	}
	public void setFollowTimeStr (String followTimeStr) {
		this.followTimeStr=followTimeStr;
	}
	public void setOrderTime (java.sql.Timestamp orderTime) {
		this.orderTime=orderTime;
	}
	public void setOrderTimeStr (String orderTimeStr) {
		this.orderTimeStr=orderTimeStr;
	}
	public void setDistributionState (String distributionState) {
		this.distributionState=distributionState;
	}
	public void setCollectionWayCur (String collectionWayCur) {
		this.collectionWayCur=collectionWayCur;
	}
	public void setCollectionUidCur (String collectionUidCur) {
		this.collectionUidCur=collectionUidCur;
	}
	public void setApplyState (String applyState) {
		this.applyState=applyState;
	}
	public void setRemark (String remark) {
		this.remark=remark;
	}
	public void setCreateUid (String createUid) {
		this.createUid=createUid;
	}
	public void setCreateTime (java.sql.Timestamp createTime) {
		this.createTime=createTime;
	}
	public void setCreateTimeStr (String createTimeStr) {
		this.createTimeStr=createTimeStr;
	}
	public void setUpdateUid (String updateUid) {
		this.updateUid=updateUid;
	}
	public void setUpdateTime (java.sql.Timestamp updateTime) {
		this.updateTime=updateTime;
	}
	public void setUpdateTimeStr (String updateTimeStr) {
		this.updateTimeStr=updateTimeStr;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getMinLateDays() {
		return minLateDays;
	}
	public void setMinLateDays(String minLateDays) {
		this.minLateDays = minLateDays;
	}
	public String getMaxLateDays() {
		return maxLateDays;
	}
	public void setMaxLateDays(String maxLateDays) {
		this.maxLateDays = maxLateDays;
	}

	public void setMonthAmountAll (double monthAmountAll) {
		this.monthAmountAll=monthAmountAll;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public String getBankAccountName() {
		return bankAccountName;
	}
	public void setBankAccountName(String bankAccountName) {
		this.bankAccountName = bankAccountName;
	}
	public String getBankAccount() {
		return bankAccount;
	}
	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getHandleState() {
		return handleState;
	}
	public void setHandleState(String handleState) {
		this.handleState = handleState;
	}
	public java.sql.Timestamp getHelpFollowTime() {
		return helpFollowTime;
	}
	public void setHelpFollowTime(java.sql.Timestamp helpFollowTime) {
		this.helpFollowTime = helpFollowTime;
	}
	public String getHelpFollowTimeStr() {
		return helpFollowTimeStr;
	}
	public void setHelpFollowTimeStr(String helpFollowTimeStr) {
		this.helpFollowTimeStr = helpFollowTimeStr;
	}
	public java.sql.Timestamp getSubmitTime() {
		return submitTime;
	}
	public void setSubmitTime(java.sql.Timestamp submitTime) {
		this.submitTime = submitTime;
	}
	public String getSubmitTimeStr() {
		return submitTimeStr;
	}
	public void setSubmitTimeStr(String submitTimeStr) {
		this.submitTimeStr = submitTimeStr;
	}
	public java.util.Date getKeepDate() {
		return keepDate;
	}
	public void setKeepDate(java.util.Date keepDate) {
		this.keepDate = keepDate;
	}
	public String getCollectionUid() {
		return collectionUid;
	}
	public void setCollectionUid(String collectionUid) {
		this.collectionUid = collectionUid;
	}
	public String getCollectionUidName() {
		return collectionUidName;
	}
	public void setCollectionUidName(String collectionUidName) {
		this.collectionUidName = collectionUidName;
	}
	public String getIsCur() {
		return isCur;
	}
	public void setIsCur(String isCur) {
		this.isCur = isCur;
	}
	public String getIsHelp() {
		return isHelp;
	}
	public void setIsHelp(String isHelp) {
		this.isHelp = isHelp;
	}
	public String getContractId() {
		return contractId;
	}
	public void setContractId(String contractId) {
		this.contractId = contractId;
	}
	public String getKeyValue() {
		return keyValue;
	}
	public void setKeyValue(String keyValue) {
		this.keyValue = keyValue;
	}
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
}


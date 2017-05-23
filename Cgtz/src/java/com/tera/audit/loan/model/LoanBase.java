package com.tera.audit.loan.model;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.tera.util.DateUtils;

/**
 * 
 * T_LOAN_BASE实体类
 * <b>功能：</b>LoanBaseDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-01-05 10:52:31<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public class LoanBase {

	//属性部分
	private int id; //ID
	private String loanId; //申请ID
	private String loanWay; //申请渠道
	private String salesman; //业务员
	private String loanType; //申请类型
	private String org; //所属机构
	private String name; //姓名、名称
	private String idType; //证件类型
	private String idNo; //证件号码
	private String product; //产品
	private double loanAmt; //借款金额
	private java.util.Date endDate; //到期日期
	private String endDateStr; //到期日期
	private String loanUse; //借款用途
	private String acctPrvn; // 开户行省
	private String acctCity; // 开户行市
	private String acctCtry; // 开户行区县
	private String acctBank; //开户行名称
	private String acctName; //开户名
	private String acctBranch; //所属分行
	private String acctCode; //账号
	private String retWay; //还款方式
	private double rate; //年化利率
	private double deRate; // 等额利率
	private int inteDays; //计息天数
	private double memFee; //会员服务费
	private double otherFee; //其他费用
	private double lawFee; //法律服务费
	private double magin; // 保证金
	private double tranFee; //转贷费
	
	private double mgrFee; // 管理服务费
	private double certFee; // 他项权证费
	private double evalFee; // 评估费
	private double agentFee; // 中介费
	
	private String retLoanSol; //周期方案 01月付 02季付03首付04末付
	private String isTgth; //是否有共同借款人
	private String isRenew; //是否续贷 0否 1是 2有续贷申请
	private int renewNum; //续贷次数
	private String origLoanId; //续贷原申请ID
	private java.sql.Timestamp inputTime; //录入时间
	private String inputTimeStr; //录入时间
	private String isEnough; //抵押物是否足额
	private java.sql.Timestamp branchAuditTime; //提交分公司审核时间
	private String branchAuditTimeStr; //提交分公司审核时间
	private java.sql.Timestamp riskFirstTime; //提交风控初审时间
	private String riskFirstTimeStr; //提交风控初审时间
	private java.sql.Timestamp riskCheckTime; //提交风控复核时间
	private String riskCheckTimeStr; //提交风控复核时间
	private java.sql.Timestamp meetFirstTime; //提交评审会初审时间
	private String meetFirstTimeStr; //提交评审会初审时间
	private java.sql.Timestamp meetCheckTime; //提交评审会复核时间
	private String meetCheckTimeStr; //提交评审会复核时间
	private java.sql.Timestamp lawTime; //提交法务时间
	private String lawTimeStr; //提交法务时间
	private java.sql.Timestamp cashTime; //提交出纳时间
	private String cashTimeStr; //提交出纳时间
	private java.sql.Timestamp acctTime; //提交财务时间
	private String acctTimeStr; //提交财务时间
	private java.sql.Timestamp loanTime; //放款时间
	private String loanTimeStr; //放款时间
	private String isBeyond; // 是否超出审批额度(1是0否)
	private String state1st; //一级状态
	private String state2nd; //二级状态
	private String updateUid; //
	private java.sql.Timestamp updateTime; //
	private String updateTimeStr; //
	private String ext1; // 续贷说明字段
	private String ext2; // 银行账户类型 1：个人   2：对公
	
	private String contractId; // 合同号
	
	/*
	 * 以下为页面显示字段
	 */
	private String productName;// 产品名称
	private String orgName; // 机构名称
	private String appState; // 申请状态
	private String getLoanWay; // 融资方式
	
	/*
	 * 以下为查询条件字段
	 */
	private String orgId;
	private String salesmanName; // 业务员名字
	
	private String inputTimeMin; // 进件时间
	private String inputTimeMax; // 进件时间
	
	private String branchAuditTimeMin; // 提交分公司审核时间
	private String branchAuditTimeMax; // 提交分公司审核时间 
	
	private String riskAuditTimeMin; // 提交风控初审时间
	private String riskAuditTimeMax; // 提交风控初审时间

	private String riskCheckTimeMin; // 提交风控总监时间
	private String riskCheckTimeMax; // 提交风控总监时间
	
	private String meetFirstTimeMin; // 提交评审会初审时间
	private String meetFirstTimeMax; // 提交评审会初审时间
	
	private String meetCheckTimeMin; // 提交评审会复核时间
	private String meetCheckTimeMax; // 提交评审会复核时间
	
	private String lawTimeMin; // 提交法务时间
	private String lawTimeMax; // 提交法务时间
	
	private String cashTimeMin; // 提交出纳时间
	private String cashTimeMax; // 提交出纳时间
	
	private String acctTimeMin; // 提价财务时间
	private String acctTimeMax; // 提价财务时间
	
	//getter部分
	public int getId () {
		return this.id;
	}
	public String getLoanId () {
		return this.loanId;
	}
	public String getLoanWay () {
		return this.loanWay;
	}
	public String getSalesman () {
		return this.salesman;
	}
	public String getLoanType () {
		return this.loanType;
	}
	public String getOrg () {
		return this.org;
	}
	public String getName () {
		return this.name;
	}
	public String getIdType () {
		return this.idType;
	}
	public String getIdNo () {
		return this.idNo;
	}
	public String getProduct () {
		return this.product;
	}
	public double getLoanAmt () {
		return this.loanAmt;
	}
	public java.util.Date getEndDate () {
		return this.endDate;
	}
	//getter部分,Date类型的修改获取String的方法
	public String getEndDateStr () {
		return DateUtils.formatDate(this.endDate);
	}
	public String getLoanUse () {
		return this.loanUse;
	}
	public String getAcctPrvn() {
		return acctPrvn;
	}
	public void setAcctPrvn(String acctPrvn) {
		this.acctPrvn = acctPrvn;
	}
	public String getAcctCity() {
		return acctCity;
	}
	public void setAcctCity(String acctCity) {
		this.acctCity = acctCity;
	}
	public String getAcctCtry() {
		return acctCtry;
	}
	public void setAcctCtry(String acctCtry) {
		this.acctCtry = acctCtry;
	}
	public String getAcctBank () {
		return this.acctBank;
	}
	public String getAcctName () {
		return this.acctName;
	}
	public String getAcctBranch () {
		return this.acctBranch;
	}
	public String getAcctCode () {
		return this.acctCode;
	}
	public String getRetWay () {
		return this.retWay;
	}
	public double getRate () {
		return this.rate;
	}
	public double getDeRate() {
		return deRate;
	}
	public void setDeRate(double deRate) {
		this.deRate = deRate;
	}
	public int getInteDays () {
		return this.inteDays;
	}
	public double getMemFee () {
		return this.memFee;
	}
	public double getOtherFee () {
		return this.otherFee;
	}
	public double getLawFee () {
		return this.lawFee;
	}
	public double getMagin() {
		return magin;
	}
	public double getTranFee () {
		return this.tranFee;
	}
	public double getMgrFee() {
		return mgrFee;
	}
	public void setMgrFee(double mgrFee) {
		this.mgrFee = mgrFee;
	}
	public double getCertFee() {
		return certFee;
	}
	public void setCertFee(double certFee) {
		this.certFee = certFee;
	}
	public double getEvalFee() {
		return evalFee;
	}
	public void setEvalFee(double evalFee) {
		this.evalFee = evalFee;
	}
	public double getAgentFee() {
		return agentFee;
	}
	public void setAgentFee(double agentFee) {
		this.agentFee = agentFee;
	}
	public String getRetLoanSol () {
		return this.retLoanSol;
	}
	public String getIsTgth () {
		return this.isTgth;
	}
	public String getIsRenew () {
		return this.isRenew;
	}
	public int getRenewNum () {
		return this.renewNum;
	}
	public String getOrigLoanId () {
		return this.origLoanId;
	}
	public java.sql.Timestamp getInputTime () {
		return this.inputTime;
	}
	//getter部分,Timestamp类型的修改获取String的方法
	public String getInputTimeStr () {
		return DateUtils.formatTime(this.inputTime);
	}
	public String getIsEnough () {
		return this.isEnough;
	}
	public java.sql.Timestamp getBranchAuditTime () {
		return this.branchAuditTime;
	}
	//getter部分,Timestamp类型的修改获取String的方法
	public String getBranchAuditTimeStr () {
		return DateUtils.formatTime(this.branchAuditTime);
	}
	public java.sql.Timestamp getRiskFirstTime () {
		return this.riskFirstTime;
	}
	//getter部分,Timestamp类型的修改获取String的方法
	public String getRiskFirstTimeStr () {
		return DateUtils.formatTime(this.riskFirstTime);
	}
	public java.sql.Timestamp getRiskCheckTime () {
		return this.riskCheckTime;
	}
	//getter部分,Timestamp类型的修改获取String的方法
	public String getRiskCheckTimeStr () {
		return DateUtils.formatTime(this.riskCheckTime);
	}
	public java.sql.Timestamp getMeetFirstTime () {
		return this.meetFirstTime;
	}
	//getter部分,Timestamp类型的修改获取String的方法
	public String getMeetFirstTimeStr () {
		return DateUtils.formatTime(this.meetFirstTime);
	}
	public java.sql.Timestamp getMeetCheckTime () {
		return this.meetCheckTime;
	}
	//getter部分,Timestamp类型的修改获取String的方法
	public String getMeetCheckTimeStr () {
		return DateUtils.formatTime(this.meetCheckTime);
	}
	public java.sql.Timestamp getLawTime() {
		return lawTime;
	}
	public String getLawTimeStr() {
		return DateUtils.formatTime(this.lawTime);
	}
	public java.sql.Timestamp getCashTime () {
		return this.cashTime;
	}
	//getter部分,Timestamp类型的修改获取String的方法
	public String getCashTimeStr () {
		return DateUtils.formatTime(this.cashTime);
	}
	public java.sql.Timestamp getAcctTime () {
		return this.acctTime;
	}
	//getter部分,Timestamp类型的修改获取String的方法
	public String getAcctTimeStr () {
		return DateUtils.formatTime(this.acctTime);
	}
	public java.sql.Timestamp getLoanTime () {
		return this.loanTime;
	}
	//getter部分,Timestamp类型的修改获取String的方法
	public String getLoanTimeStr () {
		return DateUtils.formatTime(this.loanTime);
	}
	public String getState1st () {
		return this.state1st;
	}
	public String getState2nd () {
		return this.state2nd;
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
	public String getExt1 () {
		return this.ext1;
	}
	public String getExt2 () {
		return this.ext2;
	}
	public String getContractId() {
		return contractId;
	}
	public void setContractId(String contractId) {
		this.contractId = contractId;
	}
	
	//setter部分
	public void setId (int id) {
		this.id=id;
	}
	public void setLoanId (String loanId) {
		this.loanId=loanId;
	}
	public void setLoanWay (String loanWay) {
		this.loanWay=loanWay;
	}
	public void setSalesman (String salesman) {
		this.salesman=salesman;
	}
	public void setLoanType (String loanType) {
		this.loanType=loanType;
	}
	public void setOrg (String org) {
		this.org=org;
	}
	public void setName (String name) {
		this.name=name;
	}
	public void setIdType (String idType) {
		this.idType=idType;
	}
	public void setIdNo (String idNo) {
		this.idNo=idNo;
	}
	public void setProduct (String product) {
		this.product=product;
	}
	public void setLoanAmt (double loanAmt) {
		this.loanAmt=loanAmt;
	}
	public void setEndDate (java.util.Date endDate) {
		this.endDate=endDate;
	}
	public void setEndDateStr (String endDateStr) {
		this.endDateStr=endDateStr;
	}
	public void setLoanUse (String loanUse) {
		this.loanUse=loanUse;
	}
	public void setAcctBank (String acctBank) {
		this.acctBank=acctBank;
	}
	public void setAcctName (String acctName) {
		this.acctName=acctName;
	}
	public void setAcctBranch (String acctBranch) {
		this.acctBranch=acctBranch;
	}
	public void setAcctCode (String acctCode) {
		this.acctCode=acctCode;
	}
	public void setRetWay (String retWay) {
		this.retWay=retWay;
	}
	public void setRate (double rate) {
		this.rate=rate;
	}
	public void setInteDays (int inteDays) {
		this.inteDays=inteDays;
	}
	public void setMemFee (double memFee) {
		this.memFee=memFee;
	}
	public void setOtherFee (double otherFee) {
		this.otherFee=otherFee;
	}
	public void setLawFee (double lawFee) {
		this.lawFee=lawFee;
	}
	public void setMagin(double magin) {
		this.magin = magin;
	}
	public void setTranFee (double tranFee) {
		this.tranFee=tranFee;
	}
	public void setRetLoanSol (String retLoanSol) {
		this.retLoanSol=retLoanSol;
	}
	public void setIsTgth (String isTgth) {
		this.isTgth=isTgth;
	}
	public void setIsRenew (String isRenew) {
		this.isRenew=isRenew;
	}
	public void setRenewNum (int renewNum) {
		this.renewNum=renewNum;
	}
	public void setOrigLoanId (String origLoanId) {
		this.origLoanId=origLoanId;
	}
	public void setInputTime (java.sql.Timestamp inputTime) {
		this.inputTime=inputTime;
	}
	public void setInputTimeStr (String inputTimeStr) {
		this.inputTimeStr=inputTimeStr;
	}
	public void setIsEnough (String isEnough) {
		this.isEnough=isEnough;
	}
	public void setBranchAuditTime (java.sql.Timestamp branchAuditTime) {
		this.branchAuditTime=branchAuditTime;
	}
	public void setBranchAuditTimeStr (String branchAuditTimeStr) {
		this.branchAuditTimeStr=branchAuditTimeStr;
	}
	public void setRiskFirstTime (java.sql.Timestamp riskFirstTime) {
		this.riskFirstTime=riskFirstTime;
	}
	public void setRiskFirstTimeStr (String riskFirstTimeStr) {
		this.riskFirstTimeStr=riskFirstTimeStr;
	}
	public void setRiskCheckTime (java.sql.Timestamp riskCheckTime) {
		this.riskCheckTime=riskCheckTime;
	}
	public void setRiskCheckTimeStr (String riskCheckTimeStr) {
		this.riskCheckTimeStr=riskCheckTimeStr;
	}
	public void setMeetFirstTime (java.sql.Timestamp meetFirstTime) {
		this.meetFirstTime=meetFirstTime;
	}
	public void setMeetFirstTimeStr (String meetFirstTimeStr) {
		this.meetFirstTimeStr=meetFirstTimeStr;
	}
	public void setMeetCheckTime (java.sql.Timestamp meetCheckTime) {
		this.meetCheckTime=meetCheckTime;
	}
	public void setMeetCheckTimeStr (String meetCheckTimeStr) {
		this.meetCheckTimeStr=meetCheckTimeStr;
	}
	public void setLawTime(java.sql.Timestamp lawTime) {
		this.lawTime = lawTime;
	}
	public void setLawTimeStr(String lawTimeStr) {
		this.lawTimeStr = lawTimeStr;
	}
	public void setCashTime (java.sql.Timestamp cashTime) {
		this.cashTime=cashTime;
	}
	public void setCashTimeStr (String cashTimeStr) {
		this.cashTimeStr=cashTimeStr;
	}
	public void setAcctTime (java.sql.Timestamp acctTime) {
		this.acctTime=acctTime;
	}
	public void setAcctTimeStr (String acctTimeStr) {
		this.acctTimeStr=acctTimeStr;
	}
	public void setLoanTime (java.sql.Timestamp loanTime) {
		this.loanTime=loanTime;
	}
	public void setLoanTimeStr (String loanTimeStr) {
		this.loanTimeStr=loanTimeStr;
	}
	public void setState1st (String state1st) {
		this.state1st=state1st;
	}
	public void setState2nd (String state2nd) {
		this.state2nd=state2nd;
	}
	public String getIsBeyond() {
		return isBeyond;
	}
	public void setIsBeyond(String isBeyond) {
		this.isBeyond = isBeyond;
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
	public void setExt1 (String ext1) {
		this.ext1=ext1;
	}
	public void setExt2 (String ext2) {
		this.ext2=ext2;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getGetLoanWay() {
		return getLoanWay;
	}
	public void setGetLoanWay(String getLoanWay) {
		this.getLoanWay = getLoanWay;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getSalesmanName() {
		return salesmanName;
	}
	public void setSalesmanName(String salesmanName) {
		this.salesmanName = salesmanName;
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
	public String getBranchAuditTimeMin() {
		return branchAuditTimeMin;
	}
	public void setBranchAuditTimeMin(String branchAuditTimeMin) {
		this.branchAuditTimeMin = branchAuditTimeMin;
	}
	public String getBranchAuditTimeMax() {
		return branchAuditTimeMax;
	}
	public void setBranchAuditTimeMax(String branchAuditTimeMax) {
		this.branchAuditTimeMax = branchAuditTimeMax;
	}
	public String getRiskAuditTimeMin() {
		return riskAuditTimeMin;
	}
	public void setRiskAuditTimeMin(String riskAuditTimeMin) {
		this.riskAuditTimeMin = riskAuditTimeMin;
	}
	public String getRiskAuditTimeMax() {
		return riskAuditTimeMax;
	}
	public void setRiskAuditTimeMax(String riskAuditTimeMax) {
		this.riskAuditTimeMax = riskAuditTimeMax;
	}
	public String getRiskCheckTimeMin() {
		return riskCheckTimeMin;
	}
	public void setRiskCheckTimeMin(String riskCheckTimeMin) {
		this.riskCheckTimeMin = riskCheckTimeMin;
	}
	public String getRiskCheckTimeMax() {
		return riskCheckTimeMax;
	}
	public void setRiskCheckTimeMax(String riskCheckTimeMax) {
		this.riskCheckTimeMax = riskCheckTimeMax;
	}
	public String getMeetFirstTimeMin() {
		return meetFirstTimeMin;
	}
	public void setMeetFirstTimeMin(String meetFirstTimeMin) {
		this.meetFirstTimeMin = meetFirstTimeMin;
	}
	public String getMeetFirstTimeMax() {
		return meetFirstTimeMax;
	}
	public void setMeetFirstTimeMax(String meetFirstTimeMax) {
		this.meetFirstTimeMax = meetFirstTimeMax;
	}
	public String getMeetCheckTimeMin() {
		return meetCheckTimeMin;
	}
	public void setMeetCheckTimeMin(String meetCheckTimeMin) {
		this.meetCheckTimeMin = meetCheckTimeMin;
	}
	public String getMeetCheckTimeMax() {
		return meetCheckTimeMax;
	}
	public void setMeetCheckTimeMax(String meetCheckTimeMax) {
		this.meetCheckTimeMax = meetCheckTimeMax;
	}
	public String getLawTimeMin() {
		return lawTimeMin;
	}
	public void setLawTimeMin(String lawTimeMin) {
		this.lawTimeMin = lawTimeMin;
	}
	public String getLawTimeMax() {
		return lawTimeMax;
	}
	public void setLawTimeMax(String lawTimeMax) {
		this.lawTimeMax = lawTimeMax;
	}
	public String getCashTimeMin() {
		return cashTimeMin;
	}
	public void setCashTimeMin(String cashTimeMin) {
		this.cashTimeMin = cashTimeMin;
	}
	public String getCashTimeMax() {
		return cashTimeMax;
	}
	public void setCashTimeMax(String cashTimeMax) {
		this.cashTimeMax = cashTimeMax;
	}
	public String getAcctTimeMin() {
		return acctTimeMin;
	}
	public void setAcctTimeMin(String acctTimeMin) {
		this.acctTimeMin = acctTimeMin;
	}
	public String getAcctTimeMax() {
		return acctTimeMax;
	}
	public void setAcctTimeMax(String acctTimeMax) {
		this.acctTimeMax = acctTimeMax;
	}
	public String getAppState() {
		return appState == null ? "" : (String) stateMap.get(appState);
	}
	public void setAppState(String appState) {
		this.appState = appState;
	}
	
	/**申请渠道-pc端*/
	public final static String LOAN_WAY_PC = "01";
	/**申请渠道-app端*/
	public final static String LOAN_WAY_APP = "02";
	
	private static Map<String, Object> stateMap = new ConcurrentHashMap<String,Object>();
	
	static {
		stateMap.put("A1","录入申请");
		stateMap.put("A2","资料退回");
		stateMap.put("B1","分公司审批");
		stateMap.put("B2","资料退回");
		stateMap.put("C1","风控经理审批");
		stateMap.put("C2","资料退回");
		stateMap.put("D1","评审会初审");
		stateMap.put("D2","资料退回");
		stateMap.put("E1","评审会意见");
		stateMap.put("E2","评审会复核");
		stateMap.put("E3","资料退回");
		stateMap.put("F1","风控总监审批");;
		stateMap.put("F2","资料退回");
		stateMap.put("G1","法务初审");
		stateMap.put("G2","法务内勤");
		stateMap.put("G3","法务复核");
		stateMap.put("G4","资料退回");
		stateMap.put("H1","出纳核账");
		stateMap.put("H2","资料退回");
		stateMap.put("I1","财务审批");
		stateMap.put("I2","财务复核");
		stateMap.put("I3","资料退回");
		stateMap.put("J0","出纳放款");
		stateMap.put("K0","放弃");
		stateMap.put("L1","确认放款");
		stateMap.put("N1","推送线上");
		stateMap.put("N2","推送成功");
	}
}


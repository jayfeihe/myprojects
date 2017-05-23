package com.tera.contract.model;

import com.tera.util.DateUtils;

/**
 * 
 * 合同表实体类 <b>功能：</b>ContractDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2015-01-30 13:49:26<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public class Contract {

	// 属性部分
	private int id; // ID
	private String contractNo; // 合同号
	private String contractClass; // 合同种类
	private String loanAppId; // 贷款申请号
	private String loanName; // 借款人姓名
	private String loanIdType; // 借款人证件类型
	private String loanIdNo; // 借款人证件号码
	private String loanProduct; // 借款产品
	private double loanAmount; // 借款金额
	private int loanPeriod; // 借款期限
	private double loanInterestRate; // 借款利率
	private double loanServiceRate; // 借款服务费率
	private double loanServiceRate2; // 借款服务费率2
	private java.util.Date startDate; // 开始日期
	private String startDateStr; // 开始日期
	private java.util.Date endDate; // 结束日期
	private String endDateStr; // 结束日期
	private String lendAppId; // 出借申请号
	private String contractType; // 合同类型
	private String repayMethod; // 还款方式
	private String lendName; // 出借人姓名
	private String lendIdType; // 出借人证件类型
	private String lendIdNo; // 出借人证件号码
	private java.util.Date createDate; // 合同生成日期
	private String createDateStr; // 合同生成日期
	private java.util.Date signDate; // 合同签约日期
	private String signDateStr; // 合同签约日期
	private int ext1; // 抵押物ID
	private String bankProvince; // 开户行省
	private String bankCity; // 开户行市
	private String bankCounty; // 开户行区
	private String bankName; // 开户行名称
	private String bankCode; // 开户行代码
	private String bankBranch; // 分行名称
	private String bankAccountName; // 账户名
	private String bankAccount; // 卡号
	private String bankMobile; // 银行卡 绑定手机
	private java.util.Date lendingDate; // 放款时间
	private String lendingDateStr; // 放款时间
	private java.sql.Timestamp downloadTime; // 下载时间
	private String downloadTimeStr; // 下载时间
	private String signProvince; // 签约省
	private String signCity; // 签约市
	private String signCounty; // 签约区
	private String signAddress; // 签约地址
	private String channelType; // 渠道类型
	private String channelKeyId; // 渠道关联ID
	private String channelState; // 渠道状态 1:准备中 2:准备完毕 3:投标中 4:投标结束 5:还款中 6:还款完成
									// 7:中止 8:结束 （ 鼎轩：1是等待处理 4是通过 7是拒贷 5是放款成功）
	private String channelRemark;//渠道备注
	private java.sql.Timestamp channelTime; // 渠道放款时间
	private String channelTimeStr; // 渠道放款时间
	private String state; // 状态
	private String operator; // 操作员
	private String orgId; // 所属机构
	private java.sql.Timestamp createTime; // 创建日期
	private String createTimeStr; // 创建日期
	private java.sql.Timestamp updateTime; // 修改日期
	private String updateTimeStr; // 修改日期
	// 还款历史需要用到的
	private int notReturnPeriod; // 未还期数
	private int exceedPeriod; // 逾期期数
	private double restPrincipalReceived; // 剩余本息
	private double contractMoney; // 合同金额

	private String noPeriodNum; // 未还期数
	private String defaultPeriodNum; // 违约总期数
	private String nowExceedPeriod;// 当前逾期总期数
	private String monthlyrepaymentStatus;//当月还款状态

	/*--------------功能区拒贷------------------*/
	private String appId; // 申请申请Id
	private String appName; // 申请姓名
	private double appAmount; // 申请借款金额
	private String appChannel; // 申请渠道
	private String appChannelName; // 申请渠道
	private String appProduct; // 申请产品
	private int appPeriod; // 申请期限
	private java.util.Date inputTime; // 进件时间
	private String inputTimeStr; // 进件时间
	private java.util.Date decisionDate; // 决策时间
	private String decisionChannel; // 决策 渠道
	private String decisionChannelName; // 决策 渠道名称
	private String decisionDateStr; // 决策时间
	private double decisionAmount; // 决策到手金额
	private String methodAmount;// 月还款额
	private String contractAmount;// 合同金额
	private String appState;// 申请状态
	private String orgName; // 营业部

	
	private String operateResult; // 推送操作结果 0：放弃，1：推送 
	// getter部分
	public int getId() {
		return this.id;
	}

	public String getContractNo() {
		return this.contractNo;
	}

	public String getContractClass() {
		return this.contractClass;
	}

	public String getLoanAppId() {
		return this.loanAppId;
	}

	public String getLoanName() {
		return this.loanName;
	}

	public String getLoanIdType() {
		return this.loanIdType;
	}

	public String getLoanIdNo() {
		return this.loanIdNo;
	}

	public String getLoanProduct() {
		return this.loanProduct;
	}

	public double getLoanAmount() {
		return this.loanAmount;
	}

	public int getLoanPeriod() {
		return this.loanPeriod;
	}

	public double getLoanInterestRate() {
		return this.loanInterestRate;
	}

	public double getLoanServiceRate() {
		return this.loanServiceRate;
	}

	public double getLoanServiceRate2() {
		return this.loanServiceRate2;
	}

	public java.util.Date getStartDate() {
		return this.startDate;
	}

	// getter部分,Date类型的修改获取String的方法
	public String getStartDateStr() {
		return DateUtils.formatDate(this.startDate);
	}

	public java.util.Date getEndDate() {
		return this.endDate;
	}

	// getter部分,Date类型的修改获取String的方法
	public String getEndDateStr() {
		return DateUtils.formatDate(this.endDate);
	}

	public String getLendAppId() {
		return this.lendAppId;
	}

	public String getContractType() {
		return this.contractType;
	}

	public String getRepayMethod() {
		return this.repayMethod;
	}

	public String getLendName() {
		return this.lendName;
	}

	public String getLendIdType() {
		return this.lendIdType;
	}

	public String getLendIdNo() {
		return this.lendIdNo;
	}

	public java.util.Date getCreateDate() {
		return this.createDate;
	}

	// getter部分,Date类型的修改获取String的方法
	public String getCreateDateStr() {
		return DateUtils.formatDate(this.createDate);
	}

	public java.util.Date getSignDate() {
		return this.signDate;
	}

	// getter部分,Date类型的修改获取String的方法
	public String getSignDateStr() {
		return DateUtils.formatDate(this.signDate);
	}

	public int getExt1() {
		return this.ext1;
	}

	public String getBankProvince() {
		return this.bankProvince;
	}

	public String getBankCity() {
		return this.bankCity;
	}

	public String getBankCounty() {
		return this.bankCounty;
	}

	public String getBankName() {
		return this.bankName;
	}

	public String getBankCode() {
		return this.bankCode;
	}

	public String getBankBranch() {
		return this.bankBranch;
	}

	public String getBankAccountName() {
		return this.bankAccountName;
	}

	public String getBankAccount() {
		return this.bankAccount;
	}

	public String getBankMobile() {
		return this.bankMobile;
	}

	public java.util.Date getLendingDate() {
		return this.lendingDate;
	}

	// getter部分,Date类型的修改获取String的方法
	public String getLendingDateStr() {
		return DateUtils.formatDate(this.lendingDate);
	}

	public java.sql.Timestamp getDownloadTime() {
		return this.downloadTime;
	}

	// getter部分,Timestamp类型的修改获取String的方法
	public String getDownloadTimeStr() {
		return DateUtils.formatTime(this.downloadTime);
	}

	public String getSignProvince() {
		return this.signProvince;
	}

	public String getSignCity() {
		return this.signCity;
	}

	public String getSignCounty() {
		return this.signCounty;
	}

	public String getSignAddress() {
		return this.signAddress;
	}

	public String getChannelType() {
		return this.channelType;
	}

	public String getDecisionChannelName() {
		return decisionChannelName;
	}

	public String getChannelKeyId() {
		return this.channelKeyId;
	}

	public String getChannelState() {
		return this.channelState;
	}

	public String getChannelRemark() {
		return this.channelRemark;
	}
	
	public java.sql.Timestamp getChannelTime() {
		return this.channelTime;
	}

	// getter部分,Timestamp类型的修改获取String的方法
	public String getChannelTimeStr() {
		return DateUtils.formatTime(this.channelTime);
	}

	public String getState() {
		return this.state;
	}

	public String getOperator() {
		return this.operator;
	}

	public String getOrgId() {
		return this.orgId;
	}

	public java.sql.Timestamp getCreateTime() {
		return this.createTime;
	}

	// getter部分,Timestamp类型的修改获取String的方法
	public String getCreateTimeStr() {
		return DateUtils.formatTime(this.createTime);
	}

	public java.sql.Timestamp getUpdateTime() {
		return this.updateTime;
	}

	// getter部分,Timestamp类型的修改获取String的方法
	public String getUpdateTimeStr() {
		return DateUtils.formatTime(this.updateTime);
	}

	// setter部分
	public void setId(int id) {
		this.id = id;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public void setContractClass(String contractClass) {
		this.contractClass = contractClass;
	}

	public void setLoanAppId(String loanAppId) {
		this.loanAppId = loanAppId;
	}

	public void setLoanName(String loanName) {
		this.loanName = loanName;
	}

	public void setLoanIdType(String loanIdType) {
		this.loanIdType = loanIdType;
	}

	public void setLoanIdNo(String loanIdNo) {
		this.loanIdNo = loanIdNo;
	}

	public void setLoanProduct(String loanProduct) {
		this.loanProduct = loanProduct;
	}

	public void setLoanAmount(double loanAmount) {
		this.loanAmount = loanAmount;
	}

	public void setLoanPeriod(int loanPeriod) {
		this.loanPeriod = loanPeriod;
	}

	public void setLoanInterestRate(double loanInterestRate) {
		this.loanInterestRate = loanInterestRate;
	}

	public void setLoanServiceRate(double loanServiceRate) {
		this.loanServiceRate = loanServiceRate;
	}

	public void setLoanServiceRate2(double loanServiceRate2) {
		this.loanServiceRate2 = loanServiceRate2;
	}

	public void setStartDate(java.util.Date startDate) {
		this.startDate = startDate;
	}

	public void setStartDateStr(String startDateStr) {
		this.startDateStr = startDateStr;
	}

	public void setEndDate(java.util.Date endDate) {
		this.endDate = endDate;
	}

	public void setEndDateStr(String endDateStr) {
		this.endDateStr = endDateStr;
	}

	public void setLendAppId(String lendAppId) {
		this.lendAppId = lendAppId;
	}

	public void setContractType(String contractType) {
		this.contractType = contractType;
	}

	public void setRepayMethod(String repayMethod) {
		this.repayMethod = repayMethod;
	}

	public void setLendName(String lendName) {
		this.lendName = lendName;
	}

	public void setLendIdType(String lendIdType) {
		this.lendIdType = lendIdType;
	}

	public void setLendIdNo(String lendIdNo) {
		this.lendIdNo = lendIdNo;
	}

	public void setCreateDate(java.util.Date createDate) {
		this.createDate = createDate;
	}

	public void setCreateDateStr(String createDateStr) {
		this.createDateStr = createDateStr;
	}

	public void setSignDate(java.util.Date signDate) {
		this.signDate = signDate;
	}

	public void setSignDateStr(String signDateStr) {
		this.signDateStr = signDateStr;
	}

	public void setExt1(int ext1) {
		this.ext1 = ext1;
	}

	public void setBankProvince(String bankProvince) {
		this.bankProvince = bankProvince;
	}

	public void setBankCity(String bankCity) {
		this.bankCity = bankCity;
	}

	public void setBankCounty(String bankCounty) {
		this.bankCounty = bankCounty;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public void setBankBranch(String bankBranch) {
		this.bankBranch = bankBranch;
	}

	public void setBankAccountName(String bankAccountName) {
		this.bankAccountName = bankAccountName;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public void setBankMobile(String bankMobile) {
		this.bankMobile = bankMobile;
	}

	public void setLendingDate(java.util.Date lendingDate) {
		this.lendingDate = lendingDate;
	}

	public void setLendingDateStr(String lendingDateStr) {
		this.lendingDateStr = lendingDateStr;
	}

	public void setDownloadTime(java.sql.Timestamp downloadTime) {
		this.downloadTime = downloadTime;
	}

	public void setDownloadTimeStr(String downloadTimeStr) {
		this.downloadTimeStr = downloadTimeStr;
	}

	public void setSignProvince(String signProvince) {
		this.signProvince = signProvince;
	}

	public void setSignCity(String signCity) {
		this.signCity = signCity;
	}

	public void setSignCounty(String signCounty) {
		this.signCounty = signCounty;
	}

	public void setSignAddress(String signAddress) {
		this.signAddress = signAddress;
	}

	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

	public void setDecisionChannelName(String decisionChannelName) {
		this.decisionChannelName = decisionChannelName;
	}

	public void setChannelKeyId(String channelKeyId) {
		this.channelKeyId = channelKeyId;
	}

	public void setChannelState(String channelState) {
		this.channelState = channelState;
	}

	public void setChannelRemark(String channelRemark) {
		this.channelRemark = channelRemark;
	}

	public void setChannelTime(java.sql.Timestamp channelTime) {
		this.channelTime = channelTime;
	}

	public void setChannelTimeStr(String channelTimeStr) {
		this.channelTimeStr = channelTimeStr;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public void setCreateTime(java.sql.Timestamp createTime) {
		this.createTime = createTime;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}

	public void setUpdateTime(java.sql.Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public void setUpdateTimeStr(String updateTimeStr) {
		this.updateTimeStr = updateTimeStr;
	}

	public java.util.Date getInputTime() {
		return inputTime;
	}

	public void setInputTime(java.util.Date inputTime) {
		this.inputTime = inputTime;
	}

	public String getInputTimeStr() {
		return DateUtils.formatTime(this.inputTime);
	}

	public void setInputTimeStr(String inputTimeStr) {
		this.inputTimeStr = inputTimeStr;
	}

	public String getDecisionChannel() {
		return decisionChannel;
	}

	public void setDecisionChannel(String decisionChannel) {
		this.decisionChannel = decisionChannel;
	}

	public java.util.Date getDecisionDate() {
		return decisionDate;
	}

	public void setDecisionDate(java.util.Date decisionDate) {
		this.decisionDate = decisionDate;
	}

	public String getDecisionDateStr() {
		return DateUtils.formatTime(this.decisionDate);
	}

	public void setDecisionDateStr(String decisionDateStr) {
		this.decisionDateStr = decisionDateStr;
	}

	public String getMethodAmount() {
		return methodAmount;
	}

	public void setMethodAmount(String methodAmount) {
		this.methodAmount = methodAmount;
	}

	public String getContractAmount() {
		return contractAmount;
	}

	public void setContractAmount(String contractAmount) {
		this.contractAmount = contractAmount;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public double getAppAmount() {
		return appAmount;
	}

	public void setAppAmount(double appAmount) {
		this.appAmount = appAmount;
	}

	public String getAppChannel() {
		return appChannel;
	}

	public void setAppChannel(String appChannel) {
		this.appChannel = appChannel;
	}

	public String getAppChannelName() {
		return appChannelName;
	}

	public void setAppChannelName(String appChannelName) {
		this.appChannelName = appChannelName;
	}

	public String getAppProduct() {
		return appProduct;
	}

	public void setAppProduct(String appProduct) {
		this.appProduct = appProduct;
	}

	public int getAppPeriod() {
		return appPeriod;
	}

	public void setAppPeriod(int appPeriod) {
		this.appPeriod = appPeriod;
	}

	public double getDecisionAmount() {
		return decisionAmount;
	}

	public void setDecisionAmount(double decisionAmount) {
		this.decisionAmount = decisionAmount;
	}

	public String getAppState() {
		return appState;
	}

	public void setAppState(String appState) {
		this.appState = appState;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public int getNotReturnPeriod() {
		return notReturnPeriod;
	}

	public void setNotReturnPeriod(int notReturnPeriod) {
		this.notReturnPeriod = notReturnPeriod;
	}

	public int getExceedPeriod() {
		return exceedPeriod;
	}

	public void setExceedPeriod(int exceedPeriod) {
		this.exceedPeriod = exceedPeriod;
	}

	public double getRestPrincipalReceived() {
		return restPrincipalReceived;
	}

	public void setRestPrincipalReceived(double restPrincipalReceived) {
		this.restPrincipalReceived = restPrincipalReceived;
	}

	public double getContractMoney() {
		return contractMoney;
	}

	public void setContractMoney(double contractMoney) {
		this.contractMoney = contractMoney;
	}

	public String getNoPeriodNum() {
		return noPeriodNum;
	}

	public void setNoPeriodNum(String noPeriodNum) {
		this.noPeriodNum = noPeriodNum;
	}

	public String getDefaultPeriodNum() {
		return defaultPeriodNum;
	}

	public void setDefaultPeriodNum(String defaultPeriodNum) {
		this.defaultPeriodNum = defaultPeriodNum;
	}

	public String getShowLoanIdNo() {
		if (null == getLoanIdNo())
			return null;
		else
			return getLoanIdNo().substring(0, 6)
					+ "****"
					+ getLoanIdNo().substring(getLoanIdNo().length() - 4,
							getLoanIdNo().length());
	}

	public String getNowExceedPeriod() {
		return nowExceedPeriod;
	}

	public void setNowExceedPeriod(String nowExceedPeriod) {
		this.nowExceedPeriod = nowExceedPeriod;
	}

	public String getMonthlyrepaymentStatus() {
		return monthlyrepaymentStatus;
	}

	public void setMonthlyrepaymentStatus(String monthlyrepaymentStatus) {
		this.monthlyrepaymentStatus = monthlyrepaymentStatus;
	}

	public String getOperateResult() {
		return operateResult;
	}

	public void setOperateResult(String operateResult) {
		this.operateResult = operateResult;
	}
}

package com.tera.house.model;

import com.tera.util.DateUtils;

/**
 * 
 * 信用贷款申请表实体类
 * <b>功能：</b>houseAppDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2015-04-10 16:11:54<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public class HouseApp {

	//属性部分
	private int id; //ID
	private String appId; //申请ID
	private String appCode; //申请编码
	private String customerSource; //客户来源
	private String customerId; //客户ID
	private String belongChannel; //渠道
	private String belongChannelName; //渠道名称
	private String product; //产品
	private int period; //期限
	private double sreviceFeeRate; //服务费率
	private double interestRate; //借款利率
	private double amount; //借款金额
	private String useage1; //借款用途1
	private String useage2; //借款用途2
	private String name; //姓名
	private String mobile; //手机号
	private String idType; //证件类型
	private String idNo; //证件号码
	private String diploma; //学历
	private String wechat; //微信
	private String email; //邮箱
	private int withoutChildren; //有无子女
	private int childrenSize; //子女个数
	private String marriage; //婚姻
	private String kosekiProvince; //户籍-省
	private String kosekiCity; //户籍-市
	private String addNature; //居住性质
	private int addYear; //居住时间
	private String addProvince; //居住地址-省
	private String addCity; //居住地址-市
	private String addCounty; //居住地址-区县
	private String address; //居住地址
	private String phone; //住宅电话
	private String comName; //单位名称
	private String comProvince; //单位地址-省
	private String comCity; //单位地址-市
	private String comCounty; //单位地址-区县
	private String comAddress; //单位地址
	private String comDuty; //单位担任职务
	private String comType; //单位类型
	private String comPhone; //单位电话
	private java.util.Date comAddDate; //入职时间
	private String comAddDateStr; //入职时间
	private double income; //年收入
	private double monthlyPayments; //月还款额
	private String accName; //开户行名称
	private String account; //开户行帐号
	private String spouseName; //配偶姓名
	private String spouseMobile; //配偶手机
	private String spouseIdType; //配偶证件类型
	private String spouseIdNo; //配偶证件号码
	private double spouseIncome; //配偶年收入
	private String spouseJob; //配偶职业
	private String spouseComName; //配偶单位名称
	private String spouseComProvince; //配偶单位地址-省
	private String spouseComCity; //配偶单位地址-市
	private String spouseComCounty; //配偶单位地址-区县
	private String spouseComAddress; //配偶单位地址
	private String firmName; //商号名称
	private String firmMainBusiness; //主营业务
	private double firmIncome; //年营业额
	private double firmShare; //占股比例
	private String firmProvince; //商号地址-省
	private String firmCity; //商号地址-市
	private String firmCounty; //商号地址-区县
	private String firmAddress; //商号地址
	private java.util.Date firmManageDate; //本地经营时间
	private String firmManageDateStr; //本地经营时间
	private String matchType; //撮合类型
	private String staffNo; //营销人员
	private java.sql.Timestamp inputTime; //进件时间
	private String inputTimeStr; //进件时间
	private String customerManager; //客户经理
	private String saleRefuseDescribe; //前端拒贷反馈销售描述
	private String saleRefuseCode1; //前端拒贷码1
	private String saleRefuseCode2; //前端拒贷码2
	private String remark; //申请描述
	private String ruleGrade; //规则评分
	private String ruleScore; //规则等级
	private String gradeRemind;// 评级提醒
	private String state; //状态
	private String operator; //操作员
	private String orgId; //所属机构
	private java.sql.Timestamp createTime; //创建日期
	private String createTimeStr; //创建日期
	private java.sql.Timestamp updateTime; //修改日期
	private String updateTimeStr; //修改日期
	private String auditTime;//审核时间
	
	/*
	 * 新增字段
	 */
	private double assessAmount; //评估金额
	private String assessComp; //评估公司
	private String assessRemark; //评估描述
	
	
	//为read页面添加
	private String sex;//性别
	private int workAge;//工作年限
	////////////////////////
	private String taskState; 		//流程当前节点
	private String taskProcesser; 	//流程待处理人 名字 
	private String taskProcesserId; 	//流程待处理人 登录ID
	private String reviewOperator;  //流程最后复核人

	private double decisionAmount; //最终决策 借款金额
	private String decisionChannel; //最终决策 渠道
	private String decisionChannelName; //最终决策 渠道名称
	private String decisionProduct; //最终决策 产品
	private int decisionPeriod; //最终决策 期限
	private String decisionOperator; //最终决策 人
	private java.sql.Timestamp decisionUpdateTime;//最终决策时间
	private String decisionUpdateTimeStr;//最终决策时间
	private String contractNo; // 合同编号
	
	//--------信用贷款查询----------
	private java.util.Date decisionDate; //决策时间
	private String decisionDateStr; //决策时间
	private String methodAmount;//月还款额
	private String contractAmount;//合同金额
	

	private String staffName;		//营销人员名称
	private String orgName;			//机构名字
	private String verifyName;		//审核人名称
	private String staffTeam;		//营销团队
	private String teamManager;		//团队经理
	
	//--------为审批添加，显示审核信息----------
	private String houseDecisionCode;//结果
	private String houseDecisionOperator;//操作人

	//--------为推送后中海软银显示list----------
	private String signProvince;//签约省
	private String signCity;//签约市
	private String bankName;//开户行
	private String bankAccount;//开户行账号
	private String signDate;//签约时间
	private String lendingDate;//提交时间
	private String loanAmount;//合同金额
	private String monthAmount;//月还款额
	private String channelState;//渠道状态
	
	public String getSignProvince() {
		return signProvince;
	}
	public void setSignProvince(String signProvince) {
		this.signProvince = signProvince;
	}
	public String getSignCity() {
		return signCity;
	}
	public void setSignCity(String signCity) {
		this.signCity = signCity;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBankAccount() {
		return bankAccount;
	}
	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}
	public String getSignDate() {
		return signDate;
	}
	public void setSignDate(String signDate) {
		this.signDate = signDate;
	}
	public String getLendingDate() {
		return lendingDate;
	}
	public void setLendingDate(String lendingDate) {
		this.lendingDate = lendingDate;
	}
	public String getLoanAmount() {
		return loanAmount;
	}
	public void setLoanAmount(String loanAmount) {
		this.loanAmount = loanAmount;
	}
	public String getMonthAmount() {
		return monthAmount;
	}
	public void setMonthAmount(String monthAmount) {
		this.monthAmount = monthAmount;
	}
	public String getChannelState() {
		return channelState;
	}
	public void setChannelState(String channelState) {
		this.channelState = channelState;
	}
	//getter部分
	public int getId () {
		return this.id;
	}
	public String getAppId () {
		return this.appId;
	}
	public String getAppCode () {
		return this.appCode;
	}
	public String getCustomerSource () {
		return this.customerSource;
	}
	public String getCustomerId () {
		return this.customerId;
	}
	public String getBelongChannel () {
		return this.belongChannel;
	}
	public String getBelongChannelName() {
		return belongChannelName;
	}
	public String getProduct () {
		return this.product;
	}
	public int getPeriod () {
		return this.period;
	}
	public double getSreviceFeeRate () {
		return this.sreviceFeeRate;
	}
	public double getInterestRate () {
		return this.interestRate;
	}
	public double getAmount () {
		return this.amount;
	}
	public String getUseage1 () {
		return this.useage1;
	}
	public String getUseage2 () {
		return this.useage2;
	}
	public String getName () {
		return this.name;
	}
	public String getMobile () {
		return this.mobile;
	}
	public String getIdType () {
		return this.idType;
	}
	public String getIdNo () {
		return this.idNo;
	}
	public String getDiploma () {
		return this.diploma;
	}
	public String getWechat () {
		return this.wechat;
	}
	public String getEmail () {
		return this.email;
	}
	public int getWithoutChildren () {
		return this.withoutChildren;
	}
	public int getChildrenSize () {
		return this.childrenSize;
	}
	public String getMarriage () {
		return this.marriage;
	}
	public String getKosekiProvince () {
		return this.kosekiProvince;
	}
	public String getKosekiCity () {
		return this.kosekiCity;
	}
	public String getAddNature () {
		return this.addNature;
	}
	public int getAddYear () {
		return this.addYear;
	}
	public String getAddProvince () {
		return this.addProvince;
	}
	public String getAddCity () {
		return this.addCity;
	}
	public String getAddCounty () {
		return this.addCounty;
	}
	public String getAddress () {
		return this.address;
	}
	public String getPhone () {
		return this.phone;
	}
	public String getComName () {
		return this.comName;
	}
	public String getComProvince () {
		return this.comProvince;
	}
	public String getComCity () {
		return this.comCity;
	}
	public String getComCounty () {
		return this.comCounty;
	}
	public String getComAddress () {
		return this.comAddress;
	}
	public String getComDuty () {
		return this.comDuty;
	}
	public String getComType () {
		return this.comType;
	}
	public String getComPhone () {
		return this.comPhone;
	}
	public java.util.Date getComAddDate () {
		return this.comAddDate;
	}
	//getter部分,Date类型的修改获取String的方法
	public String getComAddDateStr () {
		return DateUtils.formatDate(this.comAddDate);
	}
	public double getIncome () {
		return this.income;
	}
	public double getMonthlyPayments () {
		return this.monthlyPayments;
	}
	public String getAccName () {
		return this.accName;
	}
	public String getAccount () {
		return this.account;
	}
	public String getSpouseName () {
		return this.spouseName;
	}
	public String getSpouseMobile () {
		return this.spouseMobile;
	}
	public String getSpouseIdType () {
		return this.spouseIdType;
	}
	public String getSpouseIdNo () {
		return this.spouseIdNo;
	}
	public double getSpouseIncome () {
		return this.spouseIncome;
	}
	public String getSpouseJob () {
		return this.spouseJob;
	}
	public String getSpouseComName () {
		return this.spouseComName;
	}
	public String getSpouseComProvince () {
		return this.spouseComProvince;
	}
	public String getSpouseComCity () {
		return this.spouseComCity;
	}
	public String getSpouseComCounty () {
		return this.spouseComCounty;
	}
	public String getSpouseComAddress () {
		return this.spouseComAddress;
	}
	public String getFirmName () {
		return this.firmName;
	}
	public String getFirmMainBusiness () {
		return this.firmMainBusiness;
	}
	public double getFirmIncome () {
		return this.firmIncome;
	}
	public double getFirmShare () {
		return this.firmShare;
	}
	public String getFirmProvince () {
		return this.firmProvince;
	}
	public String getFirmCity () {
		return this.firmCity;
	}
	public String getFirmCounty () {
		return this.firmCounty;
	}
	public String getFirmAddress () {
		return this.firmAddress;
	}
	public java.util.Date getFirmManageDate () {
		return this.firmManageDate;
	}
	//getter部分,Date类型的修改获取String的方法
	public String getFirmManageDateStr () {
		return DateUtils.formatDate(this.firmManageDate);
	}
	public String getMatchType () {
		return this.matchType;
	}
	public String getStaffNo () {
		return this.staffNo;
	}
	public java.sql.Timestamp getInputTime () {
		return this.inputTime;
	}
	//getter部分,Timestamp类型的修改获取String的方法
	public String getInputTimeStr () {
		return DateUtils.formatTime(this.inputTime);
	}
	public String getCustomerManager () {
		return this.customerManager;
	}
	public String getSaleRefuseDescribe () {
		return this.saleRefuseDescribe;
	}
	public String getSaleRefuseCode1 () {
		return this.saleRefuseCode1;
	}
	public String getSaleRefuseCode2 () {
		return this.saleRefuseCode2;
	}
	public String getState () {
		return this.state;
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
	public String getRemark () {
		return this.remark;
	}
	public String getRuleGrade () {
		return this.ruleGrade;
	}
	public String getRuleScore () {
		return this.ruleScore;
	}

	public String getGradeRemind() {
		return gradeRemind;
	}
	public void setGradeRemind(String gradeRemind) {
		this.gradeRemind = gradeRemind;
	}
	//setter部分
	public void setId (int id) {
		this.id=id;
	}
	public void setAppId (String appId) {
		this.appId=appId;
	}
	public void setAppCode (String appCode) {
		this.appCode=appCode;
	}
	public void setCustomerSource (String customerSource) {
		this.customerSource=customerSource;
	}
	public void setCustomerId (String customerId) {
		this.customerId=customerId;
	}
	public void setProduct (String product) {
		this.product=product;
	}
	public void setBelongChannel (String belongChannel) {
		this.belongChannel=belongChannel;
	}
	public void setBelongChannelName(String belongChannelName) {
		this.belongChannelName = belongChannelName;
	}
	public void setPeriod (int period) {
		this.period=period;
	}
	public void setSreviceFeeRate (double sreviceFeeRate) {
		this.sreviceFeeRate=sreviceFeeRate;
	}
	public void setInterestRate (double interestRate) {
		this.interestRate=interestRate;
	}
	public void setAmount (double amount) {
		this.amount=amount;
	}
	public void setUseage1 (String useage1) {
		this.useage1=useage1;
	}
	public void setUseage2 (String useage2) {
		this.useage2=useage2;
	}
	public void setName (String name) {
		this.name=name;
	}
	public void setMobile (String mobile) {
		this.mobile=mobile;
	}
	public void setIdType (String idType) {
		this.idType=idType;
	}
	public void setIdNo (String idNo) {
		this.idNo=idNo;
	}
	public void setDiploma (String diploma) {
		this.diploma=diploma;
	}
	public void setWechat (String wechat) {
		this.wechat=wechat;
	}
	public void setEmail (String email) {
		this.email=email;
	}
	public void setWithoutChildren (int withoutChildren) {
		this.withoutChildren=withoutChildren;
	}
	public void setChildrenSize (int childrenSize) {
		this.childrenSize=childrenSize;
	}
	public void setMarriage (String marriage) {
		this.marriage=marriage;
	}
	public void setKosekiProvince (String kosekiProvince) {
		this.kosekiProvince=kosekiProvince;
	}
	public void setKosekiCity (String kosekiCity) {
		this.kosekiCity=kosekiCity;
	}
	public void setAddNature (String addNature) {
		this.addNature=addNature;
	}
	public void setAddYear (int addYear) {
		this.addYear=addYear;
	}
	public void setAddProvince (String addProvince) {
		this.addProvince=addProvince;
	}
	public void setAddCity (String addCity) {
		this.addCity=addCity;
	}
	public void setAddCounty (String addCounty) {
		this.addCounty=addCounty;
	}
	public void setAddress (String address) {
		this.address=address;
	}
	public void setPhone (String phone) {
		this.phone=phone;
	}
	public void setComName (String comName) {
		this.comName=comName;
	}
	public void setComProvince (String comProvince) {
		this.comProvince=comProvince;
	}
	public void setComCity (String comCity) {
		this.comCity=comCity;
	}
	public void setComCounty (String comCounty) {
		this.comCounty=comCounty;
	}
	public void setComAddress (String comAddress) {
		this.comAddress=comAddress;
	}
	public void setComDuty (String comDuty) {
		this.comDuty=comDuty;
	}
	public void setComType (String comType) {
		this.comType=comType;
	}
	public void setComPhone (String comPhone) {
		this.comPhone=comPhone;
	}
	public void setComAddDate (java.util.Date comAddDate) {
		this.comAddDate=comAddDate;
	}
	public void setComAddDateStr (String comAddDateStr) {
		this.comAddDateStr=comAddDateStr;
	}
	public void setIncome (double income) {
		this.income=income;
	}
	public void setMonthlyPayments (double monthlyPayments) {
		this.monthlyPayments=monthlyPayments;
	}
	public void setAccName (String accName) {
		this.accName=accName;
	}
	public void setAccount (String account) {
		this.account=account;
	}
	public void setSpouseName (String spouseName) {
		this.spouseName=spouseName;
	}
	public void setSpouseMobile (String spouseMobile) {
		this.spouseMobile=spouseMobile;
	}
	public void setSpouseIdType (String spouseIdType) {
		this.spouseIdType=spouseIdType;
	}
	public void setSpouseIdNo (String spouseIdNo) {
		this.spouseIdNo=spouseIdNo;
	}
	public void setSpouseIncome (double spouseIncome) {
		this.spouseIncome=spouseIncome;
	}
	public void setSpouseJob (String spouseJob) {
		this.spouseJob=spouseJob;
	}
	public void setSpouseComName (String spouseComName) {
		this.spouseComName=spouseComName;
	}
	public void setSpouseComProvince (String spouseComProvince) {
		this.spouseComProvince=spouseComProvince;
	}
	public void setSpouseComCity (String spouseComCity) {
		this.spouseComCity=spouseComCity;
	}
	public void setSpouseComCounty (String spouseComCounty) {
		this.spouseComCounty=spouseComCounty;
	}
	public void setSpouseComAddress (String spouseComAddress) {
		this.spouseComAddress=spouseComAddress;
	}
	public void setFirmName (String firmName) {
		this.firmName=firmName;
	}
	public void setFirmMainBusiness (String firmMainBusiness) {
		this.firmMainBusiness=firmMainBusiness;
	}
	public void setFirmIncome (double firmIncome) {
		this.firmIncome=firmIncome;
	}
	public void setFirmShare (double firmShare) {
		this.firmShare=firmShare;
	}
	public void setFirmProvince (String firmProvince) {
		this.firmProvince=firmProvince;
	}
	public void setFirmCity (String firmCity) {
		this.firmCity=firmCity;
	}
	public void setFirmCounty (String firmCounty) {
		this.firmCounty=firmCounty;
	}
	public void setFirmAddress (String firmAddress) {
		this.firmAddress=firmAddress;
	}
	public void setFirmManageDate (java.util.Date firmManageDate) {
		this.firmManageDate=firmManageDate;
	}
	public void setFirmManageDateStr (String firmManageDateStr) {
		this.firmManageDateStr=firmManageDateStr;
	}
	public void setMatchType (String matchType) {
		this.matchType=matchType;
	}
	public void setStaffNo (String staffNo) {
		this.staffNo=staffNo;
	}
	public void setInputTime (java.sql.Timestamp inputTime) {
		this.inputTime=inputTime;
	}
	public void setInputTimeStr (String inputTimeStr) {
		this.inputTimeStr=inputTimeStr;
	}
	public void setCustomerManager (String customerManager) {
		this.customerManager=customerManager;
	}
	public void setSaleRefuseDescribe (String saleRefuseDescribe) {
		this.saleRefuseDescribe=saleRefuseDescribe;
	}
	public void setSaleRefuseCode1 (String saleRefuseCode1) {
		this.saleRefuseCode1=saleRefuseCode1;
	}
	public void setSaleRefuseCode2 (String saleRefuseCode2) {
		this.saleRefuseCode2=saleRefuseCode2;
	}
	public void setState (String state) {
		this.state=state;
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
	public void setRemark (String remark) {
		this.remark=remark;
	}
	public void setRuleGrade (String ruleGrade) {
		this.ruleGrade=ruleGrade;
	}
	public void setRuleScore (String ruleScore) {
		this.ruleScore=ruleScore;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public int getWorkAge() {
		return workAge;
	}
	public void setWorkAge(int workAge) {
		this.workAge = workAge;
	}
	public String getTaskState() {
		return taskState;
	}
	public void setTaskState(String taskState) {
		this.taskState = taskState;
	}
	public String getTaskProcesser() {
		return taskProcesser;
	}
	public void setTaskProcesser(String taskProcesser) {
		this.taskProcesser = taskProcesser;
	}
	public String getReviewOperator() {
		return reviewOperator;
	}
	public void setReviewOperator(String reviewOperator) {
		this.reviewOperator = reviewOperator;
	}
	public double getDecisionAmount() {
		return decisionAmount;
	}
	public void setDecisionAmount(double decisionAmount) {
		this.decisionAmount = decisionAmount;
	}
	public String getDecisionChannel() {
		return decisionChannel;
	}
	public void setDecisionChannel(String decisionChannel) {
		this.decisionChannel = decisionChannel;
	}
	public String getDecisionChannelName() {
		return decisionChannelName;
	}
	public void setDecisionChannelName(String decisionChannelName) {
		this.decisionChannelName = decisionChannelName;
	}
	public String getDecisionProduct() {
		return decisionProduct;
	}
	public void setDecisionProduct(String decisionProduct) {
		this.decisionProduct = decisionProduct;
	}
	public int getDecisionPeriod() {
		return decisionPeriod;
	}
	public void setDecisionPeriod(int decisionPeriod) {
		this.decisionPeriod = decisionPeriod;
	}
	public String getDecisionOperator() {
		return decisionOperator;
	}
	public void setDecisionOperator(String decisionOperator) {
		this.decisionOperator = decisionOperator;
	}
	
	public java.sql.Timestamp getDecisionUpdateTime () {
		return this.decisionUpdateTime;
	}
	public String getDecisionUpdateTimeStr () {
		return DateUtils.formatTime(this.decisionUpdateTime);
	}

	public void setDecisionUpdateTime (java.sql.Timestamp decisionUpdateTime) {
		this.decisionUpdateTime=decisionUpdateTime;
	}
	public void setDecisionUpdateTimeStr (String decisionUpdateTimeStr) {
		this.decisionUpdateTimeStr=decisionUpdateTimeStr;
	}
	
	public String getContractNo() {
		return contractNo;
	}
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
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
	public String getStaffName() {
		return staffName;
	}
	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getVerifyName() {
		return verifyName;
	}
	public void setVerifyName(String verifyName) {
		this.verifyName = verifyName;
	}
	public String getStaffTeam() {
		return staffTeam;
	}
	public void setStaffTeam(String staffTeam) {
		this.staffTeam = staffTeam;
	}
	public String getTeamManager() {
		return teamManager;
	}
	public void setTeamManager(String teamManager) {
		this.teamManager = teamManager;
	}
	public String gethouseDecisionCode() {
		return houseDecisionCode;
	}
	public void sethouseDecisionCode(String houseDecisionCode) {
		this.houseDecisionCode = houseDecisionCode;
	}
	public String gethouseDecisionOperator() {
		return houseDecisionOperator;
	}
	public void sethouseDecisionOperator(String houseDecisionOperator) {
		this.houseDecisionOperator = houseDecisionOperator;
	}
	public String getTaskProcesserId() {
		return taskProcesserId;
	}
	public void setTaskProcesserId(String taskProcesserId) {
		this.taskProcesserId = taskProcesserId;
	}
	public String getAuditTime() {
		return auditTime;
	}
	public void setAuditTime(String auditTime) {
		this.auditTime = auditTime;
	}
	public double getAssessAmount() {
		return assessAmount;
	}
	public void setAssessAmount(double assessAmount) {
		this.assessAmount = assessAmount;
	}
	public String getAssessComp() {
		return assessComp;
	}
	public void setAssessComp(String assessComp) {
		this.assessComp = assessComp;
	}
	public String getAssessRemark() {
		return assessRemark;
	}
	public void setAssessRemark(String assessRemark) {
		this.assessRemark = assessRemark;
	}
}


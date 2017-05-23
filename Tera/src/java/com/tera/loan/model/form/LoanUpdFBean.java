package com.tera.loan.model.form;

/**
 * see:借款申请人员机构信息页面回显
 * @data 2014-6-16->下午05:44:45
 * @throws Exception
 */
public class LoanUpdFBean {
	private int id; //ID
	private String appId; //申请ID
	private String customerNo; //客户ID
	private String sales ;//营销人员
	private String mainFlag; //主借款人标识
	private String type; //类型-抵押/信用
	private int consultId; //咨询ID
	private String customerType; //类型-个人/机构
	private String name; //姓名/机构全称
	private String mobile; //手机号
	private String phone; //电话
	private String idType; //证件类型
	private String idNo; //证件号码
	private String marriage; //婚姻
	private String addProvince; //通讯地址-省
	private String addCity; //通讯地址-市
	private String addCounty; //通讯地址-区县
	private String address; //通讯地址
	private String industry1; //行业代码1
	private String industry2; //行业代码2
	private String industry3; //行业代码3
	private double amount; //借款金额
	private String useage; //用途
	private String detailUseage; //详细用途
	private String product; //产品
	private int period; //期限
	private double sreviceFeeRate; //服务费率
	private double interestRate; //借款利率
	private String lendAccName; //收款账户名
	private String lendAccBank; //收款开户银行
	private String lendAccount; //收款银行账号
	private String repayAccName; //还款账户名
	private String repayAccBank; //还款开户银行
	private String repayAccount; //还款银行账号
	private java.util.Date regDate; //注册时间
	private String regDateStr; //注册时间
	private double regAmount; //注册资本
	private double acctualAmount; //实缴资本
	private String orgCodeNo; //组织机构代码证
	private java.util.Date orgCodeExpiryDate; //组织机构代码证有效期
	private String orgCodeExpiryDateStr; //组织机构代码证有效期
	private String taxRegNo; //税务登记证
	private String baiscAccountBank; //基本账户开户银行
	private String baiscAccount; //基本账户账号
	private String bizzScope; //经营范围
	private String companyType; //经营实体属性
	private String mainProduct; //主要销售产品
	private double lastYearTurnover; //上一年度营业额
	private double last3mTurnover; //近三个月营业额
	private String cooperateBankCompany; //以往主要合作银行或公司
	private String dailyClearBank; //日常结算银行
	private double loanBalance; //贷款融资余额
	private String financeCompany; //融资公司名称
	private String financeBank; //融资银行名称
	private double nearly3mEleBill1; //近三个月电费单1月
	private double nearly3mEleBill2; //近三个月电费单2月
	private double nearly3mEleBill3; //近三个月电费单3月
	private String matchType; //撮合类型
	private String orgId; //所属机构
	private String appType;
	private String appChannel;
	
	
	
	
	private int collateralId; //ID
	private String collateralAppId; //申请ID
	private String collateralType; //抵押物类型
	private String collateralCertificate1; //房产证号/车辆号牌
	private String collateralCertificate2; //土地证号/登记证号
	private double collateralAppraisalAmount; //评估总价
	private double collateralHouseSize; //面积
	private String collateralHouseToward; //朝向
	private double collateralUseYears; //使用年限
	private String collateralAppraisalCompany; //评估公司
	private String collateralAddProvice; //省
	private String collateralAddCity; //市
	private String collateralAddCounty; //区县
	private String collateralAddress; //地址
	private String collateralPostcode; //邮编
	private String collateralHoldingCompany; //股权公司全称
	private String collateralHoldingType; //股权类型
	private String collateralIdType; //股权证件类型
	private String collateralIdNo; //股权证件编号
	private String collateralOrgId; //所属机构
	//人员配偶信息
	private int contactId;
	private String contactAppId; //申请ID
	private String contactCustomerNo; //客户ID
	private String contactMainFlag; //主借款人标识
	private String contactType; //联系人类型-个人/单位
	private int contactCollateralId; //抵押物序号
	private String contactName; //姓名
	private String contactGender; //性别
	private String contactMarriage; //婚姻
	private String contactIdType; //证件类型
	private String contactIdNo; //证件号码
	private java.util.Date contactIdIssueDate; //签发日期
	private String contactIdIssueDateStr; //签发日期
	private java.util.Date contactIdExpiryDate; //有效期
	private String contactIdExpiryDateStr; //有效期
	private String contactIdIssueGov; //签发机关
	private String contactMobile; //移动电话
	private String contactPhone; //固定电话
	private String contactEmail; //email
	private String contactReason; //关系
	private String contactAddProvice; //省
	private String contactAddCity; //市
	private String contactAddCounty; //区县
	private String contactAddress; //地址
	private String contactPostcode; //邮编
	private String contactName2; //姓名2
	private String contactGender2; //性别2
	private String contactIdType2; //证件类型2
	private String contactIdNo2; //证件号码2
	private java.util.Date contactIdIssueDate2; //签发日期2
	private String contactIdIssueDate2Str; //签发日期2
	private String contactPhone2; //固定电话2
	private String contactOrgId; //所属机构
	
	
	
	//属性部分
	private String approvalAppId; //申请ID
	private String approvalSelfCreditMark; //借款人信用正常标志
	private String approvalSelfCreditCardDvalue; //借款人贷记卡违约值
	private double approvalSelfCreditCardDamount; //借款人贷记卡金额
	private String approvalSelfCreditCardRemark; //借款人贷记卡备注
	private String approvalSelfLoanDvalue; //借款人贷款违约值
	private double approvalSelfLoanDamount; //借款人贷款金额
	private String approvalSelfLoanRemark; //借款人贷款备注
	private String approvalSpouseCreditMark; //配偶信用正常标志
	private String approvalSpouseCreditCardDvalue; //配偶贷记卡违约值
	private double approvalSpouseCreditCardDamount; //配偶贷记卡金额
	private String approvalSpouseCreditCardRemark; //配偶贷记卡备注
	private String approvalSpouseLoanDvalue; //配偶贷款违约值
	private double approvalSpouseLoanDamount; //配偶贷款金额
	private String approvalSpouseLoanRemark; //配偶贷款备注
	private String approvalEntLoanMark; //企业信用正常标志
	private String approvalEntLoanDvalue; //企业贷款违约值
	private double approvalEntLoanDamount; //企业贷款余额
	private java.util.Date approvalEntLoanExpiryDate; //企业贷款到期日
	private String approvalEntLoanExpiryDateStr; //企业贷款到期日
	private String approvalEntLoanRemark; //企业贷款备注
	private String approvalCourtCompany; //法院执行-公司
	private String approvalCourtCompanyRemark; //法院执行-公司备注
	private String approvalCourtLegal; //法院执行-公司法人
	private String approvalCourtLegalRemark; //法院执行-公司法人备注
	private String approvalCourtGuarantee; //法院执行-抵押担保人
	private String approvalCourtGuaranteeRemark; //法院执行-抵押担保人备注
	private String approvalIndustryNetwork; //工商网
	private String approvalIndustryNetworkRemark; //工商网备注
	private String approvalOrgNetwork; //组织结构网
	private String approvalOrgNetworkRemark; //组织结构网备注
	private String approvalNetwork114; //114查询备注
	private String approvalNetwork114Remark; //网查备注
	private String approvalTelLegal; //电话调查-法人
	private String approvalTelLegalRemark; //电话调查-法人备注
	private String approvalTelAgent; //电话调查-代理人
	private String approvalTelAgentRemark; //电话调查-代理人备注
	private String approvalTelCfo; //电话调查-财务主管
	private String approvalTelCfoRemark; //电话调查-财务主管备注
	private String approvalTelContract; //电话调查-购销合同
	private String approvalTelContractRemark; //电话调查-购销合同备注
	private double approvalApprovalAmount; //审批金额
	private int approvalApprovalPeriod; //审批期限
	private double approvalApprovalServiceRate; //审批服务费率
	private String approvalThirdPartyContract; //需要第三方购销合同
	private String approvalGuaranteeCondition; //担保条件
	private String approvalMatchType; //撮合类型
	private String approvalOrgId; //所属机构
	
	
	
	private int cunstomerId;//ID
	private String cunstomerCustomerNo;//客户编号
	private String cunstomerName;//姓名/机构全称
	private String cunstomerShortName;//简称
	private String cunstomerCustomerType;//客户类型
	private String cunstomerCustomerLever;//客户等级
	private String cunstomerEngName;//英文名称
	private String cunstomerGender;//性别
	private java.util.Date cunstomerBirthday;//生日
	private String cunstomerBirthdayStr;//生日
	private String cunstomerNationality;//国籍
	private String cunstomerLanguage;//语言
	private String cunstomerMotherFiratName;//母亲姓氏
	private String cunstomerMarriage;//婚姻
	private String cunstomerIdType;//证件类型
	private String cunstomerIdNo;//证件号码
	private java.util.Date cunstomerIdIssueDate;//签发日期
	private String cunstomerIdIssueDateStr;//签发日期
	private java.util.Date cunstomerIdExpiryDate;//失效日期
	private String cunstomerIdExpiryDateStr;//失效日期
	private String cunstomerIdIssueGov;//签发机关
	private String cunstomerEducation;//学历
	private String cunstomerJob;//职业
	private String cunstomerIndustry1;//行业代码1
	private String cunstomerIndustry2;//行业代码2
	private String cunstomerIndustry3;//行业代码3
	private String cunstomerCompanyName;//单位名称
	private int cunstomerWorkYears;//工作年限
	private String cunstomerCompanyScale;//单位规模
	private String cunstomerJobDuty;//职务
	private String cunstomerPhone;//固定电话
	private String cunstomerMobile;//移动电话
	private String cunstomerEmail;//EMAIL
	private String cunstomerAddProvince;//通讯地址-省
	private String cunstomerAddCity;//通讯地址-市
	private String cunstomerAddCounty;//通讯地址-区县
	private String cunstomerAddress;//通讯地址
	private String cunstomerPostcode;//邮编
	private String cunstomerFamily;//家庭情况
	private String cunstomerFamilyIncome;//家庭收入
	private String cunstomerFileReceive;//文件接收方式
	private String cunstomerRequirements;//资源需求
	private String cunstomerBizzScope;//经营范围
	private String cunstomerRegProvince;//注册地址-省
	private String cunstomerRegCity;//注册地址-市
	private String cunstomerRegCounty;//注册地址-区县
	private String cunstomerRegAddress;//注册地址
	private String cunstomerCompanyType;//企业性质
	private String cunstomerTrustAssets;//信托资产
	private String cunstomerTrustSettlor;//信托委托人
	private String cunstomerTrustSettlorPhone;//信托委托人电话
	private String cunstomerTrustBenefit;//信托受益人
	private String cunstomerTrustBenefitPhone;//信托受益人电话
	private String cunstomerCustomerManager;//客户经理
	private String cunstomerOrgId;//所属机构
	private String cunstomerAppTimeStr;//提交日期
	private String cunstomerState;//状态
	
	
	// j机构
	private int orgLoanAppId;
	private String orgName; 
	private java.util.Date orgRegDate; //注册时间
	private String  orgRegDateStr;
	private double orgRegAmount; //注册资本
	private String orgIdType; 
	private String orgIdNo;
	private double orgAcctualAmount;
	// -------人员信息
	private int fdOrgId;
	private String fdOrgName;
	private String fdOrgMobile;
	private String fdorgIdType;
	private String fdOrgIdNo;
	private String fdOrgOrgId;
	private String fdOrgMainFlag;
	private String fdOrgUpdateTime;
	private String fdOrgOperator;
	//授权人员信息
	private int sqOrgId;
	private String sqOrgName;
	private String sqOrgMobile;
	private String sqOrgIdType;
	private String sqOrgIdNo;
	private String sqOrgOrgId;
	private String sqOrgMainFlag;
	private String sqOrgUpdateTime;
	private String sqOrgOperator;
	//财务人员信息
	private int cwOrgId;
	private String cwOrgName;
	private String cwOrgMobile;
	private String cwOrgIdType;
	private String cwOrgIdNo;
	private String cwOrgOrgId;
	private String cwOrgMainFlag;
	private String cwOrgUpdateTime;
	private String cwOrgOperator;
	
	
	// -----END
	private String orgOrgCodeNo;
	private java.util.Date orgOrgCodeExpiryDate; //组织机构代码证有效期
	private String orgOrgCodeExpiryDateStr;
	private String orgTaxRegNo;
	private String orgBaiscAccountBank;
	private String orgBaiscAccount;
	private String orgUseage;
	private String orgIndustry1;
	private String orgIndustry2;
	private String orgIndustry3;
	private String orgBizzScope;
	private String orgCompanyType;
	private String orgMainProduct;
	private double orgLastYearTurnover;
	private double orgLast3mTurnover;
	private String orgCooperateBankCompany;
	private String orgDailyClearBank;
	private double orgLoanBalance; //贷款融资余额
	private String orgFinanceCompany; //融资公司名称
	private String orgFinanceBank; //融资银行名称
	private double orgNearly3mEleBill1; //近三个月电费单1月
	private double orgNearly3mEleBill2; //近三个月电费单2月
	private double orgNearly3mEleBill3; //近三个月电费单3月
	private String orgLendAccName; //收款账户名
	private String orgLendAccBank; //收款开户银行
	private String orgLendAccount; //收款银行账号
	
	
	
	
	private String customerOrgName;
	private java.util.Date customerOrgRegDate; // 注册时间
	private String customerOrgRegDateStr;
	private double customerOrgRegAmount; // 注册资本
	private String customerOrgIdType;
	private String customerOrgIdNo;
	private String customerOrgAcctualAmount;
	private String customerFdOrgName;
	private String customerFdOrgMobile;
	private String customerFdorgIdType;
	private String customerFdOrgIdNo;
	private String customerSqOrgName;
	private String customerSqOrgMobile;
	private String customerSqOrgIdType;
	private String customerSqOrgIdNo;
	private String customerCwOrgName;
	private String customerCwOrgMobile;
	private String customerCwOrgIdType;
	private String customerCwOrgIdNo;
	private String customerOrgOrgCodeNo;
	private java.util.Date customerOrgOrgCodeExpiryDate; // 组织机构代码证有效期
	private String customerOrgOrgCodeExpiryDateStr;
	private String customerOrgTaxRegNo;
	private String customerOrgBaiscAccountBank;
	private String customerOrgBaiscAccount;
	private String customerOrgUseage;
	private String customerOrgIndustry1;
	private String customerOrgIndustry2;
	private String customerOrgIndustry3;
	private String customerOrgBizzScope;
	private String customerOrgCompanyType;
	private String customerOrgMainProduct;
	private double customerOrgLastYearTurnover;
	private double customerOrgLast3mTurnover;
	private String customerOrgCooperateBankCompany;
	private String customerOrgDailyClearBank;
	private double customerOrgLoanBalance; // 贷款融资余额
	private String customerOrgFinanceCompany; // 融资公司名称
	private String customerOrgFinanceBank; // 融资银行名称
	private double customerOrgNearly3mEleBill1; // 近三个月电费单1月
	private double customerOrgNearly3mEleBill2; // 近三个月电费单2月
	private double customerOrgNearly3mEleBill3; // 近三个月电费单3月
	private String customerOrgLendAccName; // 收款账户名
	private String customerOrgLendAccBank; // 收款开户银行
	private String customerOrgLendAccount; // 收款银行账号

	
	


	
	public String getSales() {
		return sales;
	}
	public void setSales(String sales) {
		this.sales = sales;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getCustomerNo() {
		return customerNo;
	}
	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}
	public String getMainFlag() {
		return mainFlag;
	}
	public void setMainFlag(String mainFlag) {
		this.mainFlag = mainFlag;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getConsultId() {
		return consultId;
	}
	public void setConsultId(int consultId) {
		this.consultId = consultId;
	}
	public String getCustomerType() {
		return customerType;
	}
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getIdType() {
		return idType;
	}
	public void setIdType(String idType) {
		this.idType = idType;
	}
	public String getIdNo() {
		return idNo;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	public String getMarriage() {
		return marriage;
	}
	public void setMarriage(String marriage) {
		this.marriage = marriage;
	}
	public String getAddProvince() {
		return addProvince;
	}
	public void setAddProvince(String addProvince) {
		this.addProvince = addProvince;
	}
	public String getAddCity() {
		return addCity;
	}
	public void setAddCity(String addCity) {
		this.addCity = addCity;
	}
	public String getAddCounty() {
		return addCounty;
	}
	public void setAddCounty(String addCounty) {
		this.addCounty = addCounty;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getIndustry1() {
		return industry1;
	}
	public void setIndustry1(String industry1) {
		this.industry1 = industry1;
	}
	public String getIndustry2() {
		return industry2;
	}
	public void setIndustry2(String industry2) {
		this.industry2 = industry2;
	}
	public String getIndustry3() {
		return industry3;
	}
	public void setIndustry3(String industry3) {
		this.industry3 = industry3;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getUseage() {
		return useage;
	}
	public void setUseage(String useage) {
		this.useage = useage;
	}
	public String getDetailUseage() {
		return detailUseage;
	}
	public void setDetailUseage(String detailUseage) {
		this.detailUseage = detailUseage;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}

	public int getPeriod() {
		return period;
	}
	public void setPeriod(int period) {
		this.period = period;
	}
	public double getSreviceFeeRate() {
		return sreviceFeeRate;
	}
	public void setSreviceFeeRate(double sreviceFeeRate) {
		this.sreviceFeeRate = sreviceFeeRate;
	}
	public double getInterestRate() {
		return interestRate;
	}
	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}
	public String getLendAccName() {
		return lendAccName;
	}
	public void setLendAccName(String lendAccName) {
		this.lendAccName = lendAccName;
	}
	public String getLendAccBank() {
		return lendAccBank;
	}
	public void setLendAccBank(String lendAccBank) {
		this.lendAccBank = lendAccBank;
	}
	public String getLendAccount() {
		return lendAccount;
	}
	public void setLendAccount(String lendAccount) {
		this.lendAccount = lendAccount;
	}
	public String getRepayAccName() {
		return repayAccName;
	}
	public void setRepayAccName(String repayAccName) {
		this.repayAccName = repayAccName;
	}
	public String getRepayAccBank() {
		return repayAccBank;
	}
	public void setRepayAccBank(String repayAccBank) {
		this.repayAccBank = repayAccBank;
	}
	public String getRepayAccount() {
		return repayAccount;
	}
	public void setRepayAccount(String repayAccount) {
		this.repayAccount = repayAccount;
	}
	public java.util.Date getRegDate() {
		return regDate;
	}
	public void setRegDate(java.util.Date regDate) {
		this.regDate = regDate;
	}
	public void setRegDateStr(String regDateStr) {
		this.regDateStr = regDateStr;
	}
	public double getRegAmount() {
		return regAmount;
	}
	public void setRegAmount(double regAmount) {
		this.regAmount = regAmount;
	}
	public double getAcctualAmount() {
		return acctualAmount;
	}
	public void setAcctualAmount(double acctualAmount) {
		this.acctualAmount = acctualAmount;
	}
	public String getOrgCodeNo() {
		return orgCodeNo;
	}
	public void setOrgCodeNo(String orgCodeNo) {
		this.orgCodeNo = orgCodeNo;
	}
	public java.util.Date getOrgCodeExpiryDate() {
		return orgCodeExpiryDate;
	}
	public void setOrgCodeExpiryDate(java.util.Date orgCodeExpiryDate) {
		this.orgCodeExpiryDate = orgCodeExpiryDate;
	}
	public void setOrgCodeExpiryDateStr(String orgCodeExpiryDateStr) {
		this.orgCodeExpiryDateStr = orgCodeExpiryDateStr;
	}
	public String getTaxRegNo() {
		return taxRegNo;
	}
	public void setTaxRegNo(String taxRegNo) {
		this.taxRegNo = taxRegNo;
	}
	public String getBaiscAccountBank() {
		return baiscAccountBank;
	}
	public void setBaiscAccountBank(String baiscAccountBank) {
		this.baiscAccountBank = baiscAccountBank;
	}
	public String getBaiscAccount() {
		return baiscAccount;
	}
	public void setBaiscAccount(String baiscAccount) {
		this.baiscAccount = baiscAccount;
	}
	public String getBizzScope() {
		return bizzScope;
	}
	public void setBizzScope(String bizzScope) {
		this.bizzScope = bizzScope;
	}
	public String getCompanyType() {
		return companyType;
	}
	public void setCompanyType(String companyType) {
		this.companyType = companyType;
	}
	public String getMainProduct() {
		return mainProduct;
	}
	public void setMainProduct(String mainProduct) {
		this.mainProduct = mainProduct;
	}
	public double getLastYearTurnover() {
		return lastYearTurnover;
	}
	public void setLastYearTurnover(double lastYearTurnover) {
		this.lastYearTurnover = lastYearTurnover;
	}
	public double getLast3mTurnover() {
		return last3mTurnover;
	}
	public void setLast3mTurnover(double last3mTurnover) {
		this.last3mTurnover = last3mTurnover;
	}
	public String getCooperateBankCompany() {
		return cooperateBankCompany;
	}
	public void setCooperateBankCompany(String cooperateBankCompany) {
		this.cooperateBankCompany = cooperateBankCompany;
	}
	public String getDailyClearBank() {
		return dailyClearBank;
	}
	public void setDailyClearBank(String dailyClearBank) {
		this.dailyClearBank = dailyClearBank;
	}
	public double getLoanBalance() {
		return loanBalance;
	}
	public void setLoanBalance(double loanBalance) {
		this.loanBalance = loanBalance;
	}
	public String getFinanceCompany() {
		return financeCompany;
	}
	public void setFinanceCompany(String financeCompany) {
		this.financeCompany = financeCompany;
	}
	public String getFinanceBank() {
		return financeBank;
	}
	public void setFinanceBank(String financeBank) {
		this.financeBank = financeBank;
	}
	public double getNearly3mEleBill1() {
		return nearly3mEleBill1;
	}
	public void setNearly3mEleBill1(double nearly3mEleBill1) {
		this.nearly3mEleBill1 = nearly3mEleBill1;
	}
	public double getNearly3mEleBill2() {
		return nearly3mEleBill2;
	}
	public void setNearly3mEleBill2(double nearly3mEleBill2) {
		this.nearly3mEleBill2 = nearly3mEleBill2;
	}
	public double getNearly3mEleBill3() {
		return nearly3mEleBill3;
	}
	public void setNearly3mEleBill3(double nearly3mEleBill3) {
		this.nearly3mEleBill3 = nearly3mEleBill3;
	}
	public String getMatchType() {
		return matchType;
	}
	public void setMatchType(String matchType) {
		this.matchType = matchType;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public int getCollateralId() {
		return collateralId;
	}
	public void setCollateralId(int collateralId) {
		this.collateralId = collateralId;
	}
	public String getCollateralAppId() {
		return collateralAppId;
	}
	public void setCollateralAppId(String collateralAppId) {
		this.collateralAppId = collateralAppId;
	}
	public String getCollateralType() {
		return collateralType;
	}
	public void setCollateralType(String collateralType) {
		this.collateralType = collateralType;
	}
	public String getCollateralCertificate1() {
		return collateralCertificate1;
	}
	public void setCollateralCertificate1(String collateralCertificate1) {
		this.collateralCertificate1 = collateralCertificate1;
	}
	public String getCollateralCertificate2() {
		return collateralCertificate2;
	}
	public void setCollateralCertificate2(String collateralCertificate2) {
		this.collateralCertificate2 = collateralCertificate2;
	}
	public double getCollateralAppraisalAmount() {
		return collateralAppraisalAmount;
	}
	public void setCollateralAppraisalAmount(double collateralAppraisalAmount) {
		this.collateralAppraisalAmount = collateralAppraisalAmount;
	}
	public double getCollateralHouseSize() {
		return collateralHouseSize;
	}
	public void setCollateralHouseSize(double collateralHouseSize) {
		this.collateralHouseSize = collateralHouseSize;
	}
	public String getCollateralHouseToward() {
		return collateralHouseToward;
	}
	public void setCollateralHouseToward(String collateralHouseToward) {
		this.collateralHouseToward = collateralHouseToward;
	}
	public double getCollateralUseYears() {
		return collateralUseYears;
	}
	public void setCollateralUseYears(double collateralUseYears) {
		this.collateralUseYears = collateralUseYears;
	}
	public String getCollateralAppraisalCompany() {
		return collateralAppraisalCompany;
	}
	public void setCollateralAppraisalCompany(String collateralAppraisalCompany) {
		this.collateralAppraisalCompany = collateralAppraisalCompany;
	}
	public String getCollateralAddProvice() {
		return collateralAddProvice;
	}
	public void setCollateralAddProvice(String collateralAddProvice) {
		this.collateralAddProvice = collateralAddProvice;
	}
	public String getCollateralAddCity() {
		return collateralAddCity;
	}
	public void setCollateralAddCity(String collateralAddCity) {
		this.collateralAddCity = collateralAddCity;
	}
	public String getCollateralAddCounty() {
		return collateralAddCounty;
	}
	public void setCollateralAddCounty(String collateralAddCounty) {
		this.collateralAddCounty = collateralAddCounty;
	}
	public String getCollateralAddress() {
		return collateralAddress;
	}
	public void setCollateralAddress(String collateralAddress) {
		this.collateralAddress = collateralAddress;
	}
	public String getCollateralPostcode() {
		return collateralPostcode;
	}
	public void setCollateralPostcode(String collateralPostcode) {
		this.collateralPostcode = collateralPostcode;
	}
	public String getCollateralHoldingCompany() {
		return collateralHoldingCompany;
	}
	public void setCollateralHoldingCompany(String collateralHoldingCompany) {
		this.collateralHoldingCompany = collateralHoldingCompany;
	}
	public String getCollateralHoldingType() {
		return collateralHoldingType;
	}
	public void setCollateralHoldingType(String collateralHoldingType) {
		this.collateralHoldingType = collateralHoldingType;
	}
	public String getCollateralIdType() {
		return collateralIdType;
	}
	public void setCollateralIdType(String collateralIdType) {
		this.collateralIdType = collateralIdType;
	}
	public String getCollateralIdNo() {
		return collateralIdNo;
	}
	public void setCollateralIdNo(String collateralIdNo) {
		this.collateralIdNo = collateralIdNo;
	}
	public String getCollateralOrgId() {
		return collateralOrgId;
	}
	public void setCollateralOrgId(String collateralOrgId) {
		this.collateralOrgId = collateralOrgId;
	}
	public String getContactAppId() {
		return contactAppId;
	}
	public void setContactAppId(String contactAppId) {
		this.contactAppId = contactAppId;
	}
	public String getContactCustomerNo() {
		return contactCustomerNo;
	}
	public void setContactCustomerNo(String contactCustomerNo) {
		this.contactCustomerNo = contactCustomerNo;
	}
	public String getContactMainFlag() {
		return contactMainFlag;
	}
	public void setContactMainFlag(String contactMainFlag) {
		this.contactMainFlag = contactMainFlag;
	}
	public String getContactType() {
		return contactType;
	}
	public void setContactType(String contactType) {
		this.contactType = contactType;
	}
	public int getContactCollateralId() {
		return contactCollateralId;
	}
	public void setContactCollateralId(int contactCollateralId) {
		this.contactCollateralId = contactCollateralId;
	}
	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	public String getContactGender() {
		return contactGender;
	}
	public void setContactGender(String contactGender) {
		this.contactGender = contactGender;
	}
	public String getContactMarriage() {
		return contactMarriage;
	}
	public void setContactMarriage(String contactMarriage) {
		this.contactMarriage = contactMarriage;
	}
	public String getContactIdType() {
		return contactIdType;
	}
	public void setContactIdType(String contactIdType) {
		this.contactIdType = contactIdType;
	}
	public String getContactIdNo() {
		return contactIdNo;
	}
	public void setContactIdNo(String contactIdNo) {
		this.contactIdNo = contactIdNo;
	}
	public java.util.Date getContactIdIssueDate() {
		return contactIdIssueDate;
	}
	public void setContactIdIssueDate(java.util.Date contactIdIssueDate) {
		this.contactIdIssueDate = contactIdIssueDate;
	}
	public void setContactIdIssueDateStr(String contactIdIssueDateStr) {
		this.contactIdIssueDateStr = contactIdIssueDateStr;
	}
	public java.util.Date getContactIdExpiryDate() {
		return contactIdExpiryDate;
	}
	public void setContactIdExpiryDate(java.util.Date contactIdExpiryDate) {
		this.contactIdExpiryDate = contactIdExpiryDate;
	}
	public void setContactIdExpiryDateStr(String contactIdExpiryDateStr) {
		this.contactIdExpiryDateStr = contactIdExpiryDateStr;
	}
	public String getContactIdIssueGov() {
		return contactIdIssueGov;
	}
	public void setContactIdIssueGov(String contactIdIssueGov) {
		this.contactIdIssueGov = contactIdIssueGov;
	}
	public String getContactMobile() {
		return contactMobile;
	}
	public void setContactMobile(String contactMobile) {
		this.contactMobile = contactMobile;
	}
	public String getContactPhone() {
		return contactPhone;
	}
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
	public String getContactEmail() {
		return contactEmail;
	}
	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}
	public String getContactReason() {
		return contactReason;
	}
	public void setContactReason(String contactReason) {
		this.contactReason = contactReason;
	}
	public String getContactAddProvice() {
		return contactAddProvice;
	}
	public void setContactAddProvice(String contactAddProvice) {
		this.contactAddProvice = contactAddProvice;
	}
	public String getContactAddCity() {
		return contactAddCity;
	}
	public void setContactAddCity(String contactAddCity) {
		this.contactAddCity = contactAddCity;
	}
	public String getContactAddCounty() {
		return contactAddCounty;
	}
	public void setContactAddCounty(String contactAddCounty) {
		this.contactAddCounty = contactAddCounty;
	}
	public String getContactAddress() {
		return contactAddress;
	}
	public void setContactAddress(String contactAddress) {
		this.contactAddress = contactAddress;
	}
	public String getContactPostcode() {
		return contactPostcode;
	}
	public void setContactPostcode(String contactPostcode) {
		this.contactPostcode = contactPostcode;
	}
	public String getContactName2() {
		return contactName2;
	}
	public void setContactName2(String contactName2) {
		this.contactName2 = contactName2;
	}
	public String getContactGender2() {
		return contactGender2;
	}
	public void setContactGender2(String contactGender2) {
		this.contactGender2 = contactGender2;
	}
	public String getContactIdType2() {
		return contactIdType2;
	}
	public void setContactIdType2(String contactIdType2) {
		this.contactIdType2 = contactIdType2;
	}
	public String getContactIdNo2() {
		return contactIdNo2;
	}
	public void setContactIdNo2(String contactIdNo2) {
		this.contactIdNo2 = contactIdNo2;
	}
	public java.util.Date getContactIdIssueDate2() {
		return contactIdIssueDate2;
	}
	public void setContactIdIssueDate2(java.util.Date contactIdIssueDate2) {
		this.contactIdIssueDate2 = contactIdIssueDate2;
	}
	public void setContactIdIssueDate2Str(String contactIdIssueDate2Str) {
		this.contactIdIssueDate2Str = contactIdIssueDate2Str;
	}
	public String getContactPhone2() {
		return contactPhone2;
	}
	public void setContactPhone2(String contactPhone2) {
		this.contactPhone2 = contactPhone2;
	}
	public String getContactOrgId() {
		return contactOrgId;
	}
	public void setContactOrgId(String contactOrgId) {
		this.contactOrgId = contactOrgId;
	}
	public String getApprovalAppId() {
		return approvalAppId;
	}
	public void setApprovalAppId(String approvalAppId) {
		this.approvalAppId = approvalAppId;
	}
	public String getApprovalSelfCreditMark() {
		return approvalSelfCreditMark;
	}
	public void setApprovalSelfCreditMark(String approvalSelfCreditMark) {
		this.approvalSelfCreditMark = approvalSelfCreditMark;
	}
	public String getApprovalSelfCreditCardDvalue() {
		return approvalSelfCreditCardDvalue;
	}
	public void setApprovalSelfCreditCardDvalue(String approvalSelfCreditCardDvalue) {
		this.approvalSelfCreditCardDvalue = approvalSelfCreditCardDvalue;
	}
	public double getApprovalSelfCreditCardDamount() {
		return approvalSelfCreditCardDamount;
	}
	public void setApprovalSelfCreditCardDamount(
			double approvalSelfCreditCardDamount) {
		this.approvalSelfCreditCardDamount = approvalSelfCreditCardDamount;
	}
	public String getApprovalSelfCreditCardRemark() {
		return approvalSelfCreditCardRemark;
	}
	public void setApprovalSelfCreditCardRemark(String approvalSelfCreditCardRemark) {
		this.approvalSelfCreditCardRemark = approvalSelfCreditCardRemark;
	}
	public String getApprovalSelfLoanDvalue() {
		return approvalSelfLoanDvalue;
	}
	public void setApprovalSelfLoanDvalue(String approvalSelfLoanDvalue) {
		this.approvalSelfLoanDvalue = approvalSelfLoanDvalue;
	}
	public double getApprovalSelfLoanDamount() {
		return approvalSelfLoanDamount;
	}
	public void setApprovalSelfLoanDamount(double approvalSelfLoanDamount) {
		this.approvalSelfLoanDamount = approvalSelfLoanDamount;
	}
	public String getApprovalSelfLoanRemark() {
		return approvalSelfLoanRemark;
	}
	public void setApprovalSelfLoanRemark(String approvalSelfLoanRemark) {
		this.approvalSelfLoanRemark = approvalSelfLoanRemark;
	}
	public String getApprovalSpouseCreditMark() {
		return approvalSpouseCreditMark;
	}
	public void setApprovalSpouseCreditMark(String approvalSpouseCreditMark) {
		this.approvalSpouseCreditMark = approvalSpouseCreditMark;
	}
	public String getApprovalSpouseCreditCardDvalue() {
		return approvalSpouseCreditCardDvalue;
	}
	public void setApprovalSpouseCreditCardDvalue(
			String approvalSpouseCreditCardDvalue) {
		this.approvalSpouseCreditCardDvalue = approvalSpouseCreditCardDvalue;
	}
	public double getApprovalSpouseCreditCardDamount() {
		return approvalSpouseCreditCardDamount;
	}
	public void setApprovalSpouseCreditCardDamount(
			double approvalSpouseCreditCardDamount) {
		this.approvalSpouseCreditCardDamount = approvalSpouseCreditCardDamount;
	}
	public String getApprovalSpouseCreditCardRemark() {
		return approvalSpouseCreditCardRemark;
	}
	public void setApprovalSpouseCreditCardRemark(
			String approvalSpouseCreditCardRemark) {
		this.approvalSpouseCreditCardRemark = approvalSpouseCreditCardRemark;
	}
	public String getApprovalSpouseLoanDvalue() {
		return approvalSpouseLoanDvalue;
	}
	public void setApprovalSpouseLoanDvalue(String approvalSpouseLoanDvalue) {
		this.approvalSpouseLoanDvalue = approvalSpouseLoanDvalue;
	}
	public double getApprovalSpouseLoanDamount() {
		return approvalSpouseLoanDamount;
	}
	public void setApprovalSpouseLoanDamount(double approvalSpouseLoanDamount) {
		this.approvalSpouseLoanDamount = approvalSpouseLoanDamount;
	}
	public String getApprovalSpouseLoanRemark() {
		return approvalSpouseLoanRemark;
	}
	public void setApprovalSpouseLoanRemark(String approvalSpouseLoanRemark) {
		this.approvalSpouseLoanRemark = approvalSpouseLoanRemark;
	}
	public String getApprovalEntLoanMark() {
		return approvalEntLoanMark;
	}
	public void setApprovalEntLoanMark(String approvalEntLoanMark) {
		this.approvalEntLoanMark = approvalEntLoanMark;
	}
	public String getApprovalEntLoanDvalue() {
		return approvalEntLoanDvalue;
	}
	public void setApprovalEntLoanDvalue(String approvalEntLoanDvalue) {
		this.approvalEntLoanDvalue = approvalEntLoanDvalue;
	}
	public double getApprovalEntLoanDamount() {
		return approvalEntLoanDamount;
	}
	public void setApprovalEntLoanDamount(double approvalEntLoanDamount) {
		this.approvalEntLoanDamount = approvalEntLoanDamount;
	}
	public java.util.Date getApprovalEntLoanExpiryDate() {
		return approvalEntLoanExpiryDate;
	}
	public void setApprovalEntLoanExpiryDate(
			java.util.Date approvalEntLoanExpiryDate) {
		this.approvalEntLoanExpiryDate = approvalEntLoanExpiryDate;
	}
	public String getApprovalEntLoanExpiryDateStr() {
		return approvalEntLoanExpiryDateStr;
	}
	public void setApprovalEntLoanExpiryDateStr(String approvalEntLoanExpiryDateStr) {
		this.approvalEntLoanExpiryDateStr = approvalEntLoanExpiryDateStr;
	}
	public String getApprovalEntLoanRemark() {
		return approvalEntLoanRemark;
	}
	public void setApprovalEntLoanRemark(String approvalEntLoanRemark) {
		this.approvalEntLoanRemark = approvalEntLoanRemark;
	}
	public String getApprovalCourtCompany() {
		return approvalCourtCompany;
	}
	public void setApprovalCourtCompany(String approvalCourtCompany) {
		this.approvalCourtCompany = approvalCourtCompany;
	}
	public String getApprovalCourtCompanyRemark() {
		return approvalCourtCompanyRemark;
	}
	public void setApprovalCourtCompanyRemark(String approvalCourtCompanyRemark) {
		this.approvalCourtCompanyRemark = approvalCourtCompanyRemark;
	}
	public String getApprovalCourtLegal() {
		return approvalCourtLegal;
	}
	public void setApprovalCourtLegal(String approvalCourtLegal) {
		this.approvalCourtLegal = approvalCourtLegal;
	}
	public String getApprovalCourtLegalRemark() {
		return approvalCourtLegalRemark;
	}
	public void setApprovalCourtLegalRemark(String approvalCourtLegalRemark) {
		this.approvalCourtLegalRemark = approvalCourtLegalRemark;
	}
	public String getApprovalCourtGuarantee() {
		return approvalCourtGuarantee;
	}
	public void setApprovalCourtGuarantee(String approvalCourtGuarantee) {
		this.approvalCourtGuarantee = approvalCourtGuarantee;
	}
	public String getApprovalCourtGuaranteeRemark() {
		return approvalCourtGuaranteeRemark;
	}
	public void setApprovalCourtGuaranteeRemark(String approvalCourtGuaranteeRemark) {
		this.approvalCourtGuaranteeRemark = approvalCourtGuaranteeRemark;
	}
	public String getApprovalIndustryNetwork() {
		return approvalIndustryNetwork;
	}
	public void setApprovalIndustryNetwork(String approvalIndustryNetwork) {
		this.approvalIndustryNetwork = approvalIndustryNetwork;
	}
	public String getApprovalIndustryNetworkRemark() {
		return approvalIndustryNetworkRemark;
	}
	public void setApprovalIndustryNetworkRemark(
			String approvalIndustryNetworkRemark) {
		this.approvalIndustryNetworkRemark = approvalIndustryNetworkRemark;
	}
	public String getApprovalOrgNetwork() {
		return approvalOrgNetwork;
	}
	public void setApprovalOrgNetwork(String approvalOrgNetwork) {
		this.approvalOrgNetwork = approvalOrgNetwork;
	}
	public String getApprovalOrgNetworkRemark() {
		return approvalOrgNetworkRemark;
	}
	public void setApprovalOrgNetworkRemark(String approvalOrgNetworkRemark) {
		this.approvalOrgNetworkRemark = approvalOrgNetworkRemark;
	}
	public String getApprovalNetwork114() {
		return approvalNetwork114;
	}
	public void setApprovalNetwork114(String approvalNetwork114) {
		this.approvalNetwork114 = approvalNetwork114;
	}
	public String getApprovalNetwork114Remark() {
		return approvalNetwork114Remark;
	}
	public void setApprovalNetwork114Remark(String approvalNetwork114Remark) {
		this.approvalNetwork114Remark = approvalNetwork114Remark;
	}
	public String getApprovalTelLegal() {
		return approvalTelLegal;
	}
	public void setApprovalTelLegal(String approvalTelLegal) {
		this.approvalTelLegal = approvalTelLegal;
	}
	public String getApprovalTelLegalRemark() {
		return approvalTelLegalRemark;
	}
	public void setApprovalTelLegalRemark(String approvalTelLegalRemark) {
		this.approvalTelLegalRemark = approvalTelLegalRemark;
	}
	public String getApprovalTelAgent() {
		return approvalTelAgent;
	}
	public void setApprovalTelAgent(String approvalTelAgent) {
		this.approvalTelAgent = approvalTelAgent;
	}
	public String getApprovalTelAgentRemark() {
		return approvalTelAgentRemark;
	}
	public void setApprovalTelAgentRemark(String approvalTelAgentRemark) {
		this.approvalTelAgentRemark = approvalTelAgentRemark;
	}
	public String getApprovalTelCfo() {
		return approvalTelCfo;
	}
	public void setApprovalTelCfo(String approvalTelCfo) {
		this.approvalTelCfo = approvalTelCfo;
	}
	public String getApprovalTelCfoRemark() {
		return approvalTelCfoRemark;
	}
	public void setApprovalTelCfoRemark(String approvalTelCfoRemark) {
		this.approvalTelCfoRemark = approvalTelCfoRemark;
	}
	public String getApprovalTelContract() {
		return approvalTelContract;
	}
	public void setApprovalTelContract(String approvalTelContract) {
		this.approvalTelContract = approvalTelContract;
	}
	public String getApprovalTelContractRemark() {
		return approvalTelContractRemark;
	}
	public void setApprovalTelContractRemark(String approvalTelContractRemark) {
		this.approvalTelContractRemark = approvalTelContractRemark;
	}
	public double getApprovalApprovalAmount() {
		return approvalApprovalAmount;
	}
	public void setApprovalApprovalAmount(double approvalApprovalAmount) {
		this.approvalApprovalAmount = approvalApprovalAmount;
	}
	public int getApprovalApprovalPeriod() {
		return approvalApprovalPeriod;
	}
	public void setApprovalApprovalPeriod(int approvalApprovalPeriod) {
		this.approvalApprovalPeriod = approvalApprovalPeriod;
	}
	public double getApprovalApprovalServiceRate() {
		return approvalApprovalServiceRate;
	}
	public void setApprovalApprovalServiceRate(double approvalApprovalServiceRate) {
		this.approvalApprovalServiceRate = approvalApprovalServiceRate;
	}
	public String getApprovalThirdPartyContract() {
		return approvalThirdPartyContract;
	}
	public void setApprovalThirdPartyContract(String approvalThirdPartyContract) {
		this.approvalThirdPartyContract = approvalThirdPartyContract;
	}
	public String getApprovalGuaranteeCondition() {
		return approvalGuaranteeCondition;
	}
	public void setApprovalGuaranteeCondition(String approvalGuaranteeCondition) {
		this.approvalGuaranteeCondition = approvalGuaranteeCondition;
	}
	public String getApprovalMatchType() {
		return approvalMatchType;
	}
	public void setApprovalMatchType(String approvalMatchType) {
		this.approvalMatchType = approvalMatchType;
	}
	public String getApprovalOrgId() {
		return approvalOrgId;
	}
	public void setApprovalOrgId(String approvalOrgId) {
		this.approvalOrgId = approvalOrgId;
	}
	public int getCunstomerId() {
		return cunstomerId;
	}
	public void setCunstomerId(int cunstomerId) {
		this.cunstomerId = cunstomerId;
	}
	public String getCunstomerCustomerNo() {
		return cunstomerCustomerNo;
	}
	public void setCunstomerCustomerNo(String cunstomerCustomerNo) {
		this.cunstomerCustomerNo = cunstomerCustomerNo;
	}
	public String getCunstomerName() {
		return cunstomerName;
	}
	public void setCunstomerName(String cunstomerName) {
		this.cunstomerName = cunstomerName;
	}
	public String getCunstomerShortName() {
		return cunstomerShortName;
	}
	public void setCunstomerShortName(String cunstomerShortName) {
		this.cunstomerShortName = cunstomerShortName;
	}
	public String getCunstomerCustomerType() {
		return cunstomerCustomerType;
	}
	public void setCunstomerCustomerType(String cunstomerCustomerType) {
		this.cunstomerCustomerType = cunstomerCustomerType;
	}
	public String getCunstomerCustomerLever() {
		return cunstomerCustomerLever;
	}
	public void setCunstomerCustomerLever(String cunstomerCustomerLever) {
		this.cunstomerCustomerLever = cunstomerCustomerLever;
	}
	public String getCunstomerEngName() {
		return cunstomerEngName;
	}
	public void setCunstomerEngName(String cunstomerEngName) {
		this.cunstomerEngName = cunstomerEngName;
	}
	public String getCunstomerGender() {
		return cunstomerGender;
	}
	public void setCunstomerGender(String cunstomerGender) {
		this.cunstomerGender = cunstomerGender;
	}
	public java.util.Date getCunstomerBirthday() {
		return cunstomerBirthday;
	}
	public void setCunstomerBirthday(java.util.Date cunstomerBirthday) {
		this.cunstomerBirthday = cunstomerBirthday;
	}
	public void setCunstomerBirthdayStr(String cunstomerBirthdayStr) {
		this.cunstomerBirthdayStr = cunstomerBirthdayStr;
	}
	public String getCunstomerNationality() {
		return cunstomerNationality;
	}
	public void setCunstomerNationality(String cunstomerNationality) {
		this.cunstomerNationality = cunstomerNationality;
	}
	public String getCunstomerLanguage() {
		return cunstomerLanguage;
	}
	public void setCunstomerLanguage(String cunstomerLanguage) {
		this.cunstomerLanguage = cunstomerLanguage;
	}
	public String getCunstomerMotherFiratName() {
		return cunstomerMotherFiratName;
	}
	public void setCunstomerMotherFiratName(String cunstomerMotherFiratName) {
		this.cunstomerMotherFiratName = cunstomerMotherFiratName;
	}
	public String getCunstomerMarriage() {
		return cunstomerMarriage;
	}
	public void setCunstomerMarriage(String cunstomerMarriage) {
		this.cunstomerMarriage = cunstomerMarriage;
	}
	public String getCunstomerIdType() {
		return cunstomerIdType;
	}
	public void setCunstomerIdType(String cunstomerIdType) {
		this.cunstomerIdType = cunstomerIdType;
	}
	public String getCunstomerIdNo() {
		return cunstomerIdNo;
	}
	public void setCunstomerIdNo(String cunstomerIdNo) {
		this.cunstomerIdNo = cunstomerIdNo;
	}
	public java.util.Date getCunstomerIdIssueDate() {
		return cunstomerIdIssueDate;
	}
	public void setCunstomerIdIssueDate(java.util.Date cunstomerIdIssueDate) {
		this.cunstomerIdIssueDate = cunstomerIdIssueDate;
	}
	public void setCunstomerIdIssueDateStr(String cunstomerIdIssueDateStr) {
		this.cunstomerIdIssueDateStr = cunstomerIdIssueDateStr;
	}
	public java.util.Date getCunstomerIdExpiryDate() {
		return cunstomerIdExpiryDate;
	}
	public void setCunstomerIdExpiryDate(java.util.Date cunstomerIdExpiryDate) {
		this.cunstomerIdExpiryDate = cunstomerIdExpiryDate;
	}
	public void setCunstomerIdExpiryDateStr(String cunstomerIdExpiryDateStr) {
		this.cunstomerIdExpiryDateStr = cunstomerIdExpiryDateStr;
	}
	public String getCunstomerIdIssueGov() {
		return cunstomerIdIssueGov;
	}
	public void setCunstomerIdIssueGov(String cunstomerIdIssueGov) {
		this.cunstomerIdIssueGov = cunstomerIdIssueGov;
	}
	public String getCunstomerEducation() {
		return cunstomerEducation;
	}
	public void setCunstomerEducation(String cunstomerEducation) {
		this.cunstomerEducation = cunstomerEducation;
	}
	public String getCunstomerJob() {
		return cunstomerJob;
	}
	public void setCunstomerJob(String cunstomerJob) {
		this.cunstomerJob = cunstomerJob;
	}
	public String getCunstomerIndustry1() {
		return cunstomerIndustry1;
	}
	public void setCunstomerIndustry1(String cunstomerIndustry1) {
		this.cunstomerIndustry1 = cunstomerIndustry1;
	}
	public String getCunstomerIndustry2() {
		return cunstomerIndustry2;
	}
	public void setCunstomerIndustry2(String cunstomerIndustry2) {
		this.cunstomerIndustry2 = cunstomerIndustry2;
	}
	public String getCunstomerIndustry3() {
		return cunstomerIndustry3;
	}
	public void setCunstomerIndustry3(String cunstomerIndustry3) {
		this.cunstomerIndustry3 = cunstomerIndustry3;
	}
	public String getCunstomerCompanyName() {
		return cunstomerCompanyName;
	}
	public void setCunstomerCompanyName(String cunstomerCompanyName) {
		this.cunstomerCompanyName = cunstomerCompanyName;
	}
	public int getCunstomerWorkYears() {
		return cunstomerWorkYears;
	}
	public void setCunstomerWorkYears(int cunstomerWorkYears) {
		this.cunstomerWorkYears = cunstomerWorkYears;
	}
	public String getCunstomerCompanyScale() {
		return cunstomerCompanyScale;
	}
	public void setCunstomerCompanyScale(String cunstomerCompanyScale) {
		this.cunstomerCompanyScale = cunstomerCompanyScale;
	}
	public String getCunstomerJobDuty() {
		return cunstomerJobDuty;
	}
	public void setCunstomerJobDuty(String cunstomerJobDuty) {
		this.cunstomerJobDuty = cunstomerJobDuty;
	}
	public String getCunstomerPhone() {
		return cunstomerPhone;
	}
	public void setCunstomerPhone(String cunstomerPhone) {
		this.cunstomerPhone = cunstomerPhone;
	}
	public String getCunstomerMobile() {
		return cunstomerMobile;
	}
	public void setCunstomerMobile(String cunstomerMobile) {
		this.cunstomerMobile = cunstomerMobile;
	}
	public String getCunstomerEmail() {
		return cunstomerEmail;
	}
	public void setCunstomerEmail(String cunstomerEmail) {
		this.cunstomerEmail = cunstomerEmail;
	}
	public String getCunstomerAddProvince() {
		return cunstomerAddProvince;
	}
	public void setCunstomerAddProvince(String cunstomerAddProvince) {
		this.cunstomerAddProvince = cunstomerAddProvince;
	}
	public String getCunstomerAddCity() {
		return cunstomerAddCity;
	}
	public void setCunstomerAddCity(String cunstomerAddCity) {
		this.cunstomerAddCity = cunstomerAddCity;
	}
	public String getCunstomerAddCounty() {
		return cunstomerAddCounty;
	}
	public void setCunstomerAddCounty(String cunstomerAddCounty) {
		this.cunstomerAddCounty = cunstomerAddCounty;
	}
	public String getCunstomerAddress() {
		return cunstomerAddress;
	}
	public void setCunstomerAddress(String cunstomerAddress) {
		this.cunstomerAddress = cunstomerAddress;
	}
	public String getCunstomerPostcode() {
		return cunstomerPostcode;
	}
	public void setCunstomerPostcode(String cunstomerPostcode) {
		this.cunstomerPostcode = cunstomerPostcode;
	}
	public String getCunstomerFamily() {
		return cunstomerFamily;
	}
	public void setCunstomerFamily(String cunstomerFamily) {
		this.cunstomerFamily = cunstomerFamily;
	}
	public String getCunstomerFamilyIncome() {
		return cunstomerFamilyIncome;
	}
	public void setCunstomerFamilyIncome(String cunstomerFamilyIncome) {
		this.cunstomerFamilyIncome = cunstomerFamilyIncome;
	}
	public String getCunstomerFileReceive() {
		return cunstomerFileReceive;
	}
	public void setCunstomerFileReceive(String cunstomerFileReceive) {
		this.cunstomerFileReceive = cunstomerFileReceive;
	}
	public String getCunstomerRequirements() {
		return cunstomerRequirements;
	}
	public void setCunstomerRequirements(String cunstomerRequirements) {
		this.cunstomerRequirements = cunstomerRequirements;
	}
	public String getCunstomerBizzScope() {
		return cunstomerBizzScope;
	}
	public void setCunstomerBizzScope(String cunstomerBizzScope) {
		this.cunstomerBizzScope = cunstomerBizzScope;
	}
	public String getCunstomerRegProvince() {
		return cunstomerRegProvince;
	}
	public void setCunstomerRegProvince(String cunstomerRegProvince) {
		this.cunstomerRegProvince = cunstomerRegProvince;
	}
	public String getCunstomerRegCity() {
		return cunstomerRegCity;
	}
	public void setCunstomerRegCity(String cunstomerRegCity) {
		this.cunstomerRegCity = cunstomerRegCity;
	}
	public String getCunstomerRegCounty() {
		return cunstomerRegCounty;
	}
	public void setCunstomerRegCounty(String cunstomerRegCounty) {
		this.cunstomerRegCounty = cunstomerRegCounty;
	}
	public String getCunstomerRegAddress() {
		return cunstomerRegAddress;
	}
	public void setCunstomerRegAddress(String cunstomerRegAddress) {
		this.cunstomerRegAddress = cunstomerRegAddress;
	}
	public String getCunstomerCompanyType() {
		return cunstomerCompanyType;
	}
	public void setCunstomerCompanyType(String cunstomerCompanyType) {
		this.cunstomerCompanyType = cunstomerCompanyType;
	}
	public String getCunstomerTrustAssets() {
		return cunstomerTrustAssets;
	}
	public void setCunstomerTrustAssets(String cunstomerTrustAssets) {
		this.cunstomerTrustAssets = cunstomerTrustAssets;
	}
	public String getCunstomerTrustSettlor() {
		return cunstomerTrustSettlor;
	}
	public void setCunstomerTrustSettlor(String cunstomerTrustSettlor) {
		this.cunstomerTrustSettlor = cunstomerTrustSettlor;
	}
	public String getCunstomerTrustSettlorPhone() {
		return cunstomerTrustSettlorPhone;
	}
	public void setCunstomerTrustSettlorPhone(String cunstomerTrustSettlorPhone) {
		this.cunstomerTrustSettlorPhone = cunstomerTrustSettlorPhone;
	}
	public String getCunstomerTrustBenefit() {
		return cunstomerTrustBenefit;
	}
	public void setCunstomerTrustBenefit(String cunstomerTrustBenefit) {
		this.cunstomerTrustBenefit = cunstomerTrustBenefit;
	}
	public String getCunstomerTrustBenefitPhone() {
		return cunstomerTrustBenefitPhone;
	}
	public void setCunstomerTrustBenefitPhone(String cunstomerTrustBenefitPhone) {
		this.cunstomerTrustBenefitPhone = cunstomerTrustBenefitPhone;
	}
	public String getCunstomerCustomerManager() {
		return cunstomerCustomerManager;
	}
	public void setCunstomerCustomerManager(String cunstomerCustomerManager) {
		this.cunstomerCustomerManager = cunstomerCustomerManager;
	}
	public String getCunstomerOrgId() {
		return cunstomerOrgId;
	}
	public void setCunstomerOrgId(String cunstomerOrgId) {
		this.cunstomerOrgId = cunstomerOrgId;
	}
	public void setCunstomerAppTimeStr(String cunstomerAppTimeStr) {
		this.cunstomerAppTimeStr = cunstomerAppTimeStr;
	}
	public String getCunstomerState() {
		return cunstomerState;
	}
	public void setCunstomerState(String cunstomerState) {
		this.cunstomerState = cunstomerState;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public java.util.Date getOrgRegDate() {
		return orgRegDate;
	}
	public void setOrgRegDate(java.util.Date orgRegDate) {
		this.orgRegDate = orgRegDate;
	}
	public void setOrgRegDateStr(String orgRegDateStr) {
		this.orgRegDateStr = orgRegDateStr;
	}
	public double getOrgRegAmount() {
		return orgRegAmount;
	}
	public void setOrgRegAmount(double orgRegAmount) {
		this.orgRegAmount = orgRegAmount;
	}
	public String getOrgIdType() {
		return orgIdType;
	}
	public void setOrgIdType(String orgIdType) {
		this.orgIdType = orgIdType;
	}
	public String getOrgIdNo() {
		return orgIdNo;
	}
	public void setOrgIdNo(String orgIdNo) {
		this.orgIdNo = orgIdNo;
	}
	public double getOrgAcctualAmount() {
		return orgAcctualAmount;
	}
	public void setOrgAcctualAmount(double orgAcctualAmount) {
		this.orgAcctualAmount = orgAcctualAmount;
	}
	public String getFdOrgName() {
		return fdOrgName;
	}
	public void setFdOrgName(String fdOrgName) {
		this.fdOrgName = fdOrgName;
	}
	public String getFdOrgMobile() {
		return fdOrgMobile;
	}
	public void setFdOrgMobile(String fdOrgMobile) {
		this.fdOrgMobile = fdOrgMobile;
	}
	public String getFdorgIdType() {
		return fdorgIdType;
	}
	public void setFdorgIdType(String fdorgIdType) {
		this.fdorgIdType = fdorgIdType;
	}
	public String getFdOrgIdNo() {
		return fdOrgIdNo;
	}
	public void setFdOrgIdNo(String fdOrgIdNo) {
		this.fdOrgIdNo = fdOrgIdNo;
	}
	public String getSqOrgName() {
		return sqOrgName;
	}
	public void setSqOrgName(String sqOrgName) {
		this.sqOrgName = sqOrgName;
	}
	public String getSqOrgMobile() {
		return sqOrgMobile;
	}
	public void setSqOrgMobile(String sqOrgMobile) {
		this.sqOrgMobile = sqOrgMobile;
	}
	public String getSqOrgIdType() {
		return sqOrgIdType;
	}
	public void setSqOrgIdType(String sqOrgIdType) {
		this.sqOrgIdType = sqOrgIdType;
	}
	public String getSqOrgIdNo() {
		return sqOrgIdNo;
	}
	public void setSqOrgIdNo(String sqOrgIdNo) {
		this.sqOrgIdNo = sqOrgIdNo;
	}
	public String getCwOrgName() {
		return cwOrgName;
	}
	public void setCwOrgName(String cwOrgName) {
		this.cwOrgName = cwOrgName;
	}
	public String getCwOrgMobile() {
		return cwOrgMobile;
	}
	public void setCwOrgMobile(String cwOrgMobile) {
		this.cwOrgMobile = cwOrgMobile;
	}
	public String getCwOrgIdType() {
		return cwOrgIdType;
	}
	public void setCwOrgIdType(String cwOrgIdType) {
		this.cwOrgIdType = cwOrgIdType;
	}
	public String getCwOrgIdNo() {
		return cwOrgIdNo;
	}
	public void setCwOrgIdNo(String cwOrgIdNo) {
		this.cwOrgIdNo = cwOrgIdNo;
	}
	public String getOrgOrgCodeNo() {
		return orgOrgCodeNo;
	}
	public void setOrgOrgCodeNo(String orgOrgCodeNo) {
		this.orgOrgCodeNo = orgOrgCodeNo;
	}
	public java.util.Date getOrgOrgCodeExpiryDate() {
		return orgOrgCodeExpiryDate;
	}
	public void setOrgOrgCodeExpiryDate(java.util.Date orgOrgCodeExpiryDate) {
		this.orgOrgCodeExpiryDate = orgOrgCodeExpiryDate;
	}
	public void setOrgOrgCodeExpiryDateStr(String orgOrgCodeExpiryDateStr) {
		this.orgOrgCodeExpiryDateStr = orgOrgCodeExpiryDateStr;
	}
	public String getOrgTaxRegNo() {
		return orgTaxRegNo;
	}
	public void setOrgTaxRegNo(String orgTaxRegNo) {
		this.orgTaxRegNo = orgTaxRegNo;
	}
	public String getOrgBaiscAccountBank() {
		return orgBaiscAccountBank;
	}
	public void setOrgBaiscAccountBank(String orgBaiscAccountBank) {
		this.orgBaiscAccountBank = orgBaiscAccountBank;
	}
	public String getOrgBaiscAccount() {
		return orgBaiscAccount;
	}
	public void setOrgBaiscAccount(String orgBaiscAccount) {
		this.orgBaiscAccount = orgBaiscAccount;
	}
	public String getOrgUseage() {
		return orgUseage;
	}
	public void setOrgUseage(String orgUseage) {
		this.orgUseage = orgUseage;
	}
	public String getOrgIndustry1() {
		return orgIndustry1;
	}
	public void setOrgIndustry1(String orgIndustry1) {
		this.orgIndustry1 = orgIndustry1;
	}
	public String getOrgIndustry2() {
		return orgIndustry2;
	}
	public void setOrgIndustry2(String orgIndustry2) {
		this.orgIndustry2 = orgIndustry2;
	}
	public String getOrgIndustry3() {
		return orgIndustry3;
	}
	public void setOrgIndustry3(String orgIndustry3) {
		this.orgIndustry3 = orgIndustry3;
	}
	public String getOrgBizzScope() {
		return orgBizzScope;
	}
	public void setOrgBizzScope(String orgBizzScope) {
		this.orgBizzScope = orgBizzScope;
	}
	public String getOrgCompanyType() {
		return orgCompanyType;
	}
	public void setOrgCompanyType(String orgCompanyType) {
		this.orgCompanyType = orgCompanyType;
	}
	public String getOrgMainProduct() {
		return orgMainProduct;
	}
	public void setOrgMainProduct(String orgMainProduct) {
		this.orgMainProduct = orgMainProduct;
	}
	public double getOrgLastYearTurnover() {
		return orgLastYearTurnover;
	}
	public void setOrgLastYearTurnover(double orgLastYearTurnover) {
		this.orgLastYearTurnover = orgLastYearTurnover;
	}
	public double getOrgLast3mTurnover() {
		return orgLast3mTurnover;
	}
	public void setOrgLast3mTurnover(double orgLast3mTurnover) {
		this.orgLast3mTurnover = orgLast3mTurnover;
	}
	public String getOrgCooperateBankCompany() {
		return orgCooperateBankCompany;
	}
	public void setOrgCooperateBankCompany(String orgCooperateBankCompany) {
		this.orgCooperateBankCompany = orgCooperateBankCompany;
	}
	public String getOrgDailyClearBank() {
		return orgDailyClearBank;
	}
	public void setOrgDailyClearBank(String orgDailyClearBank) {
		this.orgDailyClearBank = orgDailyClearBank;
	}
	public double getOrgLoanBalance() {
		return orgLoanBalance;
	}
	public void setOrgLoanBalance(double orgLoanBalance) {
		this.orgLoanBalance = orgLoanBalance;
	}
	public String getOrgFinanceCompany() {
		return orgFinanceCompany;
	}
	public void setOrgFinanceCompany(String orgFinanceCompany) {
		this.orgFinanceCompany = orgFinanceCompany;
	}
	public String getOrgFinanceBank() {
		return orgFinanceBank;
	}
	public void setOrgFinanceBank(String orgFinanceBank) {
		this.orgFinanceBank = orgFinanceBank;
	}
	public double getOrgNearly3mEleBill1() {
		return orgNearly3mEleBill1;
	}
	public void setOrgNearly3mEleBill1(double orgNearly3mEleBill1) {
		this.orgNearly3mEleBill1 = orgNearly3mEleBill1;
	}
	public double getOrgNearly3mEleBill2() {
		return orgNearly3mEleBill2;
	}
	public void setOrgNearly3mEleBill2(double orgNearly3mEleBill2) {
		this.orgNearly3mEleBill2 = orgNearly3mEleBill2;
	}
	public double getOrgNearly3mEleBill3() {
		return orgNearly3mEleBill3;
	}
	public void setOrgNearly3mEleBill3(double orgNearly3mEleBill3) {
		this.orgNearly3mEleBill3 = orgNearly3mEleBill3;
	}
	public String getOrgLendAccName() {
		return orgLendAccName;
	}
	public void setOrgLendAccName(String orgLendAccName) {
		this.orgLendAccName = orgLendAccName;
	}
	public String getOrgLendAccBank() {
		return orgLendAccBank;
	}
	public void setOrgLendAccBank(String orgLendAccBank) {
		this.orgLendAccBank = orgLendAccBank;
	}
	public String getOrgLendAccount() {
		return orgLendAccount;
	}
	public void setOrgLendAccount(String orgLendAccount) {
		this.orgLendAccount = orgLendAccount;
	}
	public String getCustomerOrgName() {
		return customerOrgName;
	}
	public void setCustomerOrgName(String customerOrgName) {
		this.customerOrgName = customerOrgName;
	}
	public java.util.Date getCustomerOrgRegDate() {
		return customerOrgRegDate;
	}
	public void setCustomerOrgRegDate(java.util.Date customerOrgRegDate) {
		this.customerOrgRegDate = customerOrgRegDate;
	}
	public void setCustomerOrgRegDateStr(String customerOrgRegDateStr) {
		this.customerOrgRegDateStr = customerOrgRegDateStr;
	}
	public double getCustomerOrgRegAmount() {
		return customerOrgRegAmount;
	}
	public void setCustomerOrgRegAmount(double customerOrgRegAmount) {
		this.customerOrgRegAmount = customerOrgRegAmount;
	}
	public String getCustomerOrgIdType() {
		return customerOrgIdType;
	}
	public void setCustomerOrgIdType(String customerOrgIdType) {
		this.customerOrgIdType = customerOrgIdType;
	}
	public String getCustomerOrgIdNo() {
		return customerOrgIdNo;
	}
	public void setCustomerOrgIdNo(String customerOrgIdNo) {
		this.customerOrgIdNo = customerOrgIdNo;
	}
	public String getCustomerOrgAcctualAmount() {
		return customerOrgAcctualAmount;
	}
	public void setCustomerOrgAcctualAmount(String customerOrgAcctualAmount) {
		this.customerOrgAcctualAmount = customerOrgAcctualAmount;
	}
	public String getCustomerFdOrgName() {
		return customerFdOrgName;
	}
	public void setCustomerFdOrgName(String customerFdOrgName) {
		this.customerFdOrgName = customerFdOrgName;
	}
	public String getCustomerFdOrgMobile() {
		return customerFdOrgMobile;
	}
	public void setCustomerFdOrgMobile(String customerFdOrgMobile) {
		this.customerFdOrgMobile = customerFdOrgMobile;
	}
	public String getCustomerFdorgIdType() {
		return customerFdorgIdType;
	}
	public void setCustomerFdorgIdType(String customerFdorgIdType) {
		this.customerFdorgIdType = customerFdorgIdType;
	}
	public String getCustomerFdOrgIdNo() {
		return customerFdOrgIdNo;
	}
	public void setCustomerFdOrgIdNo(String customerFdOrgIdNo) {
		this.customerFdOrgIdNo = customerFdOrgIdNo;
	}
	public String getCustomerSqOrgName() {
		return customerSqOrgName;
	}
	public void setCustomerSqOrgName(String customerSqOrgName) {
		this.customerSqOrgName = customerSqOrgName;
	}
	public String getCustomerSqOrgMobile() {
		return customerSqOrgMobile;
	}
	public void setCustomerSqOrgMobile(String customerSqOrgMobile) {
		this.customerSqOrgMobile = customerSqOrgMobile;
	}
	public String getCustomerSqOrgIdType() {
		return customerSqOrgIdType;
	}
	public void setCustomerSqOrgIdType(String customerSqOrgIdType) {
		this.customerSqOrgIdType = customerSqOrgIdType;
	}
	public String getCustomerSqOrgIdNo() {
		return customerSqOrgIdNo;
	}
	public void setCustomerSqOrgIdNo(String customerSqOrgIdNo) {
		this.customerSqOrgIdNo = customerSqOrgIdNo;
	}
	public String getCustomerCwOrgName() {
		return customerCwOrgName;
	}
	public void setCustomerCwOrgName(String customerCwOrgName) {
		this.customerCwOrgName = customerCwOrgName;
	}
	public String getCustomerCwOrgMobile() {
		return customerCwOrgMobile;
	}
	public void setCustomerCwOrgMobile(String customerCwOrgMobile) {
		this.customerCwOrgMobile = customerCwOrgMobile;
	}
	public String getCustomerCwOrgIdType() {
		return customerCwOrgIdType;
	}
	public void setCustomerCwOrgIdType(String customerCwOrgIdType) {
		this.customerCwOrgIdType = customerCwOrgIdType;
	}
	public String getCustomerCwOrgIdNo() {
		return customerCwOrgIdNo;
	}
	public void setCustomerCwOrgIdNo(String customerCwOrgIdNo) {
		this.customerCwOrgIdNo = customerCwOrgIdNo;
	}
	public String getCustomerOrgOrgCodeNo() {
		return customerOrgOrgCodeNo;
	}
	public void setCustomerOrgOrgCodeNo(String customerOrgOrgCodeNo) {
		this.customerOrgOrgCodeNo = customerOrgOrgCodeNo;
	}
	public java.util.Date getCustomerOrgOrgCodeExpiryDate() {
		return customerOrgOrgCodeExpiryDate;
	}
	public void setCustomerOrgOrgCodeExpiryDate(
			java.util.Date customerOrgOrgCodeExpiryDate) {
		this.customerOrgOrgCodeExpiryDate = customerOrgOrgCodeExpiryDate;
	}
	public void setCustomerOrgOrgCodeExpiryDateStr(
			String customerOrgOrgCodeExpiryDateStr) {
		this.customerOrgOrgCodeExpiryDateStr = customerOrgOrgCodeExpiryDateStr;
	}
	public String getCustomerOrgTaxRegNo() {
		return customerOrgTaxRegNo;
	}
	public void setCustomerOrgTaxRegNo(String customerOrgTaxRegNo) {
		this.customerOrgTaxRegNo = customerOrgTaxRegNo;
	}
	public String getCustomerOrgBaiscAccountBank() {
		return customerOrgBaiscAccountBank;
	}
	public void setCustomerOrgBaiscAccountBank(String customerOrgBaiscAccountBank) {
		this.customerOrgBaiscAccountBank = customerOrgBaiscAccountBank;
	}
	public String getCustomerOrgBaiscAccount() {
		return customerOrgBaiscAccount;
	}
	public void setCustomerOrgBaiscAccount(String customerOrgBaiscAccount) {
		this.customerOrgBaiscAccount = customerOrgBaiscAccount;
	}
	public String getCustomerOrgUseage() {
		return customerOrgUseage;
	}
	public void setCustomerOrgUseage(String customerOrgUseage) {
		this.customerOrgUseage = customerOrgUseage;
	}
	public String getCustomerOrgIndustry1() {
		return customerOrgIndustry1;
	}
	public void setCustomerOrgIndustry1(String customerOrgIndustry1) {
		this.customerOrgIndustry1 = customerOrgIndustry1;
	}
	public String getCustomerOrgIndustry2() {
		return customerOrgIndustry2;
	}
	public void setCustomerOrgIndustry2(String customerOrgIndustry2) {
		this.customerOrgIndustry2 = customerOrgIndustry2;
	}
	public String getCustomerOrgIndustry3() {
		return customerOrgIndustry3;
	}
	public void setCustomerOrgIndustry3(String customerOrgIndustry3) {
		this.customerOrgIndustry3 = customerOrgIndustry3;
	}
	public String getCustomerOrgBizzScope() {
		return customerOrgBizzScope;
	}
	public void setCustomerOrgBizzScope(String customerOrgBizzScope) {
		this.customerOrgBizzScope = customerOrgBizzScope;
	}
	public String getCustomerOrgCompanyType() {
		return customerOrgCompanyType;
	}
	public void setCustomerOrgCompanyType(String customerOrgCompanyType) {
		this.customerOrgCompanyType = customerOrgCompanyType;
	}
	public String getCustomerOrgMainProduct() {
		return customerOrgMainProduct;
	}
	public void setCustomerOrgMainProduct(String customerOrgMainProduct) {
		this.customerOrgMainProduct = customerOrgMainProduct;
	}
	public double getCustomerOrgLastYearTurnover() {
		return customerOrgLastYearTurnover;
	}
	public void setCustomerOrgLastYearTurnover(double customerOrgLastYearTurnover) {
		this.customerOrgLastYearTurnover = customerOrgLastYearTurnover;
	}
	public double getCustomerOrgLast3mTurnover() {
		return customerOrgLast3mTurnover;
	}
	public void setCustomerOrgLast3mTurnover(double customerOrgLast3mTurnover) {
		this.customerOrgLast3mTurnover = customerOrgLast3mTurnover;
	}
	public String getCustomerOrgCooperateBankCompany() {
		return customerOrgCooperateBankCompany;
	}
	public void setCustomerOrgCooperateBankCompany(
			String customerOrgCooperateBankCompany) {
		this.customerOrgCooperateBankCompany = customerOrgCooperateBankCompany;
	}
	public String getCustomerOrgDailyClearBank() {
		return customerOrgDailyClearBank;
	}
	public void setCustomerOrgDailyClearBank(String customerOrgDailyClearBank) {
		this.customerOrgDailyClearBank = customerOrgDailyClearBank;
	}
	public double getCustomerOrgLoanBalance() {
		return customerOrgLoanBalance;
	}
	public void setCustomerOrgLoanBalance(double customerOrgLoanBalance) {
		this.customerOrgLoanBalance = customerOrgLoanBalance;
	}
	public String getCustomerOrgFinanceCompany() {
		return customerOrgFinanceCompany;
	}
	public void setCustomerOrgFinanceCompany(String customerOrgFinanceCompany) {
		this.customerOrgFinanceCompany = customerOrgFinanceCompany;
	}
	public String getCustomerOrgFinanceBank() {
		return customerOrgFinanceBank;
	}
	public void setCustomerOrgFinanceBank(String customerOrgFinanceBank) {
		this.customerOrgFinanceBank = customerOrgFinanceBank;
	}
	public double getCustomerOrgNearly3mEleBill1() {
		return customerOrgNearly3mEleBill1;
	}
	public void setCustomerOrgNearly3mEleBill1(double customerOrgNearly3mEleBill1) {
		this.customerOrgNearly3mEleBill1 = customerOrgNearly3mEleBill1;
	}
	public double getCustomerOrgNearly3mEleBill2() {
		return customerOrgNearly3mEleBill2;
	}
	public void setCustomerOrgNearly3mEleBill2(double customerOrgNearly3mEleBill2) {
		this.customerOrgNearly3mEleBill2 = customerOrgNearly3mEleBill2;
	}
	public double getCustomerOrgNearly3mEleBill3() {
		return customerOrgNearly3mEleBill3;
	}
	public void setCustomerOrgNearly3mEleBill3(double customerOrgNearly3mEleBill3) {
		this.customerOrgNearly3mEleBill3 = customerOrgNearly3mEleBill3;
	}
	public String getCustomerOrgLendAccName() {
		return customerOrgLendAccName;
	}
	public void setCustomerOrgLendAccName(String customerOrgLendAccName) {
		this.customerOrgLendAccName = customerOrgLendAccName;
	}
	public String getCustomerOrgLendAccBank() {
		return customerOrgLendAccBank;
	}
	public void setCustomerOrgLendAccBank(String customerOrgLendAccBank) {
		this.customerOrgLendAccBank = customerOrgLendAccBank;
	}
	public String getCustomerOrgLendAccount() {
		return customerOrgLendAccount;
	}
	public void setCustomerOrgLendAccount(String customerOrgLendAccount) {
		this.customerOrgLendAccount = customerOrgLendAccount;
	}
	public String getAppType() {
		return appType;
	}
	public void setAppType(String appType) {
		this.appType = appType;
	}
	public String getFdOrgOrgId() {
		return fdOrgOrgId;
	}
	public void setFdOrgOrgId(String fdOrgOrgId) {
		this.fdOrgOrgId = fdOrgOrgId;
	}
	public String getFdOrgMainFlag() {
		return fdOrgMainFlag;
	}
	public void setFdOrgMainFlag(String fdOrgMainFlag) {
		this.fdOrgMainFlag = fdOrgMainFlag;
	}
	public String getFdOrgUpdateTime() {
		return fdOrgUpdateTime;
	}
	public void setFdOrgUpdateTime(String fdOrgUpdateTime) {
		this.fdOrgUpdateTime = fdOrgUpdateTime;
	}
	public String getFdOrgOperator() {
		return fdOrgOperator;
	}
	public void setFdOrgOperator(String fdOrgOperator) {
		this.fdOrgOperator = fdOrgOperator;
	}
	public String getSqOrgOrgId() {
		return sqOrgOrgId;
	}
	public void setSqOrgOrgId(String sqOrgOrgId) {
		this.sqOrgOrgId = sqOrgOrgId;
	}
	public String getSqOrgMainFlag() {
		return sqOrgMainFlag;
	}
	public void setSqOrgMainFlag(String sqOrgMainFlag) {
		this.sqOrgMainFlag = sqOrgMainFlag;
	}
	public String getSqOrgUpdateTime() {
		return sqOrgUpdateTime;
	}
	public void setSqOrgUpdateTime(String sqOrgUpdateTime) {
		this.sqOrgUpdateTime = sqOrgUpdateTime;
	}
	public String getSqOrgOperator() {
		return sqOrgOperator;
	}
	public void setSqOrgOperator(String sqOrgOperator) {
		this.sqOrgOperator = sqOrgOperator;
	}
	public String getCwOrgOrgId() {
		return cwOrgOrgId;
	}
	public void setCwOrgOrgId(String cwOrgOrgId) {
		this.cwOrgOrgId = cwOrgOrgId;
	}
	public String getCwOrgMainFlag() {
		return cwOrgMainFlag;
	}
	public void setCwOrgMainFlag(String cwOrgMainFlag) {
		this.cwOrgMainFlag = cwOrgMainFlag;
	}
	public String getCwOrgUpdateTime() {
		return cwOrgUpdateTime;
	}
	public void setCwOrgUpdateTime(String cwOrgUpdateTime) {
		this.cwOrgUpdateTime = cwOrgUpdateTime;
	}
	public String getCwOrgOperator() {
		return cwOrgOperator;
	}
	public void setCwOrgOperator(String cwOrgOperator) {
		this.cwOrgOperator = cwOrgOperator;
	}
	public int getContactId() {
		return contactId;
	}
	public void setContactId(int contactId) {
		this.contactId = contactId;
	}
	public int getFdOrgId() {
		return fdOrgId;
	}
	public void setFdOrgId(int fdOrgId) {
		this.fdOrgId = fdOrgId;
	}
	public int getSqOrgId() {
		return sqOrgId;
	}
	public void setSqOrgId(int sqOrgId) {
		this.sqOrgId = sqOrgId;
	}
	public int getCwOrgId() {
		return cwOrgId;
	}
	public void setCwOrgId(int cwOrgId) {
		this.cwOrgId = cwOrgId;
	}
	public int getOrgLoanAppId() {
		return orgLoanAppId;
	}
	public void setOrgLoanAppId(int orgLoanAppId) {
		this.orgLoanAppId = orgLoanAppId;
	}
	public String getRegDateStr() {
		return regDateStr;
	}
	public String getOrgCodeExpiryDateStr() {
		return orgCodeExpiryDateStr;
	}
	public String getContactIdIssueDateStr() {
		return contactIdIssueDateStr;
	}
	public String getContactIdExpiryDateStr() {
		return contactIdExpiryDateStr;
	}
	public String getContactIdIssueDate2Str() {
		return contactIdIssueDate2Str;
	}
	public String getCunstomerBirthdayStr() {
		return cunstomerBirthdayStr;
	}
	public String getCunstomerIdIssueDateStr() {
		return cunstomerIdIssueDateStr;
	}
	public String getCunstomerIdExpiryDateStr() {
		return cunstomerIdExpiryDateStr;
	}
	public String getCunstomerAppTimeStr() {
		return cunstomerAppTimeStr;
	}
	public String getOrgRegDateStr() {
		return orgRegDateStr;
	}
	public String getOrgOrgCodeExpiryDateStr() {
		return orgOrgCodeExpiryDateStr;
	}
	public String getCustomerOrgRegDateStr() {
		return customerOrgRegDateStr;
	}
	public String getCustomerOrgOrgCodeExpiryDateStr() {
		return customerOrgOrgCodeExpiryDateStr;
	}
	public String getAppChannel() {
		return appChannel;
	}
	public void setAppChannel(String appChannel) {
		this.appChannel = appChannel;
	}

}

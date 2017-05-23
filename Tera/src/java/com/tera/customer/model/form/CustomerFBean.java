package com.tera.customer.model.form;

import java.util.List;

import com.tera.customer.model.Contact;
import com.tera.customer.model.Customer;
import com.tera.customer.model.CustomerExt;

/**
 * 
 * <br>
 * <b>功能：</b>CustomerDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-05-29 16:51:12<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public class CustomerFBean {

	private int id;//ID
	private String name;//姓名/机构全称
	private String shortName;//简称
	private String customerType;//客户类型
	private String engName;//英文名称
	private String gender;//性别
	private java.util.Date birthday;//生日
	private String nationality;//国籍
	private String language;//语言
	private String motherFiratName;//母亲姓氏
	private String marriage;//婚姻
	private String idType;//证件类型
	private String idNo;//证件号码
	private java.util.Date idIssueDate;//签发日期
	private java.util.Date idExpiryDate;//失效日期
	private String idIssueGov;//签发机关
	private String education;//学历
	private String job;//职业
	private String industry1;//行业代码1
	private String industry2;//行业代码2
	private String industry3;//行业代码3
	private String companyName;//单位名称
	private int workYears;//工作年限
	private String companyScale;//单位规模
	private String jobDuty;//职务
	private String phone;//固定电话
	private String mobile;//移动电话
	private String email;//EMAIL
	private String addProvince;//通讯地址-省
	private String addCity;//通讯地址-市
	private String addCounty;//通讯地址-区县
	private String address;//通讯地址
	private String postcode;//邮编
	private String family;//家庭情况
	private String familyIncome;//家庭收入
	private String fileReceive;//文件接收方式
	private String requirements;//资源需求
	private String bizzScope;//经营范围
	private String regProvince;//注册地址-省
	private String regCity;//注册地址-市
	private String regCounty;//注册地址-区县
	private String regAddress;//注册地址
	private String companyType;//企业性质
	private String trustAssets;//信托资产
	private String trustSettlor;//信托委托人
	private String trustSettlorPhone;//信托委托人电话
	private String trustBenefit;//信托受益人
	private String trustBenefitPhone;//信托受益人电话
	private String customerManager;//客户经理
	private String orgId;//所属机构
	private java.sql.Timestamp createTime;//创建日期
	private java.sql.Timestamp updateTime;//修改日期
	private java.sql.Timestamp appTime;//提交日期
	private String state;//状态
	
	private int farenId;//法人ID
	private String farenName;//法人姓名
	private String farenIdType;//法人证件类型
	private String farenIdNo;//法人证件号码
	private   java.util.Date farenIdIssueDate;//法人签发日期
	private String farenIssueGov;//法人签发机关
	
	private int cfoId;//财务ID
	private String cfoName;//财务姓名
	private String cfoIdType;//财务证件类型
	private String cfoIdNo;//财务证件号码
	private   java.util.Date cfoIdIssueDate;//财务签发日期
	private String cfoIssueGov;//财务签发机关
	
	private int kongzhiId;//控制人ID
	private String kongzhiName;//控制人姓名
	private String kongzhiIdType;//控制人证件类型
	private String kongzhiIdNo;//控制人证件号码
	private   java.util.Date kongzhiIdIssueDate;//控制人签发日期
	private String kongzhiIssueGov;//控制人签发机关
	
	private int contactId;//联系人ID
	private String contactType;//联系人类型
	private String contactName;//姓名
	private String contactEngName;//英文名
	private String contactGender;//性别
	private   java.util.Date contactBirthday;//生日
	private String contactIdType;//证件类型
	private String contactIdNo;//证件号码
	private   java.util.Date contactIdIssueDate;//签发日期
	private   java.util.Date contactIdExpiryDate;//有效期
	private String contactIdIssueGov;//签发机关
	private String contactMobile;//移动电话
	private String contactPhone;//固定电话
	private String contactEmail;//email
	private String contactRelation;//关系
	private String contactAddProvice;//省
	private String contactAddCity;//市
	private String contactAddCounty;//区县
	private String contactAddress;//地址
	private String contactPostcode;//邮编

	private String[] interests;//兴趣爱好
	private String[] activities;//参加活动
	private String[] realEstate;//置业情况
	private String[] investmentLevel;//投资了解程度
	private String[] investmentSource;//投资资金来源
	private String[] investmentExp;//投资经验
	private String[] investmentProduct;//投资产品
	private String[] investmentIncome;//投资收益
	private String[] followType;//关注方式
	private String[] followPoint;//关注特点
	private String[] preferenceGoal;//偏好-目标
	private String[] preferencePeriod;//偏好-期限
	private String preferenceDecision;//偏好-投资决策
	private String preferenceRisk;//偏好-风险偏好
	private String[] preferenceProduct;//偏好-投资产品
	private String[] preferenceAmount;//偏好-追加金额

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	public String getCustomerType() {
		return customerType;
	}
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}
	public String getEngName() {
		return engName;
	}
	public void setEngName(String engName) {
		this.engName = engName;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public java.util.Date getBirthday() {
		return birthday;
	}
	public void setBirthday(java.util.Date birthday) {
		this.birthday = birthday;
	}
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getMotherFiratName() {
		return motherFiratName;
	}
	public void setMotherFiratName(String motherFiratName) {
		this.motherFiratName = motherFiratName;
	}
	public String getMarriage() {
		return marriage;
	}
	public void setMarriage(String marriage) {
		this.marriage = marriage;
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
	public java.util.Date getIdIssueDate() {
		return idIssueDate;
	}
	public void setIdIssueDate(java.util.Date idIssueDate) {
		this.idIssueDate = idIssueDate;
	}
	public java.util.Date getIdExpiryDate() {
		return idExpiryDate;
	}
	public void setIdExpiryDate(java.util.Date idExpiryDate) {
		this.idExpiryDate = idExpiryDate;
	}
	public String getIdIssueGov() {
		return idIssueGov;
	}
	public void setIdIssueGov(String idIssueGov) {
		this.idIssueGov = idIssueGov;
	}
	public String getEducation() {
		return education;
	}
	public void setEducation(String education) {
		this.education = education;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
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
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public int getWorkYears() {
		return workYears;
	}
	public void setWorkYears(int workYears) {
		this.workYears = workYears;
	}
	public String getCompanyScale() {
		return companyScale;
	}
	public void setCompanyScale(String companyScale) {
		this.companyScale = companyScale;
	}
	public String getJobDuty() {
		return jobDuty;
	}
	public void setJobDuty(String jobDuty) {
		this.jobDuty = jobDuty;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
	public String getPostcode() {
		return postcode;
	}
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	public String getFamily() {
		return family;
	}
	public void setFamily(String family) {
		this.family = family;
	}
	public String getFamilyIncome() {
		return familyIncome;
	}
	public void setFamilyIncome(String familyIncome) {
		this.familyIncome = familyIncome;
	}
	public String getFileReceive() {
		return fileReceive;
	}
	public void setFileReceive(String fileReceive) {
		this.fileReceive = fileReceive;
	}
	public String getRequirements() {
		return requirements;
	}
	public void setRequirements(String requirements) {
		this.requirements = requirements;
	}
	public String getBizzScope() {
		return bizzScope;
	}
	public void setBizzScope(String bizzScope) {
		this.bizzScope = bizzScope;
	}
	public String getRegProvince() {
		return regProvince;
	}
	public void setRegProvince(String regProvince) {
		this.regProvince = regProvince;
	}
	public String getRegCity() {
		return regCity;
	}
	public void setRegCity(String regCity) {
		this.regCity = regCity;
	}
	public String getRegCounty() {
		return regCounty;
	}
	public void setRegCounty(String regCounty) {
		this.regCounty = regCounty;
	}
	public String getRegAddress() {
		return regAddress;
	}
	public void setRegAddress(String regAddress) {
		this.regAddress = regAddress;
	}
	public String getCompanyType() {
		return companyType;
	}
	public void setCompanyType(String companyType) {
		this.companyType = companyType;
	}
	public String getTrustAssets() {
		return trustAssets;
	}
	public void setTrustAssets(String trustAssets) {
		this.trustAssets = trustAssets;
	}
	public String getTrustSettlor() {
		return trustSettlor;
	}
	public void setTrustSettlor(String trustSettlor) {
		this.trustSettlor = trustSettlor;
	}
	public String getTrustSettlorPhone() {
		return trustSettlorPhone;
	}
	public void setTrustSettlorPhone(String trustSettlorPhone) {
		this.trustSettlorPhone = trustSettlorPhone;
	}
	public String getTrustBenefit() {
		return trustBenefit;
	}
	public void setTrustBenefit(String trustBenefit) {
		this.trustBenefit = trustBenefit;
	}
	public String getTrustBenefitPhone() {
		return trustBenefitPhone;
	}
	public void setTrustBenefitPhone(String trustBenefitPhone) {
		this.trustBenefitPhone = trustBenefitPhone;
	}
	public String getCustomerManager() {
		return customerManager;
	}
	public void setCustomerManager(String customerManager) {
		this.customerManager = customerManager;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public java.sql.Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(java.sql.Timestamp createTime) {
		this.createTime = createTime;
	}
	public java.sql.Timestamp getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(java.sql.Timestamp updateTime) {
		this.updateTime = updateTime;
	}
	public java.sql.Timestamp getAppTime() {
		return appTime;
	}
	public void setAppTime(java.sql.Timestamp appTime) {
		this.appTime = appTime;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getContactType() {
		return contactType;
	}
	public void setContactType(String contactType) {
		this.contactType = contactType;
	}
	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	public String getContactEngName() {
		return contactEngName;
	}
	public void setContactEngName(String contactEngName) {
		this.contactEngName = contactEngName;
	}
	public String getContactGender() {
		return contactGender;
	}
	public void setContactGender(String contactGender) {
		this.contactGender = contactGender;
	}
	public java.util.Date getContactBirthday() {
		return contactBirthday;
	}
	public void setContactBirthday(java.util.Date contactBirthday) {
		this.contactBirthday = contactBirthday;
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
	public java.util.Date getContactIdExpiryDate() {
		return contactIdExpiryDate;
	}
	public void setContactIdExpiryDate(java.util.Date contactIdExpiryDate) {
		this.contactIdExpiryDate = contactIdExpiryDate;
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
	public String getContactRelation() {
		return contactRelation;
	}
	public void setContactRelation(String contactRelation) {
		this.contactRelation = contactRelation;
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
	public String[] getInterests() {
		return interests;
	}
	public void setInterests(String[] interests) {
		this.interests = interests;
	}
	public String[] getActivities() {
		return activities;
	}
	public void setActivities(String[] activities) {
		this.activities = activities;
	}
	public String[] getRealEstate() {
		return realEstate;
	}
	public void setRealEstate(String[] realEstate) {
		this.realEstate = realEstate;
	}
	public String[] getInvestmentLevel() {
		return investmentLevel;
	}
	public void setInvestmentLevel(String[] investmentLevel) {
		this.investmentLevel = investmentLevel;
	}
	public String[] getInvestmentSource() {
		return investmentSource;
	}
	public void setInvestmentSource(String[] investmentSource) {
		this.investmentSource = investmentSource;
	}
	public String[] getInvestmentExp() {
		return investmentExp;
	}
	public void setInvestmentExp(String[] investmentExp) {
		this.investmentExp = investmentExp;
	}
	public String[] getInvestmentProduct() {
		return investmentProduct;
	}
	public void setInvestmentProduct(String[] investmentProduct) {
		this.investmentProduct = investmentProduct;
	}
	public String[] getInvestmentIncome() {
		return investmentIncome;
	}
	public void setInvestmentIncome(String[] investmentIncome) {
		this.investmentIncome = investmentIncome;
	}
	public String[] getFollowType() {
		return followType;
	}
	public void setFollowType(String[] followType) {
		this.followType = followType;
	}
	public String[] getFollowPoint() {
		return followPoint;
	}
	public void setFollowPoint(String[] followPoint) {
		this.followPoint = followPoint;
	}
	public String[] getPreferenceGoal() {
		return preferenceGoal;
	}
	public void setPreferenceGoal(String[] preferenceGoal) {
		this.preferenceGoal = preferenceGoal;
	}
	public String[] getPreferencePeriod() {
		return preferencePeriod;
	}
	public void setPreferencePeriod(String[] preferencePeriod) {
		this.preferencePeriod = preferencePeriod;
	}
	public String getPreferenceDecision() {
		return preferenceDecision;
	}
	public void setPreferenceDecision(String preferenceDecision) {
		this.preferenceDecision = preferenceDecision;
	}
	public String getPreferenceRisk() {
		return preferenceRisk;
	}
	public void setPreferenceRisk(String preferenceRisk) {
		this.preferenceRisk = preferenceRisk;
	}
	public String[] getPreferenceProduct() {
		return preferenceProduct;
	}
	public void setPreferenceProduct(String[] preferenceProduct) {
		this.preferenceProduct = preferenceProduct;
	}
	public String[] getPreferenceAmount() {
		return preferenceAmount;
	}
	public void setPreferenceAmount(String[] preferenceAmount) {
		this.preferenceAmount = preferenceAmount;
	}
	public String getFarenName() {
		return farenName;
	}
	public void setFarenName(String farenName) {
		this.farenName = farenName;
	}
	public String getFarenIdType() {
		return farenIdType;
	}
	public void setFarenIdType(String farenIdType) {
		this.farenIdType = farenIdType;
	}
	public String getFarenIdNo() {
		return farenIdNo;
	}
	public void setFarenIdNo(String farenIdNo) {
		this.farenIdNo = farenIdNo;
	}
	public java.util.Date getFarenIdIssueDate() {
		return farenIdIssueDate;
	}
	public void setFarenIdIssueDate(java.util.Date farenIdIssueDate) {
		this.farenIdIssueDate = farenIdIssueDate;
	}
	public String getFarenIssueGov() {
		return farenIssueGov;
	}
	public void setFarenIssueGov(String farenIssueGov) {
		this.farenIssueGov = farenIssueGov;
	}
	public String getCfoName() {
		return cfoName;
	}
	public void setCfoName(String cfoName) {
		this.cfoName = cfoName;
	}
	public String getCfoIdType() {
		return cfoIdType;
	}
	public void setCfoIdType(String cfoIdType) {
		this.cfoIdType = cfoIdType;
	}
	public String getCfoIdNo() {
		return cfoIdNo;
	}
	public void setCfoIdNo(String cfoIdNo) {
		this.cfoIdNo = cfoIdNo;
	}
	public java.util.Date getCfoIdIssueDate() {
		return cfoIdIssueDate;
	}
	public void setCfoIdIssueDate(java.util.Date cfoIdIssueDate) {
		this.cfoIdIssueDate = cfoIdIssueDate;
	}
	public String getCfoIssueGov() {
		return cfoIssueGov;
	}
	public void setCfoIssueGov(String cfoIssueGov) {
		this.cfoIssueGov = cfoIssueGov;
	}
	public String getKongzhiName() {
		return kongzhiName;
	}
	public void setKongzhiName(String kongzhiName) {
		this.kongzhiName = kongzhiName;
	}
	public String getKongzhiIdType() {
		return kongzhiIdType;
	}
	public void setKongzhiIdType(String kongzhiIdType) {
		this.kongzhiIdType = kongzhiIdType;
	}
	public String getKongzhiIdNo() {
		return kongzhiIdNo;
	}
	public void setKongzhiIdNo(String kongzhiIdNo) {
		this.kongzhiIdNo = kongzhiIdNo;
	}
	public java.util.Date getKongzhiIdIssueDate() {
		return kongzhiIdIssueDate;
	}
	public void setKongzhiIdIssueDate(java.util.Date kongzhiIdIssueDate) {
		this.kongzhiIdIssueDate = kongzhiIdIssueDate;
	}
	public String getKongzhiIssueGov() {
		return kongzhiIssueGov;
	}
	public void setKongzhiIssueGov(String kongzhiIssueGov) {
		this.kongzhiIssueGov = kongzhiIssueGov;
	}
	public int getContactId() {
		return contactId;
	}
	public void setContactId(int contactId) {
		this.contactId = contactId;
	}
	public int getFarenId() {
		return farenId;
	}
	public void setFarenId(int farenId) {
		this.farenId = farenId;
	}
	public int getCfoId() {
		return cfoId;
	}
	public void setCfoId(int cfoId) {
		this.cfoId = cfoId;
	}
	public void setKongzhiId(int kongzhiId) {
		this.kongzhiId = kongzhiId;
	}
	public int getKongzhiId() {
		return kongzhiId;
	}


}


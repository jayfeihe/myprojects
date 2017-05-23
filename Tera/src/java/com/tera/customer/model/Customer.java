package com.tera.customer.model;

import com.tera.util.DateUtils;

/**
 * 
 * <br>
 * <b>功能：</b>CustomerDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-05-30 16:22:42<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public class Customer {

	private int id;//ID	private String customerNo;//客户编号	private String name;//姓名/机构全称	private String shortName;//简称	private String customerType;//客户类型
	
	private String customerTypeName;//字典值
		private String customerLever;//客户等级	private String engName;//英文名称	private String gender;//性别	private java.util.Date birthday;//生日	private String birthdayStr;//生日	private String nationality;//国籍	private String language;//语言	private String motherFiratName;//母亲姓氏	private String marriage;//婚姻	private String idType;//证件类型
	
	private String idTypeName;//证件类型名称
		private String idNo;//证件号码	private java.util.Date idIssueDate;//签发日期	private String idIssueDateStr;//签发日期	private java.util.Date idExpiryDate;//失效日期	private String idExpiryDateStr;//失效日期	private String idIssueGov;//签发机关	private String education;//学历	private String job;//职业	private String industry1;//行业代码1	private String industry2;//行业代码2	private String industry3;//行业代码3	private String companyName;//单位名称	private int workYears;//工作年限	private String companyScale;//单位规模	private String jobDuty;//职务	private String phone;//固定电话	private String mobile;//移动电话	private String email;//EMAIL	private String addProvince;//通讯地址-省	private String addCity;//通讯地址-市	private String addCounty;//通讯地址-区县	private String address;//通讯地址	private String postcode;//邮编	private String family;//家庭情况	private String familyIncome;//家庭收入	private String fileReceive;//文件接收方式	private String requirements;//资源需求	private String bizzScope;//经营范围	private String regProvince;//注册地址-省	private String regCity;//注册地址-市	private String regCounty;//注册地址-区县	private String regAddress;//注册地址	private String companyType;//企业性质	private String trustAssets;//信托资产	private String trustSettlor;//信托委托人	private String trustSettlorPhone;//信托委托人电话	private String trustBenefit;//信托受益人	private String trustBenefitPhone;//信托受益人电话	private String customerManager;//客户经理	private String operator;//操作员	private String orgId;//所属机构	private java.sql.Timestamp createTime;//创建日期	private String createTimeStr;//创建日期	private java.sql.Timestamp updateTime;//修改日期	private String updateTimeStr;//修改日期	private java.sql.Timestamp appTime;//提交日期	private String appTimeStr;//提交日期	private String state;//状态		public int getId() {		return this.id;	}	public void setId(int id) {		this.id=id;	}	public String getCustomerNo() {		return this.customerNo;	}	public void setCustomerNo(String customerNo) {		this.customerNo=customerNo;	}	public String getName() {		return this.name;	}	public void setName(String name) {		this.name=name;	}	public String getShortName() {		return this.shortName;	}	public void setShortName(String shortName) {		this.shortName=shortName;	}	public String getCustomerType() {		return this.customerType;	}	public void setCustomerType(String customerType) {		this.customerType=customerType;	}
	public String getCustomerLever() {
		return customerLever;
	}
	public void setCustomerLever(String customerLever) {
		this.customerLever = customerLever;
	}
	public String getEngName() {		return this.engName;	}	public void setEngName(String engName) {		this.engName=engName;	}	public String getGender() {		return this.gender;	}	public void setGender(String gender) {		this.gender=gender;	}	public java.util.Date getBirthday() {		return this.birthday;	}	public void setBirthday(java.util.Date birthday) {		this.birthday=birthday;	}	public String getNationality() {		return this.nationality;	}	public void setNationality(String nationality) {		this.nationality=nationality;	}	public String getLanguage() {		return this.language;	}	public void setLanguage(String language) {		this.language=language;	}	public String getMotherFiratName() {		return this.motherFiratName;	}	public void setMotherFiratName(String motherFiratName) {		this.motherFiratName=motherFiratName;	}	public String getMarriage() {		return this.marriage;	}	public void setMarriage(String marriage) {		this.marriage=marriage;	}	public String getIdType() {		return this.idType;	}	public void setIdType(String idType) {		this.idType=idType;	}	public String getIdNo() {		return this.idNo;	}	public void setIdNo(String idNo) {		this.idNo=idNo;	}	public java.util.Date getIdIssueDate() {		return this.idIssueDate;	}	public void setIdIssueDate(java.util.Date idIssueDate) {		this.idIssueDate=idIssueDate;	}	public java.util.Date getIdExpiryDate() {		return this.idExpiryDate;	}	public void setIdExpiryDate(java.util.Date idExpiryDate) {		this.idExpiryDate=idExpiryDate;	}	public String getIdIssueGov() {		return this.idIssueGov;	}	public void setIdIssueGov(String idIssueGov) {		this.idIssueGov=idIssueGov;	}	public String getEducation() {		return this.education;	}	public void setEducation(String education) {		this.education=education;	}	public String getJob() {		return this.job;	}	public void setJob(String job) {		this.job=job;	}	public String getIndustry1() {		return this.industry1;	}	public void setIndustry1(String industry1) {		this.industry1=industry1;	}	public String getIndustry2() {		return this.industry2;	}	public void setIndustry2(String industry2) {		this.industry2=industry2;	}	public String getIndustry3() {		return this.industry3;	}	public void setIndustry3(String industry3) {		this.industry3=industry3;	}	public String getCompanyName() {		return this.companyName;	}	public void setCompanyName(String companyName) {		this.companyName=companyName;	}	public int getWorkYears() {		return this.workYears;	}	public void setWorkYears(int workYears) {		this.workYears=workYears;	}	public String getCompanyScale() {		return this.companyScale;	}	public void setCompanyScale(String companyScale) {		this.companyScale=companyScale;	}	public String getJobDuty() {		return this.jobDuty;	}	public void setJobDuty(String jobDuty) {		this.jobDuty=jobDuty;	}	public String getPhone() {		return this.phone;	}	public void setPhone(String phone) {		this.phone=phone;	}	public String getMobile() {		return this.mobile;	}	public void setMobile(String mobile) {		this.mobile=mobile;	}	public String getEmail() {		return this.email;	}	public void setEmail(String email) {		this.email=email;	}	public String getAddProvince() {		return this.addProvince;	}	public void setAddProvince(String addProvince) {		this.addProvince=addProvince;	}	public String getAddCity() {		return this.addCity;	}	public void setAddCity(String addCity) {		this.addCity=addCity;	}	public String getAddCounty() {		return this.addCounty;	}	public void setAddCounty(String addCounty) {		this.addCounty=addCounty;	}	public String getAddress() {		return this.address;	}	public void setAddress(String address) {		this.address=address;	}	public String getPostcode() {		return this.postcode;	}	public void setPostcode(String postcode) {		this.postcode=postcode;	}	public String getFamily() {		return this.family;	}	public void setFamily(String family) {		this.family=family;	}	public String getFamilyIncome() {		return this.familyIncome;	}	public void setFamilyIncome(String familyIncome) {		this.familyIncome=familyIncome;	}	public String getFileReceive() {		return this.fileReceive;	}	public void setFileReceive(String fileReceive) {		this.fileReceive=fileReceive;	}	public String getRequirements() {		return this.requirements;	}	public void setRequirements(String requirements) {		this.requirements=requirements;	}	public String getBizzScope() {		return this.bizzScope;	}	public void setBizzScope(String bizzScope) {		this.bizzScope=bizzScope;	}	public String getRegProvince() {		return this.regProvince;	}	public void setRegProvince(String regProvince) {		this.regProvince=regProvince;	}	public String getRegCity() {		return this.regCity;	}	public void setRegCity(String regCity) {		this.regCity=regCity;	}	public String getRegCounty() {		return this.regCounty;	}	public void setRegCounty(String regCounty) {		this.regCounty=regCounty;	}	public String getRegAddress() {		return this.regAddress;	}	public void setRegAddress(String regAddress) {		this.regAddress=regAddress;	}	public String getCompanyType() {		return this.companyType;	}	public void setCompanyType(String companyType) {		this.companyType=companyType;	}	public String getTrustAssets() {		return this.trustAssets;	}	public void setTrustAssets(String trustAssets) {		this.trustAssets=trustAssets;	}	public String getTrustSettlor() {		return this.trustSettlor;	}	public void setTrustSettlor(String trustSettlor) {		this.trustSettlor=trustSettlor;	}	public String getTrustSettlorPhone() {		return this.trustSettlorPhone;	}	public void setTrustSettlorPhone(String trustSettlorPhone) {		this.trustSettlorPhone=trustSettlorPhone;	}	public String getTrustBenefit() {		return this.trustBenefit;	}	public void setTrustBenefit(String trustBenefit) {		this.trustBenefit=trustBenefit;	}	public String getTrustBenefitPhone() {		return this.trustBenefitPhone;	}	public void setTrustBenefitPhone(String trustBenefitPhone) {		this.trustBenefitPhone=trustBenefitPhone;	}	public String getCustomerManager() {		return this.customerManager;	}	public void setCustomerManager(String customerManager) {		this.customerManager=customerManager;	}	public String getOperator() {		return this.operator;	}	public void setOperator(String operator) {		this.operator=operator;	}	public String getOrgId() {		return this.orgId;	}	public void setOrgId(String orgId) {		this.orgId=orgId;	}	public java.sql.Timestamp getCreateTime() {		return this.createTime;	}	public void setCreateTime(java.sql.Timestamp createTime) {		this.createTime=createTime;	}	public java.sql.Timestamp getUpdateTime() {		return this.updateTime;	}	public void setUpdateTime(java.sql.Timestamp updateTime) {		this.updateTime=updateTime;	}	public java.sql.Timestamp getAppTime() {		return this.appTime;	}	public void setAppTime(java.sql.Timestamp appTime) {		this.appTime=appTime;	}	public String getState() {		return this.state;	}	public void setState(String state) {		this.state=state;	}
	public String getBirthdayStr() {
		return DateUtils.formatDate(this.birthday);
	}
	public String getIdIssueDateStr() {
		return DateUtils.formatDate(this.idIssueDate);
	}
	public String getIdExpiryDateStr() {
		return DateUtils.formatDate(this.idExpiryDate);
	}
	public String getCreateTimeStr() {
		return DateUtils.formatTime(this.createTime);
	}
	public String getUpdateTimeStr() {
		return DateUtils.formatTime(this.updateTime);
	}
	public String getAppTimeStr() {
		return DateUtils.formatTime(this.appTime);
	}
	public void setBirthdayStr(String birthdayStr) {
		this.birthdayStr = birthdayStr;
	}
	public void setIdIssueDateStr(String idIssueDateStr) {
		this.idIssueDateStr = idIssueDateStr;
	}
	public void setIdExpiryDateStr(String idExpiryDateStr) {
		this.idExpiryDateStr = idExpiryDateStr;
	}
	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}
	public void setUpdateTimeStr(String updateTimeStr) {
		this.updateTimeStr = updateTimeStr;
	}
	public void setAppTimeStr(String appTimeStr) {
		this.appTimeStr = appTimeStr;
	}
	public String getCustomerTypeName() {
		return customerTypeName;
	}
	public void setCustomerTypeName(String customerTypeName) {
		this.customerTypeName = customerTypeName;
	}
	public String getIdTypeName() {
		return idTypeName;
	}
	public void setIdTypeName(String idTypeName) {
		this.idTypeName = idTypeName;
	}

}


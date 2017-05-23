package com.tera.interfaces.model;

/**
 * 借款人债权人信息 包含机构
 * @author Jesse
 *
 */
public class PushDebtPer {
	

	//公共信息
	private String borrow_type;//1个人2企业
	private String name;//姓名
	private String phone;//电话
	private String address;//地址
	//个人信息
	private String identity_number;//借款人身份证
	private String sex;
	private String age;
	private String province;
	private String city;
	private String marrage;//１.已婚２.未婚
	private String education;//1小学2初中3 高中4专科5中专6大专7本科8大学9 硕士

	//企业信息
	
	private String organization_code;//企业注册时代码机构
	private String license_number;//企业工商执照
	private String legal_person_name;//法人姓名
	private String legal_person_identity;//身份证15或者18位
	private String period;//企业经营年限
	private String annual_sales;//企业年营业额
	private String premises;//企业经营场所
	
	public String getBorrow_type() {
		return borrow_type;
	}
	public void setBorrow_type(String borrowType) {
		borrow_type = borrowType;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getIdentity_number() {
		return identity_number;
	}
	public void setIdentity_number(String identityNumber) {
		identity_number = identityNumber;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getMarrage() {
		return marrage;
	}
	public void setMarrage(String marrage) {
		this.marrage = marrage;
	}
	public String getEducation() {
		return education;
	}
	public void setEducation(String education) {
		this.education = education;
	}
	public String getOrganization_code() {
		return organization_code;
	}
	public void setOrganization_code(String organizationCode) {
		organization_code = organizationCode;
	}
	public String getLicense_number() {
		return license_number;
	}
	public void setLicense_number(String licenseNumber) {
		license_number = licenseNumber;
	}
	public String getLegal_person_name() {
		return legal_person_name;
	}
	public void setLegal_person_name(String legalPersonName) {
		legal_person_name = legalPersonName;
	}
	public String getLegal_person_identity() {
		return legal_person_identity;
	}
	public void setLegal_person_identity(String legalPersonIdentity) {
		legal_person_identity = legalPersonIdentity;
	}
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	public String getAnnual_sales() {
		return annual_sales;
	}
	public void setAnnual_sales(String annualSales) {
		annual_sales = annualSales;
	}
	public String getPremises() {
		return premises;
	}
	public void setPremises(String premises) {
		this.premises = premises;
	}
	

}

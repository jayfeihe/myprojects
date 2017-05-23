package com.tera.customer.model;

import com.tera.util.DateUtils;

/**
 * 
 * <br>
 * <b>功能：</b>ContactDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-06-13 12:39:36<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public class Contact {

	//属性部分
	private int id; //ID
	private int customerId; //客户ID
	private String type; //联系人类型
	private String name; //姓名
	private String engName; //英文名
	private String gender; //性别
	private java.util.Date birthday; //生日
	private String birthdayStr; //生日
	private String idType; //证件类型
	private String idNo; //证件号码
	private java.util.Date idIssueDate; //签发日期
	private String idIssueDateStr; //签发日期
	private java.util.Date idExpiryDate; //有效期
	private String idExpiryDateStr; //有效期
	private String idIssueGov; //签发机关
	private String mobile; //移动电话
	private String phone; //固定电话
	private String email; //email
	private String relation; //关系
	private String addProvince; //省
	private String addCity; //市
	private String addCounty; //区县
	private String address; //地址
	private String postcode; //邮编
	private String operator; //操作员
	private String orgId; //所属机构
	private java.sql.Timestamp createTime; //创建日期
	private String createTimeStr; //创建日期
	private java.sql.Timestamp updateTime; //修改日期
	private String updateTimeStr; //修改日期

	//getter部分
	public int getId () {
		return this.id;
	}
	public int getCustomerId () {
		return this.customerId;
	}
	public String getType () {
		return this.type;
	}
	public String getName () {
		return this.name;
	}
	public String getEngName () {
		return this.engName;
	}
	public String getGender () {
		return this.gender;
	}
	public java.util.Date getBirthday () {
		return this.birthday;
	}
	//getter部分,Date类型的修改获取String的方法
	public String getBirthdayStr () {
		return DateUtils.formatDate(this.birthday);
	}
	public String getIdType () {
		return this.idType;
	}
	public String getIdNo () {
		return this.idNo;
	}
	public java.util.Date getIdIssueDate () {
		return this.idIssueDate;
	}
	//getter部分,Date类型的修改获取String的方法
	public String getIdIssueDateStr () {
		return DateUtils.formatDate(this.idIssueDate);
	}
	public java.util.Date getIdExpiryDate () {
		return this.idExpiryDate;
	}
	//getter部分,Date类型的修改获取String的方法
	public String getIdExpiryDateStr () {
		return DateUtils.formatDate(this.idExpiryDate);
	}
	public String getIdIssueGov () {
		return this.idIssueGov;
	}
	public String getMobile () {
		return this.mobile;
	}
	public String getPhone () {
		return this.phone;
	}
	public String getEmail () {
		return this.email;
	}
	public String getRelation () {
		return this.relation;
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
	public String getPostcode () {
		return this.postcode;
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
	public void setId (int id) {
		this.id=id;
	}
	public void setCustomerId (int customerId) {
		this.customerId=customerId;
	}
	public void setType (String type) {
		this.type=type;
	}
	public void setName (String name) {
		this.name=name;
	}
	public void setEngName (String engName) {
		this.engName=engName;
	}
	public void setGender (String gender) {
		this.gender=gender;
	}
	public void setBirthday (java.util.Date birthday) {
		this.birthday=birthday;
	}
	public void setBirthdayStr (String birthdayStr) {
		this.birthdayStr=birthdayStr;
	}
	public void setIdType (String idType) {
		this.idType=idType;
	}
	public void setIdNo (String idNo) {
		this.idNo=idNo;
	}
	public void setIdIssueDate (java.util.Date idIssueDate) {
		this.idIssueDate=idIssueDate;
	}
	public void setIdIssueDateStr (String idIssueDateStr) {
		this.idIssueDateStr=idIssueDateStr;
	}
	public void setIdExpiryDate (java.util.Date idExpiryDate) {
		this.idExpiryDate=idExpiryDate;
	}
	public void setIdExpiryDateStr (String idExpiryDateStr) {
		this.idExpiryDateStr=idExpiryDateStr;
	}
	public void setIdIssueGov (String idIssueGov) {
		this.idIssueGov=idIssueGov;
	}
	public void setMobile (String mobile) {
		this.mobile=mobile;
	}
	public void setPhone (String phone) {
		this.phone=phone;
	}
	public void setEmail (String email) {
		this.email=email;
	}
	public void setRelation (String relation) {
		this.relation=relation;
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
	public void setPostcode (String postcode) {
		this.postcode=postcode;
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


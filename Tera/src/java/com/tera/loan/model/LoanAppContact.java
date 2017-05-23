package com.tera.loan.model;

import com.tera.util.DateUtils;

/**
 * 
 * <br>
 * <b>功能：</b>LoanAppContactDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-06-16 17:25:59<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public class LoanAppContact {

	//属性部分
	private int id; //ID
	private String appId; //申请ID
	private String customerNo; //客户ID
	private String mainFlag; //主借款人标识
	private String type; //联系人类型-个人/单位
	private String contactType; //联系人类型
	private int collateralId; //抵押物序号
	private String name; //姓名
	private String gender; //性别
	private String marriage; //婚姻
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
	private String reason; //关系
	private String addProvice; //省
	private String addCity; //市
	private String addCounty; //区县
	private String address; //地址
	private String postcode; //邮编
	private String name2; //姓名2
	private String gender2; //性别2
	private String idType2; //证件类型2
	private String idNo2; //证件号码2
	private java.util.Date idIssueDate2; //签发日期2
	private String idIssueDate2Str; //签发日期2
	private String phone2; //固定电话2
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
	public String getAppId () {
		return this.appId;
	}
	public String getCustomerNo () {
		return this.customerNo;
	}
	public String getMainFlag () {
		return this.mainFlag;
	}
	public String getType () {
		return this.type;
	}
	public String getContactType () {
		return this.contactType;
	}
	public int getCollateralId () {
		return this.collateralId;
	}
	public String getName () {
		return this.name;
	}
	public String getGender () {
		return this.gender;
	}
	public String getMarriage () {
		return this.marriage;
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
	public String getReason () {
		return this.reason;
	}
	public String getAddProvice () {
		return this.addProvice;
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
	public String getName2 () {
		return this.name2;
	}
	public String getGender2 () {
		return this.gender2;
	}
	public String getIdType2 () {
		return this.idType2;
	}
	public String getIdNo2 () {
		return this.idNo2;
	}
	public java.util.Date getIdIssueDate2 () {
		return this.idIssueDate2;
	}
	//getter部分,Date类型的修改获取String的方法
	public String getIdIssueDate2Str () {
		return DateUtils.formatDate(this.idIssueDate2);
	}
	public String getPhone2 () {
		return this.phone2;
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
	public void setAppId (String appId) {
		this.appId=appId;
	}
	public void setCustomerNo (String customerNo) {
		this.customerNo=customerNo;
	}
	public void setMainFlag (String mainFlag) {
		this.mainFlag=mainFlag;
	}
	public void setType (String type) {
		this.type=type;
	}
	public void setContactType (String contactType) {
		this.contactType=contactType;
	}
	public void setCollateralId (int collateralId) {
		this.collateralId=collateralId;
	}
	public void setName (String name) {
		this.name=name;
	}
	public void setGender (String gender) {
		this.gender=gender;
	}
	public void setMarriage (String marriage) {
		this.marriage=marriage;
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
	public void setReason (String reason) {
		this.reason=reason;
	}
	public void setAddProvice (String addProvice) {
		this.addProvice=addProvice;
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
	public void setName2 (String name2) {
		this.name2=name2;
	}
	public void setGender2 (String gender2) {
		this.gender2=gender2;
	}
	public void setIdType2 (String idType2) {
		this.idType2=idType2;
	}
	public void setIdNo2 (String idNo2) {
		this.idNo2=idNo2;
	}
	public void setIdIssueDate2 (java.util.Date idIssueDate2) {
		this.idIssueDate2=idIssueDate2;
	}
	public void setIdIssueDate2Str (String idIssueDate2Str) {
		this.idIssueDate2Str=idIssueDate2Str;
	}
	public void setPhone2 (String phone2) {
		this.phone2=phone2;
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


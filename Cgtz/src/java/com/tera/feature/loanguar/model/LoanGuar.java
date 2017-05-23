package com.tera.feature.loanguar.model;

import com.tera.util.DateUtils;

/**
 * 
 * T_LOAN_GUAR实体类
 * <b>功能：</b>LoanGuarDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-01-06 13:51:35<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public class LoanGuar {

	//属性部分
	private int id; //ID
	private String loanId; //申请ID
	private String type; //类型(个人，机构)
	private String name; //姓名、机构名称
	private String sex; //性别
	private String idType; //证件类型
	private java.util.Date validType; //有效期
	private String validTypeStr; //有效期
	private String idNo; //证件号码
	private String tel; //手机号
	private String tel2; //备用手机号
	private String marriage; //婚姻状况
	private String edu; //学历
	private String homePrvn; //户籍、注册所在省
	private String homeCity; //户籍，注册所在市
	private String homeCtry; //户籍，注册所在县
	private String homeAddr; //户籍，注册地址
	private String nowPrvn; //现居地，经营所在省
	private String nowCity; //现居地，经营所在市
	private String nowCtry; //现居地，经营所在县
	private String nowAddr; //现居地，经营地址
	private java.util.Date orgRegTime; //机构注册时间
	private String orgRegTimeStr; //机构注册时间
	private double orgRegAmt; //机构注册资本
	private String legalName; //法人姓名
	private String legalTel; //法人手机号
	private String legalIdType; //法人证件类型
	private String legalIdNo; //法人证件号码
	private String shareName; //股东姓名
	private String shareTel; //股东手机号
	private String shareIdType; //股东证件类型
	private String shareIdNo; //股东证件号码
	private String orgBus; //机构经营范围
	private String saleRemark; //业务员备注
	private String lawState; //诉讼情况
	private String lawRemark; //诉讼说明
	private String lawCheckState; //诉讼复核
	private String lawCheckRemark; //诉讼复核说明
	private String createUid; //
	private java.sql.Timestamp createTime; //
	private String createTimeStr; //
	private String updateUid; //
	private java.sql.Timestamp updateTime; //
	private String updateTimeStr; //
	private String createName;
	private String updateName;

	private String isOrig; // 是否是原先申请标识
	
	//getter部分
	public int getId () {
		return this.id;
	}
	public String getLoanId () {
		return this.loanId;
	}
	public String getType () {
		return this.type;
	}
	public String getName () {
		return this.name;
	}
	public String getSex () {
		return this.sex;
	}
	public String getIdType () {
		return this.idType;
	}
	public java.util.Date getValidType () {
		return this.validType;
	}
	//getter部分,Date类型的修改获取String的方法
	public String getValidTypeStr () {
		return DateUtils.formatDate(this.validType);
	}
	public String getIdNo () {
		return this.idNo;
	}
	public String getTel () {
		return this.tel;
	}
	public String getTel2 () {
		return this.tel2;
	}
	public String getMarriage () {
		return this.marriage;
	}
	public String getEdu () {
		return this.edu;
	}
	public String getHomePrvn () {
		return this.homePrvn;
	}
	public String getHomeCity () {
		return this.homeCity;
	}
	public String getHomeCtry () {
		return this.homeCtry;
	}
	public String getHomeAddr () {
		return this.homeAddr;
	}
	public String getNowPrvn () {
		return this.nowPrvn;
	}
	public String getNowCity () {
		return this.nowCity;
	}
	public String getNowCtry () {
		return this.nowCtry;
	}
	public String getNowAddr () {
		return this.nowAddr;
	}
	public java.util.Date getOrgRegTime () {
		return this.orgRegTime;
	}
	//getter部分,Date类型的修改获取String的方法
	public String getOrgRegTimeStr () {
		return DateUtils.formatDate(this.orgRegTime);
	}
	public double getOrgRegAmt () {
		return this.orgRegAmt;
	}
	public String getLegalName () {
		return this.legalName;
	}
	public String getLegalTel () {
		return this.legalTel;
	}
	public String getLegalIdType () {
		return this.legalIdType;
	}
	public String getLegalIdNo () {
		return this.legalIdNo;
	}
	public String getShareName () {
		return this.shareName;
	}
	public String getShareTel () {
		return this.shareTel;
	}
	public String getShareIdType () {
		return this.shareIdType;
	}
	public String getShareIdNo () {
		return this.shareIdNo;
	}
	public String getOrgBus () {
		return this.orgBus;
	}
	public String getSaleRemark () {
		return this.saleRemark;
	}
	public String getLawState () {
		return this.lawState;
	}
	public String getLawRemark () {
		return this.lawRemark;
	}
	public String getLawCheckState () {
		return this.lawCheckState;
	}
	public String getLawCheckRemark () {
		return this.lawCheckRemark;
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
    
	
	public String getCreateName() {
		return createName;
	}
	public String getUpdateName() {
		return updateName;
	}
	//setter部分
	public void setId (int id) {
		this.id=id;
	}
	public void setLoanId (String loanId) {
		this.loanId=loanId;
	}
	public void setType (String type) {
		this.type=type;
	}
	public void setName (String name) {
		this.name=name;
	}
	public void setSex (String sex) {
		this.sex=sex;
	}
	public void setIdType (String idType) {
		this.idType=idType;
	}
	public void setValidType (java.util.Date validType) {
		this.validType=validType;
	}
	public void setValidTypeStr (String validTypeStr) {
		this.validTypeStr=validTypeStr;
	}
	public void setIdNo (String idNo) {
		this.idNo=idNo;
	}
	public void setTel (String tel) {
		this.tel=tel;
	}
	public void setTel2 (String tel2) {
		this.tel2=tel2;
	}
	public void setMarriage (String marriage) {
		this.marriage=marriage;
	}
	public void setEdu (String edu) {
		this.edu=edu;
	}
	public void setHomePrvn (String homePrvn) {
		this.homePrvn=homePrvn;
	}
	public void setHomeCity (String homeCity) {
		this.homeCity=homeCity;
	}
	public void setHomeCtry (String homeCtry) {
		this.homeCtry=homeCtry;
	}
	public void setHomeAddr (String homeAddr) {
		this.homeAddr=homeAddr;
	}
	public void setNowPrvn (String nowPrvn) {
		this.nowPrvn=nowPrvn;
	}
	public void setNowCity (String nowCity) {
		this.nowCity=nowCity;
	}
	public void setNowCtry (String nowCtry) {
		this.nowCtry=nowCtry;
	}
	public void setNowAddr (String nowAddr) {
		this.nowAddr=nowAddr;
	}
	public void setOrgRegTime (java.util.Date orgRegTime) {
		this.orgRegTime=orgRegTime;
	}
	public void setOrgRegTimeStr (String orgRegTimeStr) {
		this.orgRegTimeStr=orgRegTimeStr;
	}
	public void setOrgRegAmt (double orgRegAmt) {
		this.orgRegAmt=orgRegAmt;
	}
	public void setLegalName (String legalName) {
		this.legalName=legalName;
	}
	public void setLegalTel (String legalTel) {
		this.legalTel=legalTel;
	}
	public void setLegalIdType (String legalIdType) {
		this.legalIdType=legalIdType;
	}
	public void setLegalIdNo (String legalIdNo) {
		this.legalIdNo=legalIdNo;
	}
	public void setShareName (String shareName) {
		this.shareName=shareName;
	}
	public void setShareTel (String shareTel) {
		this.shareTel=shareTel;
	}
	public void setShareIdType (String shareIdType) {
		this.shareIdType=shareIdType;
	}
	public void setShareIdNo (String shareIdNo) {
		this.shareIdNo=shareIdNo;
	}
	public void setOrgBus (String orgBus) {
		this.orgBus=orgBus;
	}
	public void setSaleRemark (String saleRemark) {
		this.saleRemark=saleRemark;
	}
	public void setLawState (String lawState) {
		this.lawState=lawState;
	}
	public void setLawRemark (String lawRemark) {
		this.lawRemark=lawRemark;
	}
	public void setLawCheckState (String lawCheckState) {
		this.lawCheckState=lawCheckState;
	}
	public void setLawCheckRemark (String lawCheckRemark) {
		this.lawCheckRemark=lawCheckRemark;
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
	public String getIsOrig() {
		return isOrig;
	}
	public void setIsOrig(String isOrig) {
		this.isOrig = isOrig;
	}
	public void setCreateName(String createName) {
		this.createName = createName;
	}
	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}
	
}


package com.tera.audit.loan.model;

import com.tera.util.DateUtils;

/**
 * 
 * T_LOAN_APP实体类
 * <b>功能：</b>LoanAppDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-01-05 10:52:19<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public class LoanApp {

	//属性部分
	private int id; //ID
	private String loanId; //申请ID
	private String mainFlag; //主借款人标识
	private String name; //姓名、机构名称
	private String sex; //性别
	private String idType; //证件类型
	private java.util.Date validDate; //有效期
	private String validDateStr; //有效期
	private String idNo; //证件号码
	private String tel; //手机号
	private String tel2; //备用手机号
	private String marriage; //婚姻状况
	private String hasChil; //子女有无
	private String chilNum; //子女几个
	private String edu; //学历
	private String live; //居住情况
	private String nativeHouse; //本市房产情况
	private String nativeTime; //本地居住时间
	private String homePrvn; //户籍、注册所在省
	private String homeCity; //户籍，注册所在市
	private String homeCtry; //户籍，注册所在县
	private String homeAddr; //户籍，注册地址
	private String nowPrvn; //现居地，经营所在省
	private String nowCity; //现居地，经营所在市
	private String nowCtry; //现居地，经营所在县
	private String nowAddr; //现居地，经营地址
	private String phone; //家庭固定电话
	private String school; //毕业院校
	private java.util.Date eduTime; //毕业时间
	private String eduTimeStr; //毕业时间
	private String wechat; //微信
	private String email; //邮箱
	private String qq; //QQ
	private String postcode; //邮编
	private String companyName; //单位名称
	private String companyType; //单位类型
	private String dept; //部门
	private java.util.Date inTime; //入职时间
	private String inTimeStr; //入职时间
	private String job; //职位
	private String companyPrvn; //单位所在省
	private String companyCity; //单位所在市
	private String companyCtry; //单位所在县
	private String companyAddr; //单位地址
	private String companyTel; //单位电话
	private double yearIncome; //年收入
	private double monthIncome; //月工作收入
	private double monthOther; //月其他收入
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
	private java.sql.Timestamp createTime; //创建时间
	private String createTimeStr; //创建时间
	private java.sql.Timestamp updateTime; //修改时间
	private String updateTimeStr; //修改时间
	private String ext1; //
	private String ext2; //

	private String age; // 年龄
	private int orgPeriod; // 经营年限
	private double orgSalesAmt; // 年经营额
	
	//getter部分
	public int getId () {
		return this.id;
	}
	public String getLoanId () {
		return this.loanId;
	}
	public String getMainFlag () {
		return this.mainFlag;
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
	public java.util.Date getValidDate () {
		return this.validDate;
	}
	//getter部分,Date类型的修改获取String的方法
	public String getValidDateStr () {
		return DateUtils.formatDate(this.validDate);
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
	public String getHasChil () {
		return this.hasChil;
	}
	public String getChilNum () {
		return this.chilNum;
	}
	public String getEdu () {
		return this.edu;
	}
	public String getLive () {
		return this.live;
	}
	public String getNativeHouse () {
		return this.nativeHouse;
	}
	public String getNativeTime () {
		return this.nativeTime;
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
	public String getPhone () {
		return this.phone;
	}
	public String getSchool () {
		return this.school;
	}
	public java.util.Date getEduTime () {
		return this.eduTime;
	}
	//getter部分,Date类型的修改获取String的方法
	public String getEduTimeStr () {
		return DateUtils.formatDate(this.eduTime);
	}
	public String getWechat () {
		return this.wechat;
	}
	public String getEmail () {
		return this.email;
	}
	public String getQq () {
		return this.qq;
	}
	public String getPostcode () {
		return this.postcode;
	}
	public String getCompanyName () {
		return this.companyName;
	}
	public String getCompanyType () {
		return this.companyType;
	}
	public String getDept () {
		return this.dept;
	}
	public java.util.Date getInTime () {
		return this.inTime;
	}
	//getter部分,Date类型的修改获取String的方法
	public String getInTimeStr () {
		return DateUtils.formatDate(this.inTime);
	}
	public String getJob () {
		return this.job;
	}
	public String getCompanyPrvn () {
		return this.companyPrvn;
	}
	public String getCompanyCity () {
		return this.companyCity;
	}
	public String getCompanyCtry () {
		return this.companyCtry;
	}
	public String getCompanyAddr () {
		return this.companyAddr;
	}
	public String getCompanyTel () {
		return this.companyTel;
	}
	public double getYearIncome () {
		return this.yearIncome;
	}
	public double getMonthIncome () {
		return this.monthIncome;
	}
	public double getMonthOther () {
		return this.monthOther;
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
	public String getExt1 () {
		return this.ext1;
	}
	public String getExt2 () {
		return this.ext2;
	}

	//setter部分
	public void setId (int id) {
		this.id=id;
	}
	public void setLoanId (String loanId) {
		this.loanId=loanId;
	}
	public void setMainFlag (String mainFlag) {
		this.mainFlag=mainFlag;
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
	public void setValidDate (java.util.Date validDate) {
		this.validDate=validDate;
	}
	public void setValidDateStr (String validDateStr) {
		this.validDateStr=validDateStr;
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
	public void setHasChil (String hasChil) {
		this.hasChil=hasChil;
	}
	public void setChilNum (String chilNum) {
		this.chilNum=chilNum;
	}
	public void setEdu (String edu) {
		this.edu=edu;
	}
	public void setLive (String live) {
		this.live=live;
	}
	public void setNativeHouse (String nativeHouse) {
		this.nativeHouse=nativeHouse;
	}
	public void setNativeTime (String nativeTime) {
		this.nativeTime=nativeTime;
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
	public void setPhone (String phone) {
		this.phone=phone;
	}
	public void setSchool (String school) {
		this.school=school;
	}
	public void setEduTime (java.util.Date eduTime) {
		this.eduTime=eduTime;
	}
	public void setEduTimeStr (String eduTimeStr) {
		this.eduTimeStr=eduTimeStr;
	}
	public void setWechat (String wechat) {
		this.wechat=wechat;
	}
	public void setEmail (String email) {
		this.email=email;
	}
	public void setQq (String qq) {
		this.qq=qq;
	}
	public void setPostcode (String postcode) {
		this.postcode=postcode;
	}
	public void setCompanyName (String companyName) {
		this.companyName=companyName;
	}
	public void setCompanyType (String companyType) {
		this.companyType=companyType;
	}
	public void setDept (String dept) {
		this.dept=dept;
	}
	public void setInTime (java.util.Date inTime) {
		this.inTime=inTime;
	}
	public void setInTimeStr (String inTimeStr) {
		this.inTimeStr=inTimeStr;
	}
	public void setJob (String job) {
		this.job=job;
	}
	public void setCompanyPrvn (String companyPrvn) {
		this.companyPrvn=companyPrvn;
	}
	public void setCompanyCity (String companyCity) {
		this.companyCity=companyCity;
	}
	public void setCompanyCtry (String companyCtry) {
		this.companyCtry=companyCtry;
	}
	public void setCompanyAddr (String companyAddr) {
		this.companyAddr=companyAddr;
	}
	public void setCompanyTel (String companyTel) {
		this.companyTel=companyTel;
	}
	public void setYearIncome (double yearIncome) {
		this.yearIncome=yearIncome;
	}
	public void setMonthIncome (double monthIncome) {
		this.monthIncome=monthIncome;
	}
	public void setMonthOther (double monthOther) {
		this.monthOther=monthOther;
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
	public void setExt1 (String ext1) {
		this.ext1=ext1;
	}
	public void setExt2 (String ext2) {
		this.ext2=ext2;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public int getOrgPeriod() {
		return orgPeriod;
	}
	public void setOrgPeriod(int orgPeriod) {
		this.orgPeriod = orgPeriod;
	}
	public double getOrgSalesAmt() {
		return orgSalesAmt;
	}
	public void setOrgSalesAmt(double orgSalesAmt) {
		this.orgSalesAmt = orgSalesAmt;
	}
}


package com.tera.renhang.model;

import com.tera.util.DateUtils;

/**
 * 
 * 信用贷款人行报告实体类
 * <b>功能：</b>RhReportDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2015-01-14 16:05:04<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public class RhReport {

	//属性部分
	private int id; //ID
	private String appId; //申请ID
	private String flag = "1"; //有无标记 0-白户  1-非白户   默认为非白户
	private String reportNo; //报告编号
	private java.sql.Timestamp requestTime; //请求时间
	private String requestTimeStr; //请求时间
	private java.sql.Timestamp reportTime; //报告时间
	private String reportTimeStr; //报告时间
	private String name; //姓名
	private String idType; //证件类型
	private String idNo; //证件号码
	private String queryName; //查询操作员
	private String queryReason; //查询原因
	private String gender; //性别
	private java.util.Date birthday; //生日
	private String birthdayStr; //生日
	private String marriage; //婚姻状况
	private String mobile; //手机号
	private String comPhone; //单位电话
	private String homePhone; //住宅电话
	private String education; //学历
	private String degree; //学位
	private String address; //通讯地址
	private String hkAddress; //户籍地址
	private String mateName; //配偶姓名
	private String mateIdType; //配偶证件类型
	private String mateIdNo; //配偶证件号码
	private String mateCom; //配偶工作单位
	private String matePhone; //配偶联系电话
	private String remarks; //备注
	private String state; //状态
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
	public String getFlag () {
		return this.flag;
	}
	public String getReportNo () {
		return this.reportNo;
	}
	public java.sql.Timestamp getRequestTime () {
		return this.requestTime;
	}
	//getter部分,Timestamp类型的修改获取String的方法
	public String getRequestTimeStr () {
		return DateUtils.formatTime(this.requestTime);
	}
	public java.sql.Timestamp getReportTime () {
		return this.reportTime;
	}
	//getter部分,Timestamp类型的修改获取String的方法
	public String getReportTimeStr () {
		return DateUtils.formatTime(this.reportTime);
	}
	public String getName () {
		return this.name;
	}
	public String getIdType () {
		return this.idType;
	}
	public String getIdNo () {
		return this.idNo;
	}
	public String getQueryName () {
		return this.queryName;
	}
	public String getQueryReason () {
		return this.queryReason;
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
	public String getMarriage () {
		return this.marriage;
	}
	public String getMobile () {
		return this.mobile;
	}
	public String getComPhone () {
		return this.comPhone;
	}
	public String getHomePhone () {
		return this.homePhone;
	}
	public String getEducation () {
		return this.education;
	}
	public String getDegree () {
		return this.degree;
	}
	public String getAddress () {
		return this.address;
	}
	public String getHkAddress () {
		return this.hkAddress;
	}
	public String getMateName () {
		return this.mateName;
	}
	public String getMateIdType () {
		return this.mateIdType;
	}
	public String getMateIdNo () {
		return this.mateIdNo;
	}
	public String getMateCom () {
		return this.mateCom;
	}
	public String getMatePhone () {
		return this.matePhone;
	}
	public String getRemarks () {
		return this.remarks;
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

	//setter部分
	public void setId (int id) {
		this.id=id;
	}
	public void setAppId (String appId) {
		this.appId=appId;
	}
	public void setFlag (String flag) {
		this.flag=flag;
	}
	public void setReportNo (String reportNo) {
		this.reportNo=reportNo;
	}
	public void setRequestTime (java.sql.Timestamp requestTime) {
		this.requestTime=requestTime;
	}
	public void setRequestTimeStr (String requestTimeStr) {
		this.requestTimeStr=requestTimeStr;
	}
	public void setReportTime (java.sql.Timestamp reportTime) {
		this.reportTime=reportTime;
	}
	public void setReportTimeStr (String reportTimeStr) {
		this.reportTimeStr=reportTimeStr;
	}
	public void setName (String name) {
		this.name=name;
	}
	public void setIdType (String idType) {
		this.idType=idType;
	}
	public void setIdNo (String idNo) {
		this.idNo=idNo;
	}
	public void setQueryName (String queryName) {
		this.queryName=queryName;
	}
	public void setQueryReason (String queryReason) {
		this.queryReason=queryReason;
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
	public void setMarriage (String marriage) {
		this.marriage=marriage;
	}
	public void setMobile (String mobile) {
		this.mobile=mobile;
	}
	public void setComPhone (String comPhone) {
		this.comPhone=comPhone;
	}
	public void setHomePhone (String homePhone) {
		this.homePhone=homePhone;
	}
	public void setEducation (String education) {
		this.education=education;
	}
	public void setDegree (String degree) {
		this.degree=degree;
	}
	public void setAddress (String address) {
		this.address=address;
	}
	public void setHkAddress (String hkAddress) {
		this.hkAddress=hkAddress;
	}
	public void setMateName (String mateName) {
		this.mateName=mateName;
	}
	public void setMateIdType (String mateIdType) {
		this.mateIdType=mateIdType;
	}
	public void setMateIdNo (String mateIdNo) {
		this.mateIdNo=mateIdNo;
	}
	public void setMateCom (String mateCom) {
		this.mateCom=mateCom;
	}
	public void setMatePhone (String matePhone) {
		this.matePhone=matePhone;
	}
	public void setRemarks (String remarks) {
		this.remarks=remarks;
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

}


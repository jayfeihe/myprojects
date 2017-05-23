package com.tera.sales.model;

import com.tera.util.DateUtils;

/**
 * 
 * <br>
 * <b>功能：</b>SalesDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-08-19 16:13:28<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public class Sales {

	//属性部分
	private int id; //ID
	private String staffNo; //员工卡号
	private String name; //姓名
	private String gender; //性别
	private String idType; //证件类型
	private String idNo; //证件号码
	private String hukou; //户口性质
	private String orgId; //所属机构
	private String orgName; //所属机构名称
	private String level; //岗位级别
	private java.util.Date entryDate; //入职时间
	private String entryDateStr; //入职时间
	private java.util.Date permanentDate; //拟转正时间
	private String permanentDateStr; //拟转正时间
	private java.util.Date leaveDate; //离职时间
	private String leaveDateStr; //离职时间
	private java.util.Date adjustDate; //调岗时间
	private String adjustDateStr; //调岗时间
	private int contractTerm; //劳动合同期限
	private String mobile; //电话
	private String email; //邮箱
	private String marriage; //婚姻
	private String birthFlag; //婚育
	private String education; //学历
	private String degreeCertifyNo; //学位证
	private String university; //毕业院校
	private String major; //所学专业
	private String idAddress; //身份证地址
	private String curAddress; //现住址
	private String contact; //紧急联系人
	private String contactPhone; //紧急联系人电话
	private String wageCardNo; //工资卡卡号
	private String wageCardBank; //工资卡开户行
	private String socialSecurity; //社保缴纳情况
	private String fullFlag; //转正标志
	private String state; //状态
	private String remark; //备注
	private String operator; //操作员
	private java.sql.Timestamp createTime; //创建日期
	private String createTimeStr; //创建日期
	private java.sql.Timestamp updateTime; //修改日期
	private String updateTimeStr; //修改日期
	private int departId; //组织ID
	private String departName; // 销售团队 

	//getter部分
	public int getId () {
		return this.id;
	}
	public String getStaffNo () {
		return this.staffNo;
	}
	public String getName () {
		return this.name;
	}
	public String getGender () {
		return this.gender;
	}
	public String getIdType () {
		return this.idType;
	}
	public String getIdNo () {
		return this.idNo;
	}
	public String getHukou () {
		return this.hukou;
	}
	public String getOrgId () {
		return this.orgId;
	}
	
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	
	public String getLevel () {
		return this.level;
	}
	public java.util.Date getEntryDate () {
		return this.entryDate;
	}
	//getter部分,Date类型的修改获取String的方法
	public String getEntryDateStr () {
		return DateUtils.formatDate(this.entryDate);
	}
	public java.util.Date getPermanentDate () {
		return this.permanentDate;
	}
	//getter部分,Date类型的修改获取String的方法
	public String getPermanentDateStr () {
		return DateUtils.formatDate(this.permanentDate);
	}
	public java.util.Date getLeaveDate () {
		return this.leaveDate;
	}
	//getter部分,Date类型的修改获取String的方法
	public String getLeaveDateStr () {
		return DateUtils.formatDate(this.leaveDate);
	}
	public java.util.Date getAdjustDate () {
		return this.adjustDate;
	}
	//getter部分,Date类型的修改获取String的方法
	public String getAdjustDateStr () {
		return DateUtils.formatDate(this.adjustDate);
	}
	public int getContractTerm () {
		return this.contractTerm;
	}
	public String getMobile () {
		return this.mobile;
	}
	public String getEmail () {
		return this.email;
	}
	public String getMarriage () {
		return this.marriage;
	}
	public String getBirthFlag () {
		return this.birthFlag;
	}
	public String getEducation () {
		return this.education;
	}
	public String getDegreeCertifyNo () {
		return this.degreeCertifyNo;
	}
	public String getUniversity () {
		return this.university;
	}
	public String getMajor () {
		return this.major;
	}
	public String getIdAddress () {
		return this.idAddress;
	}
	public String getCurAddress () {
		return this.curAddress;
	}
	public String getContact () {
		return this.contact;
	}
	public String getContactPhone () {
		return this.contactPhone;
	}
	public String getWageCardNo () {
		return this.wageCardNo;
	}
	public String getWageCardBank () {
		return this.wageCardBank;
	}
	public String getSocialSecurity () {
		return this.socialSecurity;
	}
	public String getFullFlag () {
		return this.fullFlag;
	}
	public String getState () {
		return this.state;
	}
	public String getRemark () {
		return this.remark;
	}
	public String getOperator () {
		return this.operator;
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
	public void setStaffNo (String staffNo) {
		this.staffNo=staffNo;
	}
	public void setName (String name) {
		this.name=name;
	}
	public void setGender (String gender) {
		this.gender=gender;
	}
	public void setIdType (String idType) {
		this.idType=idType;
	}
	public void setIdNo (String idNo) {
		this.idNo=idNo;
	}
	public void setHukou (String hukou) {
		this.hukou=hukou;
	}
	public void setOrgId (String orgId) {
		this.orgId=orgId;
	}
	public void setLevel (String level) {
		this.level=level;
	}
	public void setEntryDate (java.util.Date entryDate) {
		this.entryDate=entryDate;
	}
	public void setEntryDateStr (String entryDateStr) {
		this.entryDateStr=entryDateStr;
	}
	public void setPermanentDate (java.util.Date permanentDate) {
		this.permanentDate=permanentDate;
	}
	public void setPermanentDateStr (String permanentDateStr) {
		this.permanentDateStr=permanentDateStr;
	}
	public void setLeaveDate (java.util.Date leaveDate) {
		this.leaveDate=leaveDate;
	}
	public void setLeaveDateStr (String leaveDateStr) {
		this.leaveDateStr=leaveDateStr;
	}
	public void setAdjustDate (java.util.Date adjustDate) {
		this.adjustDate=adjustDate;
	}
	public void setAdjustDateStr (String adjustDateStr) {
		this.adjustDateStr=adjustDateStr;
	}
	public void setContractTerm (int contractTerm) {
		this.contractTerm=contractTerm;
	}
	public void setMobile (String mobile) {
		this.mobile=mobile;
	}
	public void setEmail (String email) {
		this.email=email;
	}
	public void setMarriage (String marriage) {
		this.marriage=marriage;
	}
	public void setBirthFlag (String birthFlag) {
		this.birthFlag=birthFlag;
	}
	public void setEducation (String education) {
		this.education=education;
	}
	public void setDegreeCertifyNo (String degreeCertifyNo) {
		this.degreeCertifyNo=degreeCertifyNo;
	}
	public void setUniversity (String university) {
		this.university=university;
	}
	public void setMajor (String major) {
		this.major=major;
	}
	public void setIdAddress (String idAddress) {
		this.idAddress=idAddress;
	}
	public void setCurAddress (String curAddress) {
		this.curAddress=curAddress;
	}
	public void setContact (String contact) {
		this.contact=contact;
	}
	public void setContactPhone (String contactPhone) {
		this.contactPhone=contactPhone;
	}
	public void setWageCardNo (String wageCardNo) {
		this.wageCardNo=wageCardNo;
	}
	public void setWageCardBank (String wageCardBank) {
		this.wageCardBank=wageCardBank;
	}
	public void setSocialSecurity (String socialSecurity) {
		this.socialSecurity=socialSecurity;
	}
	public void setFullFlag (String fullFlag) {
		this.fullFlag=fullFlag;
	}
	public void setState (String state) {
		this.state=state;
	}
	public void setRemark (String remark) {
		this.remark=remark;
	}
	public void setOperator (String operator) {
		this.operator=operator;
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
	public int getDepartId() {
		return departId;
	}
	public void setDepartId(int departId) {
		this.departId = departId;
	}
	public String getDepartName() {
		return departName;
	}
	public void setDepartName(String departName) {
		this.departName = departName;
	}
}


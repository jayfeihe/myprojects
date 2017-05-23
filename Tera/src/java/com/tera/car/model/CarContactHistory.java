package com.tera.car.model;

import com.tera.util.DateUtils;

/**
 * 
 * 信用借款申请联系人表实体类
 * <b>功能：</b>CarContactHistory<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-09-04 11:22:44<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public class CarContactHistory {

	//属性部分
	private int id; //ID
	private String appId; //申请ID
	private String type; //类型
	private String name; //名字
	private String relation; //关系
	private int age; //年龄
	private String phone; //固话
	private String mobile; //手机
	private String idType; //证件类型
	private String idNo; //证件号码
	private String comName; //单位名称
	private String addProvince; //居住地址-省
	private String addCity; //居住地址-市
	private String addCounty; //居住地址-区县
	private String address; //居住地址
	private String state; //状态
	private String operator; //操作员
	private String orgId; //所属机构
	private java.sql.Timestamp createTime; //创建日期
	private String createTimeStr; //创建日期
	private java.sql.Timestamp updateTime; //修改日期
	private String updateTimeStr; //修改日期
	private String comProvince; //单位地址-省
	private String comCity; //单位地址-市
	private String comCounty; //单位地址-区县
	private String comAddress; //单位地址
	private String comDuty; //单位担任职务
	private java.util.Date comAddDate; //入职时间
	private String comAddDateStr; //入职时间
	private double income; //年收入
	private Integer cooperationYear; //合作年限
	private String cooperationType; //合作方式
	private String email; //邮箱
	private String contractNo;//合同号 add by wangyongliang 20150615
	private String remark; //备注 
	private String phoneSummary;//摘要 
	//getter部分
	public int getId () {
		return this.id;
	}
	public String getAppId () {
		return this.appId;
	}
	public String getType () {
		return this.type;
	}
	public String getName () {
		return this.name;
	}
	public String getRelation () {
		return this.relation;
	}
	public int getAge () {
		return this.age;
	}
	public String getPhone () {
		return this.phone;
	}
	public String getMobile () {
		return this.mobile;
	}
	public String getIdType () {
		return this.idType;
	}
	public String getIdNo () {
		return this.idNo;
	}
	public String getComName () {
		return this.comName;
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
	public String getComProvince () {
		return this.comProvince;
	}
	public String getComCity () {
		return this.comCity;
	}
	public String getComCounty () {
		return this.comCounty;
	}
	public String getComAddress () {
		return this.comAddress;
	}
	public String getComDuty () {
		return this.comDuty;
	}
	public java.util.Date getComAddDate () {
		return this.comAddDate;
	}
	//getter部分,Date类型的修改获取String的方法
	public String getComAddDateStr () {
		return DateUtils.formatDate(this.comAddDate);
	}
	public double getIncome () {
		return this.income;
	}
	public Integer getCooperationYear () {
		return this.cooperationYear;
	}
	public String getCooperationType () {
		return this.cooperationType;
	}
	public String getEmail () {
		return this.email;
	}

	//setter部分
	public void setId (int id) {
		this.id=id;
	}
	public void setAppId (String appId) {
		this.appId=appId;
	}
	public void setType (String type) {
		this.type=type;
	}
	public void setName (String name) {
		this.name=name;
	}
	public void setRelation (String relation) {
		this.relation=relation;
	}
	public void setAge (int age) {
		this.age=age;
	}
	public void setPhone (String phone) {
		this.phone=phone;
	}
	public void setMobile (String mobile) {
		this.mobile=mobile;
	}
	public void setIdType (String idType) {
		this.idType=idType;
	}
	public void setIdNo (String idNo) {
		this.idNo=idNo;
	}
	public void setComName (String comName) {
		this.comName=comName;
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
	public void setComProvince (String comProvince) {
		this.comProvince=comProvince;
	}
	public void setComCity (String comCity) {
		this.comCity=comCity;
	}
	public void setComCounty (String comCounty) {
		this.comCounty=comCounty;
	}
	public void setComAddress (String comAddress) {
		this.comAddress=comAddress;
	}
	public void setComDuty (String comDuty) {
		this.comDuty=comDuty;
	}
	public void setComAddDate (java.util.Date comAddDate) {
		this.comAddDate=comAddDate;
	}
	public void setComAddDateStr (String comAddDateStr) {
		this.comAddDateStr=comAddDateStr;
	}
	public void setIncome (double income) {
		this.income=income;
	}
	public void setCooperationYear (Integer cooperationYear) {
		this.cooperationYear=cooperationYear;
	}
	public void setCooperationType (String cooperationType) {
		this.cooperationType=cooperationType;
	}
	public void setEmail (String email) {
		this.email=email;
	}
	public String getContractNo() {
		return contractNo;
	}
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getPhoneSummary() {
		return phoneSummary;
	}
	public void setPhoneSummary(String phoneSummary) {
		this.phoneSummary = phoneSummary;
	}

}

